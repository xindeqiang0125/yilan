package com.xindq.yilan;

import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xindq.yilan.view.ScreenView;
import com.xindq.yilan.view.shape.CircleShape;
import com.xindq.yilan.view.shape.LineArrowShape;
import com.xindq.yilan.view.shape.LineShape;
import com.xindq.yilan.view.shape.RectangleShape;
import com.xindq.yilan.view.shape.Shape;
import com.xindq.yilan.view.shape.ShapeGroup;
import com.xindq.yilan.view.shape.TextShape;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private ScreenView screenView;


    private LineArrowShape lineArrowShape;
    private LineShape lineShape;
    private CircleShape circleShape;
    private TextShape textShape;
    private ShapeGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screenView = findViewById(R.id.scrollView);
        screenView.setOnClickListener(this);

        String[] strings={"xdq"};
        textShape=new TextShape(strings);
        textShape.setPosition(new Point(50,50));

        circleShape = new CircleShape(100,50,new Point(200,200), (float) (Math.PI/4));

        lineArrowShape=new LineArrowShape(new Point(600,300),
                new Point(600,650));
        lineArrowShape.setLineWidth(2);
        lineArrowShape.setLineColor(Color.RED);

        ShapeGroup group2=new ShapeGroup();
        group2.addShape(textShape);
        group2.addShape(circleShape);
        group=new ShapeGroup();
        group.addShape(group2);
        group.addShape(lineArrowShape);

        screenView.addShape(group);
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 25; j++) {
//                shape = new CircleShape(20,15,new Point(10+50*i,10+50*j), 0.7f);
//                textShape=new TextShape(strings);
//                textShape.setTextSize(20);
//                textShape.setPosition(new Point(10+50*i,10+50*j));
//                shape=new RectangleShape(Arrays.asList(
//                        new Point(10+50*i,10+50*j),
//                        new Point(40+50*i,10+50*j),
//                        new Point(40+50*i,40+50*j),
//                        new Point(10+50*i,40+50*j)
//            ));
//                shape=new LineShape(new Point(10+50*i,10+50*j),
//                        new Point(40+50*i,30+50*j));
//                shape.setLineWidth(10);
//                shape.setLineColor(Color.RED);
//                screenView.addShape(shape);
            }
        }

    }

    @Override
    public void onClick(View v) {
        Random random = new Random();
        int a = Math.abs(random.nextInt());
//        int a = Math.abs(random.nextInt()%5)+10;
//        int a = random.nextInt()%200+200;
//        shape.setLineWidth(20);
//        shape.setEnd(new Point(a,a));
//        shape.setLineColor(a);

//        Log.i(TAG, "onClick: LineColor"+shape.getLineColor());
        Log.i(TAG, "onClick: "+a);
    }
}
