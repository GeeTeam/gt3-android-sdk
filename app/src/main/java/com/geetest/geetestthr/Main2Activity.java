package com.geetest.geetestthr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.geetest.gt3unbindsdk.Bind.GT3GeetestUtilsBind;

public class Main2Activity extends AppCompatActivity {
    private static final String captchaURL = "http://www.geetest.com/demo/gt/register-click";

    // 设置二次验证的URL，需替换成自己的服务器URL
    private static final String validateURL = "http://www.geetest.com/demo/gt/validate-click";
    private GT3GeetestUtilsBind gt3GeetestUtils;
    Button btn1;
    Button btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));


            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Main3Activity.class));
            }
        });


    }


}
