package testo.pl.hacktorymcgyver;



import android.os.Handler;
/**
 * Created by Lukasz Marczak on 2015-07-04.
 */
public class Looper {
    private static boolean isStarted = false;
    private static Handler handler = new Handler();
    public static boolean isActive = true;

    private static Runnable stepTimer = new Runnable() {
        @Override
        public void run() {
            if (isActive) {
                RunningWidget.moveDot();
                handler.postDelayed(this, 10);
            }
        }

    };

    public static void start() {
        if (!isStarted) {
            handler.postDelayed(stepTimer, 0);
            isStarted = true;
        }
//        activity = view;
    }
}
