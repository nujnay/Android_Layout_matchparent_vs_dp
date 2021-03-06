package com.comet.android_layout_matchparent_vs_dp;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
    View view;

    private int count = 0;
    private long timeMillis = 0;
    private long timeMillis2 = 0;
    private long timeMillis3 = 0;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        ViewGroup root = new FrameLayout(this) {
            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                long l = System.nanoTime();
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                timeMillis += (System.nanoTime() - l);
                if (++count == 1000) {
                    Log.e("cww", "finish measure: " + timeMillis);
                } else {
                    if (count < 1000) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                view.requestLayout();
                                view.invalidate();
                            }
                        }, 0);
                    }
                }
            }

            @Override
            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                long l = System.nanoTime();
                super.onLayout(changed, left, top, right, bottom);
                timeMillis2 += (System.nanoTime() - l);
                if (count == 1000) {
                    Log.e("cww", "finish layout: " + timeMillis2);
                }
            }

            @Override
            protected void dispatchDraw(Canvas canvas) {
                long l = System.nanoTime();
                super.dispatchDraw(canvas);
                timeMillis3 += (System.nanoTime() - l);
                if (count == 1000) {
                    Log.e("cww", "finish draw: " + timeMillis3);
                }
            }

        };

        setContentView(root);

        for (int i = 0; i < 10; i++) {
            FrameLayout rootLocal = new FrameLayout(this);
            root.addView(rootLocal);
            root = rootLocal;
        }

        view = new View(this);
        root.addView(view);
    }
}
