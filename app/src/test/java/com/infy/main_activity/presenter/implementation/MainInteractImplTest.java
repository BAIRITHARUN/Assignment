package com.infy.main_activity.presenter.implementation;

import com.infy.main_activity.model.Row;
import com.infy.network.APIUtils;
import com.infy.room_database.RoomEntity;

import org.junit.Test;

import java.util.ArrayList;

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
        ArrayList<RoomEntity> titlesArrayList = new ArrayList<>();
        for (int i=0; i<3; i++){
            RoomEntity titles = new RoomEntity("Test",
                    "Test","http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg");
            titlesArrayList.add(titles);
        }
        assertEquals(3, titlesArrayList.size());
    }

    @Test
    public void prepareList() {
        ArrayList<Row> titlesModelArrayList = new ArrayList<>();
        for (int i = 0; i<5; i++){
            Row row = new Row();
            row.setTitle("Test");
            row.setDescription("Test");
            row.setImageHref("http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg");
            titlesModelArrayList.add(row);
        }
        assertEquals(5, titlesModelArrayList.size());
    }
}