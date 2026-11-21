package es.ugr.dss.dssshop.api;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiClient {
    private static final String BASE_URL =
            "http://192.168.18.125:8080/";
    private static final Retrofit retrofit =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    public static ProductApi getProductApi() {
        return retrofit.create(ProductApi.class);
    }
}