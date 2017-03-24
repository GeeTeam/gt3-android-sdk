package com.example.gt3captcha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.example.sdk.GT3GeetestButton;
import com.example.sdk.GT3GeetestUrl;
import com.example.sdk.GT3GeetestUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ll_btn_type)
    GT3GeetestButton linearLayout;
    // 设置获取id，challenge，success的URL，需替换成自己的服务器URL
    private static final String captchaURL = "http://192.168.1.208:9977/gt/register3";
    // 设置二次验证的URL，需替换成自己的服务器URL
    private static final String validateURL = "http://192.168.1.208:9977/gt/validate3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ButterKnife.bind(this);
        //验证码加载开始
        new GT3GeetestUtils(MainActivity.this).getGeetest();



    }
    private void init() {
        new GT3GeetestUrl().setCaptchaURL(captchaURL);
        new GT3GeetestUrl().setValidateURL(validateURL);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void text(String a) {
        if (a.equals("dosuccess")) {
            //验证码验证成功后的操作
            Toast.makeText(getApplicationContext(), "这里是验证成功后执行的操作", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
}
