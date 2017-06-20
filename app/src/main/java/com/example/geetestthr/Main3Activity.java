package com.example.geetestthr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.gt3unbindsdk.unBind.GT3Geetest2Utils;
import com.example.gt3unbindsdk.unBind.GT3Toast;

import org.json.JSONObject;

import java.util.Map;


public class Main3Activity extends AppCompatActivity {

    private static final String captchaURL = "http://www.geetest.com/demo/gt/register-slide";
    // 设置二次验证的URL，需替换成自己的服务器URL
    private static final String validateURL = "http://www.geetest.com/demo/gt/validate-slide";
    private EditText et1;
    private EditText et2;
    private GT3Geetest2Utils gt3GeetestUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
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
                    gt3GeetestUtils.getGeetest();
                }

            }
        });
        findViewById(R.id.btnToSecond).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this, Main4Activity.class));
            }
        });
        gt3GeetestUtils.setGtListener(new GT3Geetest2Utils.GT3Listener() {
            //点击验证码的关闭按钮来关闭验证码
            @Override
            public void gt3CloseDialog() {
                GT3Toast.show("验证未通过 请重试", getApplicationContext());
            }

            //支持"zh-cn","zh-hk","zh-tw","ko-kr","ja-jp","en-us".默认"zh-cn".


            //点击屏幕关闭验证码
            @Override
            public void gt3CancelDialog() {
                GT3Toast.show("验证未通过 请重试", getApplicationContext());
            }

            //验证码加载准备完成
            @Override
            public void gt3DialogReady() {

            }

            //往第一个请求中添加头部数据
            @Override
            public Map<String, String> captchaHeaders() {
                return null;
            }

            @Override
            public Map<String, String> validateHeaders() {
                return null;
            }

            @Override
            public boolean gtSetIsCustom() {
                return false;
            }

            //拿到第一个url返回的数据
            @Override
            public void gt3FirstResult(JSONObject jsonObject) {

            }

            //拿到验证返回的结果,此时还未进行二次验证
            @Override
            public void gt3GetDialogResult(String result) {

            }

            @Override
            public void gt3GetDialogResult(boolean b, String s) {

            }

            //往二次验证里面put数据，是map类型,注意map的键名不能是以下三个：geetest_challenge，geetest_validate，geetest_seccode
            @Override
            public Map<String, String> gt3SecondResult() {
                return null;
            }

            //二次验证请求之后的结果
            @Override
            public void gt3DialogSuccessResult(String result) {

            }

            //验证码验证成功
            @Override
            public void gt3DialogSuccess() {
                if (et2.getText().length() == 6) {
                    GT3Toast.show("验证成功", getApplicationContext());
                } else {
                    GT3Toast.show("验证未通过0 请重试", getApplicationContext());
                }
            }

            @Override
            public void gt3DialogOnError(String error) {

            }


        });
    }

    private void init() {
//        new GT3ReadyMsg().setLogoid(R.drawable.success);//设置准备界面头部的gif图片
        gt3GeetestUtils = new GT3Geetest2Utils(Main3Activity.this);
        gt3GeetestUtils.gtDologo(captchaURL, validateURL,null);//加载验证码之前判断有没有logo

    }

}


