package co.strategicsoft.marioenturadiojuvenil;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import co.strategicsoft.marioenturadiojuvenil.Reproductor.InteractivePlayerView;
import co.strategicsoft.marioenturadiojuvenil.Reproductor.OnActionClickedListener;

public class ActMain extends Activity implements OnActionClickedListener {

    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_main);
            final InteractivePlayerView ipv = (InteractivePlayerView) findViewById(R.id.ipv);
            ipv.setMax(1000);
            ipv.setProgress(0);
            ipv.setOnActionClickedListener(this);

            new Thread(new Runnable() {
                public void run() {
                    try {
                        if (mMediaPlayer == null){
                            mMediaPlayer = new MediaPlayer();
                            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mMediaPlayer.setDataSource("http://5.199.169.190:8221/;stream.mp");
                            mMediaPlayer.prepare();
                        }
                        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                final ImageView control = (ImageView) findViewById(R.id.control);
                                control.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!ipv.isPlaying()) {
                                            ipv.start();
                                            control.setBackgroundResource(R.drawable.pause);
                                            mMediaPlayer.start();
                                        } else {
                                            ipv.stop();
                                            control.setBackgroundResource(R.drawable.play);
                                            if (mMediaPlayer.isPlaying()) {
                                                mMediaPlayer.pause();
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        //Toast.makeText(this,"Hola como estas",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed(){}

    @Override
    public void onActionClicked(int id) {
        switch (id){
            case 1:
                //Called when 1. action is clicked.
                break;
            case 2:
                //Called when 2. action is clicked.
                break;
            case 3:
                //Called when 3. action is clicked.
                break;
            default:
                break;
        }
    }
}
