package com.example.newsfeed.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.newsfeed.R;

public class WebActivity extends AppCompatActivity {

    Toolbar toolbar;
    WebView webView;
    ImageView share , backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        toolbar = findViewById(R.id.Toolbar);
        webView = findViewById(R.id.webView);
        share = findViewById(R.id.share);
        backArrow = findViewById(R.id.backArrow);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        StatusBarColor();

        // Dark Mode
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final boolean isDarkEnabled = sharedPreferences.getBoolean("isDarkEnabled", false);

        if (isDarkEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        Intent intent = getIntent();
        String Url = intent.getStringExtra("url");
        String Source = intent.getStringExtra("source");
        String msgContent = intent.getStringExtra("content") + "\n" + Url + "\n" + "Shared from NewsFeed App\n";

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, Source);  // Subject/Title of the shared news.
                    intent.putExtra(Intent.EXTRA_TEXT, msgContent); // Content about shared news(Here, Title and URL).
                    startActivity(intent.createChooser(intent, "Share With : "));   // It shows the bottom sheet containing apps to which we share the news.
                }
                catch (Exception e) {
                    Toast.makeText(WebActivity.this, "Sorry, It Cannot be shared", Toast.LENGTH_SHORT).show();
                }
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // It helps in viewing the multimedia inside webView.

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

//        webView.setWebViewClient(new Callback());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(Url);
    }

    private void NavigationColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationColor, null));
        }
    }

    private void StatusBarColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar, this.getTheme()));
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar, null));
        }
    }

}