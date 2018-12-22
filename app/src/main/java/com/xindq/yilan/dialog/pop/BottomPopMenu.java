package com.xindq.yilan.dialog.pop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xindq.yilan.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BottomPopMenu extends Dialog {
    private List<String> menus = new ArrayList<>();
    private Map<AppCompatTextView, Integer> map = new HashMap<>();

    private TextView cancle;
    private LinearLayout container;
    private OnCilckMenuItemListener onCilckMenuItemListener;
    private OnClickItemListener listener = new OnClickItemListener();

    public BottomPopMenu(@NonNull Context context) {
        super(context);
        init();
    }

    public BottomPopMenu(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected BottomPopMenu(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        setContentView(R.layout.menu_pop);
        cancle = findViewById(R.id.pop_menu_btn_cancle);
        container = findViewById(R.id.pop_menu_container);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setWidth();
    }

    private void setWidth() {
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager manager = getWindow().getWindowManager();
        Display display = manager.getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        Point size = new Point();
        display.getSize(size);
        params.width = (int) (size.x * 0.8);
        getWindow().setAttributes(params);
    }

    public BottomPopMenu setMenus(List<String> menus) {
        if (menus == null) return this;
        this.menus = menus;
        for (int i = 0; i < menus.size(); i++) {
            String menu = menus.get(i);
            AppCompatTextView textView = new AppCompatTextView(getContext());
            textView.setBackgroundResource(R.drawable.btn_default);
            textView.setPadding(5,5,5,5);
            textView.setGravity(Gravity.CENTER);
            textView.setTextAppearance(getContext(),R.style.pop_menu_item);
            textView.setText(menu);
            textView.setOnClickListener(listener);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,5,0,5);
            container.addView(textView, params);
            map.put(textView, i + 1);
        }
        return this;
    }

    public BottomPopMenu setOnCilckMenuItemListener(OnCilckMenuItemListener onCilckMenuItemListener) {
        this.onCilckMenuItemListener = onCilckMenuItemListener;
        return this;
    }

    public String getMenuText(int position) {
        return menus.get(position - 1);
    }


    public interface OnCilckMenuItemListener {
        void onMenuItemClicked(BottomPopMenu menu, int position);
    }

    private class OnClickItemListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Integer pisition = map.get(v);
            if (onCilckMenuItemListener != null) {
                onCilckMenuItemListener.onMenuItemClicked(BottomPopMenu.this, pisition);
            }
            dismiss();
        }
    }


}
