package com.xindq.yilan.dialog;

import android.content.Context;
import android.content.Intent;

import com.xindq.yilan.activity.history.HistoryActivity;
import com.xindq.yilan.domain.Item;
import com.xindq.yilan.util.ToastUtil;

public class ItemDialogBtnListener implements ItemDialog.DialogListener {
    private Context context;
    private ItemDialog dialog;

    public ItemDialogBtnListener(Context context, ItemDialog dialog) {
        this.context = context;
        this.dialog = dialog;
    }

    @Override
    public void onConfirm() {
        Item item = dialog.getItem();
        if ("INTEGER".equals(item.getType()) || "DOUBLE".equals(item.getType())) {
            openHistoryActivity();
        } else {
            ToastUtil.showShort(context, item.getType() + "类型数据无法查看历史趋势！");
        }
    }

    private void openHistoryActivity() {
        Intent intent = new Intent(context, HistoryActivity.class);
        intent.putExtra("item",dialog.getItem());
        context.startActivity(intent);
    }

    @Override
    public void onCancle() {

    }
}
