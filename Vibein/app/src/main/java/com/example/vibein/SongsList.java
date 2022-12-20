package com.example.vibein;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SongsList extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView noMusicText;
    ArrayList<AudioModel> songslist = new ArrayList<>();
    SearchView searchView;


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);
        recyclerView = findViewById(R.id.list_of_songs);
        noMusicText = findViewById(R.id.no_songs_text);


        searchView = findViewById(R.id.searchbox);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        if(checkPermission()==false)
        {
            requestPermission();
            return;
        }
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
        while(cursor.moveToNext())
        {
            AudioModel songData = new AudioModel(cursor.getString(1),cursor.getString(0),cursor.getString(2));
            if(new File(songData.getPath()).exists())
            songslist.add(songData);

        }

        if(songslist.size()==0)
        {
            noMusicText.setVisibility(View.VISIBLE);
        }
        else
        {
            //recyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new recyclerAdapter(songslist,getApplicationContext()));
        }
    }
    void filterList(String text) {

        ArrayList<AudioModel> filteredList = new ArrayList<>();
        for (AudioModel item: songslist){
            if(item.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        if(filteredList.size()==0)
        {
            noMusicText.setVisibility(View.VISIBLE);
        }
        else {
            noMusicText.setVisibility(View.GONE);
            recyclerView.setAdapter(new recyclerAdapter(filteredList,getApplicationContext()));
        }
    }
    boolean checkPermission()
    {
        int result = ContextCompat.checkSelfPermission(SongsList.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }

    void requestPermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(SongsList.this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "READ PERMISSION IS REQUIRED, PLEASE ALLOW FROM SETTINGS", Toast.LENGTH_SHORT).show();
        }
        else{
        ActivityCompat.requestPermissions(SongsList.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(recyclerView!=null){
            recyclerView.setAdapter(new recyclerAdapter(songslist,getApplicationContext()));
        }
    }
}