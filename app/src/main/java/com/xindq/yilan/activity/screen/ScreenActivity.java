package com.xindq.yilan.activity.screen;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.xindq.yilan.R;
import com.xindq.yilan.dialog.item.ItemDialog;
import com.xindq.yilan.dialog.item.ItemDialogBtnListener;
import com.xindq.yilan.dialog.pop.BottomPopMenu;
import com.xindq.yilan.domain.Item;
import com.xindq.yilan.view.ScreenView;
import com.xindq.yilan.view.config.Config;
import com.xindq.yilan.view.shape.Shape;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScreenActivity extends AppCompatActivity implements ScreenCallback, ScreenView.OnLongClickShape {
    private static final String TAG = "ScreenActivity";
    private List<Config> configs;
    private ScreenPresenter presenter;
    private LinearLayout screenContainer;
    private ItemDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        screenContainer = findViewById(R.id.screen_container);
        presenter = new ScreenPresenter(this, this);
        int fileId = getIntent().getIntExtra("fileId", 1);
        presenter.requestShapesAndConfigs(fileId);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.closeDatasConnection();
    }

    /**
     * 解析图形组态完成回调函数
     *
     * @param shapes
     * @param configs
     */
    @Override
    public void onDecode(List<Shape> shapes, List<Config> configs) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //创建ScreenView，并初始化
                ScreenView screenView = new ScreenView(ScreenActivity.this);
                screenView.setRefreshTime(100);
                screenView.setBackgroundColor(Color.parseColor("#660000ff"));
                //LayoutParams，并初始化
                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 10, 10, 10);
                //将获取的图形加入screenView
                for (Shape shape : shapes) {
                    screenView.addShape(shape);
                }
                //添加点击形状的事件回调
                screenView.setOnLongClickShape(ScreenActivity.this);
                //将screenView加入screenContainer
                screenContainer.addView(screenView, params);
                ScreenActivity.this.configs = configs;
            }
        });
        Set<String> requestItems = new HashSet<>();
        for (Config config : configs) {
            requestItems.addAll(config.getRequestItems());
        }
        presenter.requestdatas(requestItems);
    }

    /**
     * 接受到实时数据回调函数
     *
     * @param datas
     */
    @Override
    public void onReceiveDatas(Map<String, String> datas) {
        for (Config config : configs) {
            config.startUp(datas);
        }
    }

    @Override
    public void onGetItem(Item item) {
        Log.i(TAG, "onGetItem: " + item);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog = new ItemDialog(ScreenActivity.this);
                dialog.setItem(item)
                        .setOnClickBtnListener(new ItemDialogBtnListener(ScreenActivity.this, dialog))
                        .show();
            }
        });
    }

    @Override
    public void onLongClickShape(Shape shape) {
        List<Config> configsByShape = getConfigsByShape(shape);
        List<String> menus = new ArrayList<>();
        for (Config config : configsByShape) {
            String actionItem = config.getActionItem();
            if (actionItem != null && !menus.contains("测点:" + actionItem))
                menus.add("测点:" + actionItem);
            Set<String> conditionItems = config.getConditionItems();
            for (String conditionItem : conditionItems) {
                if (conditionItem != null && !menus.contains("测点:" + conditionItem))
                    menus.add("测点:" + conditionItem);
            }
        }
        BottomPopMenu popMenu = new BottomPopMenu(ScreenActivity.this);
        popMenu.setMenus(menus)
                .setOnCilckMenuItemListener(new BottomPopMenu.OnCilckMenuItemListener() {
                    @Override
                    public void onMenuItemClicked(BottomPopMenu menu, int position) {
                        String s = menu.getMenuText(position).split(":")[1];
                        presenter.requestItem(Integer.parseInt(s));
                    }
                }).show();
    }

    private List<Config> getConfigsByShape(Shape shape) {
        List<Config> c = new ArrayList<>();
        for (Config config : configs) {
            if (config.getAction().getShape() == shape) {
                c.add(config);
            }
        }
        return c;
    }
}
