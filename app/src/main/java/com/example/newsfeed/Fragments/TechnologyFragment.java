package com.example.newsfeed.Fragments;

import android.os.Bundle;

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

public class TechnologyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout refreshLayout;
    RecyclerView rcvTechnology;
    Retrofit retrofit;
    ApiInterface apiInterface;
    NewsAdapter adapter;
    List<ArticleModel> list;
    String country = "in";
    String Category = "technology";
    //    String key = "78abc95f4780c0288dad5f59e018b821";
    String key = "ad6c8df5b4dd47b3a498e4232f4a53b0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_technology, container, false);
//        View view = inflater.inflate(R.layout.fragment_all, null);

        rcvTechnology = view.findViewById(R.id.rcv_tech);
        list = new ArrayList<>();
        retrofit = ApiController.getApi();
        apiInterface = retrofit.create(ApiInterface.class);
        adapter = new NewsAdapter(getContext(), list);
        rcvTechnology.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvTechnology.setAdapter(adapter);

        // SwipeRefresh Layout
        refreshLayout = view.findViewById(R.id.refreshRCV);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.accent);

//        findNews();
        onLoadingSwipeRefreshLayout();

        return view;
    }

    private void findNews() {
        refreshLayout.setRefreshing(true);

        apiInterface.getCategoryNews(key, Category, 100, country).enqueue(new Callback<MainNews>() {
            @Override
            public void onResponse(Call<MainNews> call, Response<MainNews> response) {
                if (response.isSuccessful()){
                    if (!list.isEmpty()){
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

    public void onLoadingSwipeRefreshLayout() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                findNews();
            }
        });
    }
}