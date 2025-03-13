package com.example.perfectmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.perfectmovie.Interface.IAPI;
import com.example.perfectmovie.Model.Film;
import com.example.perfectmovie.Model.Release;
import com.example.perfectmovie.Model.Staff;
import com.example.perfectmovie.Model.Videos;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutFilm extends AppCompatActivity {

    TextView _filmTitle;
    TextView _filmDate;
    TextView _filmRating;
    TextView _filmDetails;
    WebView _webView;
    ImageView _imageView;
    ProgressBar _progressBar;
    IAPI _apiInterface;
    ArrayList<Staff> _staffList;
    Videos _videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_film);
        _progressBar = findViewById(R.id.progressBar2);
        _filmTitle = findViewById(R.id.filmTitle);
        _filmDate = findViewById(R.id.filmDate);
        _filmRating = findViewById(R.id.filmRating);
        _webView = findViewById(R.id.webView);
        _imageView = findViewById(R.id.imageView);
        _filmDetails = findViewById(R.id.filmDetails);
        _progressBar.setVisibility(View.VISIBLE);

        if(getIntent().getStringExtra("Film") != null){
            Film film = new Gson().fromJson(getIntent().getStringExtra("Film"), Film.class);
            if(!film.getNameRu().equals("")) {
                _filmTitle.setText(film.getNameRu());
            }else{ _filmTitle.setText(film.getNameEn());}
            _filmDate.setText(String.format("Date: %s", film.getDate()));
            _filmRating.setText(String.format("Rate: %s", String.valueOf(film.getRating())));
            Picasso.with(this).load(film.getImg()).into(_imageView);

            _apiInterface = RequestBuilder.buildRequest().create(IAPI.class);
            Call<ArrayList<Staff>> getStaff = _apiInterface.getStaff(film.getKinopoiskId());
            Call<Videos> getVideo = _apiInterface.getVideos(film.getKinopoiskId());
            getStaff.enqueue(new Callback<ArrayList<Staff>>() {
                @Override
                public void onResponse(Call<ArrayList<Staff>> call, Response<ArrayList<Staff>> response) {
                    if(response.isSuccessful()){
                        StringBuilder sb = new StringBuilder("Staff: ");
                        _staffList = response.body();
                        int i = 0;
                        for (Staff staffin:
                                _staffList) {
                            ++i;
                            if(i > 5) break;
                            sb.append(staffin.getNameRu() + " | ");
                        }
                        _filmDetails.setText(String.format("%s\n%s", film.getDetails(), sb));
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Staff>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Can't get staff", Toast.LENGTH_SHORT).show();
                }
            });

            getVideo.enqueue(new Callback<Videos>() {
                @Override
                public void onResponse(Call<Videos> call, Response<Videos> response) {
                    if(response.isSuccessful()){
                        _videos = response.body();
                        _webView.getSettings().setJavaScriptEnabled(true);
                        _webView.loadUrl(_videos.getVideoUrl());
                        _webView.setWebViewClient(new WebViewClient() {
                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                view.loadUrl(url);
                                return true;
                            }
                        });
                        _progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Videos> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Can't get video", Toast.LENGTH_SHORT).show();
                }
            });
        }else if(getIntent().getStringExtra("Release") != null) {
            Release film = new Gson().fromJson(getIntent().getStringExtra("Release"), Release.class);
            if(!film.getNameRu().equals("")) {
                _filmTitle.setText(film.getNameRu());
            }else{ _filmTitle.setText(film.getNameEn());}
            _filmDate.setText(String.format("Date: %s", film.getDate()));
            _filmRating.setText(String.format("Rate: %s", String.valueOf(film.getRating())));
            Picasso.with(this).load(film.getImg()).into(_imageView);

            _apiInterface = RequestBuilder.buildRequest().create(IAPI.class);
            Call<ArrayList<Staff>> getStaff = _apiInterface.getStaff(film.getKinopoiskId());
            Call<Videos> getVideo = _apiInterface.getVideos(film.getKinopoiskId());
            getStaff.enqueue(new Callback<ArrayList<Staff>>() {
                @Override
                public void onResponse(Call<ArrayList<Staff>> call, Response<ArrayList<Staff>> response) {
                    if(response.isSuccessful()){
                        StringBuilder sb = new StringBuilder("Staff: ");
                        _staffList = response.body();
                        int i = 0;
                        for (Staff staffin:
                                _staffList) {
                            ++i;
                            if(i > 5) break;
                            sb.append(staffin.getNameRu() + " | ");
                        }
                        _filmDetails.setText(String.format("%s\n%s", film.getDetails(), sb));
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Staff>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Can't get staff", Toast.LENGTH_SHORT).show();
                }
            });

            getVideo.enqueue(new Callback<Videos>() {
                @Override
                public void onResponse(Call<Videos> call, Response<Videos> response) {
                    if(response.isSuccessful()){
                        _videos = response.body();
                        _webView.getSettings().setJavaScriptEnabled(true);
                        _webView.loadUrl(_videos.getVideoUrl());
                        _webView.setWebViewClient(new WebViewClient() {
                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                view.loadUrl(url);
                                return true;
                            }
                        });
                        _progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Videos> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Can't get video", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}