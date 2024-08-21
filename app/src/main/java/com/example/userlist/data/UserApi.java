package com.example.userlist.data;


import com.example.userlist.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApi {
    @GET("api/users")
    Call<List<User>> getUsers(@Query("page") int page);
}