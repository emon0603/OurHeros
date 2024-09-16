package com.developeremon.khadimuddin;

import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Notice_Details_View extends AppCompatActivity {

    TextView details_noticetv, details_datetv;

    public static String detail_date = "";
    public static String details_notice = "";

    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details_view);

        details_datetv = findViewById(R.id.details_datetv);
        details_noticetv = findViewById(R.id.details_noticetv);

        details_datetv.setText("তারিখঃ "+detail_date);
        details_noticetv.setText(details_notice);

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());


    }


    @Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        // GestureDetector ব্যবহার করে টাচ ইভেন্ট পরিচালনা করা
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    // ScaleListener class ব্যবহার করা যা Gesture থেকে স্কেল পরিবর্তন করে
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(0.5f, Math.min(scaleFactor, 5.0f));
            details_noticetv.setScaleX(scaleFactor);
            details_noticetv.setScaleY(scaleFactor);
            return true;
        }
    }



}