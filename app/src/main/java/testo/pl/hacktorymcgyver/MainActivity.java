package testo.pl.hacktorymcgyver;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private static ImageView cartman;
    private static TextView textView;
    private static RelativeLayout layout;
    private Context context;
    public static float dotPositionX = 100;
    public static float dotPositionY = 100;
    public static boolean moveToLeft = false;
    public static final int MIN = 20;
    public static final int MAX = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rocket_layout);
        cartman = (ImageView) findViewById(R.id.cartman);
        textView = (TextView) findViewById(R.id.text);
        layout = (RelativeLayout)findViewById(R.id.cartman_layout);
        context = this;


        dotPositionY = cartman.getY();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float dx = Math.abs(v.getX() - dotPositionX + cartman.getWidth() / 2);
                float dy = Math.abs(v.getY() - dotPositionY + cartman.getHeight() / 2);

                float distance = (float)Math.sqrt(dx*dx+dy*dy);
                textView.setText((400-distance)+" %");
                new RequestTask(context).execute("hey");
            }
        });
        Looper.start(this, 5);
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
        if (moveToLeft) {
            if (dotPositionX < MAX)
                dotPositionX += 10;
            else
                moveToLeft = false;
        } else {
            if (dotPositionX > MIN)
                dotPositionX -= 10;
            else {
                moveToLeft = true;
            }

        }
        cartman.setX(dotPositionX);
    }
}
