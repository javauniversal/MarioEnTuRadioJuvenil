package co.strategicsoft.marioenturadiojuvenil.Fragments;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import java.io.IOException;
import java.util.ArrayList;

import co.strategicsoft.marioenturadiojuvenil.Adapters.AdapterNoticias;
import co.strategicsoft.marioenturadiojuvenil.R;
import co.strategicsoft.marioenturadiojuvenil.Reproductor.InteractivePlayerView;
import co.strategicsoft.marioenturadiojuvenil.Reproductor.OnActionClickedListener;
import co.strategicsoft.marioenturadiojuvenil.helper.Tweet;
import co.strategicsoft.marioenturadiojuvenil.helper.TwitterManager;
import co.strategicsoft.marioenturadiojuvenil.interfaces.NewsListener;
import co.strategicsoft.marioenturadiojuvenil.model.TwitterM;

public class FragMenu extends Fragment implements NewsListener, OnActionClickedListener {

    private int operador;
    private ArrayList<TwitterM> listItems;
    private static final String SCREEN_NAME = "MarioEnTuRadio_";
    private ListView listNews;
    private MediaPlayer mMediaPlayer;
    private ProgressDialog progress;

    public static FragMenu newInstance(Bundle param1) {
        FragMenu fragment = new FragMenu();
        fragment.setArguments(param1);
        return fragment;
    }

    public FragMenu() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            operador = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        switch (operador) {
            case 0:
                rootView = inflater.inflate(R.layout.layout_main, container, false);
                break;
            case 1:
                rootView = inflater.inflate(R.layout.fragment_noticias, container, false);
                break;
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        switch (operador) {
            case 0:
                final InteractivePlayerView ipv = (InteractivePlayerView) getActivity().findViewById(R.id.ipv);
                ipv.setMax(1000);
                ipv.setProgress(0);
                ipv.setOnActionClickedListener(this);

                progress = ProgressDialog.show(getActivity(), "", "Cargando...", true);
                progress.setCancelable(false);

                new Thread(new Runnable() {
                    public void run() {
                        try {
                            mMediaPlayer = new MediaPlayer();
                            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mMediaPlayer.setDataSource("http://5.199.169.190:8221/;stream.mp");
                            mMediaPlayer.prepare();
                            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    progress.dismiss();
                                    final ImageView control = (ImageView) getActivity().findViewById(R.id.control);
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
                            progress.dismiss();
                            //Toast.makeText(getActivity(), "Active el Wifi o los datos para que pueda escuchar la emisora ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).start();
                break;
            case 1:
                TwitterManager twitterManager = new TwitterManager(getActivity());
                twitterManager.execute(SCREEN_NAME);
                twitterManager.setNewsListener(this);
                if (android.os.Build.VERSION.SDK_INT > 14) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }

                listItems = new ArrayList<>();
                listNews = (ListView) getActivity().findViewById(R.id.listView);

                break;
            case 2:
                break;
        }
    }

    @Override
    public void onNewsLoaded(co.strategicsoft.marioenturadiojuvenil.helper.Twitter tweets) {
        listItems.clear();
        if(tweets != null){
            for (Tweet tweet : tweets) {

                listItems.add(new TwitterM("Mario en tu Radio", tweet.getText().toString(), tweet.getUser().getScreenName()));
            }

            AdapterNoticias tw = new AdapterNoticias(getActivity(), listItems);
            listNews.setAdapter(tw);
        }
    }

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
