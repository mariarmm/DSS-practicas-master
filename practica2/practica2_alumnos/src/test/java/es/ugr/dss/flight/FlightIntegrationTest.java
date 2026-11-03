package es.ugr.dss.flight;
import org.junit.jupiter.api.Test;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
class FlightIntegrationTest {
	
 @Autowired TestRestTemplate http;
 
 private ResponseEntity<TravelResult> book(String emp,String dest,String start,String end){
  return http.postForEntity("http://localhost:8080/api/bookings",new TravelRequest(emp,dest,start,end),TravelResult.class);
 }

 //Ampliad FlightIntegrationTest con aerolínea y precio exactos, 
 	// fallo de Vueling y fechas inválidas. 
 //Añadid una prueba unitaria de empate y de ausencia de ofertas
 
 @Test void authorizedGetsQuote(){
	 var r = book("EMP001","AMS","2026-11-10","2026-11-14");
	 assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
	 assertThat(r.getBody().status()).isEqualTo("QUOTED");
	 assertThat(r.getBody().totalPrice()).isNotNull();
	 
	 //Añado:
	 assertThat(r.getBody().selectedAirline()).isEqualTo("VUELING");
	 assertThat(r.getBody().totalPrice()).isEqualByComparingTo("275");
 }
 
 @Test void unauthorizedIsRejected(){
	 var r=book("EMP999","AMS","2026-11-10","2026-11-14");
	 assertThat(r.getBody().status()).isEqualTo("REJECTED");
 }
 
 @Test void invalidDatesRejected(){
	 var r=book("EMP001","AMS","2026-11-14","2026-11-10");
	 assertThat(r.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
 }
 
 @Test void noOffers(){
	 var r=book("EMP001","NONE","2026-11-10","2026-11-14");
	 assertThat(r.getBody().status()).isEqualTo("NO_OFFER");
	 assertThat(r.getBody().selectedAirline()).isNull();
	 assertThat(r.getBody().totalPrice()).isNull();	 
 }
 
 //MIS TESTS: 
 
 //fallo de Vueling (FAIL): IBERIA, 170;
 //HTTP 200; QUOTED; IBERIA; 170 EUR
 @Test void failVueling(){
	 
	 var r = book("EMP001","FAIL","2026-11-10","2026-11-14");
	 assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
	 assertThat(r.getBody().status()).isEqualTo("QUOTED");
	 assertThat(r.getBody().totalPrice()).isNotNull();
	 assertThat(r.getBody().selectedAirline()).isEqualTo("IBERIA");
	 assertThat(r.getBody().totalPrice()).isEqualByComparingTo("170");
 }
 
 //EMP002 (business): VUELING, 550;
 @Test void offerVueling(){
	 
	 var r = book("EMP002","AMS","2026-11-10","2026-11-14");
	 assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
	 assertThat(r.getBody().status()).isEqualTo("QUOTED");
	 assertThat(r.getBody().totalPrice()).isNotNull();
	 assertThat(r.getBody().selectedAirline()).isEqualTo("VUELING");
	 assertThat(r.getBody().totalPrice()).isEqualByComparingTo("550");
 }
 
 
 //fechas invalidas (formato incorrecto): 400
@Test void invalidDateFormat(){
   var r = book("EMP001","AMS","11-11-2026","11-12-2026");
   assertThat(r.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
}
 
}
