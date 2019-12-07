package com.infy.room_database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface TitlesDao {

    @Query("Select * from Titles")
    List<RoomEntity> getTitlesList();

    @Query("Delete from Titles")
    void deleteTitle();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(RoomEntity roomEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertListOfUsers(List<RoomEntity> titlesArrayList);
}
