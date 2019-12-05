package com.infy.main_activity.presenter.interfaces;

import com.infy.main_activity.model.TitilesModel;

import java.util.ArrayList;

public interface IMainView {
    boolean checkIntentConnection();

    void setActionBarTitle(String title);

    void setList(ArrayList<TitilesModel.Row> rowArrayList);

    void showLoading();

    void hideRefreshing();

    void showToast(String message);
}
