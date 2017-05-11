package com.example.gt3kydemo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sdk.GT3GeetestUrl;
import com.example.sdk.GT3GeetestUtils;
import com.example.sdk.GT3BaseActivity;
import com.example.sdk.GT3ReadyMsg;
import com.example.sdk.GT3Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private static final String captchaURL = "http://www.geetest.com/demo/gt/register-slide";
    // 设置二次验证的URL，需替换成自己的服务器URL
    private static final String validateURL = "http://www.geetest.com/demo/gt/validate-slide";
    private EditText et1;
    private EditText et2;
    private GT3GeetestUtils gt3GeetestUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et1.getText() == null || et1.getText().length() == 0 || et2.getText() == null || et2.getText().length() == 0) {
                    GT3Toast.show("请输入账号和密码", getApplicationContext());
                } else if (et1.getText().length() != 6) {
                    GT3Toast.show("账号格式不正确", getApplicationContext());
                } else {
                    //验证码正式开始
                    gt3GeetestUtils.getGeetest();
                }

            }
        });
        findViewById(R.id.btnToSecond).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Demo2Activity.class));

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

            //验证码加载准备完成
            @Override
            public void gt3DialogReady() {

            }

            //拿到第一个url返回的数据
            @Override
            public void gt3FirstResult(JSONObject jsonObject) {

            }

            //往二次验证里面put数据，是map类型,注意map的键名不能是以下三个：geetest_challenge，geetest_validate，geetest_seccode
            @Override
            public Map gt3SecondResult() {
                return null;
            }


            //拿到验证返回的结果,此时还未进行二次验证
            @Override
            public void gt3GetDialogResult(String result) {

            }

            //验证码验证成功
            @Override
            public void gt3DialogSuccess() {
                if (et2.getText().length() == 6) {
                    GT3Toast.show("验证成功", getApplicationContext());
                } else {
                    GT3Toast.show("验证未通过 请重试", getApplicationContext());
                }
            }

            //验证码验证失败
            @Override
            public void gt3DialogFail() {

            }

            //二次验证请求之后的结果
            @Override
            public void gt3DialogSuccessResult(String result) {

            }
        });
    }

    private void init() {
        new GT3GeetestUrl().setCaptchaURL(captchaURL);
        new GT3GeetestUrl().setValidateURL(validateURL);
//        new GT3ReadyMsg().setReadyTextBotColor(0xffcccccc);//设置准备界面底部文字颜色
//        new GT3ReadyMsg().setReadyTextBot("AAAAAA");//设置准备界面底部文字
//        new GT3ReadyMsg().setLogoid();//设置准备界面头部的gif图片
//        new GT3ReadyMsg().setReadyTextMid();//设置准备界面中间的文字
//        new GT3ReadyMsg().setReadyTextMidColor();//设置准备界面中间的文字的颜色
        gt3GeetestUtils = new GT3GeetestUtils(MainActivity.this);
        gt3GeetestUtils.gtDologo();//加载验证码之前判断有没有logo
    }

}
