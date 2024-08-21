package com.example.userlist.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.userlist.data.UserApi;
import com.example.userlist.data.RetrofitClient;
import com.example.userlist.model.User;

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
        userApi.getUsers(page).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null); // Handle HTTP error response
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                data.setValue(null); // Handle network failure
            }
        });
        return data;
    }
}
