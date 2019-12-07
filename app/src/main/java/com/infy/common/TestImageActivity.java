package com.infy.common;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.infy.R;

public class TestImageActivity extends AppCompatActivity {
    ImageView mIVFullImg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        mIVFullImg = findViewById(R.id.mIVFullImg);
        String url = getIntent().getExtras().getString("Image");
        if (url!=null) {
//            if (url.contains("http://")) {
//                url = url.replace("http://", "https://");
//            }
            Glide.with(this).load(url).into(mIVFullImg);
            Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        }
    }
}
