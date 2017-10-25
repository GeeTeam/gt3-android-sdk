package com.example.geetestthr;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.geetest.gt3unbindsdk.Bind.GT3GeetestBind;
import com.geetest.gt3unbindsdk.GT3GeetestButton;
import com.geetest.gt3unbindsdk.GT3GeetestUtils;
import com.geetest.gt3unbindsdk.Gt3GeetestTestMsg;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * mainActivity
 */
public class MainUnBindActivity extends AppCompatActivity {

    @BindView(R.id.ll_btn_type)
    GT3GeetestButton llBtnType;
        private static final String captchaURL = "http://www.geetest.com/demo/gt/register-click";
    // 设置二次验证的URL，需替换成自己的服务器URL
    private static final String validateURL = "http://www.geetest.com/demo/gt/validate-click";
    GT3GeetestUtils gt3GeetestUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gt3GeetestUtils =  GT3GeetestUtils.getInstance(MainUnBindActivity.this);
//        gt3GeetestUtils.getISonto();
        gt3GeetestUtils.getGeetest(captchaURL,validateURL,null);
        ButterKnife.bind(this);
        gt3GeetestUtils.setGtListener(new GT3GeetestUtils.GT3Listener() {


            /**
             * num 1 点击验证码的关闭按钮来关闭验证码
             * num 2 点击屏幕关闭验证码
             * num 3 点击返回键关闭验证码
             */
            @Override
            public void gt3CloseDialog(int  num) {
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
             * 验证过程错误
             * 返回的错误码为判断错误类型的依据
             */

            @Override
            public void gt3DialogOnError(String error) {

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
            public void gt3GetDialogResult(boolean success,String result) {

                if (success) {
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
             * 拿到第一个url（API1）返回的数据
             */
            @Override
            public void gt3FirstResult(JSONObject jsonObject) {
            }

            /**
             * 往二次验证里面put数据
             * put类型是map类型
             * 注意map的键名不能是以下三个：geetest_challenge，geetest_validate，geetest_seccode
             */
            @Override
            public Map<String, String> gt3SecondResult() {
                return null;
            }

            /**
             * 二次验证完成的回调
             * result为验证后的数据
             * 根据二次验证返回的数据判断此次验证是否成功
             * 二次验证成功调用 gt3GeetestUtils.gt3TestFinish();
             * 二次验证失败调用 gt3GeetestUtils.gt3TestClose();
             */
            //请求成功数据
            @Override
            public void gt3DialogSuccessResult(String result) {
                if(!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jobj = new JSONObject(result);
                        String sta = jobj.getString("status");

                        if ("success".equals(sta)) {
                            gt3GeetestUtils.gt3TestFinish();
                        } else {
                            gt3GeetestUtils.gt3CloseButton();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    gt3GeetestUtils.gt3CloseButton();
                }
                Toast.makeText(MainUnBindActivity.this,result,Toast.LENGTH_LONG).show();
                Gt3GeetestTestMsg.setCandotouch(false);//这里设置验证成功后是否可以关闭
            }


            /**
             * 验证码加载准备完成
             * 此时弹出验证码
             */
            @Override
            public void gt3DialogReady() {

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
             * 判断自定义按键是否被点击
             */

            @Override
            public void gtOnClick(boolean onclick) {
                if(onclick){
                    //被点击
                    /**
                     * 如果api1也想自己自定,那么访问您的服务器后讲INFO数据以如下格式传给我
                     *  gt3GeetestUtils.gtSetApi1Json(jsonObject);
                     */
//                    mGtppDlgTask =  new GtppDlgTask();
//                    mGtppDlgTask.execute();

                    gt3GeetestUtils.setDialogTouch(true);

                }
            }


        });

    }

    /**
     * 以下代码是模拟自定义api1的异步请求
     * 需要自定义api1的可以参考这边的写法
     */
    GT3GeetestBind captcha;
    GtppDlgTask mGtppDlgTask;
    // 请求的API1
    private class GtppDlgTask extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... params) {
            captcha = new GT3GeetestBind(captchaURL,validateURL,null);
            String Str_map ="?";
            JSONObject jsonObject;

            jsonObject = captcha.check2Server(Str_map);

            return jsonObject;

        }

        @Override
        protected void onPostExecute(JSONObject parmas) {
            //{"success":1,"challenge":"323b14a7fe13fcfb7c830bb44a687e7f","gt":"019924a82c70bb123aae90d483087f94","new_captcha":true}
            gt3GeetestUtils.gtSetApi1Json(parmas);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        gt3GeetestUtils.cancelUtils();
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        gt3GeetestUtils.changeDialogLayout();
    }



}
