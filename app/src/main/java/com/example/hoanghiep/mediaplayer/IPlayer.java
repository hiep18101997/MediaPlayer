package com.example.hoanghiep.mediaplayer;

import android.net.Uri;

/**
 * Created by Hoang Hiep on 3/3/2017.
 */

public interface IPlayer {
    void startSong(Uri uri);

    void pauseSong();

    void resumeSong();

    void stopSong();

}
