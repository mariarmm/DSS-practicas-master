package es.ugr.dss.dssshop;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;

import es.ugr.dss.dssshop.api.ApiClient;
import es.ugr.dss.dssshop.model.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView txtProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtProducts = findViewById(R.id.txtProducts);

        loadProducts();
    }

    private void loadProducts() {

        ApiClient.getProductApi()
                .getProducts()
                .enqueue(new Callback<List<Product>>() {

                    @Override
                    public void onResponse(
                            Call<List<Product>> call,
                            Response<List<Product>> response) {

                        if (!response.isSuccessful()) {
                            txtProducts.setText(
                                    "HTTP error: " + response.code());
                            return;
                        }

                        List<Product> products = response.body();

                        if (products == null || products.isEmpty()) {
                            txtProducts.setText("No products found");
                            return;
                        }

                        StringBuilder result = new StringBuilder();
                        result.append("PRODUCT CATALOG\n\n");

                        for (Product p : products) {
                            result.append(p.getName())
                                    .append("\n")
                                    .append(String.format(
                                            Locale.getDefault(),
                                            "%.2f EUR\n\n",
                                            p.getPrice()));
                        }

                        txtProducts.setText(result.toString());
                    }

                    @Override
                    public void onFailure(
                            Call<List<Product>> call,
                            Throwable t) {

                        txtProducts.setText(
                                "Connection error: " + t.getMessage());
                    }
                });
    }
}