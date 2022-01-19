package com.example.newsfeed.API;

import com.example.newsfeed.Models.MainNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<MainNews> getNews(
            @Query("apiKey") String ApiKey,
            @Query("pageSize") int Size,
            @Query("country") String Country
    );

    @GET("top-headlines")
    Call<MainNews> getCategoryNews(
            @Query("apiKey") String ApiKey,
            @Query("category") String Category,
            @Query("pageSize") int Size,
            @Query("country") String Country
    );

    @GET("everything")
    Call<MainNews> getCustomNews(
            @Query("apiKey") String ApiKey,
            @Query("q") String CustomCategory,
            @Query("pageSize") int Size,
            @Query("language") String Language
    );

}
