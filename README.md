# gt3-android-sdk

# develop分支为功能开发版。

# 优化

 1.新增captchaHeaders（） 添加第一次验证数据，gt3SecondResult（） 添加第二次验证数据,添加类型均为Map集合


 2.新增gtIsClick（boolean a） 是只有在有Button的SDK中才有用到，用于监听Button是否被点击，返回为true,表示被点击
 
              public void gtIsClick(boolean a) {
                  if(a){
                  //按键被点击
                  }
              }
            
3.新增客户可以根据自己的需求去自定义API2的请求，可以使用默认申请的API2做二次验证，也可以自己自定义接口（不推荐使用，只是开放出来，后期如果有产品安全问题，不承担责任）

    //设置是否自定义第二次验证，当方法gtSetIsCustom()设置ture为自定义二次验证 默认为false(不自定义)
    //如果设置为false，二次验证完成走gt3DialogSuccessResult，后续流程SDK帮忙走完，不需要用户做操作
    //如果设置为true，二次验证完成走gt3DialogSuccessResult，同时需要完成gt3GetDialogResult里面的二次验证参数提交，验证完毕记得关闭dialog,调用          gt3GeetestUtils.gt3TestFinish();

     情况1.如果使用默认申请的API2做二次验证，那么
         @Override
         public boolean gtSetIsCustom() {

               return false;
         }

         @Override
         public void gt3GetDialogResult(boolean success,String result) {
          //不做任何操作
          }

    情况2.如果使用自定义的API2做二次验证，那么

       @Override
         public boolean gtSetIsCustom() {

               return false;
         }

         @Override
         public void gt3GetDialogResult(boolean success,String result) {

                      if (success) {
        /**
        *  利用异步进行解析这result进行二次验证，结果成功后调用gt3GeetestUtils.gt3TestFinish()方法调用成功后的动画，然后在     gt3DialogSuccessResult执行成功之后的结果
       * //
        //          JSONObject res_json = new JSONObject(result);
        //
        //          Map<String, String> validateParams = new HashMap<>();
        //
        //          validateParams.put("geetest_challenge", res_json.getString("geetest_challenge"));
        //
        //          validateParams.put("geetest_validate", res_json.getString("geetest_validate"));
        //
        //          validateParams.put("geetest_seccode", res_json.getString("geetest_seccode"));

                     gt3GeetestUtils.gt3TestFinish();
              }
          }
          
4.unband模式下（不带按钮）的SDK，新增了网络检测接口。利用广播监控网络状态

       项目清单
        <!-- 注册广播 -->
        <receiver android:name="com.example.gt3unbindsdk.unBind.NetBroadcastReceiver">
            <intent-filter>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
            </intent-filter>
        </receiver>
        
       Activity里面需要NetBroadcastReceiver.mListeners.add(this); 绑定广播
       
       调用方法 
       @Override
        public void onNetChange() {

       if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE) {

              GT3Gt2Dialog  dialog= gt3GeetestUtils.getDialog();
              if(dialog!=null){
                  //弹框样式，务必照着写
                  dialog.setErrDialog("网络不给力", "201");
              }
             }
       }
        
5.新增客户可以根据自己的需求去自定义API1的请求，可以使用默认申请的API1做参数请求，也可以自己自定义接口  （不推荐使用，只是开放出来，后期如果有产品安全问题，不承担责任）


       //首先设置你需要去自己定义，调用getISonto()
       gt3GeetestUtils.getISonto();

       //请求你们自己的api1然后返回一个json数据给我，json格式务必按照如下
        JSONObject jsoninfo = null;
       // String info = "{\"success\":1,\"challenge\":\"4a5cef77243baa51b2090f7258bf1368\",\"gt\":\"019924a82c70bb123aae90d483087f94\",\"new_captcha\":true}";
       //        try {
       //            jsoninfo = new JSONObject(info);
       //                   gt3GeetestUtils.gt3oneto(jsoninfo);
       //
       //        } catch (JSONException e) {
       //            e.printStackTrace();
       //        }

       //最后把你的json传给我
        gt3GeetestUtils.setISonto(jsoninfo);

        以上数据请在初始化之前去修改

6.新增一个验证码Stop接口

        //清空资源，关闭弹框
        gt3GeetestUtils.gt3TestClose();
                

.对内部代码的一个优化

    其他使用方法和前面保持一致，请参考前面的SDK的使用方法
# 说明
  
  本SDK包含了2个SDK合成版本，所以集成时小心注意，不要弄混淆了。同时极验会继续努力，给予您更好的SDK
