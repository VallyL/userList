package com.example.userlist;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.userlist.ui.WelcomeScreen;
import com.example.userlist.ui.UserListScreen;
import com.example.userlist.viewModel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if (savedInstanceState == null) {
            // Only replace fragment if there's no saved instance state
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new WelcomeScreen())
                    .commit();
        }

        // Observe navigation state
        viewModel.getNavigateToList().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean navigate) {
                if (navigate != null && navigate) {
                    // Replace fragment if navigate is true
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new UserListScreen())
                            .commit();

                    // Optionally reset navigation state to prevent multiple triggers
                    viewModel.resetNavigateToList();
                }
            }
        });
    }
}
