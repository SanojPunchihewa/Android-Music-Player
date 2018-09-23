package com.example.sanoj.androidmplayer;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.support.v4.app.NotificationCompat;

public class player extends
        Activity implements OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    private ImageButton btnPlay;
    //private ImageButton btnForward;
    //private ImageButton btnBackward;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private ImageButton btnPlaylist;
    private ImageButton btnEq;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    private SeekBar songProgressBar;
    private TextView songTitleLabel;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    // Media Player
    private MediaPlayer mp;
    // Handler to update UI timer, progress bar etc,.
    private Handler mHandler = new Handler();
    ;
    private SongsManager songManager;
    private Utilities utils;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    public int currentSongIndex;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private String songTitle = "";
    private TextView txt_songindex;
    private int nowplayingsongIndex;
    private ImageView songAlburmArtView;
    private NotificationManager nm;
    private byte[] AlburmArt;
    //private int currentlength;
    private int SessionID;
    private TextView songsong;
    final int audiosessionId = 1088112;


    MediaMetadataRetriever mediaInfo = new MediaMetadataRetriever();


    private static final int NOTIFY_ID = 1;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);


        // All player buttons
        btnPlay = (ImageButton) findViewById(R.id.play_button);
        //btnForward = (ImageButton) findViewById(R.id.btnForward);
        //btnBackward = (ImageButton) findViewById(R.id.btnBackward);
        btnNext = (ImageButton) findViewById(R.id.next_button);
        btnPrevious = (ImageButton) findViewById(R.id.back_button);
        btnPlaylist = (ImageButton) findViewById(R.id.Playlist_Button);
        btnEq = (ImageButton) findViewById(R.id.eq_Button);
        //btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        //btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        songProgressBar = (SeekBar) findViewById(R.id.seekbar_progress);
        songTitleLabel = (TextView) findViewById(R.id.songid);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
        txt_songindex = (TextView) findViewById(R.id.txt_currentsongindex);
        songAlburmArtView = (ImageView) findViewById(R.id.imageview);
        songsong = (TextView)findViewById(R.id.txt_songsong);


        // By default play first song

        // Mediaplayer
        mp = new MediaPlayer();
        songManager = new SongsManager();
        utils = new Utilities();

        // Listeners
        songProgressBar.setOnSeekBarChangeListener(this); // Important
        mp.setOnCompletionListener(this); // Important

        // Getting all songs list
        songsList = songManager.getPlayList();

        if (savedInstanceState != null) {

            int lastplayedsongIndexsaved = savedInstanceState.getInt("CURRENTSONGINDEX");
            currentSongIndex = lastplayedsongIndexsaved;

        }


        int EMPTY = songsList.size() + 1;

        int lastplayedsongIndex = getPreferences(Context.MODE_PRIVATE).getInt("CURRENTSONGINDEX",
                EMPTY);
        if (lastplayedsongIndex != EMPTY) {
            currentSongIndex = lastplayedsongIndex;
        } else
            currentSongIndex = 0;





        mp.setAudioSessionId(audiosessionId);
        SessionID = mp.getAudioSessionId();
        songsong.setText(String.valueOf(SessionID));


        /**
         * Play button click event
         * plays a song and changes button to pause image
         * pauses a song and changes button to play image
         * */
        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check for already playing
                if (mp.isPlaying()) {
                    if (mp != null) {
                        mp.pause();
                        // Changing button image to play button
                        btnPlay.setImageResource(R.drawable.btn_play);
                    }
                } else {
                    // Resume song
                    if (mp != null) {
                        mp.start();
                        // Changing button image to pause button
                        btnPlay.setImageResource(R.drawable.btn_pause);
                    }
                }

            }
        });


        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Intent notIntent = new Intent(getApplicationContext(), player.class);
        notIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0, notIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendInt);
        builder.setSmallIcon(R.drawable.play_notify);
        builder.setContentTitle("Playing");
        builder.setContentText(songTitle);

        Notification not = builder.build();

        nm = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);

        nm.notify(NOTIFY_ID, not);*/




         /* Forward button click event
         * Forwards song specified seconds

        btnForward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // get current song position
                int currentPosition = mp.getCurrentPosition();
                // check if seekForward time is lesser than song duration
                if(currentPosition + seekForwardTime <= mp.getDuration()){
                    // forward song
                    mp.seekTo(currentPosition + seekForwardTime);
                }else{
                    // forward to end position
                    mp.seekTo(mp.getDuration());
                }
            }
        });*/

        /**
         * Backward button click event
         * Backward song to specified seconds
         *
         btnBackward.setOnClickListener(new View.OnClickListener() {

        @Override public void onClick(View arg0) {
        // get current song position
        int currentPosition = mp.getCurrentPosition();
        // check if seekBackward time is greater than 0 sec
        if(currentPosition - seekBackwardTime >= 0){
        // forward song
        mp.seekTo(currentPosition - seekBackwardTime);
        }else{
        // backward to starting position
        mp.seekTo(0);
        }

        }
        });*/

        /**
         * Next button click event
         * Plays next song by taking currentSongIndex + 1
         * */
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check if next song is there or not
                if (currentSongIndex < (songsList.size() - 1)) {
                    playSong(currentSongIndex + 1);
                    currentSongIndex = currentSongIndex + 1;
                } else {
                    // play first song
                    playSong(0);
                    currentSongIndex = 0;
                }

            }
        });

        btnNext.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                forwardsong();

                return true;

            }
        });


        /**
         * Back button click event
         * Plays previous song by currentSongIndex - 1
         * */
        btnPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (currentSongIndex > 0) {
                    playSong(currentSongIndex - 1);
                    currentSongIndex = currentSongIndex - 1;
                } else {
                    // play last song
                    playSong(songsList.size() - 1);
                    currentSongIndex = songsList.size() - 1;
                }

            }
        });

        /**
         * Button Click event for Repeat button
         * Enables repeat flag to true
         *
         btnRepeat.setOnClickListener(new View.OnClickListener() {

        @Override public void onClick(View arg0) {
        if(isRepeat){
        isRepeat = false;
        Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
        btnRepeat.setImageResource(R.drawable.btn_repeat);
        }else{
        // make repeat to true
        isRepeat = true;
        Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
        // make shuffle to false
        isShuffle = false;
        btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
        btnShuffle.setImageResource(R.drawable.btn_shuffle);
        }
        }
        });*/

        /**
         * Button Click event for Shuffle button
         * Enables shuffle flag to true
         *
         btnShuffle.setOnClickListener(new View.OnClickListener() {

        @Override public void onClick(View arg0) {
        if(isShuffle){
        isShuffle = false;
        Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
        btnShuffle.setImageResource(R.drawable.btn_shuffle);
        }else{
        // make repeat to true
        isShuffle= true;
        Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
        // make shuffle to false
        isRepeat = false;
        btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
        btnRepeat.setImageResource(R.drawable.btn_repeat);
        }
        }
        });*/

        /**
         * Button Click event for Play list click event
         * Launches list activity which displays list of songs
         * */
        btnPlaylist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(i, 100);
            }
        });

        btnEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent eq = new Intent(getApplicationContext(), Eqactivity.class);

                eq.putExtra("ID", SessionID);
                setResult(150, eq);
                startActivity(eq);

            }
        });


    }

    private void forwardsong() {

        // get current song position
        int currentPosition = mp.getCurrentPosition();
        // check if seekForward time is lesser than song duration

        if (currentPosition + seekForwardTime <= mp.getDuration()) {
            // forward song
            mp.seekTo(currentPosition + seekForwardTime);
        } else {
            // forward to end position
            mp.seekTo(mp.getDuration());
        }


    }


    /**
     * Receiving song index from playlist view
     * and play the song
     */
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            currentSongIndex = data.getExtras().getInt("songIndex");
            // play selected song
            playSong(currentSongIndex);

        }

    }

    /**
     * Function to play a song
     *
     * @param songIndex - index of song
     */
    public void playSong(int songIndex) {
        // Play song
        try {
            mp.reset();
            mp.setDataSource(songsList.get(songIndex).get("songPath"));
            mp.prepare();
            mp.start();
            // Displaying Song title
            songTitle = songsList.get(songIndex).get("songTitle");
            songTitleLabel.setText(songTitle);


            nowplayingsongIndex = Integer.valueOf(songIndex);

            txt_songindex.setText(String.valueOf(songIndex));

            mediaInfo.setDataSource(songsList.get(songIndex).get("songPath"));

            try {

                AlburmArt = mediaInfo.getEmbeddedPicture();


                Bitmap songArt = BitmapFactory.decodeByteArray(AlburmArt, 0, AlburmArt.length);


                songAlburmArtView.setImageBitmap(songArt);

            } catch (Exception e) {
                songAlburmArtView.setImageResource(R.drawable.adele);
            }


            // Changing Button Image to pause image
            btnPlay.setImageResource(R.drawable.btn_pause);

            // set Progress bar values
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);

            // Updating progress bar
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * Update timer on seekbar
     */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();

            // Displaying Total Duration time
            songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    /**
     *
     * */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

    }

    /**
     * When user starts moving the progress handler
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    /**
     * When user stops moving the progress hanlder
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mp.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mp.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    /**
     * On Song Playing completed
     * if repeat is ON play same song again
     * if shuffle is ON play random song
     */
    @Override
    public void onCompletion(MediaPlayer arg0) {

        // check for repeat is ON or OFF
        if (isRepeat) {
            // repeat is on play same song again
            playSong(currentSongIndex);
        } else if (isShuffle) {
            // shuffle is on - play a random song
            Random rand = new Random();
            currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
            playSong(currentSongIndex);
        } else {
            // no repeat or shuffle ON - play next song
            if (currentSongIndex < (songsList.size() - 1)) {
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            } else {
                // play first song
                playSong(0);
                currentSongIndex = 0;
            }
        }
    }


    private void savesettings() {


        SharedPreferences.Editor SPeditor = getPreferences(Context.MODE_PRIVATE).edit();
        SPeditor.clear();
        SPeditor.putInt("CURRENTSONGINDEX", nowplayingsongIndex);



        SPeditor.apply();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("CURRENTSONGINDEX", nowplayingsongIndex);


        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {

        savesettings();

        super.onStop();
    }




}

