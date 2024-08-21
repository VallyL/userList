package com.example.userlist.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userlist.R;
import com.example.userlist.model.User;
import com.example.userlist.viewModel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserListScreen extends Fragment {
    private MainViewModel viewModel;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private boolean isLoading = false;
    private int currentPage = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        // Initialize RecyclerView and Adapter
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);

        // Observe user data
        viewModel.getUserListState().observe(getViewLifecycleOwner(), state -> {
            if (state != null) {
                if (state.isLoading()) {
                    // Show loading indicator if needed
                } else if (state.getUsers() != null) {
                    adapter.setUsers(state.getUsers());
                } else {
                    Toast.makeText(getContext(), state.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
                isLoading = false; // Reset loading state
            }
        });

        // Pagination handling
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading && layoutManager != null &&
                        layoutManager.findLastVisibleItemPosition() == adapter.getItemCount() - 1) {
                    currentPage++;
                    isLoading = true;
                    viewModel.loadMoreUsers(currentPage);
                }
            }
        });

        // Add new user
        FloatingActionButton fab = view.findViewById(R.id.fab_add_user);
        fab.setOnClickListener(v -> {
            // Logic to add a new user
        });

        return view;
    }
}