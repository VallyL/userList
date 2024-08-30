package com.example.userlist.data;


import com.example.userlist.model.UserResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApi {
    @GET("users")
    Call<UserResponse> getUsers(@Query("page") int page);
}