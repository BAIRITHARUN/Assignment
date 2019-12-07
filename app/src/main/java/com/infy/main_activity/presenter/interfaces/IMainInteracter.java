package com.infy.main_activity.presenter.interfaces;

import com.infy.main_activity.model.Row;
import com.infy.room_database.RoomEntity;

import java.util.ArrayList;
import java.util.List;

public interface IMainInteracter {
    void getTitles();

    void prepareLocalDbList(ArrayList<Row> titilesModelArrayList);

    void prepareList(List<RoomEntity> roomEntityList);
}
