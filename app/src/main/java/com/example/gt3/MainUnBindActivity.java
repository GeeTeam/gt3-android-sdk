package com.example.gt3;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.geetest.sdk.GT3GeetestButton;
import com.geetest.sdk.GT3GeetestListener;
import com.geetest.sdk.GT3GeetestUtils;
import com.geetest.sdk.Gt3GeetestTestMsg;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainUnBindActivity extends AppCompatActivity {

    private static String TAG = MainUnBindActivity.class.getSimpleName();

    /**
     * api1，需替换成自己的服务器URL
     */
    private static final String captchaURL = "http://www.geetest.com/demo/gt/register-click";
    /**
     * api2，需替换成自己的服务器URL
     */
    private static final String validateURL = "http://www.geetest.com/demo/gt/validate-click";

    private GT3GeetestButton geetestButton;
    private GT3GeetestUtils gt3GeetestUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_unbind);

        geetestButton = (GT3GeetestButton) findViewById(R.id.btn_geetest);

        // TODO 若使用默认API1请求 则无需多余设置
        // TODO 若使用自定义api2请求 则调用gt3GeetestUtils.getISonto()，且在gtOnClick回调开启api1
        // TODO 若使用默认api2请求 则gt3SetIsCustom设置为false
        // TODO 若要开启自定义api2 则需 gt3SetIsCustom设置为true，且实现gt3GetDialogResult(boolean status, String result)方法内逻辑

        // TODO 本示例为非自定义api1及api2

        gt3GeetestUtils = GT3GeetestUtils.getInstance(MainUnBindActivity.this);
        // 设置是否可以点击Dialog灰色区域关闭验证码
        gt3GeetestUtils.setDialogTouch(true);
        // 设置debug模式，开代理抓包可使用，默认关闭，TODO 生产环境务必设置为false
        gt3GeetestUtils.setDebug(true);
        // 设置加载webview超时时间，单位毫秒，默认15000，仅且webview加载静态文件超时，不包括之前的http请求
        gt3GeetestUtils.setTimeout(15000);
        // 设置webview请求超时(用户点选或滑动完成，前端请求后端接口)，单位毫秒，默认10000
        gt3GeetestUtils.setWebviewTimeout(10000);
        // gt3GeetestUtils.getISonto();
        gt3GeetestUtils.getGeetest(captchaURL, validateURL, null, new GT3GeetestListener() {
            /**
             * @param num 1: 点击验证码的关闭按钮, 2: 点击屏幕关闭验证码, 3: 点击返回键关闭验证码
             */
            @Override
            public void gt3CloseDialog(int num) {
                Log.i(TAG, "gt3CloseDialog-->num: " + num);
            }

            /**
             * 为API1接口添加数据，数据拼接在URL后，API1接口默认get请求
             */
            @Override
            public Map<String, String> gt3CaptchaApi1() {
                Log.i(TAG, "gt3CaptchaApi1");
                Map<String, String> map = new HashMap<String, String>();
                map.put("time", "" + System.currentTimeMillis());
                return map;
            }

            /**
             * api1接口返回数据
             */
            @Override
            public void gt3FirstResult(JSONObject jsonObject) {
                Log.i(TAG, "gt3FirstResult-->" + jsonObject);
            }


            /**
             * 准备完成，即将弹出验证码
             */
            @Override
            public void gt3DialogReady() {
                Log.i(TAG, "gt3DialogReady");
            }

            /**
             * 数据统计，从开启验证到成功加载验证码结束，具体解释详见GitHub文档
             */
            public void gt3GeetestStatisticsJson(JSONObject jsonObject) {
                Log.i(TAG, "gt3GeetestStatisticsJson-->" + jsonObject);
            }

            /**
             * 返回是否自定义api2，true为自定义api2
             * false： gt3GetDialogResult(String result)，返回api2需要参数
             * true： gt3GetDialogResult(boolean a, String result)，返回api2需要的参数
             */
            @Override
            public boolean gt3SetIsCustom() {
                Log.i(TAG, "gt3SetIsCustom");
                return false;
            }

            /**
             * 用户滑动或点选完成后触发，gt3SetIsCustom配置为false才走此接口
             *
             * @param result api2接口需要参数
             */
            @Override
            public void gt3GetDialogResult(String result) {
                Log.i(TAG, "gt3GetDialogResult-->" + result);
            }

            /**
             * 用户滑动或点选完成后触发，gt3SetIsCustom配置为true才走此接口
             *
             * @param status 验证是否成功
             * @param result api2接口需要参数
             */
            @Override
            public void gt3GetDialogResult(boolean status, String result) {
                Log.i(TAG, "gt3GetDialogResult-->status: " + status + "result: " + result);
                if (status) {
                    try {
                        // 1.取出该接口返回的三个参数用于自定义二次验证
                        JSONObject jsonObject = new JSONObject(result);
                        Map<String, String> validateParams = new HashMap<>();
                        validateParams.put("geetest_challenge", jsonObject.getString("geetest_challenge"));
                        validateParams.put("geetest_validate", jsonObject.getString("geetest_validate"));
                        validateParams.put("geetest_seccode", jsonObject.getString("geetest_seccode"));
                        // 可继续添加其余参数
                        validateParams.put("test", "test");
                        // 开启自定义请求api2
                        new RequestAPI2().execute(jsonObject.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    gt3GeetestUtils.gt3CloseButton();
                }
            }

            /**
             * 为API2接口添加数据，数据拼接在URL后，API2接口默认get请求
             * 默认已有数据：geetest_challenge，geetest_validate，geetest_seccode
             * TODO 注意： 切勿重复添加以上数据
             */
            @Override
            public Map<String, String> gt3SecondResult() {
                Log.i(TAG, "gt3SecondResult");
                Map<String, String> map = new HashMap<String, String>();
                map.put("test", "test");
                return map;
            }

            /**
             * api2完成回调，判断是否验证成功，且成功调用gt3TestFinish，失败调用gt3TestClose
             *
             * @param result api2接口返回数据
             */
            @Override
            public void gt3DialogSuccessResult(String result) {
                Log.i(TAG, "gt3DialogSuccessResult-->" + result);
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String status = jsonObject.getString("status");
                        if ("success".equals(status)) {
                            gt3GeetestUtils.gt3TestFinish();
                        } else {
                            gt3GeetestUtils.gt3CloseButton();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    gt3GeetestUtils.gt3CloseButton();
                }
                // 设置验证成功后是否可以关闭
                Gt3GeetestTestMsg.setCandotouch(true);
            }

            /**
             * @param error 返回错误码，具体解释见GitHub文档
             */
            @Override
            public void gt3DialogOnError(String error) {
                Log.i(TAG, "gt3DialogOnError-->" + error);
            }

            /**
             * 判断自定义按键是否被点击
             */
            @Override
            public void gtOnClick(boolean onclick) {
                if (onclick) {
                    // 开启自定义API1
                    // new RequestAPI1().execute();
                }
            }
        });
    }

    /**
     * 请求api1
     */
    class RequestAPI1 extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... params) {
            String string = HttpUtils.requsetUrl(captchaURL);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(string);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject parmas) {
            // 继续验证
            Log.i(TAG, "RequestAPI1-->onPostExecute: " + parmas);
            // 无需判空处理，SDK内部已经处理
            gt3GeetestUtils.gtSetApi1Json(parmas);
        }
    }

    /**
     * 请求api2
     */
    class RequestAPI2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if (!TextUtils.isEmpty(params[0])) {
                return HttpUtils.requestPost(validateURL, params[0]);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "RequestAPI2-->onPostExecute: " + result);
            if (!TextUtils.isEmpty(result)) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("success".equals(status)) {
                        gt3GeetestUtils.gt3TestFinish();
                    } else {
                        gt3GeetestUtils.gt3CloseButton();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                gt3GeetestUtils.gt3CloseButton();
            }
            // 设置验证成功后是否可以关闭
            Gt3GeetestTestMsg.setCandotouch(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * TODO 务必调用
         * 页面关闭时释放资源
         */
        gt3GeetestUtils.cancelUtils();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        /**
         * 横竖屏切换适配
         */
        gt3GeetestUtils.changeDialogLayout();
    }

}
