package com.xindq.yilan.activity.screen;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xindq.yilan.util.ColorUtil;
import com.xindq.yilan.view.config.Action;
import com.xindq.yilan.view.config.Condition;
import com.xindq.yilan.view.config.Config;
import com.xindq.yilan.view.shape.CircleShape;
import com.xindq.yilan.view.shape.LineArrowShape;
import com.xindq.yilan.view.shape.LineShape;
import com.xindq.yilan.view.shape.PolygonShape;
import com.xindq.yilan.view.shape.RectangleShape;
import com.xindq.yilan.view.shape.Shape;
import com.xindq.yilan.view.shape.ShapeGroup;
import com.xindq.yilan.view.shape.TextShape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FileDecoder {
    private static final String TAG = "FileDecoder";

    private Map<Integer,Shape> shapeMap=new HashMap<>();
    private JSONArray shapes;
    private JSONArray configs;

    public FileDecoder(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        JSONObject screen = jsonObject.getJSONObject("screen");
        shapes = screen.getJSONArray("shapeList");

        configs = screen.getJSONArray("configurationList");
    }

    /**
     * 解码组态
     * @return
     */
    public List<Config> decodeConfigs() {
        List<Config> list=new ArrayList<>();
        for (int i = 0; i < configs.size(); i++) {
//            Log.i(TAG, "decodeConfigs: "+configs.get(i));
            JSONObject configJson = configs.getJSONObject(i);
            //shapeId,用于构建Config的参数
            Integer shapeId = configJson.getInteger("shapeId");

            String detail = configJson.getString("detail");
            //wheres[0]:action;wheres[0]:condition
            String[] wheres = detail.split("where");
            //condition,用于构建Config的参数
            Condition condition;
            if (wheres.length>1){
                condition=decodeCondition(wheres[1].trim());
            }else {
                condition=null;
            }
            //action,用于构建Config的参数
            Action action=decodeAction(wheres[0].trim(),shapeMap.get(shapeId));

            list.add(new Config(condition,action));
        }
        return list;
    }

    private Action decodeAction(String s,Shape shape) {
        Action action=new Action(shape,s);
        return action;
    }

    private Condition decodeCondition(String s) {
        return new Condition(s);
    }

    /**
     * 解码图形
     * @return
     */
    public List<Shape> decodeShapes() {
        List<Shape> list=new ArrayList<>();
        for (int i = 0; i < shapes.size(); i++) {
            JSONObject shapeJson = shapes.getJSONObject(i);
            Shape shape=null;
            switch (shapeJson.getString("type")){
                case "rect":
                    shape=decodeRectangle(shapeJson);
                    break;
                case "polygon":
                    shape=decodePolygon(shapeJson);
                    break;
                case "circle":
                    shape=decodeCircle(shapeJson);
                    break;
                case "text":
                    shape=decodeText(shapeJson);
                    break;
                case "line":
                    shape=decodeLine(shapeJson);
                    break;
                case "linearrow":
                    shape=decodeLineArrow(shapeJson);
                    break;
                case "group":
                    shape=decodeGroup(shapeJson);
                    break;
            }
            list.add(shape);
            shapeMap.put(shapeJson.getInteger("id"),shape);
        }
        return list;
    }

    //<editor-fold desc="decodeShapes子方法">
    private Shape decodeGroup(JSONObject groupJson) {
        ShapeGroup group=new ShapeGroup();
        JSONArray shapes = groupJson.getJSONArray("shapeList");
        for (int i = 0; i < shapes.size(); i++) {
            JSONObject shapeJson = shapes.getJSONObject(i);
            Shape shape=null;
            switch (shapeJson.getString("type")){
                case "rect":
                    shape=decodeRectangle(shapeJson);
                    break;
                case "polygon":
                    shape=decodePolygon(shapeJson);
                    break;
                case "circle":
                    shape=decodeCircle(shapeJson);
                    break;
                case "text":
                    shape=decodeText(shapeJson);
                    break;
                case "line":
                    shape=decodeLine(shapeJson);
                    break;
                case "linearrow":
                    shape=decodeLineArrow(shapeJson);
                    break;
                case "group":
                    shape=decodeGroup(shapeJson);
                    break;
            }
            group.addShape(shape);
        }
        return group;
    }

    private Shape decodeLineArrow(JSONObject shapeJson) {
        JSONArray points = shapeJson.getJSONArray("points");
        Point[] p=new Point[points.size()];
        for (int i = 0; i < points.size(); i++) {
            JSONObject point = points.getJSONObject(i);
            p[i]=new Point(
                    point.getInteger("x"),
                    point.getInteger("y"));
        }
        LineArrowShape shape=new LineArrowShape(p[0],p[1]);
        shape.setLineWidth(shapeJson.getInteger("lineWidth"));
        shape.setLineColor(ColorUtil.parseColor(shapeJson.getString("lineColor")));
        return shape;
    }

    private Shape decodeLine(JSONObject shapeJson) {
        JSONArray points = shapeJson.getJSONArray("points");
        Point[] p=new Point[points.size()];
        for (int i = 0; i < points.size(); i++) {
            JSONObject point = points.getJSONObject(i);
            p[i]=new Point(
                    point.getInteger("x"),
                    point.getInteger("y"));
        }
        LineShape shape=new LineShape(p[0],p[1]);
        shape.setLineWidth(shapeJson.getInteger("lineWidth"));
        shape.setLineColor(ColorUtil.parseColor(shapeJson.getString("lineColor")));
        return shape;
    }

    private Shape decodeText(JSONObject shapeJson) {
        JSONArray text = shapeJson.getJSONArray("text");
        TextShape shape=new TextShape(text.toArray(new String[1]));
        shape.setBackgroundColor(ColorUtil.parseColor(shapeJson.getString("backgroundColor")));
        shape.setTextColor(ColorUtil.parseColor(shapeJson.getString("fontColor")));
        shape.setTextSize(shapeJson.getInteger("fontSize"));
        shape.setPosition(shapeJson.getInteger("startX"),
                shapeJson.getInteger("startY"));
        return shape;
    }

    private Shape decodeCircle(JSONObject shapeJson) {
        JSONArray points = shapeJson.getJSONArray("points");
        List<Point> pointList=new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            JSONObject point = points.getJSONObject(i);
            pointList.add(new Point(
                    point.getInteger("x"),
                    point.getInteger("y")
            ));
        }
        int a= shapeJson.getInteger("a");
        int b= shapeJson.getInteger("b");
        Point center=new Point((pointList.get(0).x+pointList.get(2).x)/2,
                (pointList.get(0).y+pointList.get(2).y)/2);
        float radian= (float) Math.atan2(pointList.get(0).y-pointList.get(2).y,
                pointList.get(0).x-pointList.get(2).x);
        CircleShape shape=new CircleShape(a,b,center,radian);
        shape.setLineWidth(shapeJson.getInteger("lineWidth"));
        shape.setLineColor(ColorUtil.parseColor(shapeJson.getString("lineColor")));
        shape.setFillColor(ColorUtil.parseColor(shapeJson.getString("fillColor")));
        return shape;
    }

    private Shape decodePolygon(JSONObject shapeJson) {
        JSONArray points = shapeJson.getJSONArray("points");
        List<Point> list=new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            JSONObject point = points.getJSONObject(i);
            list.add(new Point(
                    point.getInteger("x"),
                    point.getInteger("y")
            ));
        }
        PolygonShape shape=new PolygonShape(list);
        shape.setLineWidth(shapeJson.getInteger("lineWidth"));
        shape.setLineColor(ColorUtil.parseColor(shapeJson.getString("lineColor")));
        shape.setFillColor(ColorUtil.parseColor(shapeJson.getString("fillColor")));
        return shape;
    }

    private Shape decodeRectangle(JSONObject shapeJson) {
        JSONArray points = shapeJson.getJSONArray("points");
        List<Point> list=new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            JSONObject point = points.getJSONObject(i);
            list.add(new Point(
                    point.getInteger("x"),
                    point.getInteger("y")
            ));
        }
        RectangleShape shape=new RectangleShape(list);
        shape.setLineWidth(shapeJson.getInteger("lineWidth"));
        shape.setLineColor(ColorUtil.parseColor(shapeJson.getString("lineColor")));
        shape.setFillColor(ColorUtil.parseColor(shapeJson.getString("fillColor")));
        return shape;
    }
    //</editor-fold>
}
