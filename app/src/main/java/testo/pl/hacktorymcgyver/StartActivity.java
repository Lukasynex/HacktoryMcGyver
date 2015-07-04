package testo.pl.hacktorymcgyver;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class StartActivity extends ActionBarActivity {
    Intent mainActivityIntent;
    Intent highScoreActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button b = (Button) findViewById(R.id.btn);
        Button high = (Button) findViewById(R.id.highscore_btn);
        mainActivityIntent = new Intent(this, MainActivity.class);
        highScoreActivityIntent = new Intent(this, HighscoreActivity.class);

        Config.highScores = new ArrayList<>();
        Config.highScores.add(new Record("Paulina", "1000"));
        Config.highScores.add(new Record("Mateusz", "800"));
        Config.highScores.add(new Record("Mariusz", "600"));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainActivityIntent);
            }
        });
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(highScoreActivityIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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
}
