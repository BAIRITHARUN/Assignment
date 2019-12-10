package com.infy.main_activity.view.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.infy.R;
import com.infy.common.Utility;
import com.infy.main_activity.model.Row;
import com.infy.main_activity.presenter.implementation.MainInteractImpl;
import com.infy.main_activity.presenter.interfaces.IMainFragView;
import com.infy.main_activity.presenter.interfaces.IMainInteracter;
import com.infy.main_activity.view.activity.MainActivity;
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


public class MainFragFragment extends Fragment implements IMainFragView{

    RecyclerView mRcvTitlesList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView mTvNetWorkStatus;

    IMainInteracter interpreter;
    TitlesAdapter titlesAdapter;
    BroadcastReceiver networkReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.inflater_main_fragment, container, false);
        mRcvTitlesList = rootView.findViewById(R.id.mRcvTitlesList);
        mSwipeRefreshLayout = rootView.findViewById(R.id.mSwipeRefreshLayout);
        mTvNetWorkStatus = rootView.findViewById(R.id.mTvNetWorkStatus);
        interpreter = new MainInteractImpl(this);
//        interpreter.getTitles();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkInternetStatus();
            }
        });
        checkInternetStatus();
        return rootView;
    }

    /*method calls checkInternetConnection() in Utility
     * by calling checkInternetConnection() it will check mobile network or wifi is enabled or not
     * if any of the mobile network and wifi is enabled it will return true otherwise false*/
    @Override
    public boolean checkIntentConnection(){
        return Utility.checkInternetConnection(getActivity());
    }

    /*method for updating action bar title*/
    @Override
    public void setActionBarTitle(String title){
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(title);

    }

    /*method for setting list in recycler view*/
    @Override
    public void setList(final ArrayList<Row> rowArrayList){
        titlesAdapter = new TitlesAdapter(getActivity(), rowArrayList, new TitlesAdapter.IOnRowClickListener() {
            @Override
            public void onRowClick(int position) {
            }
        });
        mRcvTitlesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRcvTitlesList.setItemAnimator(new DefaultItemAnimator());
        mRcvTitlesList.setAdapter(titlesAdapter);
        mRcvTitlesList.setHasFixedSize(true);
        mRcvTitlesList.setItemViewCacheSize(20);
        mRcvTitlesList.setDrawingCacheEnabled(true);
        mRcvTitlesList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /*method executes AsyncTask  to Get list of Titles table from Room Data base
     * */
    @Override
    public void getTitlesFromLocal() {
        class GetTasks extends AsyncTask<Void, Void, List<RoomEntity>> {

            @Override
            protected List<RoomEntity> doInBackground(Void... voids) {
                List<RoomEntity> taskList = TitlesRoomDatabase
                        .getInstance(getActivity().getApplicationContext()).titlesDao().getTitlesList();
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
                TitlesRoomDatabase.getInstance(getActivity().getApplicationContext()).titlesDao().
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
                TitlesRoomDatabase.getInstance(getActivity().getApplicationContext()).titlesDao().deleteTitle();
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

    public void checkInternetStatus(){
        if (networkReceiver == null){
            networkReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle extras = intent.getExtras();

                    NetworkInfo info = (NetworkInfo) extras
                            .getParcelable("networkInfo");

                    NetworkInfo.State state = info.getState();
                    if (state == NetworkInfo.State.CONNECTED) {
                        mTvNetWorkStatus.setVisibility(View.GONE);
                        interpreter.getTitles();
                    } else {
                        mTvNetWorkStatus.setVisibility(View.VISIBLE);
                        mTvNetWorkStatus.setText("Your internet Connection is Disabled");
                        getTitlesFromLocal();
                    }
                }
            };
            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver((BroadcastReceiver) networkReceiver, intentFilter);
        } else {
            hideRefreshing();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (networkReceiver != null){
            getActivity().unregisterReceiver(networkReceiver);
        }
    }
}
