package com.example.at3_33;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ConnectivityManager connectivityManager;
    ImageView img;
    Button btn;
     int progressStatus = 0;
     Handler handler = new Handler();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.b1);
        img = findViewById(R.id.img);
        progressBar=findViewById(R.id.pg);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dothis();
            }
        });
    }

    private void dothis()
    {
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        String imagepath = "https://cdn.pixabay.com/photo/2015/12/01/20/28/fall-1072821_1280.jpg";
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(this, "WI FI", Toast.LENGTH_SHORT).show();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                Toast.makeText(this, "Mobile", Toast.LENGTH_SHORT).show();
            }
            Glide.with(this)
                    .load("https://cdn.pixabay.com/photo/2015/12/01/20/28/fall-1072821_1280.jpg")
                    .override(200, 200).into(img);
        }
    }

    }


