package com.example.sanoj.androidmplayer;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class Eqactivity extends Activity{

    private SeekBar sixtyHz_SeekBar;
    private SeekBar twothirtyHz_SeekBar;
    private SeekBar ninetenHz_SeekBar;
    private SeekBar fourkiloHz_SeekBar;
    private SeekBar onefourkiloHz_SeekBar;
    private TextView TXT_sixtyHz_Intensity;
    private TextView TXT_twothirtyHz_Intensity;
    private TextView TXT_ninetenHz_Intensity;
    private TextView TXT_fourkiloHz_Intensity;
    private TextView TXT_onefourkiloHz_Intensity;
    private Equalizer EQ;
    final int audiosessionId = 1088112;
    EqService eqService;


    @Override
    protected void onResume() {
        super.onResume();
        /*int lastsavesixty = getPreferences(Context.MODE_PRIVATE).getInt("BANDSIXTYHZ", 0);
        sixtyHz_SeekBar.setProgress(lastsavesixty);*/

        Intent intent_eq = new Intent(getApplicationContext(), EqService.class);
        startService(intent_eq);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equilizer);

        Intent intent_eq = new Intent(getApplicationContext(), EqService.class);
        startService(intent_eq);

        eqService = new EqService();

        final short minEQLevel = eqService.equalizer.getBandLevelRange()[0];
        final short maxEQLevel = eqService.equalizer.getBandLevelRange()[1];

        TXT_sixtyHz_Intensity = (TextView)findViewById(R.id.txt_sixtyhz_intensity);
        /*TXT_twothirtyHz_Intensity = (TextView)findViewById(R.id.txt_twothirtyhz_intensity);
        TXT_ninetenHz_Intensity = (TextView)findViewById(R.id.txt_ninetenhz_intensity);
        TXT_fourkiloHz_Intensity = (TextView)findViewById(R.id.txt_fourkilohz_intensity);
        TXT_onefourkiloHz_Intensity = (TextView)findViewById(R.id.txt_onefourkilohz_intensity);*/


        sixtyHz_SeekBar = (SeekBar)findViewById(R.id.sixtyhz_seekbar);
        sixtyHz_SeekBar.setMax(maxEQLevel - minEQLevel);
        sixtyHz_SeekBar.setMax(maxEQLevel - minEQLevel);




        //sixtyHz_SeekBar.setProgress(EQ.getBand(60000));

        sixtyHz_SeekBar.setProgress(eqService.equalizer.getBand(60000));


        sixtyHz_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            short sixtyHzBand = eqService.equalizer.getBand(60000);


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                eqService.equalizer.setBandLevel(sixtyHzBand, (short) (progress + minEQLevel));
                TXT_sixtyHz_Intensity.setText(String.valueOf((progress + minEQLevel) / 100));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });

        /*twothirtyHz_SeekBar = (SeekBar)findViewById(R.id.twothirtyhz_seekbar);
        twothirtyHz_SeekBar.setMax(maxEQLevel - minEQLevel);

        twothirtyHz_SeekBar.setProgress(EQ.getBand(230000));

        twothirtyHz_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            short twothirtyHzBand = EQ.getBand(230000);


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                EQ.setBandLevel(twothirtyHzBand, (short) (progress + minEQLevel));
                TXT_twothirtyHz_Intensity.setText(String.valueOf((progress + minEQLevel) / 100));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ninetenHz_SeekBar = (SeekBar) findViewById(R.id.ninetenhz_seekbar);
        ninetenHz_SeekBar.setMax(maxEQLevel - minEQLevel);

        ninetenHz_SeekBar.setProgress(EQ.getBand(910000));

        ninetenHz_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            short ninetenHzBand = EQ.getBand(910000);


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                EQ.setBandLevel(ninetenHzBand, (short) (progress + minEQLevel));
                TXT_ninetenHz_Intensity.setText(String.valueOf((progress + minEQLevel) / 100));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        fourkiloHz_SeekBar = (SeekBar)findViewById(R.id.fourkilohz_seekbar);
        fourkiloHz_SeekBar.setMax(maxEQLevel - minEQLevel);

        fourkiloHz_SeekBar.setProgress(EQ.getBand(4000000));

        fourkiloHz_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            short fourkiloHzBand = EQ.getBand(4000000);


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                EQ.setBandLevel(fourkiloHzBand, (short)(progress + minEQLevel) );
                TXT_fourkiloHz_Intensity.setText(String.valueOf((progress + minEQLevel) / 100));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        onefourkiloHz_SeekBar = (SeekBar)findViewById(R.id.onefourkilohz_seekbar);
        onefourkiloHz_SeekBar.setMax(maxEQLevel - minEQLevel);

        onefourkiloHz_SeekBar.setProgress(EQ.getBand(14000000));

        onefourkiloHz_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            short onefourkiloHzBand = EQ.getBand(14000000);


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                EQ.setBandLevel(onefourkiloHzBand, (short)(progress + minEQLevel) );
                TXT_onefourkiloHz_Intensity.setText(String.valueOf((progress + minEQLevel) / 100));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        */





    }
    /*private void saveEqSettings(){
        SharedPreferences.Editor SPEditor = getPreferences(Context.MODE_PRIVATE).edit();
        SPEditor.putInt("BANDSIXTYHZ", sixtyHz_SeekBar.getProgress());


        SPEditor.commit();
    }

    @Override
    protected void onStop() {

        EQ.setEnabled(true);
        saveEqSettings();

        super.onStop();
    }*/
}