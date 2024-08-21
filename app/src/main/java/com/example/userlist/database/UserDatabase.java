package com.example.userlist.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.userlist.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
