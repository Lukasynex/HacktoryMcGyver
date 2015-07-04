package testo.pl.hacktorymcgyver;



import android.os.Handler;
/**
 * Created by Lukasz Marczak on 2015-07-04.
 */
public class Looper {
    private static boolean isStarted = false;
    private static Handler handler = new Handler();
    public static boolean isActive = true;
    public static int speed=10;

    public static MainActivity activity;
    private static Runnable stepTimer = new Runnable() {
        @Override
        public void run() {
            if (isActive) {
                activity.moveDot();
                handler.postDelayed(this, speed);
            }
        }

    };

    public static void start(MainActivity view, int _speed) {
        if (!isStarted) {
            handler.postDelayed(stepTimer, 0);
            isStarted = true;
        }
        activity = view;
        speed = _speed;
    }
}
