package com.example.geetestthr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.geetest.gt3unbindsdk.unBind.GT3Geetest2Utils;
import com.geetest.gt3unbindsdk.unBind.GT3Toast;

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


                gt3GeetestUtils.setGtListener(new GT3Geetest2Utils.GT3Listener() {


                    /**
                     * 点击验证码的关闭按钮来关闭验证码
                     */
                    @Override
                    public void gt3CloseDialog() {
                        GT3Toast.show("验证未通过 请重试", getApplicationContext());
                    }


                    /**
                     * 点击屏幕关闭验证码
                     */
                    @Override
                    public void gt3CancelDialog() {
                        GT3Toast.show("验证未通过 请重试", getApplicationContext());
                    }


                    /**
                     * 验证码加载准备完成
                     */
                    @Override
                    public void gt3DialogReady() {

                    }


                    /**
                     * 往API1请求中添加参数
                     */
                    @Override
                    public Map<String, String> captchaHeaders() {
                        return null;
                    }


                    /**
                     * 设置网络的头部信息
                     */
                    @Override
                    public Map<String, String> validateHeaders() {
                        return null;
                    }


                    /**
                     * 设置是否自定义第二次验证ture为是 默认为false(不自定义)
                     * 如果为false这边的的完成走gt3GetDialogResult(String result) ，后续流程SDK帮忙走完，不需要做操作
                     * 如果为true这边的的完成走gt3GetDialogResult(boolean a, String result)，同时需要完成gt3GetDialogResult里面的二次验证，验证完毕记得关闭dialog,调用gt3GeetestUtils.gt3TestFinish();
                     * 完成方法统一是gt3DialogSuccess
                     */

                    @Override
                    public boolean gtSetIsCustom() {
                        return false;
                    }

                    /**
                     * 当验证码放置10分钟后，重新启动验证码
                     */
                    @Override
                    public void gereg() {

                    }


                    /**
                     * 拿到第一个url返回的数据
                     */
                    @Override
                    public void gt3FirstResult(JSONObject jsonObject) {

                    }

                    /**
                     * 拿到二次验证需要的数据
                     */
                    @Override
                    public void gt3GetDialogResult(String result) {

                    }


                    /**
                     * 自定义二次验证，当gtSetIsCustom为ture时执行这里面的代码
                     */
                    @Override
                    public void gt3GetDialogResult(boolean a, String result) {

                        if (a) {

                            /**
                             *  利用异步进行解析这result进行二次验证，结果成功后调用gt3GeetestUtils.gt3TestFinish()方法调用成功后的动画，然后在gt3DialogSuccess执行成功之后的结果
                             * //                JSONObject res_json = new JSONObject(result);
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


                    /**
                     * 往二次验证里面put数据，是map类型,注意map的键名不能是以下三个：geetest_challenge，geetest_validate，geetest_seccode
                     */
                    @Override
                    public Map<String, String> gt3SecondResult() {
                        return null;
                    }


                    /**
                     * 验证全部走完的回调，result为验证后的数据
                     */
                    @Override
                    public void gt3DialogSuccessResult(String result) {
                        GT3Toast.show(result, Main3Activity.this);
                    }


                    /**
                     * 验证全部走完的回调，用于弹出完成框
                     */

                    @Override
                    public void gt3DialogSuccess() {

//                GT3Toast.show("验证成功", getApplicationContext());

                    }

                    /**
                     * 验证过程中有错误会走这里
                     */

                    @Override
                    public void gt3DialogOnError(String error) {

                        gt3GeetestUtils.cancelAllTask();
                    }
                });


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                gt3GeetestUtils.getGeetest(Main3Activity.this);


            }
        });
    }

    private void init() {
//        new GT3ReadyMsg().setLogoid(R.drawable.success);//设置准备界面头部的gif图片
        gt3GeetestUtils =new GT3Geetest2Utils(Main3Activity.this);
        gt3GeetestUtils.gtDologo(captchaURL, validateURL,null);//加载验证码之前判断有没有logo





        /**
         * 如果api1也想自己自定义，开放这边的代码gt3GeetestUtils.setISonto(jsoninfo);并给我传递下面格式的json数据
         */
//        gt3GeetestUtils.getISonto();
//        JSONObject jsoninfo = null;
//
//        String info = "{\"success\":1,\"challenge\":\"4a5cef77243baa51b2090f7258bf1368\",\"gt\":\"019924a82c70bb123aae90d483087f94\",\"new_captcha\":true}";
//        try {
//            jsoninfo = new JSONObject(info);
////                    gt3GeetestUtils.gt3oneto(jsoninfo);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        gt3GeetestUtils.setISonto(jsoninfo);
    }






}


