package com.xindq.yilan.dialog;

import android.content.Context;

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
            ToastUtil.showShort(context, item.getItemName());
        } else {
            ToastUtil.showShort(context, item.getType() + "类型数据无法查看历史趋势！");
        }
    }

    @Override
    public void onCancle() {

    }
}
