package com.awikwok.uas_slemantimes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    TextView tvUsername;
    CardView cvAddNews,cvBookmark;
    SharedPrefManager sharedPrefManager;
    Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btnLogout = findViewById(R.id.buttonLogout);
        cvAddNews = findViewById(R.id.cvAddNews);
        cvBookmark = findViewById(R.id.cvBookmark);
        sharedPrefManager = new SharedPrefManager(this);

        cvAddNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,AddNewsActivity.class);
                startActivity(intent);
            }
        });

        cvBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,BookmarkActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity activity = new LoginActivity();
                activity.logout();
                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intent);
                activity.finish();
                System.exit(0);

            }

        });




    }
}