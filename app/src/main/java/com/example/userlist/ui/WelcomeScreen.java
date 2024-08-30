package com.example.userlist.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.userlist.R;
import com.example.userlist.viewModel.MainViewModel;
import com.example.userlist.state.UserListState;

public class WelcomeScreen extends Fragment {
    private MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        TextView welcomeMessage = view.findViewById(R.id.welcome_message);
        welcomeMessage.setText("Hello, Dear Guest! Welcome to your guest list! By clicking on the bottom of the screen, you can open it to view and edit if necessary. Enjoy!");

        Button openListButton = view.findViewById(R.id.open_list_button);
        openListButton.setOnClickListener(v -> {
            viewModel.navigateToList();
        });


        TextView userPreview = view.findViewById(R.id.user_preview);
        viewModel.getUserListState().observe(getViewLifecycleOwner(), userListState -> {
            if (userListState != null && userListState.getUsers() != null && !userListState.getUsers().isEmpty()) {
                userPreview.setText(userListState.getUsers().get(0).getFirstName() + " " + userListState.getUsers().get(0).getLastName());
                userPreview.setOnClickListener(v -> viewModel.navigateToList());
            }
        });

        return view;
    }
}
