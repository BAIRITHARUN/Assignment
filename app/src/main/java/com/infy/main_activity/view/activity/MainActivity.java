package com.infy.main_activity.view.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.infy.R;
import com.infy.main_activity.presenter.interfaces.IMainView;
import com.infy.main_activity.view.fragments.MainFragFragment;


public class MainActivity extends AppCompatActivity implements IMainView {


    FrameLayout mFLMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFLMain = findViewById(R.id.mFLMain);
        replaceFragment();
    }

//    @Override
//    public void setActionBarTitle(String title) {
//        getSupportActionBar().setTitle(title);
//    }

    @Override
    public void replaceFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.mFLMain, new MainFragFragment())
                .addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mFLMain);
        if (fragment instanceof MainFragFragment){
            finish();
        }else {
            super.onBackPressed();
        }
    }
}
