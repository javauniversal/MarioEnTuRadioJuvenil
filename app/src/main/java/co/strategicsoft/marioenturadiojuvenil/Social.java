package co.strategicsoft.marioenturadiojuvenil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class Social extends Activity {

    private WebView web;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        loader = new ProgressDialog(this);
        loader.setMessage("Cargando...");
        loader.setCancelable(false);

        web = (WebView) findViewById(R.id.webview);
        web.getSettings().setJavaScriptEnabled(true);

        Bundle extras = getIntent().getExtras();
        if(!extras.isEmpty()){
            web.loadUrl(extras.getString("urlSocial"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpTo(this, new Intent(this, ActMain.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
