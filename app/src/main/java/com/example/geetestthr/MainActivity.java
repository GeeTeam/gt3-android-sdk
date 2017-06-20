package com.example.geetestthr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.gt3unbindsdk.GT3GeetestButton;
import com.example.gt3unbindsdk.GT3GeetestUtils;

import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * mainActivity
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.ll_btn_type)
    GT3GeetestButton llBtnType;
    // 设置获取id，challenge，success的URL，需替换成自己的服务器URL
    private static final String captchaURL = "http://www.geetest.com/demo/gt/register-slide";
    // 设置二次验证的URL，需替换成自己的服务器URL
    private static final String validateURL = "http://www.geetest.com/demo/gt/validate-slide";
    private GT3GeetestUtils gt3GeetestUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gt3GeetestUtils = GT3GeetestUtils.getInstance(MainActivity.this);
        gt3GeetestUtils.getGeetest(captchaURL,validateURL,null);
        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);
        gt3GeetestUtils.setGtListener(new GT3GeetestUtils.GT3Listener() {

            //添加头部数据
            @Override
            public Map<String, String> captchaHeaders() {
                return null;
            }

            @Override
            public void gt3DialogOnError(String error) {

            }

            @Override
            public void gt3CloseDialog() {

            }


            @Override
            public void gt3CancelDialog() {

            }

            @Override
            public void gt3GetDialogResult(String result) {

            }

            //准备二次请求的数据
            @Override
            public void gt3GetDialogResult(boolean success,String result) {

                if (success) {


                    /**
                     *  利用异步进行解析这result进行二次验证，结果成功后调用gt3GeetestUtils.gt3TestFinish()方法调用成功后的动画，然后在gt3DialogSuccess执行成功之后的结果
                     * //
                     //          JSONObject res_json = new JSONObject(result);
                     //
                     //                Map<String, String> validateParams = new HashMap<>();
                     //
                     //                validateParams.put("geetest_challenge", res_json.getString("geetest_challenge"));
                     //
                     //                validateParams.put("geetest_validate", res_json.getString("geetest_validate"));
                     //
                     //                validateParams.put("geetest_seccode", res_json.getString("geetest_seccode"));
                     在二次验证结果验证完成之后，执行gt3GeetestUtils.gt3TestFinish()方法进行动画执行
                     */


                    gt3GeetestUtils.gt3TestFinish();



                }
            }
            //二次请求后数据
            @Override
            public void gt3FirstResult(JSONObject jsonObject) {

            }



            //添加二次验证数据
            @Override
            public Map<String, String> gt3SecondResult() {
                return null;
            }



            //请求成功数据
            @Override
            public void gt3DialogSuccessResult(String result) {
//                Gt3GeetestTestMsg.setCandotouch(false);//这里设置验证成功后是否可以关闭
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public Map<String, String> validateHeaders() {
                return null;
            }


            //用于客户自己定义第二次验证的成功后的操作
            @Override
            public void gt3DialogSuccess() {
            }

            //设置是否自定义第二次验证ture为是 默认为false(不自定义)
            //如果为false这边的的完成走gt3DialogSuccessResult，后续流程SDK帮忙走完，不需要做操作
            //如果为true这边的的完成走gt3DialogSuccess，同时需要完成gt3GetDialogResult里面的二次验证，验证完毕记得关闭dialog,调用gt3GeetestUtils.gt3TestFinish();
            @Override
            public boolean gtSetIsCustom() {

                return true;
            }
        });
    }




}
