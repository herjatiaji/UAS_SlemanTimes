package com.awikwok.uas_slemantimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    ImageView profile;
    CardView allNews;
    News mNews;
    DatabaseReference mDatabaseReference;
    TextView tvTitle,tvContent,tvSeeMore;
    String Key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profile = findViewById(R.id.imageViewProf);
        allNews = findViewById(R.id.cvNews);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("News");
        tvTitle = findViewById(R.id.textView8);
        tvContent = findViewById(R.id.content_tv);
        tvSeeMore = findViewById(R.id.tvseemore);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        readLatestData();
        tvSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewsListActivity.class);
                startActivity(intent);
            }
        });
        allNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewsListActivity.class);
                intent.putExtra("title",tvTitle.getText().toString());
                intent.putExtra("content",tvContent.getText().toString());
                startActivity(intent);
            }
        });

    }
    private void readLatestData() {
        mNews = new News();
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    for (DataSnapshot currentData : snapshot.getChildren()){
                        Key = currentData.getKey();
                        mNews.setTitle(currentData.child("title").getValue().toString());
                        mNews.setContent(currentData.child("content").getValue().toString());
                    }
                }

                tvTitle.setText(mNews.getTitle());
                tvContent.setText(mNews.getContent());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }
