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
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);

                        }
                    });
                    try {

                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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
            new MyTextTask().execute(imagepath);
        }
    }

    private class MyTextTask extends AsyncTask<String, Void, Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... strings) {
            return downloadImage(strings[0]);
        }

        private Bitmap downloadImage(String string)
        {
            Bitmap bitmap=null;
            URL url= null;
            try {
                url = new URL(string);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(4000);
                httpURLConnection.setConnectTimeout(4000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                int code = httpURLConnection.getResponseCode();
                if (code == HttpURLConnection.HTTP_OK)
                {
                    InputStream inputStream=httpURLConnection.getInputStream();
                    if(inputStream!=null) {
                        bitmap = BitmapFactory.decodeStream(inputStream);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            if (bitmap != null)
            {                  img.setImageBitmap(bitmap);
                img.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }

        }
    }


