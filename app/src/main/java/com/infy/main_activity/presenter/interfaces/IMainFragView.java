package com.infy.main_activity.presenter.interfaces;

import com.infy.main_activity.model.Row;
import com.infy.main_activity.model.TitilesModel;
import com.infy.room_database.RoomEntity;

import java.util.ArrayList;
import java.util.List;

public interface IMainFragView {
    boolean checkIntentConnection();

    void setActionBarTitle(String title);

    void setList(ArrayList<Row> rowArrayList);

    void showLoading();

    void hideRefreshing();

    void showToast(String message) throws Throwable;

    void getTitlesFromLocal() throws Throwable;

    void setList(List<RoomEntity> roomEntityList) throws Throwable;

    void clearLocalDb(List<RoomEntity> roomEntityList) throws Throwable;
}
