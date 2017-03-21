package com.example.gt3captcha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sdk.GT3GeetestButton;
import com.example.sdk.GT3GeetestUrl;
import com.example.sdk.GT3GeetestUtils;
import com.example.sdk.Gt3GeetestTestMsg;
import com.example.sdk.Gt3GeetestViewPath;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.ll_btn_type)
    GT3GeetestButton linearLayout;
    // 设置获取id，challenge，success的URL，需替换成自己的服务器URL
    private static final String captchaURL = "http://192.168.1.208:9977/gt/register1";
    // 设置二次验证的URL，需替换成自己的服务器URL
    private static final String validateURL = "http://192.168.1.208:9977/gt/form-validate1";
    //设置正常显示的文字
    private static final String normalText = "点击按钮进行验证";
    //设置失败显示的文字
    private static final String failText = "请求失败";
    //设置等待显示的文字
    private static final String waitText = "请完成上方验证";
    //设置成功显示的文字
    private static final String successText = "验证成功";
    //内部的半径               以下单位都为dp,如果用px的话，本sdk内部有DensityUtil方法px2dip()可以将px转换成dp
    private static final int internalRadius = 12;
    //外部的半径
    private static final int externalRadius = 30;
    //呼吸内部的半径
    private static final int BreatheRadius = 25;
    //完成加载的点的半径
    private static final int waitRadius = 5;
    //成功的半径
    private static final int successRadius = 24;
    //报错的半径
    private static final int failRadius = 16;
    //宕机状态三角的直角边长
    private static final int downTimePath = 25;
    //报错内部线的长度的一半
    private static final int failLine = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //执行自定义的一些操作
        init();
        EventBus.getDefault().register(this);
        //验证码加载开始
        new GT3GeetestUtils(MainActivity.this).getGeetest();
    }
    private void init() {
        new GT3GeetestUrl().setCaptchaURL(captchaURL);
        new GT3GeetestUrl().setValidateURL(validateURL);
        new Gt3GeetestTestMsg().setFailText(failText);
        new Gt3GeetestTestMsg().setSuccessText(successText);
        new Gt3GeetestTestMsg().setNormalText(normalText);
        new Gt3GeetestTestMsg().setWaitText(waitText);
        new Gt3GeetestViewPath().setInternalRadius(internalRadius);
        new Gt3GeetestViewPath().setExternalRadius(externalRadius);
        new Gt3GeetestViewPath().setBreatheRadius(BreatheRadius);
        new Gt3GeetestViewPath().setWaitRadius(waitRadius);
        new Gt3GeetestViewPath().setSuccessRadius(successRadius);
        new Gt3GeetestViewPath().setFailRadius(failRadius);
        new Gt3GeetestViewPath().setDownTimePath(downTimePath);
        new Gt3GeetestViewPath().setFailLine(failLine);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void test(String a) {
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
}
