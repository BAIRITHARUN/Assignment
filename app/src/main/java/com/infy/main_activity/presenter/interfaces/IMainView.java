package com.infy.main_activity.presenter.interfaces;

import com.infy.main_activity.model.Row;
import com.infy.main_activity.model.TitilesModel;
import com.infy.room_database.RoomEntity;

import java.util.ArrayList;
import java.util.List;

public interface IMainView {
    boolean checkIntentConnection();

    void setActionBarTitle(String title);

    void setList(ArrayList<Row> rowArrayList);

    void showLoading();

    void hideRefreshing();

    void showToast(String message);

    void getTitlesFromLocal();

    void setList(List<RoomEntity> roomEntityList);

    void clearLocalDb(List<RoomEntity> roomEntityList);
}
