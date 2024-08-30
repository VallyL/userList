package com.example.userlist;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new WelcomeScreen())
                    .commit();
        }


        viewModel.getNavigateToList().observe(this, navigate -> {
            if (navigate) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new UserListScreen())
                        .commit();
                viewModel.resetNavigateToList();
            }
        });
    }
}
