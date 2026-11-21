package es.ugr.dss.dssshop.api;

import java.util.List;
import es.ugr.dss.dssshop.model.Product;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductApi {
    @GET("api/products")
    Call<List<Product>> getProducts();
}