package testo.pl.hacktorymcgyver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukasz Marczak on 2015-07-04.
 */
public class RequestTask extends AsyncTask<String, Integer, Double> {

    private Activity activity;

    public RequestTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Double doInBackground(String... params) {
        // TODO Auto-generated method stub
        postData(params[0]);
        return null;
    }

    protected void onPostExecute(Double result) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "Beacon thrown!",
                        Toast.LENGTH_LONG).show();

            }
        });
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    public void postData(String valueIWantToSend) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                Config.SERVER_URL);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("myHttpData",
                    valueIWantToSend));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }


}