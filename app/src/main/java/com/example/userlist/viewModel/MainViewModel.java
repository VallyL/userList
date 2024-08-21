package com.example.userlist.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.userlist.model.User;
import com.example.userlist.repository.UserRepository;

import java.util.List;

public class MainViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<Boolean> navigateToList = new MutableLiveData<>();
    private final MutableLiveData<List<User>> users = new MutableLiveData<>();

    public MainViewModel() {
        userRepository = new UserRepository();
        // Optionally, you can load initial data here, if needed
        // loadMoreUsers(1); // Load the first page of users
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public LiveData<Boolean> getNavigateToList() {
        return navigateToList;
    }

    public void navigateToList() {
        navigateToList.setValue(true);
    }

    public void loadMoreUsers(int page) {
        userRepository.getUsersByPage(page).observeForever(usersList -> {
            List<User> currentUsers = users.getValue();
            if (currentUsers != null) {
                currentUsers.addAll(usersList);
                users.postValue(currentUsers);
            } else {
                users.postValue(usersList);
            }
        });
    }
}