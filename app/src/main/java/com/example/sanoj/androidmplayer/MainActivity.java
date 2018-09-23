package com.example.sanoj.androidmplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;





public class MainActivity extends Activity {

    public ArrayList<HashMap<String, String>> songlist = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        ArrayList<HashMap<String, String>> songlistdata = new ArrayList<HashMap<String, String>>();

        SongsManager manager = new SongsManager();
        this.songlist = manager.getPlayList();


        for(int i = 0; i < songlist.size(); i++){
            HashMap<String, String> songs = songlist.get(i);
            songlistdata.add(songs);
        }



        ListAdapter theAdapter = new SimpleAdapter(this, songlistdata,
                R.layout.playlist_item, new String[] {"songTitle", "songArtist"}, new int[] {
                R.id.songtitle, R.id.songartist });

        ListView list = (ListView)findViewById(R.id.theListView);

        list.setAdapter(theAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                int songIndex = position;

                Intent in = new Intent(getApplicationContext(),
                        player.class);
                in.putExtra("songIndex", songIndex);
                setResult(100, in);
                finish();





            }
        });






    }





}