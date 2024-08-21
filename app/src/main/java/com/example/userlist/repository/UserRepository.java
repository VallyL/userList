package com.example.userlist.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.userlist.data.UserApi;
import com.example.userlist.data.RetrofitClient;
import com.example.userlist.model.User;
import com.example.userlist.model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final UserApi userApi;

    public UserRepository() {
        userApi = RetrofitClient.getRetrofitInstance().create(UserApi.class);
    }

    public LiveData<List<User>> getUsersByPage(int page) {
        MutableLiveData<List<User>> data = new MutableLiveData<>();
        userApi.getUsers(page).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getData()); // Extract the list of users from UserResponse
                } else {
                    data.setValue(null); // Handle HTTP error response
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null); // Handle network failure
            }
        });
        return data;
    }
}
