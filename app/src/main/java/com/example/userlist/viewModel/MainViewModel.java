package com.example.userlist.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.userlist.model.User;
import com.example.userlist.repository.UserRepository;
import com.example.userlist.state.UserListState;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<Boolean> navigateToList = new MutableLiveData<>();
    private final MutableLiveData<UserListState> userListState = new MutableLiveData<>(new UserListState());

    public MainViewModel() {
        userRepository = new UserRepository();
        // Optionally, load initial data here
        // loadMoreUsers(1);
    }

    public LiveData<Boolean> getNavigateToList() {
        return navigateToList;
    }

    public LiveData<UserListState> getUserListState() {
        return userListState;
    }

    public void navigateToList() {
        navigateToList.setValue(true);
    }

    public void resetNavigateToList() {
        navigateToList.setValue(false);
    }

    public void loadMoreUsers(int page) {
        // Update loading state
        UserListState currentState = userListState.getValue();
        if (currentState != null) {
            currentState.setLoading(true);
            userListState.setValue(currentState);

            userRepository.getUsersByPage(page).observeForever(usersList -> {
                currentState.setLoading(false);
                if (usersList != null) {
                    List<User> currentUsers = currentState.getUsers();
                    if (currentUsers == null) {
                        currentUsers = new ArrayList<>();
                    }
                    currentUsers.addAll(usersList);
                    currentState.setUsers(currentUsers);
                    currentState.setErrorMessage(null);
                } else {
                    currentState.setErrorMessage("Failed to load users.");
                }
                userListState.postValue(currentState);
            });
        }
    }
}
