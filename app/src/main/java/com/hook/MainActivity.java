package com.hook;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.reflect.FieldManager;
import com.result.activity.ActivityResult;
import com.utils.LogUtil;
import com.xml.ProvinceManager;

public class MainActivity extends FragmentActivity {
    private final static String TAG = "MainActivity";
    private TextView test;
    private ActivityResult result;
    private RightView search;
    private Handler handler = new Handler();

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        ActivityInfo activityInfo = (ActivityInfo) FieldManager.getField(this, FieldManager.field(Activity.class, "mActivityInfo"));
        LogUtil.e(activityInfo.targetActivity);
//        Toast.makeText()
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = findViewById(R.id.test);
        result = new ActivityResult(this);
        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName(getPackageName(), "com.tvblackAD.demo.MainActivity");
                intent.setComponent(componentName);
                startActivity(intent);
//                startActivity(intent);
            }
        });
//        PackageManager manager=getPackageManager();
//        Field mPM= FieldManager.field(manager.getClass(),"mPM");
//        LogUtil.e(TAG,FieldManager.getField(manager,mPM).getClass().getName());
//        test.startAnimation(AnimationUtils.loadAnimation(this,R.anim.animation));
//        PoolManager.runUiThread(new Runnable() {
//            @Override
//            public void run() {
//                result=new ActivityResult(MainActivity.this);
//
//                result.startActivityForResult(100, new Intent(MainActivity.this, FistActivity.class), new ResultCallback() {
//                    @Override
//                    public void onActivityResult(int resultCode, Intent data) {
//                        test.setText(data.getStringExtra("test"));
//                    }
//                });
//            }
//        }, 3000);
    }

    public void search(View view) {
        if (view.getId() == R.id.search_card) {
            TextView textView = findViewById(R.id.place);
            EditText editText = findViewById(R.id.id_card);
            textView.setText(ProvinceManager.manager(this).idCard2Place(editText.getText().toString().trim()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e(TAG, "onResume");
//        startActivity();
    }


}
