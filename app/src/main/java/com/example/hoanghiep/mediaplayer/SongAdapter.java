package com.example.hoanghiep.mediaplayer;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.util.ArrayList;

/**
 * Created by Hoang Hiep on 3/15/2017.
 */

public class SongAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<Song> songList;
    private OnClickItemListener listener;

    public SongAdapter(ArrayList<Song> songs, Context context) {
        mContext = context;
        songList = songs;
    }
    public void setOnItemClickListener(OnClickItemListener listener){
        this.listener=listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemSong = View.inflate(mContext, R.layout.item_song, null);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(5,5,5,5);
        itemSong.setLayoutParams(params);
        return new SongHolder(itemSong);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final SongHolder songHolder = (SongHolder) holder;
        songHolder.tvName.setText(songList.get(position).getTitle());
        //songHolder.tvTime.setText(String.valueOf(songList.get(position).getDuration()));
        songHolder.tvAuth.setText(songList.get(position).getAuthor());
        songHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
        songHolder.imageView.setImageBitmap(getAlbumart(songList.get(position).getData()));

    }
    public Song getItem(int position) {
        return songList.get(position);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }
    private class SongHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvAuth;
        private ImageView imageView;

        public SongHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvAuth = (TextView) itemView.findViewById(R.id.tv_auth);
            imageView=(ImageView)itemView.findViewById(R.id.bt_play_song);
        }
    }
    public interface OnClickItemListener {
        void onItemClick(int position);
    }
    public Bitmap getAlbumart(Uri uri) {
//        Bitmap bm = null;
//        try
//        {
//            final Uri sArtworkUri = Uri
//                    .parse("content://media/external/audio/albumart");
//
//            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
//
//            ParcelFileDescriptor pfd = mContext.getContentResolver()
//                    .openFileDescriptor(uri, "r");
//
//            if (pfd != null)
//            {
//                FileDescriptor fd = pfd.getFileDescriptor();
//                bm = BitmapFactory.decodeFileDescriptor(fd);
//            }
//        } catch (Exception e) {
//        }
//        return bm;
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        byte[] rawArt;
        Bitmap art=null;
        BitmapFactory.Options bfo=new BitmapFactory.Options();

        mmr.setDataSource(mContext, uri);
        rawArt = mmr.getEmbeddedPicture();

// if rawArt is null then no cover art is embedded in the file or is not
// recognized as such.
        if (null != rawArt) {
            art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);
        }
        return art;
    }

}
