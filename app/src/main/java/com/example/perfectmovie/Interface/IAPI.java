package com.example.perfectmovie.Interface;

import com.example.perfectmovie.Model.ReleasedFilms;
import com.example.perfectmovie.Model.Staff;
import com.example.perfectmovie.Model.TopFilms;
import com.example.perfectmovie.Model.Videos;

import java.time.Month;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAPI {

    @Headers({
            "X-API-KEY: a344bc93-9250-46cd-9d6e-d7a0f8ef7af4",
            "Accept: application/json",
    })
    @GET("v2.2/films/collections")
    Call<TopFilms> getTop250Films(@Query("type") String type, @Query("page") int page);

    @Headers({
            "X-API-KEY: a344bc93-9250-46cd-9d6e-d7a0f8ef7af4",
            "Accept: application/json",
    })
    @GET("v2.1/films/releases")
    Call<ReleasedFilms> getReleases(@Query("year") int year, @Query("month") String month, @Query("page") int page);

    @Headers({
            "X-API-KEY: a4dab797-b199-407a-94f0-6a1b9999347f",
            "Accept: application/json"
    })
    @GET("v1/staff")
    Call<ArrayList<Staff>> getStaff(@Query("filmId") int filmId);

    @Headers({
            "X-API-KEY: a4dab797-b199-407a-94f0-6a1b9999347f",
            "Accept: application/json",
    })
    @GET("v2.2/films/{filmId}/videos")
    Call<Videos> getVideos(@Path("filmId") int filmId);

}
