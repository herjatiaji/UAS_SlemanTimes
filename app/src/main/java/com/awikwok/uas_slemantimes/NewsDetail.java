package com.awikwok.uas_slemantimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NewsDetail extends AppCompatActivity {
    TextView tvTitle, tvContent, tvWriter, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        tvTitle = findViewById(R.id.textViewTitle);
        tvContent = findViewById(R.id.textViewContent);
        tvWriter = findViewById(R.id.textViewWriter);
        tvDate = findViewById(R.id.textViewDate);
        Intent newI= getIntent();
        String title = newI.getStringExtra("title");
        String content = newI.getStringExtra("content");
        String writer = newI.getStringExtra("writer");
        String date = newI.getStringExtra("date");

        tvTitle.setText(title);
        tvWriter.setText(writer);
        tvContent.setText(content);
        tvDate.setText(date);

    }
}