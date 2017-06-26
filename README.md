# gt3-android-sdk

# master分支为不带Button的验证码,dv-master分支为带Button的验证码，develop分支为两个整合优化版。

# develop本分支为两个整合优化版

# 优化

 1.新增captchaHeaders（） 添加第一次验证数据，gt3SecondResult（） 添加第二次验证数据,添加类型均为Map集合


 2.新增gtIsClick（boolean a） 是只有在有Button的SDK中才有用到，用于监听Button是否被点击，返回为true,表示被点击
 
              public void gtIsClick(boolean a) {
                  if(a){
                  //按键被点击
                  }
              }
            
3.新增客户可以根据自己的需求去自定义API2的请求，可以使用默认申请的API2做二次验证，也可以自己自定义接口

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
          
    4.新增错误码返回，开发人员可以在gt3DialogOnError（String error）这里去得到错误码，具体错误码代表什么，如果出现请联系极验人员协助调试。       

    5.对内部代码的一个优化

    其他使用方法和前面保持一致，请参考前面的SDK的使用方法
# 说明
  
  本SDK包含了2个SDK合成版本，所以集成时小心注意，不要弄混淆了。同时极验会继续努力，给予您更好的SDK
