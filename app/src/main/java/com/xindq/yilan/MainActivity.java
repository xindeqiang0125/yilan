package com.xindq.yilan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xindq.yilan.view.ScreenView;
import com.xindq.yilan.view.shape.CircleShape;
import com.xindq.yilan.view.shape.RectangleShape;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ScreenView screenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screenView = findViewById(R.id.scrollView);

        RectangleShape shape ;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 50; j++) {
                Random random=new Random();
                int a = random.nextInt() % 2;
                if (a==0) shape = new RectangleShape();
                else shape = new CircleShape();
                shape.setX(5+100*i);
                shape.setY(5+100*j);
                shape.setHeight(50);
                shape.setWidth(50);
                screenView.addShape(shape);
            }
        }

    }
}
