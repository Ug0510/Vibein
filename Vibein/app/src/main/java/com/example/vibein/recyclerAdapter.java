package com.example.vibein;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.myViewHolder> {
    private ArrayList<AudioModel> songlist;
    Context context;

    public recyclerAdapter(ArrayList<AudioModel> songlist, Context context) {
        this.songlist = songlist;
        this.context = context;
    }

    @NonNull
    @Override
    public recyclerAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_items,parent,false);
        return new recyclerAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AudioModel songData = songlist.get(position);
        holder.songName.setText(songData.getTitle());

        if(MyMediaPlayer.currentIndex==position){
            holder.songName.setTextColor(Color.parseColor("#FF0000"));
        }
        else{
            holder.songName.setTextColor(Color.parseColor("#000000"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;
                Intent intent = new Intent(context,MusicPlayer.class);
                intent.putExtra("List",songlist);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return songlist.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView songName;
        ImageView imageView;

        public myViewHolder(View itemView)
        {
            super(itemView);
            songName = itemView.findViewById(R.id.music_title);
            imageView = itemView.findViewById(R.id.music_note);
            imageView.setImageResource(R.drawable.musical_note);
        }
    }
}
