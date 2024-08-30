package com.example.userlist.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.userlist.data.UserApi;
import com.example.userlist.data.RetrofitClient;
import com.example.userlist.model.User;
import com.example.userlist.model.UserResponse;
import com.example.userlist.utils.Resource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final UserApi userApi;

    public UserRepository() {
        userApi = RetrofitClient.getRetrofitInstance().create(UserApi.class);
    }

    public LiveData<Resource<List<User>>> getUsersByPage(int page) {
        MutableLiveData<Resource<List<User>>> data = new MutableLiveData<>();
        data.setValue(Resource.loading(null));

        userApi.getUsers(page).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(Resource.success(response.body().getData()));
                } else {
                    data.setValue(Resource.error("HTTP Error: " + response.code(), null));
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            data.setValue(Resource.error("Network Error: " + t.getMessage(), null));
            }
        });
        return data;
    }
}
