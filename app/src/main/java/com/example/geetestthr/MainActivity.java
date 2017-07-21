package com.example.geetestthr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.geetest.gt3unbindsdk.GT3GeetestButton;
import com.geetest.gt3unbindsdk.GT3GeetestUtils;

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

    Button bu_go;
    // 设置获取id，challenge，success的URL，需替换成自己的服务器URL
    private static final String captchaURL = "http://www.geetest.com/demo/gt/register-slide";
    // 设置二次验证的URL，需替换成自己的服务器URL
    private static final String validateURL = "http://www.geetest.com/demo/gt/validate-slide";
     GT3GeetestUtils gt3GeetestUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gt3GeetestUtils =  GT3GeetestUtils.getInstance(MainActivity.this);

        /**
         * 如果api1也想自己自定义，开放这边的代码gt3GeetestUtils.setISonto(jsoninfo);并给我传递下面格式的json数据
         */
//        gt3GeetestUtils.getISonto();
//        JSONObject jsoninfo = null;
//
//        String info = "{\"success\":1,\"challenge\":\"4a5cef77243baa51b2090f7258bf1368\",\"gt\":\"019924a82c70bb123aae90d483087f94\",\"new_captcha\":true}";
//        try {
//            jsoninfo = new JSONObject(info);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        gt3GeetestUtils.setISonto(jsoninfo);



        gt3GeetestUtils.getGeetest(captchaURL,validateURL,null);



        ButterKnife.bind(this);


        gt3GeetestUtils.setGtListener(new GT3GeetestUtils.GT3Listener() {


            /**
             * Api1可以在这添加参数
             */
            @Override
            public Map<String, String> captchaHeaders() {
                return null;
            }

            /**
             * 验证过程中有错误会走这里
             */
            @Override
            public void gt3DialogOnError(String error) {

            }

            /**
             * 点击验证码的关闭按钮来关闭验证码
             */
            @Override
            public void gt3CloseDialog() {

            }

            /**
             * 点击屏幕关闭验证码
             */
            @Override
            public void gt3CancelDialog() {

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



            /**
             * 第一次次请求后数据
             */
            @Override
            public void gt3FirstResult(JSONObject jsonObject) {

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
            //请求成功数据
            @Override
            public void gt3DialogSuccessResult(String result) {
//                Gt3GeetestTestMsg.setCandotouch(false);//这里设置验证成功后是否可以关闭

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
             * 判断自定义按键是否被点击
             */

            @Override
            public void gtIsClick(boolean a) {
                if(a){
                //被点击
                }
            }

            /**
             * 当验证码放置10分钟后，重新启动验证码
             */
            @Override
            public void rege() {

//                gt3GeetestUtils.getGeetest(captchaURL,validateURL,null);

            }
        });



    }


}
