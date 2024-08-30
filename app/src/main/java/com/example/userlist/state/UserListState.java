package com.example.userlist.state;

import com.example.userlist.model.User;
import java.util.List;

public class UserListState {
    private final List<User> users;
    private final boolean isLoading;
    private final String errorMessage;

    public UserListState(List<User> users, boolean isLoading, String errorMessage) {
        this.users = users;
        this.isLoading = isLoading;
        this.errorMessage = errorMessage;
    }

    public List<User> getUsers() {
        return users;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
