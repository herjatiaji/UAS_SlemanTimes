package com.awikwok.uas_slemantimes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyNewsAdapter extends RecyclerView.Adapter<MyNewsAdapter.ViewHolder> {
    private ArrayList<News> mList;
    private Activity activity;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String username;

    public MyNewsAdapter(ArrayList<News> mList, Activity activity, String username) {
        this.mList = mList;
        this.activity = activity;
        this.username = username;
    }

    @NonNull
    @Override
    public MyNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_mynews, parent, false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNewsAdapter.ViewHolder holder, int position) {
        final News news = mList.get(position);
        holder.newsTitle.setText(news.getTitle());
        holder.newsDate.setText(news.getReleasedDate());

        if (mList.get(position).getWriter().equals(username)) {
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDatabase.child("News").child(mDatabase.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(activity, "Data has been removed", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(activity, "Data Faileed to removed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setMessage("Are you sure want to delete " + news.getTitle() + "?");
                    builder.show();
                }
            });
        }else {
            holder.btnDelete.setVisibility(View.INVISIBLE);
        }
        if (mList.get(position).getWriter().equals(username)){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = Intent(v.getContext(),);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle, newsDate;
        CardView cvMynews;
        Button btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.tvTitlemynews);
            newsDate = itemView.findViewById(R.id.tvDatemynews);
            cvMynews = itemView.findViewById(R.id.cardview_mynews);
            btnDelete = itemView.findViewById(R.id.button_delete_news);
        }
    }
}
