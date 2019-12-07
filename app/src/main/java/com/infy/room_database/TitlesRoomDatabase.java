package com.infy.room_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = RoomEntity.class, exportSchema = false, version = 1)
public abstract class TitlesRoomDatabase extends RoomDatabase {

    public abstract TitlesDao titlesDao();

    private static final String DB_NAME = "Titles.db";
    private static TitlesRoomDatabase instance;
//    private static final int NUMBER_OF_THREADS = 10;
//    static final ExecutorService databaseWriteExecutor =
//            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static TitlesRoomDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), TitlesRoomDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
