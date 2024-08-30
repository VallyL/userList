package com.example.userlist.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.userlist.model.User;
import com.example.userlist.repository.UserRepository;
import com.example.userlist.state.UserListState;
import com.example.userlist.utils.Resource;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<Boolean> navigateToList = new MutableLiveData<>(false);
    private final MutableLiveData<UserListState> userListState = new MutableLiveData<>(new UserListState(new ArrayList<>(), false, null));

    public MainViewModel() {
        userRepository = new UserRepository();
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

    public void setNavigateToList(boolean navigate) {
        navigateToList.setValue(navigate);
    }

    public void resetNavigateToList() {
        navigateToList.setValue(false);
    }

    public void loadMoreUsers(int page) {
        UserListState currentState = userListState.getValue();
        if (currentState != null) {
            UserListState loadingState = new UserListState(currentState.getUsers(), true, null);
            userListState.setValue(loadingState);

            userRepository.getUsersByPage(page).observeForever(resource -> {
                if (resource != null) {
                    if (resource.status == Resource.Status.SUCCESS) {
                        List<User> newUsers = resource.data;
                        if (newUsers != null) {
                            List<User> updatedUsers = new ArrayList<>();
                            if (currentState.getUsers() != null) {
                                updatedUsers.addAll(currentState.getUsers());
                            }
                            updatedUsers.addAll(newUsers);
                            UserListState successState = new UserListState(updatedUsers, false, null);
                            userListState.postValue(successState);
                        }
                    } else if (resource.status == Resource.Status.ERROR) {
                        UserListState errorState = new UserListState(currentState.getUsers(), false, resource.message);
                        userListState.postValue(errorState);}
                }
            });
        }
    }

}
