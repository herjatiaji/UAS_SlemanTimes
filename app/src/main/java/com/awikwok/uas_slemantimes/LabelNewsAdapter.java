package com.awikwok.uas_slemantimes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LabelNewsAdapter extends RecyclerView.Adapter<LabelNewsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<News>news_list;

    public LabelNewsAdapter(Context context, ArrayList<News> news_list) {
        this.context = context;
        this.news_list = news_list;
    }

    @NonNull
    @Override
    public LabelNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_title,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LabelNewsAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(news_list.get(position).getTitle().toString());
        holder.tvDate.setText(news_list.get(position).getReleasedDate().toString());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context,NewsDetail.class);
                intent.putExtra("title",news_list.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("content",news_list.get(holder.getAdapterPosition()).getContent());
                intent.putExtra("date",news_list.get(holder.getAdapterPosition()).getReleasedDate());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return news_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate;
        CardView cv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            cv = itemView.findViewById(R.id.cardview);
        }
    }
}
