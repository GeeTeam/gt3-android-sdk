package com.example.geetestthr;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.geetest.gt3unbindsdk.Bind.GT3GeetestBind;
import com.geetest.gt3unbindsdk.Bind.GT3GeetestUtilsBind;
import com.geetest.gt3unbindsdk.Bind.GT3Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainBindActivity extends Activity {

    private static final String captchaURL = "http://www.geetest.com/demo/gt/register-slide";
    // 设置二次验证的URL，需替换成自己的服务器URL
    private static final String validateURL = "http://www.geetest.com/demo/gt/validate-slide";
    private EditText et1;
    private EditText et2;
    private Button btn;
    private GT3GeetestUtilsBind gt3GeetestUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        btn = (Button) findViewById(R.id.btn);
        init();
        gt3GeetestUtils.setGtListener(new GT3GeetestUtilsBind.GT3Listener() {

            /**
             * 点击验证码的关闭按钮来关闭验证码
             */
            @Override
            public void gt3CloseDialog() {
                GT3Toast.show("验证未通过 请重试", getApplicationContext());
            }


            /**
             * 点击屏幕关闭验证码
             * 点击返回键关闭验证码
             */
            @Override
            public void gt3CancelDialog() {

            }

            /**
             * 验证码加载准备完成
             * 此时弹出验证码
             */
            @Override
            public void gt3DialogReady() {

            }

            /**
             * 验证码开始
             * 可以理解成点击按钮开始启动验证码
             */
            @Override
            public void gt3FirstGo() {
            }


            /**
             * 拿到第一个url（API1）返回的数据
             */
            @Override
            public void gt3FirstResult(JSONObject jsonObject) {
            }


            /**
             * 往API1请求中添加参数
             * 添加数据为Map集合
             * 添加的数据以get形式提交
             */
            @Override
            public Map<String, String> captchaApi1() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("t", System.currentTimeMillis()+"");
                return map;
            }

            /**
             * 设置是否自定义第二次验证ture为是 默认为false(不自定义)
             * 如果为false这边的的完成走gt3GetDialogResult(String result)
             * 如果为true这边的的完成走gt3GetDialogResult(boolean a, String result)
             * result为二次验证所需要的数据
             */
            @Override
            public boolean gtSetIsCustom() {
                return false;
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
            public void gt3GetDialogResult(boolean stu, String result) {

                if (stu) {
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
                     //  二次验证成功调用 gt3GeetestUtils.gt3TestFinish();
                     //  二次验证失败调用 gt3GeetestUtils.gt3TestClose();
                     */
                }
            }


            /**
             * 当验证码放置10分钟后
             * 此接口用到的不多
             */
            @Override
            public void gereg_21() {
            }


            /**
             * 需要做验证统计的可以打印此处的JSON数据
             * JSON数据包含了极验每一步的运行状态
             */
            @Override
            public void gt3GeetestStatisticsJson(JSONObject jsonObject) {
            }

            /**
             * 往二次验证里面put数据
             * put类型是map类型
             * 注意map的键名不能是以下三个：geetest_challenge，geetest_validate，geetest_seccode
             */
            @Override
            public Map<String, String> gt3SecondResult() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("testkey","12315");
                return map;
            }

            /**
             * 二次验证完成的回调
             * result为验证后的数据
             * 根据二次验证返回的数据判断此次验证是否成功
             * 二次验证成功调用 gt3GeetestUtils.gt3TestFinish();
             * 二次验证失败调用 gt3GeetestUtils.gt3TestClose();
             */
            @Override
            public void gt3DialogSuccessResult(String result) {

                if(!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jobj = new JSONObject(result);
                        String sta = jobj.getString("status");
                        if ("success".equals(sta)) {
                            gt3GeetestUtils.gt3TestFinish();
                        } else {
                            gt3GeetestUtils.gt3TestClose();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    gt3GeetestUtils.gt3TestClose();
                }
            }

            /**
             * ajax请求返回的值
             * 用于判断是什么类型的验证
             * slide 滑动验证 fullpage 一键通过 click 大图点字验证
             */
            @Override
            public void gt3AjaxResult(String result) {
            }


            /**
             * 验证过程错误
             * 返回的错误码为判断错误类型的依据
             */

            @Override
            public void gt3DialogOnError(String error) {
                gt3GeetestUtils.cancelAllTask();

            }
        });
    }

    private void init() {
        /**
         * 初始化
         * 务必放在onCreate方法里面执行
         */
        gt3GeetestUtils = new GT3GeetestUtilsBind(MainBindActivity.this);
        gt3GeetestUtils.gtDologo(captchaURL, validateURL,null);//加载验证码之前判断有没有logo

        /**
         * 点击调起验证
         */
         btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gt3GeetestUtils.getGeetest(MainBindActivity.this);
                //设置是否可以点击屏幕边缘关闭验证码
                gt3GeetestUtils.setDialogTouch(true);

            }
        });
    }



    /**
     * 以下代码是模拟自定义api1的异步请求
     * 需要自定义api1的可以参考这边的写法
     */
    GtApi1json mGtApi1json;
    GT3GeetestBind captcha= new GT3GeetestBind(
            captchaURL, validateURL,null
    );

    class GtApi1json extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... params) {
            String map_st = "";
            return captcha.check2Server(map_st);
        }

        @Override
        protected void onPostExecute(JSONObject parmas) {
            //parmas格式"{\"success\":1,\"challenge\":\"4a5cef77243baa51b2090f7258bf1368\",\"gt\":\"019924a82c70bb123aae90d483087f94\",\"new_captcha\":true}"

            /**
             * 如果api1也想自己自定,那么访问您的服务器后将JSON数据以如下格式传给我
             *  gt3GeetestUtils.gtSetApi1Json(jsonObject);
             */
            gt3GeetestUtils.gtSetApi1Json(parmas);
            gt3GeetestUtils.getGeetest(MainBindActivity.this);
            gt3GeetestUtils.setDialogTouch(true);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        gt3GeetestUtils.setGtListener(null);
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        gt3GeetestUtils.changeDialogLayout();
    }


}


