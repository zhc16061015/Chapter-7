package com.bytedance.videoplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {

    public static String TAG ="mediaplayer";
    private Uri mSelectedVideo;
    public SurfaceView surfaceView;
    public Button sBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sBtn = findViewById(R.id.sBtn);
        surfaceView = findViewById(R.id.media);
        sBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseVideo();
            }
        });

    }
    public void startVideo(){
        IjkMediaPlayer mediaPlayer = new IjkMediaPlayer();
        mediaPlayer.native_setLogLevel(mediaPlayer.IJK_LOG_DEBUG);

        mediaPlayer.setOption(mediaPlayer.OPT_CATEGORY_PLAYER,"mediacodec",1);
//        try {
//            IjkMediaPlayer.loadLibrariesOnce(null);
//            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
//        } catch (Exception e) {
//            this.finish();
//        }


        Toast.makeText(this, mSelectedVideo+"", Toast.LENGTH_SHORT).show();
        try{
            mediaPlayer.setDataSource(mSelectedVideo.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        mediaPlayer.setDisplay(surfaceView.getHolder());
        //mediaPlayer.prepareAsync();

        mediaPlayer.start();
    }
    public void chooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select video"),2);
        System.out.println("搞完video");
        startVideo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");

        if (resultCode == RESULT_OK && null != data) {

            if (requestCode == 2) {
                mSelectedVideo = data.getData();
                Log.d(TAG, "selectedVideo = " + mSelectedVideo);
                //sBtn.setVisibility(View.GONE);
            }
        }
    }
}
