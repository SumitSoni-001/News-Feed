package com.example.newsfeed.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class BitcoinFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView rcvBitcoin;
    Retrofit retrofit;
    ApiInterface apiInterface;
    NewsAdapter adapter;
    List<ArticleModel> list;
    String key = "ad6c8df5b4dd47b3a498e4232f4a53b0";
    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bitcoin, container, false);

        rcvBitcoin = view.findViewById(R.id.rcv_bitcoin);
        list = new ArrayList<>();
        retrofit = ApiController.getApi();
        apiInterface = retrofit.create(ApiInterface.class);
        adapter = new NewsAdapter(getContext() , list);
        rcvBitcoin.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvBitcoin.setAdapter(adapter);

        refreshLayout = view.findViewById(R.id.refreshRCV);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.accent);

        onLoadingSwipeRefreshLayout();
        
        return view;
    }

    private void findNews() {
        refreshLayout.setRefreshing(true);

        apiInterface.getCustomNews(key , "bitcoin" , 100 , "en" ).enqueue(new Callback<MainNews>() {
            @Override
            public void onResponse(Call<MainNews> call, Response<MainNews> response) {
                if (response.isSuccessful()){

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
                Toast.makeText(getContext(), "Error: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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