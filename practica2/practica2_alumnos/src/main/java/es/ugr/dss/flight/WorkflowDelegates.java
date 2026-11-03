package es.ugr.dss.flight;
import java.math.BigDecimal;


import org.flowable.engine.delegate.DelegateExecution;


import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;


//Delegado para obtener info de un empleado (authorized y travelClass)
@Component("checkEmployee") 
class CheckEmployee implements JavaDelegate {
	
 private final HttpPartners partners; 
 CheckEmployee(HttpPartners partners){this.partners=partners;}
 
 @Override public void execute(DelegateExecution e){
	 
  try {
	  
	  var info=partners.employee((String)e.getVariable("employeeId"));
	  e.setVariable("authorized",info.authorized());
	  e.setVariable("travelClass",info.travelClass());
  }
  catch(Exception ex){
	  e.setVariable("authorized",false);
	  e.setVariable("travelClass","NONE");
  }
 }
}


//Delegado encargado del rechazo de un empleado
@Component("rejectEmployee") 
class RejectEmployee implements JavaDelegate {
 @Override public void 
 execute(DelegateExecution e){
	 e.setVariable("status","REJECTED");
	 e.setVariable("detail","Employee not authorized or unknown");
 }
}


//Delegado encargado de construir la solicitud de Iberia
@Component("quoteIberia") 
class QuoteIberia implements JavaDelegate {
 
	private final HttpPartners partners;
 
	QuoteIberia(HttpPartners partners){
		this.partners=partners;
	}
 
 
	@Override public void execute(DelegateExecution e){
  
		var r = new TravelRequest((String)e.getVariable("employeeId"),(String)e.getVariable("destination"),(String)e.getVariable("departureDate"),(String)e.getVariable("returnDate"));

		try {
			var q = partners.quote("IBERIA",r,(String)e.getVariable("travelClass"));
			e.setVariable("iberiaAvailable", q.available());
			e.setVariable("iberiaPrice", q.available()?q.price():null);
			e.setVariable("iberiaFailed", false);
		}
		catch(Exception ex){
			e.setVariable("iberiaAvailable", false);
			e.setVariable("iberiaPrice", null);
			e.setVariable("iberiaFailed", true);
		}
 }
}

//Delegado encargado de construir la solicitud de Vueling
@Component("quoteVueling") 
class QuoteVueling implements JavaDelegate {
	 
	private final HttpPartners partners;
 
	QuoteVueling(HttpPartners partners){
		this.partners=partners;
	}
 
 
	@Override public void execute(DelegateExecution e){
  
		var r = new TravelRequest((String)e.getVariable("employeeId"),(String)e.getVariable("destination"),(String)e.getVariable("departureDate"),(String)e.getVariable("returnDate"));

		try {
			var q = partners.quote("VUELING",r,(String)e.getVariable("travelClass"));
			e.setVariable("vuelingAvailable", q.available());
			e.setVariable("vuelingPrice", q.available()?q.price():null);
			e.setVariable("vuelingFailed", false);
		}
		catch(Exception ex){
			e.setVariable("vuelingAvailable", false);
			e.setVariable("vuelingPrice", null);
			e.setVariable("vuelingFailed", true);
		}
 }	
}

	
//Delegado encargado de la decisión (aceptar oferta de iberia o de vueling)
@Component("chooseOffer") 
class ChooseOffer implements JavaDelegate {
	
	 @Override public void execute(DelegateExecution e){
		 
		  BigDecimal ib=valid(e,"iberia");
		  BigDecimal vu=valid(e,"vueling");
		  
		  if(ib==null && vu==null){
			   boolean fallo=Boolean.TRUE.equals(e.getVariable("iberiaFailed"))||Boolean.TRUE.equals(e.getVariable("vuelingFailed"));
			   e.setVariable("status","NO_OFFER");
			   e.setVariable("selectedAirline",null);
			   e.setVariable("totalPrice",null);
			   e.setVariable("detail",fallo?"No offers: airline query failed":"No offers: no availability");
			   return;
		  }
		  
		  boolean iberia = vu==null || (ib!=null && ib.compareTo(vu)<=0); // empate -> IBERIA
		  e.setVariable("status","QUOTED");
		  e.setVariable("selectedAirline",iberia?"IBERIA":"VUELING");
		  e.setVariable("totalPrice",iberia?ib:vu);
		  e.setVariable("detail","Cheapest valid offer selected");
	 }
	 
	 
	 private BigDecimal valid(DelegateExecution e,String p){
		 
		  boolean ok=Boolean.TRUE.equals(e.getVariable(p+"Available"));
		  Object price=e.getVariable(p+"Price");
		  return ok && price!=null ? (BigDecimal)price : null;
	 }
}
 

