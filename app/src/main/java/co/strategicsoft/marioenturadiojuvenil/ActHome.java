package co.strategicsoft.marioenturadiojuvenil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import co.strategicsoft.marioenturadiojuvenil.Fragments.FragMenu;
import co.strategicsoft.marioenturadiojuvenil.ViewPager.SlidingTabLayout;
import co.strategicsoft.marioenturadiojuvenil.helper.NetworkManager;
import co.strategicsoft.marioenturadiojuvenil.helper.TwitterManager;


public class ActHome extends AppCompatActivity {

    private Bundle bundle;
    private String valorURL;
    private String texto;
    private static final String SCREEN_NAME = "MarioEnTuRadio_";
    private TwitterManager twitterManager = null;
    private NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setContentView(R.layout.layout_home);

            ViewPager mPager = (ViewPager) findViewById(R.id.pager);
            mPager.setAdapter(new MyClasPagerAdapter(getSupportFragmentManager()));
            SlidingTabLayout mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
            mTabs.setBackgroundColor(getResources().getColor(R.color.ColorPrimary));
            mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.actionBarColorText));
            mTabs.setDistributeEvenly(true);
            mTabs.setViewPager(mPager);
            networkManager = new NetworkManager(this);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            shareApp();
            return true;
        }
        else if (id == R.id.action_social) {
            openSocialNetwork();
            return true;
        }
        else if(id == R.id.action_reload){
            if(networkManager.isNetworkOnline()){
                twitterManager = new TwitterManager(this);
                twitterManager.execute(SCREEN_NAME);
                //twitterManager.setNewsListener(this);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyClasPagerAdapter extends FragmentPagerAdapter {
        String[] tabas;

        public MyClasPagerAdapter(FragmentManager fm) {
            super(fm);
            tabas = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabas[position];
        }

        @Override
        public Fragment getItem(int position) {
            Bundle arguments = new Bundle();
            arguments.putInt("position", position);
            arguments.putString("valorURL", valorURL);
            arguments.putString("texto", texto);
            FragMenu myFragment;
            myFragment = FragMenu.newInstance(arguments);

            return myFragment;
        }

        @Override
        public int getCount() {
            return tabas.length;
        }

    }

    private void openSocialNetwork(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_text));
        builder.setCancelable(true);
        builder.setItems(new CharSequence[] {getResources().getString(R.string.dialog_facebook), getResources().getString(R.string.dialog_twitter), getResources().getString(R.string.dialog_instagram)}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = "";
                switch (which){
                    case 0:
                        url = "https://m.facebook.com/EmisoraMarioEnTuRadioOficial";
                        break;

                    case 1:
                        url = "https://mobile.twitter.com/MarioEnTuRadio_";
                        break;

                    case 2:
                        url = "http://instagram.com/marioenturadio";
                        break;
                }

                Intent i = new Intent(getApplicationContext(), Social.class);
                i.putExtra("urlSocial", url);


                startActivity(i);
            }
        });
        builder.show();
    }

    private void shareApp(){
        Intent send = new Intent(Intent.ACTION_SEND);
        send.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_text));
        send.setType("text/plain");
        startActivity(Intent.createChooser(send, getResources().getString(R.string.share_chooser_title)));
    }

    @Override
    public void onResume(){
        super.onResume();
        //Toast.makeText(this,"Hola como estas",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed(){}
}
