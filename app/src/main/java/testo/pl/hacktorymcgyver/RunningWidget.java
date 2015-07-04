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
    }

    @Override
    public void onDraw(Canvas c) {
        c.drawRect(100, 100, 300, 150, new Paint());
    }
}
