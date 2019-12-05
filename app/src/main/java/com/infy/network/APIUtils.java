package com.infy.network;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class APIUtils {

    public static Call<ResponseBody> getAPI(String strUrl){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return apiService.getAPI(strUrl);
    }

}
