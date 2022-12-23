    package com.awikwok.uas_slemantimes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

    public class AddNewsActivity extends AppCompatActivity {
        EditText etTitle, etContent, etDate, etCategory,etWriter;
        Button addData;
        DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        etTitle = findViewById(R.id.et_add_title);
        etCategory = findViewById(R.id.et_add_category);
        etContent = findViewById(R.id.et_add_content);
        etWriter = findViewById(R.id.et_add_writer);
        etDate = findViewById(R.id.et_add_rd);
        addData = findViewById(R.id.btn_tambah);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(News.class.getSimpleName());
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }
        private void insertData() {
            News news = new News();
            String title = etTitle.getText().toString();
            String content = etContent.getText().toString();
            String writer = etWriter.getText().toString();
            String category = etCategory.getText().toString();
            String date = etDate.getText().toString();
            if(title!="" && content != "" && writer!="" && category!="" && date!=""){
                news.setTitle(title);
                news.setContent(content);
                news.setCategory(category);
                news.setReleasedDate(date);
                news.setWriter(writer);
                mDatabaseReference.push().setValue(news);
                Toast.makeText(this,"Successfully insert data",Toast.LENGTH_SHORT).show();
            }
        }
}