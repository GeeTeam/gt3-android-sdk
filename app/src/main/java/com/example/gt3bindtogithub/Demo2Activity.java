package com.example.gt3bindtogithub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.sdk.GT3GeetestUrl;
import com.example.sdk.GT3GeetestUtils;
import com.example.sdk.GT3Toast;

import org.json.JSONObject;

import java.util.Map;

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

            //验证码加载完成
            @Override
            public void gt3DialogReady() {

            }

            //拿到第一个url返回的数据
            @Override
            public void gt3FirstResult(JSONObject jsonObject) {

            }

            //往二次验证里面put数据，是map类型,注意map的键名不能是以下三个：geetest_challenge，geetest_validate，geetest_seccode
            @Override
            public Map<String, String> gt3SecondResult() {
                return null;
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

            @Override
            public void gt3DialogOnError() {

            }

            @Override
            public void gt3DialogSuccessResult(String result) {

            }

            @Override
            public Map<String, String> captchaHeaders() {
                return null;
            }


        });

    }


    private void init() {
        new GT3GeetestUrl().setCaptchaURL(captchaURL);
        new GT3GeetestUrl().setValidateURL(validateURL);
//        new GT3ReadyMsg().setLogoid(R.drawable.success);//设置准备界面头部的gif图片
        gt3GeetestUtils = new GT3GeetestUtils(Demo2Activity.this);
        gt3GeetestUtils.gtDologo();//加载验证码之前判断有没有logo
    }


}
