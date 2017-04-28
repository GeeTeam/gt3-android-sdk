package com.example.gt3kydemo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.sdk.GT3BaseActivity;
import com.example.sdk.GT3GeetestUrl;
import com.example.sdk.GT3GeetestUtils;
import com.example.sdk.GT3Toast;


public class Demo2Activity extends AppCompatActivity {
    private static final String captchaURL = "http://www.geetest.com/demo/gt/register-click";
    // 设置二次验证的URL，需替换成自己的服务器URL
    private static final String validateURL = "http://www.geetest.com/demo/gt/validate-click";
    private EditText et1;
    private EditText et2;
    private GT3GeetestUtils gt3GeetestUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
        init();

        et1 = (EditText) findViewById(R.id.et11);
        et2 = (EditText) findViewById(R.id.et12);
        findViewById(R.id.btnget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et1.getText().length() != 11) {
                    GT3Toast.show("请输入正确的手机号", getApplicationContext());
                } else {
                    gt3GeetestUtils.getGeetest();
                }

            }
        });
        findViewById(R.id.btndenglu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et1.getText().length() != 11) {
                    GT3Toast.show("请输入正确的手机号", getApplicationContext());
                } else if (et2.getText().length() == 0) {
                    GT3Toast.show("请输入验证码", getApplicationContext());
                } else if (et2.getText().length() == 6) {
                    GT3Toast.show("登录成功", getApplicationContext());
                }
            }
        });
        gt3GeetestUtils.setGtListener(new GT3GeetestUtils.GT3Listener() {
            //点击验证码的关闭按钮来关闭验证码
            @Override
            public void gt3CloseDialog() {
                GT3Toast.show("验证未通过 请重试", getApplicationContext());
            }

            //点击屏幕关闭验证码
            @Override
            public void gt3CancelDialog() {
                GT3Toast.show("验证未通过 请重试", getApplicationContext());

            }

            //验证码加载失败
            @Override
            public void gt3DialogError() {

            }

            //验证码加载完成
            @Override
            public void gt3DialogReady() {

            }

            //拿到验证返回的结果
            @Override
            public void gt3GetDialogResult(String result) {

            }

            //验证码验证成功
            @Override
            public void gt3DialogSuccess() {

                GT3Toast.show("获取验证码成功", getApplicationContext());

            }

            //验证码验证失败
            @Override
            public void gt3DialogFail() {

            }

            @Override
            public void gt3DialogSuccessResult(String result) {

            }
        });

    }


    private void init() {
        new GT3GeetestUrl().setCaptchaURL(captchaURL);
        new GT3GeetestUrl().setValidateURL(validateURL);
        gt3GeetestUtils = new GT3GeetestUtils(Demo2Activity.this);
        gt3GeetestUtils.gtDologo();//加载验证码之前判断有没有logo
    }

}