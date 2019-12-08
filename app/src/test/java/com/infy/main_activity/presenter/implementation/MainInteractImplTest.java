package com.infy.main_activity.presenter.implementation;

import com.infy.network.APIUtils;

import org.junit.Test;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;

public class MainInteractImplTest {

    @Test
    public void getTitles() {
        Call<ResponseBody> call = APIUtils.getAPI("s/2iodh4vg0eortkl/facts.json");
        try{
            Response<ResponseBody> response = call.execute();
            assertTrue(response.isSuccessful());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void prepareLocalDbList() {
    }

    @Test
    public void prepareList() {
    }
}