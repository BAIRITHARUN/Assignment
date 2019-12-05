package com.infy.main_activity.presenter.implementation;

import android.os.Build;

import com.google.gson.Gson;
import com.infy.main_activity.model.TitilesModel;
import com.infy.network.APIUtils;
import com.infy.main_activity.presenter.interfaces.IMainInterpreter;
import com.infy.main_activity.presenter.interfaces.IMainView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainInterpreterImpl implements IMainInterpreter {

    IMainView view;

    public MainInterpreterImpl(IMainView view) {
        this.view = view;
    }

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
        }
    }

}