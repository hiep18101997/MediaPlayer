package com.example.hoanghiep.mediaplayer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Hoang Hiep on 3/12/2017.
 */

public class MainActivity extends Activity implements SongAdapter.OnClickItemListener, View.OnClickListener {
    public static final String KEY_START = "start";

    private RecyclerView rvSong;

    private ArrayList<Song> songs;

    private SongAdapter songAdapter;

    private int positionSong = -1;

    private ImageView btPause, btPlay, btStop;
    private IPlayer songPlayer;
    private int flag = -1;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        rvSong = (RecyclerView) findViewById(R.id.rv_song);
        rvSong.setLayoutManager(new LinearLayoutManager(this));
        rvSong.setHasFixedSize(true);

        songs = new SongManager().getAllSong(this);

        songAdapter = new SongAdapter(songs, this);
        songAdapter.setOnItemClickListener(this);

        rvSong.setAdapter(songAdapter);

        songPlayer = new SongPlayer(this);
        btPause = (ImageView) findViewById(R.id.bt_pause);
        btPlay = (ImageView) findViewById(R.id.bt_play);
        btStop = (ImageView) findViewById(R.id.bt_stop);
        btPause.setOnClickListener(this);
        btPlay.setOnClickListener(this);
        btStop.setOnClickListener(this);

        linearLayout=(LinearLayout)findViewById(R.id.ln_bar);
    }

    @Override
    public void onItemClick(int position) {
        positionSong = position;
        flag = position;
        linearLayout.setVisibility(View.VISIBLE);
        Song song = songAdapter.getItem(position);
        int state = ((SongPlayer) songPlayer).getState();
        if (state != SongPlayer.STATE_IDLE) {
            songPlayer.stopSong();
        }
        songPlayer.startSong(song.getData());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_play:
                if (((SongPlayer) songPlayer).getState() == SongPlayer.STATE_PAUSE) {
                    songPlayer.resumeSong();
                    Log.i("MainActivity", "play");
                }
                break;
            case R.id.bt_stop:
                if (((SongPlayer) songPlayer).getState() == SongPlayer.STATE_PLAYING || ((SongPlayer) songPlayer).getState() == SongPlayer.STATE_PAUSE) {
                    songPlayer.stopSong();
                    linearLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.bt_pause:
                if (((SongPlayer) songPlayer).getState() == SongPlayer.STATE_PLAYING) {
                    songPlayer.pauseSong();
                }
                break;
            default:
                break;
        }

    }
}

