package testo.pl.hacktorymcgyver;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Lukasz Marczak on 2015-07-04.
 */
public class RunningWidget extends View {

    private Paint dotPaint;
    private Paint linePaint;
    public static int dotPositionX = 100;
    public static boolean moveToLeft = false;
    public static final int MIN = 20;
    public static final int MAX = 400;


    public RunningWidget(Context context) {
        super(context);
        initStuff();
    }

    public RunningWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStuff();

    }

    public RunningWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStuff();
    }

    private void initStuff() {
        dotPaint = new Paint();
        dotPaint.setColor(Color.RED);
        linePaint = new Paint();
        linePaint.setColor(Color.LTGRAY);

    }

    @Override
    public void onDraw(Canvas c) {
        c.drawRect(50, 100, 300, 150, linePaint);
        c.drawCircle(dotPositionX, 125, 25, dotPaint);

    }


    public static void moveDot() {
        if (moveToLeft) {
            if (dotPositionX < MAX)
                dotPositionX++;
            else
                moveToLeft = false;
        } else {
            if (dotPositionX > MIN)
                dotPositionX--;
            else {
                moveToLeft = true;
            }

        }
    }
}
