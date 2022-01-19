package com.example.newsfeed.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsfeed.API.ApiController;
import com.example.newsfeed.API.ApiInterface;
import com.example.newsfeed.Activities.MainActivity;
import com.example.newsfeed.Adapters.NewsAdapter;
import com.example.newsfeed.Models.ArticleModel;
import com.example.newsfeed.Models.MainNews;
import com.example.newsfeed.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout refreshLayout;
    RecyclerView rcvAll;
    Retrofit retrofit;
    ApiInterface apiInterface;
    NewsAdapter adapter;
    List<ArticleModel> list;
    String country = "in";
    String key = "ad6c8df5b4dd47b3a498e4232f4a53b0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        // SwipeRefresh Layout
        refreshLayout = view.findViewById(R.id.refreshRCV);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.accent);

        // Recycler View
        rcvAll = view.findViewById(R.id.rcv_All);
        list = new ArrayList<>();
        adapter = new NewsAdapter(getContext(), list);
        rcvAll.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvAll.setAdapter(adapter);

        // API
        retrofit = ApiController.getApi();
        apiInterface = retrofit.create(ApiInterface.class);

        // Dark Mode
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final boolean isDarkEnabled = sharedPreferences.getBoolean("isDarkEnabled", false);

        if (isDarkEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        onLoadingSwipeRefreshLayout();

        return view;
    }

    private void findNews() {

        refreshLayout.setRefreshing(true);

        apiInterface.getNews(key, 100, country).enqueue(new Callback<MainNews>() {
            @Override
            public void onResponse(Call<MainNews> call, Response<MainNews> response) {
                if (response.isSuccessful()) {

                    if (!list.isEmpty()) {
                        list.clear();
                    }
                    list.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<MainNews> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        findNews();
    }

    /**
     * This method make the refresh layout to run the news loading on a thread. so that when the news is
     * completely loaded from the api, it stops the loading animation.
     * -> So, Instead of findNews() method directly we use this method inside onCreateView() Method.
     */
    public void onLoadingSwipeRefreshLayout() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                findNews();
            }
        });
    }
}