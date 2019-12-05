package com.infy.network;

import com.infy.common.IAppConstants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class ApiClient {

    private static final String API_BASE_URL = IAppConstants.domain;


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    static Retrofit getClient() {
        return retrofit;
    }

}
