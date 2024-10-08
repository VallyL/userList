package com.example.userlist.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.userlist.model.User;
import androidx.lifecycle.LiveData;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE FROM users WHERE id = :id")
    void deleteUser(int id);
}