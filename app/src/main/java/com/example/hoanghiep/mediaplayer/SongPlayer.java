package com.example.hoanghiep.mediaplayer;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Hoang Hiep on 3/3/2017.
 */

public class SongPlayer extends Service implements IPlayer {
    public static final int STATE_IDLE = 1;
    public static final int STATE_PLAYING = 2;
    public static final int STATE_PAUSE = 3;
    private static final int MEDIA_NOTIFICATION_ID = 100;
    private static final String TAG = "SongPlayer";

    private int state;
    private MediaPlayer mediaPlayer;

    private Context context;

    public SongPlayer(Context context) {
        state = STATE_IDLE;
        this.context = context;
    }



    @Override
    public void startSong(Uri uri) {
        if (state == STATE_IDLE) {
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(context, uri);
                mediaPlayer.prepare();

                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        state = STATE_PLAYING;
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        state = STATE_IDLE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void pauseSong() {
        if (state == STATE_PLAYING) {
            mediaPlayer.pause();
            state = STATE_PAUSE;
        }

    }

    @Override
    public void resumeSong() {
        if (state == STATE_PAUSE) {
            mediaPlayer.start();
            state = STATE_PLAYING;
        }

    }

    @Override
    public void stopSong() {
        if (state != STATE_IDLE) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            state = STATE_IDLE;
        }

    }

    public int getState() {
        return state;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    private Bitmap getCoverPictureSong(Uri uri) {
//        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//        byte[] rawArt;
//        Bitmap art;
//        BitmapFactory.Options bfo = new BitmapFactory.Options();
//
//        mmr.setDataSource(getApplicationContext(), uri);
//        rawArt = mmr.getEmbeddedPicture();
//
//        if (null != rawArt) {
//            art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);
//            return art;
//        }
//        return null;
//    }

}
