package co.strategicsoft.marioenturadiojuvenil.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import co.strategicsoft.marioenturadiojuvenil.Fragments.FragMenu;
import co.strategicsoft.marioenturadiojuvenil.interfaces.NewsListener;

public class TwitterManager extends AsyncTask<String, Void, String> {
    private Context context;
    private NewsListener newsListener;

    private static final String CONSUMER_KEY = "gSaiTHdDt8bJh9UiVotqcUsm0";
    private static final String CONSUMER_SECRET = "sQwhJ1DZJoQEQOXR6mqH1t9r91er6xXKmLJeB6WiUaLLAF76Ee";
    private final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
    private final static String TwitterStreamURL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";

    public TwitterManager(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;
        String screenName = params[0];

        if (screenName != null) {
            result = getTwitterStream(screenName);
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Twitter twits = jsonToTwitter(result);
        if(newsListener != null) {
            newsListener.onNewsLoaded(twits);
        }

    }

    private Twitter jsonToTwitter(String result) {
        Twitter twits = null;
        if (result != null && result.length() > 0) {
            try {
                Gson gson = new Gson();
                twits = gson.fromJson(result, Twitter.class);
            }
            catch (IllegalStateException ex) {}
        }

        return twits;
    }

    private Authenticated jsonToAuthenticated(String rawAuthorization) {
        Authenticated auth = null;
        if (rawAuthorization != null && rawAuthorization.length() > 0) {
            try {
                Gson gson = new Gson();
                auth = gson.fromJson(rawAuthorization, Authenticated.class);
            }
            catch (IllegalStateException ex) {}
        }

        return auth;
    }

    private String getResponseBody(HttpRequestBase request) {
        StringBuilder sb = new StringBuilder();
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            String reason = response.getStatusLine().getReasonPhrase();

            if (statusCode == 200) {

                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();

                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sb.append(line);
                }
            }
            else {
                sb.append(reason);
            }
        }
        catch (UnsupportedEncodingException ex) {}
        catch (ClientProtocolException ex1) {}
        catch (IOException ex2) {}

        return sb.toString();
    }

    private String getTwitterStream(String screenName) {
        String results = null;

        try {
            String urlApiKey = URLEncoder.encode(CONSUMER_KEY, "UTF-8");
            String urlApiSecret = URLEncoder.encode(CONSUMER_SECRET, "UTF-8");
            String combined = urlApiKey + ":" + urlApiSecret;

            String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

            HttpPost httpPost = new HttpPost(TwitterTokenURL);
            httpPost.setHeader("Authorization", "Basic " + base64Encoded);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
            String rawAuthorization = getResponseBody(httpPost);
            Authenticated auth = jsonToAuthenticated(rawAuthorization);

            if (auth != null && auth.token_type.equals("bearer")) {
                HttpGet httpGet = new HttpGet(TwitterStreamURL + screenName);
                httpGet.setHeader("Authorization", "Bearer " + auth.access_token);
                httpGet.setHeader("Content-Type", "application/json");

                results = getResponseBody(httpGet);
            }
        }
        catch (UnsupportedEncodingException ex) {}
        catch (IllegalStateException ex1) {}

        return results;
    }

    public void setNewsListener(FragMenu newsListener) {
        this.newsListener = newsListener;
    }
}
