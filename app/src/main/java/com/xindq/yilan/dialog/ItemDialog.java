package com.xindq.yilan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xindq.yilan.R;
import com.xindq.yilan.domain.Item;

public class ItemDialog extends Dialog implements View.OnClickListener {
    private TextView mTvTitle, mTvCancle, mTvConfilm;
    private TextView mTvItemId, mTvItemName, mTvItemType, mTvItemNotes, mTvItemGroup;
    private TextView mTvItemUnit, mTvItemMin, mTvItemMax, mTvItemNormal;
    private Item item;
    private DialogListener listener;

    public ItemDialog(@NonNull Context context) {
        super(context);
    }

    public ItemDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_item);
        setWidth();
        init();
    }

    private void setWidth() {
        WindowManager manager = getWindow().getWindowManager();
        Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        Point size = new Point();
        display.getSize(size);
        params.width = (int) (size.x * 0.8);
        getWindow().setAttributes(params);
    }

    private void init() {
        mTvTitle = findViewById(R.id.dialog_title);
        mTvCancle = findViewById(R.id.dialog_cancle);
        mTvConfilm = findViewById(R.id.dialog_confirm);
        mTvCancle.setOnClickListener(this);
        mTvConfilm.setOnClickListener(this);

        mTvItemId = findViewById(R.id.dialog_item_id);
        mTvItemName = findViewById(R.id.dialog_item_name);
        mTvItemType = findViewById(R.id.dialog_item_type);
        mTvItemNotes = findViewById(R.id.dialog_item_notes);
        mTvItemGroup = findViewById(R.id.dialog_item_group);
        mTvItemUnit = findViewById(R.id.dialog_item_unit);
        mTvItemMin = findViewById(R.id.dialog_item_min);
        mTvItemMax = findViewById(R.id.dialog_item_max);
        mTvItemNormal = findViewById(R.id.dialog_item_normal);

        mTvItemId.setText(item.getId() + "");
        mTvItemName.setText(item.getItemName());
        mTvItemType.setText(item.getType());
        mTvItemNotes.setText(item.getNotes());
        mTvItemGroup.setText(item.getGroup());
        mTvItemUnit.setText(item.getUnit());
        mTvItemMax.setText(item.getMax() + "");
        mTvItemMin.setText(item.getMin() + "");
        mTvItemNormal.setText(item.getNormal() + "");
    }

    public ItemDialog setItem(Item item) {
        this.item = item;
        return this;
    }

    public Item getItem() {
        return item;
    }

    public ItemDialog setOnClickBtnListener(DialogListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_cancle:
                dismiss();
                if (listener != null) {
                    listener.onCancle();
                }
                break;
            case R.id.dialog_confirm:
                if (listener != null) {
                    listener.onConfirm();
                }
                break;
        }
    }

    interface DialogListener {
        void onConfirm();

        void onCancle();
    }
}
