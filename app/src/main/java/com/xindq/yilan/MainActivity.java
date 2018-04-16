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

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
