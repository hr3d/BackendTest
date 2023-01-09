package lesson5.api;

import lesson5.dto.CategoryResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryController {

    @GET("categories/{id}")
    Call<CategoryResponse> getCategory(@Path("id") int id);

}
