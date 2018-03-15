package com.example.geetestthr;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;

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

    }

    private void init() {
        /**
         * 初始化
         * 务必放在onCreate方法里面执行
         */
        gt3GeetestUtils = new GT3GeetestUtilsBind(MainBindActivity.this);

        /**
         * 点击调起验证
         */
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gt3GeetestUtils.getGeetest(MainBindActivity.this, captchaURL, validateURL, null, new GT3GeetestBindListener() {
                    /**
                     * num 1 点击验证码的关闭按钮来关闭验证码
                     * num 2 点击屏幕关闭验证码
                     * num 3 点击返回键关闭验证码
                     */
                    @Override
                    public void gt3CloseDialog(int  num) {
                    }


                    /**
                     * 验证码加载准备完成
                     * 此时弹出验证码
                     */
                    @Override
                    public void gt3DialogReady() {
                    }


                    /**
                     * 拿到第一个url（API1）返回的数据
                     * 该方法只适用于不使用自定义api1时使用
                     */
                    @Override
                    public void gt3FirstResult(JSONObject jsonObject) {
                    }


                    /**
                     * 往API1请求中添加参数
                     * 该方法只适用于不使用自定义api1时使用
                     * 添加数据为Map集合
                     * 添加的数据以get形式提交
                     */
                    @Override
                    public Map<String, String> gt3CaptchaApi1() {
                        Map<String, String> map = new HashMap<String, String>();
                        return map;
                    }

                    /**
                     * 设置是否自定义第二次验证ture为是 默认为false(不自定义)
                     * 如果为false后续会走gt3GetDialogResult(String result)拿到api2需要的参数
                     * 如果为true后续会走gt3GetDialogResult(boolean a, String result)拿到api2需要的参数
                     * result为二次验证所需要的数据
                     */
                    @Override
                    public boolean gt3SetIsCustom() {
                        return false;
                    }

                    /**
                     * 拿到第二个url（API2）需要的数据
                     * 该方法只适用于不使用自定义api2时使用
                     */
                    @Override
                    public void gt3GetDialogResult(String result) {
                    }


                    /**
                     * 自定义二次验证，也就是当gtSetIsCustom为ture时才执行
                     * 拿到第二个url（API2）需要的数据
                     * 在该回调里面自行请求api2
                     * 对api2的结果进行处理
                     */
                    @Override
                    public void gt3GetDialogResult(boolean status, String result) {

                        if (status) {


                            /**
                             * 基本使用方法：
                             *
                             * 1.取出该接口返回的三个参数用于自定义二次验证
                             * JSONObject res_json = new JSONObject(result);
                             *
                             * Map<String, String> validateParams = new HashMap<>();
                             *
                             * validateParams.put("geetest_challenge", res_json.getString("geetest_challenge"));
                             *
                             * validateParams.put("geetest_validate", res_json.getString("geetest_validate"));
                             *
                             * validateParams.put("geetest_seccode", res_json.getString("geetest_seccode"));
                             *
                             * 新加参数可以继续比如
                             *
                             * validateParams.put("user_key1", "value1");
                             *
                             * validateParams.put("user_key2", "value2");
                             *
                             * 2.自行做网络请求，请求时用上前面取出来的参数
                             *
                             * 3.拿到网络请求后的结果，判断是否成功
                             *
                             * 二次验证成功调用 gt3GeetestUtils.gt3TestFinish();
                             * 二次验证失败调用 gt3GeetestUtils.gt3TestClose();
                             */
                        }
                    }


                    /**
                     * 需要做验证统计的可以打印此处的JSON数据
                     * JSON数据包含了极验每一步的运行状态和结果
                     */
                    @Override
                    public void gt3GeetestStatisticsJson(JSONObject jsonObject) {
                    }

                    /**
                     * 往二次验证里面put数据
                     * put类型是map类型
                     * 注意map的键名不能是以下三个：geetest_challenge，geetest_validate，geetest_seccode
                     * 该方法只适用于不使用自定义api2时使用
                     */
                    @Override
                    public Map<String, String> gt3SecondResult() {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("testkey","12315");
                        return map;

                    }

                    /**
                     * 二次验证完成的回调
                     * 该方法只适用于不使用自定义api2时使用
                     * result为俄二次验证后的数据
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
                     * 验证过程错误
                     * 返回的错误码为判断错误类型的依据
                     */

                    @Override
                    public void gt3DialogOnError(String error) {
                        Log.i("dsd","gt3DialogOnError");

                    }
                });
                //设置是否可以点击屏幕边缘关闭验证码
                gt3GeetestUtils.setDialogTouch(true);

            }
        });
    }



    /**
     * 以下代码是模拟自定义api1的异步请求
     * 需要自定义api1的可以参考这边的写法
     */
//    GtApi1json mGtApi1json;
//    GT3Geetest captcha= new GT3Geetest(
//            captchaURL, validateURL,null
//    );
//
//    class GtApi1json extends AsyncTask<Void, Void, JSONObject> {
//
//        @Override
//        protected JSONObject doInBackground(Void... params) {
//            String map_st = "";
//            return captcha.check2Server(map_st);
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject parmas) {
//            //parmas格式"{\"success\":1,\"challenge\":\"4a5cef77243baa51b2090f7258bf1368\",\"gt\":\"019924a82c70bb123aae90d483087f94\",\"new_captcha\":true}"
//
//            /**
//             * 如果api1也想自己自定,那么访问您的服务器后将JSON数据以如下格式传给我
//             *  gt3GeetestUtils.gtSetApi1Json(jsonObject);
//             */
//            gt3GeetestUtils.gtSetApi1Json(parmas);
//            gt3GeetestUtils.getGeetest(MainBindActivity.this,captchaURL, validateURL,null);
//            gt3GeetestUtils.setDialogTouch(true);
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 页面关闭时释放资源
         */
        gt3GeetestUtils.cancelUtils();
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        /**
         * 设置后，界面横竖屏不会关闭验证码，推荐设置
         */
        gt3GeetestUtils.changeDialogLayout();
    }


}


