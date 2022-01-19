package com.example.newsfeed.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.Activities.Utils;
import com.example.newsfeed.Models.ArticleModel;
import com.example.newsfeed.R;
import com.example.newsfeed.Activities.WebActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.viewHolder> {

    Context context;
    List<ArticleModel> newsList;
    SimpleDateFormat simpleDateFormat;
    Calendar calendar;

    public NewsAdapter(Context context, List<ArticleModel> list) {
        this.context = context;
        this.newsList = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ArticleModel dataModel = newsList.get(position);

        holder.channelName.setText(dataModel.getSource().getName());
//        holder.time.setText(Utils.DateFormat(dataModel.getPublishedAt()));
        holder.time.setText(TimeFormatting(dataModel.getPublishedAt()));
        holder.headline.setText(dataModel.getTitle());
        holder.author.setText(dataModel.getAuthor());
        holder.description.setText(dataModel.getDescription());
        Picasso.get().load(dataModel.getUrlToImage()).placeholder(R.drawable.placeholder).into(holder.newsImg);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", dataModel.getUrl());
                intent.putExtra("source", dataModel.getSource().getName());
                intent.putExtra("content", dataModel.getTitle());
                context.startActivity(intent);
            }
        });

        holder.headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", dataModel.getUrl());
                intent.putExtra("source", dataModel.getSource().getName());
                intent.putExtra("content", dataModel.getTitle());
                context.startActivity(intent);
            }
        });

        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", dataModel.getUrl());
                intent.putExtra("source", dataModel.getSource().getName());
                intent.putExtra("content", dataModel.getTitle());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView newsImg;
        TextView channelName, time, headline, description, author;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            newsImg = itemView.findViewById(R.id.newsImg);
            channelName = itemView.findViewById(R.id.channel_name);
            time = itemView.findViewById(R.id.time);
            headline = itemView.findViewById(R.id.headline);
            description = itemView.findViewById(R.id.description);
            author = itemView.findViewById(R.id.author);
        }
    }

    public String TimeFormatting(String Time) {
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm aa, dd MMM yyyy");
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(Time);
            /** The time provided in SimpleDateFormat() above, is the time format provided by the API, and the
             * time format stored in 'simpleDateFormat' variable is the format we specified acc. to our need. */

        } catch (ParseException e) {
            Log.d("TimeError", e.getLocalizedMessage());
        }
        String DateTime = simpleDateFormat.format(date);

        return DateTime;
    }

}
