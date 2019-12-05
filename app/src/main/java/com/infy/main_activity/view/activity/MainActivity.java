package com.infy.main_activity.view.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.infy.R;
import com.infy.common.Utility;
import com.infy.main_activity.model.TitilesModel;
import com.infy.main_activity.presenter.implementation.MainInterpreterImpl;
import com.infy.main_activity.presenter.interfaces.IMainInterpreter;
import com.infy.main_activity.presenter.interfaces.IMainView;
import com.infy.main_activity.view.adapter.TitlesAdapter;

import java.util.ArrayList;

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
    public void setList(ArrayList<TitilesModel.Row> rowArrayList){
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
}
