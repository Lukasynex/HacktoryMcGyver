package testo.pl.hacktorymcgyver;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    private static ImageView cartman;
    private static TextView textView;
    private static RelativeLayout layout;
    private static Random generator = new Random();
    public static float dotPositionX = 100;
    public static float dotPositionY = 100;
    public static boolean moveToLeft = false;
    public static boolean moveToUp = false;
    public static final int MIN = 20;
    public static final int MAX = 400;
    public static final int MIN_h = 20;
    public static final int MAX_h = 800;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rocket_layout);
        cartman = (ImageView) findViewById(R.id.cartman);
        textView = (TextView) findViewById(R.id.text);
        layout = (RelativeLayout) findViewById(R.id.cartman_layout);
        final Activity activity = this;


        dotPositionY = cartman.getY();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float dx = Math.abs(v.getX() - dotPositionX + cartman.getWidth() / 2);
                float dy = Math.abs(v.getY() - dotPositionY + cartman.getHeight() / 2);

                float distance = (float) Math.sqrt(dx * dx + dy * dy);
                final String data = 400 - distance + "";
                textView.setText(data + " %");
                //new RequestTask(activity).execute("hey");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        makePostRequest(data);
                    }
                }).start();
            }
        });

        Looper.start(this, 2);
    }

    private static final int step = 5;

    private void makePostRequest(String value) {


        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost("http://www.ndbc.noaa.gov/data/5day2/44013_5day.txt");


        //Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("value", value));
//        nameValuePair.add(new BasicNameValuePair("password", "123456789"));


        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        //making POST request.
        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            Log.d("Http Post Response:", response.toString());
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void moveDot() {

        boolean isVertical = generator.nextBoolean();

        if (isVertical) {
            boolean isLeft = generator.nextBoolean();

            if (isLeft) {
                if (dotPositionX < MAX)
                    dotPositionX += step;
                else
                    dotPositionX -= step;
            } else {
                if (dotPositionX > MIN)
                    dotPositionX -= step;
                else
                    dotPositionX += step;
            }
        } else {
            boolean isUp = generator.nextBoolean();
            if (isUp) {
                if (dotPositionY < MAX_h)
                    dotPositionY += step;
                else
                    dotPositionY -= step;
            } else {
                if (dotPositionY > MIN_h)
                    dotPositionY -= step;
                else
                    dotPositionY += step;
            }
        }
        cartman.setX(dotPositionX);
        cartman.setY(dotPositionY);
    }
}
