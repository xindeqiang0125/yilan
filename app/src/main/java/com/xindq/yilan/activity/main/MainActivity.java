package com.xindq.yilan.activity.main;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.xindq.yilan.R;
import com.xindq.yilan.fragment.filelist.FileListFragment;
import com.xindq.yilan.fragment.search.SearchFragment;
import com.xindq.yilan.fragment.setting.SettingFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private FrameLayout frameLayout;
    private RadioGroup radioGroup;

    private Fragment fragment1,fragment2,fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        frameLayout = findViewById(R.id.content);
        radioGroup = findViewById(R.id.nav_bar);
        radioGroup.setOnCheckedChangeListener(new BarSelectedListener());
        fragment1 = new FileListFragment();
        fragment2 = new SearchFragment();
        fragment3 = new SettingFragment();
        radioGroup.check(R.id.btn_file_list);
    }

    class BarSelectedListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentTransaction transaction = MainActivity.this.getFragmentManager().beginTransaction();
            Log.i(TAG, "onCheckedChanged: " + checkedId);
            switch (checkedId){
                case R.id.btn_file_list:
                    transaction.replace(R.id.content,fragment1).commit();
                    break;
                case R.id.btn_history:
                    transaction.replace(R.id.content,fragment2).commit();
                    break;
                case R.id.btn_setting:
                    transaction.replace(R.id.content,fragment3).commit();
                    break;
            }
        }
    }

}
