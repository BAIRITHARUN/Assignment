package com.infy.main_activity.presenter.implementation;

import android.os.Build;

import com.google.gson.Gson;
import com.infy.main_activity.model.Row;
import com.infy.main_activity.model.TitilesModel;
import com.infy.network.APIUtils;
import com.infy.main_activity.presenter.interfaces.IMainInteracter;
import com.infy.main_activity.presenter.interfaces.IMainView;
import com.infy.room_database.RoomEntity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*class will perform business logic of MainActivity*/
public class MainInteracterImpl implements IMainInteracter {

    IMainView view;

    /*constructor with IMainView Interface
    * for access methods in which implemets IMainView*/
    public MainInteracterImpl(IMainView view) {
        this.view = view;
    }

    /* Method will calls API if internet connection Available
    * else it will calls getTitlesFromLocal() in MainActivity
    * it will return Records from Titles*/
    @Override
    public void getTitles(){
        if (view.checkIntentConnection()) {
            Call<ResponseBody> call = APIUtils.getAPI("s/2iodh4vg0eortkl/facts.json");
            view.showLoading();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        assert response.body() != null;
                        TitilesModel titilesModel = new Gson().fromJson(response.body().string(), TitilesModel.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            view.setActionBarTitle(titilesModel.getTitle());
                        } else {
                            view.setActionBarTitle("Assignment");
                        }
                        if (titilesModel.getRows().size() != 0) {
                            view.setList(titilesModel.getRows());
                            prepareLocalDbList(titilesModel.getRows());
                        }
                        view.hideRefreshing();
                    } catch (Exception e) {
                        e.printStackTrace();
                        view.hideRefreshing();
                        view.showToast(e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    view.hideRefreshing();
                    view.showToast(t.getMessage());
                }
            });
        } else {
            view.showToast("Please check your Internet Connection");
            view.hideRefreshing();
            view.getTitlesFromLocal();
        }
    }

    /* method will prepare list for Titles Table */
    @Override
    public void prepareLocalDbList(ArrayList<Row> titlesModelArrayList){
        ArrayList<RoomEntity> titlesArrayList = new ArrayList<>();
        for (int i=0; i<titlesModelArrayList.size(); i++){
            RoomEntity titles = new RoomEntity(titlesModelArrayList.get(i).getTitle(),
                    titlesModelArrayList.get(i).getDescription(),titlesModelArrayList.get(i).getImageHref());
            titlesArrayList.add(titles);
        }
        view.clearLocalDb(titlesArrayList);
    }

    /*method will prepare list for recyclerview from  list return by Room Database*/
    @Override
    public void prepareList(List<RoomEntity> roomEntityList){
        ArrayList<Row> titlesModelArrayList = new ArrayList<>();
        for (int i = 0; i<roomEntityList.size(); i++){
            Row row = new Row();
            row.setTitle(roomEntityList.get(i).getTitle());
            row.setDescription(roomEntityList.get(i).getDescription());
            row.setImageHref(roomEntityList.get(i).getImageUrl());
            titlesModelArrayList.add(row);
        }
        view.setList(titlesModelArrayList);
    }
}
