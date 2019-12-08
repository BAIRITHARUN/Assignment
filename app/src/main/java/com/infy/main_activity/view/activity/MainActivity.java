package com.infy.main_activity.view.activity;

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
import com.infy.main_activity.presenter.implementation.MainInteractImpl;
import com.infy.main_activity.presenter.interfaces.IMainInteracter;
import com.infy.main_activity.presenter.interfaces.IMainView;
import com.infy.main_activity.view.adapter.TitlesAdapter;
import com.infy.room_database.RoomEntity;
import com.infy.room_database.TitlesRoomDatabase;

import java.util.ArrayList;
import java.util.List;

/*
* IMainInteractor will Interacts with Business Logic class(MainInteractImpl)
 * Room Database is used to store in Local Database
 * Room Database actions will execute in AsyncTask
 * If we run Room Database operations in Main Thread it leads to ANR Exception
 * for that AsyncTask used for Run in New Threads*/

public class MainActivity extends AppCompatActivity implements IMainView {

    RecyclerView mRcvTitlesList;
    SwipeRefreshLayout mSwipeRefreshLayout;

    IMainInteracter interpreter;
    TitlesAdapter titlesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
        mRcvTitlesList = findViewById(R.id.mRcvTitlesList);
        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout);
        interpreter = new MainInteractImpl(this);
        interpreter.getTitles();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                interpreter.getTitles();
            }
        });
    }

    /*method calls checkInternetConnection() in Utility
    * by calling checkInternetConnection() it will check mobile network or wifi is enabled or not
    * if any of the mobile network and wifi is enabled it will return true otherwise false*/
    @Override
    public boolean checkIntentConnection(){
        return Utility.checkInternetConnection(this);
    }

    /*method for updating action bar title*/
    @Override
    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    /*method for setting list in recycler view*/
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

    /*method for showing circular loading progress*/
    @Override
    public void showLoading(){
        mSwipeRefreshLayout.setRefreshing(true);
    }

    /*method for hiding circular loading progress*/
    @Override
    public void hideRefreshing(){
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /*method for showing toast message*/
    @Override
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /*method executes AsyncTask  to Get list of Titles table from Room Data base
    * */
    @Override
    public void getTitlesFromLocal() {
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

    /*
    * method executes AsyncTask  to insert list into Titles table in Room Data base
    * */
    @Override
    public void setList(final List<RoomEntity> roomEntityList){

        class InsertTitles extends AsyncTask<Void, Void, Void> {

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

        InsertTitles st = new InsertTitles();
        st.execute();
    }

    /*
    * method executes AsyncTask to delete rows in Titles table from Room Data base
     * */
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
