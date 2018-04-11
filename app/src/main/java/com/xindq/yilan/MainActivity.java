package com.xindq.yilan;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xindq.yilan.view.ScreenView;
import com.xindq.yilan.view.shape.RectangleShape;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private ScreenView screenView;
    private RectangleShape shape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screenView = findViewById(R.id.scrollView);
        screenView.setOnClickListener(this);
        shape = new RectangleShape(Arrays.asList(
                new Point(100, 100),
                new Point(140, 130),
                new Point(110, 170),
                new Point(70, 140)
        ));
        screenView.addShape(shape);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

            }
        }

    }

    @Override
    public void onClick(View v) {
        Random random = new Random();
        int a = Math.abs(random.nextInt() % 100)+100;
        shape.setHeight(a, RectangleShape.RELATIVE_BOTTOM);

        Log.i(TAG, "onClick: "+shape.getHeight());
    }
}
