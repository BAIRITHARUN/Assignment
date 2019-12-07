package com.infy.main_activity.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.infy.R;
import com.infy.common.Utility;
import com.infy.main_activity.model.Row;
import com.infy.main_activity.presenter.implementation.MainInterpreterImpl;
import com.infy.main_activity.presenter.interfaces.IMainInterpreter;
import com.infy.main_activity.presenter.interfaces.IMainView;
import com.infy.main_activity.view.adapter.TitlesAdapter;
import com.infy.room_database.RoomEntity;
import com.infy.room_database.TitlesRoomDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainView {

    RecyclerView mRcvTitlesList;
    SwipeRefreshLayout mSwipeRefreshLayout;

    IMainInterpreter interpreter;
    TitlesAdapter titlesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
        mRcvTitlesList = findViewById(R.id.mRcvTitlesList);
        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout);
        interpreter = new MainInterpreterImpl(this);
        interpreter.getTitles();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                interpreter.getTitles();
            }
        });
    }

    @Override
    public boolean checkIntentConnection(){
        return Utility.checkIntenetConnection(this);
    }

    @Override
    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void setList(final ArrayList<Row> rowArrayList){
        titlesAdapter = new TitlesAdapter(this, rowArrayList, new TitlesAdapter.IOnRowClickListener() {
            @Override
            public void onRowClick(int position) {

            }
        });
        mRcvTitlesList.setLayoutManager(new LinearLayoutManager(this));
        mRcvTitlesList.setItemAnimator(new DefaultItemAnimator());
        mRcvTitlesList.setAdapter(titlesAdapter);
    }

    @Override
    public List<RoomEntity> getLocalValues(){
        List<RoomEntity> rowArrayList = new ArrayList<>();
        getTasks();
        return rowArrayList;
    }

    @Override
    public void storeInLocal(RoomEntity roomEntity){
        TitlesRoomDatabase roomDatabase = TitlesRoomDatabase.getInstance(this);
        roomDatabase.titlesDao().insert(roomEntity);
    }

    @Override
    public void storeInLocal(List<RoomEntity> roomEntityList) {

    }

    @Override
    public void deleteTable(){
        TitlesRoomDatabase roomDatabase = TitlesRoomDatabase.getInstance(this);
        roomDatabase.titlesDao().deleteTitle();
    }

    @Override
    public void showLoading(){
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshing(){
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<RoomEntity>> {

            @Override
            protected List<RoomEntity> doInBackground(Void... voids) {
                List<RoomEntity> taskList = TitlesRoomDatabase
                        .getInstance(getApplicationContext()).titlesDao().getTitlesList();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<RoomEntity> tasks) {
                super.onPostExecute(tasks);
                interpreter.prepareList(tasks);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    @Override
    public void setList(final List<RoomEntity> roomEntityList){

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                //adding to database
                TitlesRoomDatabase.getInstance(getApplicationContext()).titlesDao().
                        insertListOfUsers(roomEntityList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

    @Override
    public void clearLocalDb(final List<RoomEntity> roomEntityList){
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                TitlesRoomDatabase.getInstance(getApplicationContext()).titlesDao().deleteTitle();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setList(roomEntityList);
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();
    }

}
