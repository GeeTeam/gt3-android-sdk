# gt3-android-sdk

# develop分支为功能开发版。

# 概述与资源

极验验证3.0 Android SDK提供给集成Android原生客户端开发的开发者使用。
集成极验验证3.0的时，需要先了解极验验证3.0的 [产品结构](http://docs.geetest.com/install/overview/#产品结构)。并且必须要先在您的后端搭建相应的**服务端SDK**，并配置从[极验后台]()获取的`<gt_captcha_id>`和`<geetest_key>`用来配置您集成了极验服务端sdk的后台。

### 手动下载获取

使用从github下载`.zip`文件获取最新的sdk。

[Github: gt3-android-sdk](https://github.com/GeeTeam/gt3-android-sdk)

## 手动导入SDK

从github上获取到`.aar`文件，同时将获取的`.aar`文件拖拽到工程中的libs文件夹下。
[Github: aar](https://github.com/GeeTeam/gt3-android-sdk/tree/develop/app/libs)

在拖入`.aar`到libs文件夹后, 还要检查`.aar`是否被添加到**Library**,要在项目的build.gradle下添加如下代码：

```java
        repositories {
            flatDir {
                dirs 'libs'
            }
        }

```

并且要手动将aar包添加依赖：

```java
       compile(name: 'gt3geetest-sdk', ext: 'aar')

``` 

### 如需使用依赖, 需要在你的主工程文件里加入一下配置

```java
dependencies {
	compile 'gt3bind.android:sdk:1.1.0'
	}
``` 
 
## 初始化

### 在AndroidManifest.xml文件中添加权限

```java
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />

```

### 设置服务端URL以及初始化验证码

| Attribute | Descripion |
| ------ | ------ |
|captchaURL|设置获取id，challenge，success的URL，需替换成自己的服务器URL|
|validateURL|设置二次验证的URL，需替换成自己的服务器URL|

```java
       （unband模式下）
        gt3GeetestUtils =new GT3Geetest2Utils(Main3Activity.this);
        gt3GeetestUtils.gtDologo(captchaURL, validateURL,null);//加载验证码之前判断有没有logo
	//点击调用
	gt3GeetestUtils.getGeetest(Main3Activity.this);
	
       （按键模式下）
	gt3GeetestUtils =  GT3GeetestUtils.getInstance(MainActivity.this);
	gt3GeetestUtils.getGeetest(captchaURL,validateURL,null);
```


### 验证码加载过程中的接口


```java
  
   gt3GeetestUtils.setGtListener(new GT3GeetestUtils.GT3Listener() {
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

            }


            /**
             * 验证全部走完的回调，用于弹出完成框
             */

            @Override
            public void gt3DialogSuccess() {

                    GT3Toast.show("验证成功", getApplicationContext());

            }

            /**
             * 验证过程中有错误会走这里
             */

            @Override
            public void gt3DialogOnError(String error) {
                }
		
        });
``` 


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
          
4.自定义API1的请求，可以使用默认申请的API1做参数请求，也可以自己自定义接口  （不推荐使用，只是开放出来，后期如果有产品安全问题，不承担责任）

   //首先设置你需要去自己定义，调用getISonto()
   gt3GeetestUtils.getISonto();

   //请求你们自己的api1然后返回一个json数据给我，json格式务必按照如下
    
     	JSONObject jsoninfo = null;
   	 String info = "		{\"success\":1,\"challenge\":\"4a5cef77243baa51b2090f7258bf1368\",\"gt\":\"019924a82c70bb123aae90d483087f94\",\"new_captcha\":true}";
  	 jsoninfo = new JSONObject(info);
 	  gt3GeetestUtils.gtSetApi1Json(jsoninfo);

   //最后把你的json传给我
    gt3GeetestUtils.gtSetApi1Json(jsoninfo);

    以上数据请在初始化之前去修改，就可以自定义API1接口了




# 错误码 
1.timeoutError 201

   全局网络请求超时，请检查网络连接

2.forbiddenError 202

   验证码停用，检查下验证码是否到期

3.webViewError 204

   webview加载出现的错误

4.httpError 205

   api1接口返回为null，查看api1的参数和地址是否有误，网络保持畅通

5.httpError 206

   gettype接口返回为null，查看gettype的参数和地址是否有误，网络保持畅通

6.httpError 207

   getphp接口返回为null，查看getphp的参数和地址是否有误，网络保持畅通

7.httpError 208

   ajax接口返回返回为null，查看ajax的参数和地址是否有误，网络保持畅通
    
8.httpError 209

   api2接口返回返回为null，查看api2的参数和地址是否有误，网络保持畅通

9.尝试过多 _01

   连续刷新5次

10.尝试过多 _12

   连续验证错误6次

11.web请求错误 _105

   web弹出后没有网络滑动会报错
    
12.初始化错误 211

   验证码初始化回调用2个接口，找找这里的问题

13.初始化错误 222

   网络请求时，此时已经断网
    
14.challenge错误  _02

   challenge 过时，或者重复使用
    
15.challenge错误  _22

   challenge 没传  
    
16.gt错误  _31

   gt 没传  



以上是比较常见的错误码

# 代码混淆 
1.代码已经进行了混淆，请不要重复混淆
.在您的proguard-rules.pro下keep掉验证码即可

   
# 说明
  
  1.本SDK包含了2个SDK合成版本，所以集成时小心注意，不要弄混淆了。同时极验会继续努力，给予您更好的SDK
  2.Eclipse版本的SDK属于定制开发
