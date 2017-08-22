# gt3-android-sdk

# 概述与资源

极验验证3.0 Android SDK提供给集成Android原生客户端开发的开发者使用。
集成极验验证3.0的时，需要先了解极验验证3.0的 [产品结构](http://docs.geetest.com/install/overview/#产品结构)。并且必须要先在您的后端搭建相应的**服务端SDK**，并配置从[极验后台]()获取的`<gt_captcha_id>`和`<geetest_key>`用来配置您集成了极验服务端sdk的后台。

# 安装

## 获取SDK

### 使用`git`命令从Github获取

```
git clone https://github.com/GeeTeam/gt3-android-sdk.git
```

### 手动下载获取

使用从github下载`.zip`文件获取最新的sdk。

[Github: gt3-android-sdk](https://github.com/GeeTeam/gt3-android-sdk)

## 手动导入SDK

从github上获取到`.aar`文件，同时将获取的`.aar`文件拖拽到工程中的libs文件夹下。
[Github: aar](https://github.com/GeeTeam/gt3-android-sdk/tree/master/app/libs)

1.在拖入`.aar`到libs文件夹后, 还要检查`.aar`是否被添加到**Library**,要在项目的build.gradle下添加如下代码：

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
 
2. 在AndroidManifest.xml文件中添加权限

```java
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />

```

3.设置服务端URL以及初始化验证码
```java
    //设置获取id，challenge，success的URL，需替换成自己的服务器URL
    private static final String captchaURL = "http://www.geetest.com/demo/gt/register-click";
    // 设置二次验证的URL，需替换成自己的服务器URL
    private static final String validateURL = "http://www.geetest.com/demo/gt/validate-click";
```
4.重写gt3GeetestUtils.setGtListener接口回调，具体用法查看demo，下面也会列出

```java
   gt3GeetestUtils =new GT3GeetestUtilsBind(Main3Activity.this);
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
            public Map<String, String> captchaApi1() {
                return null;
            }


            /**
             * ajax请求返回的值，用于判断验证类型
             */

            @Override
            public void gt3AjaxResult(String result) {
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
	    //对验证结果进行判断 是否成功
  		try {
                    JSONObject jobj = new JSONObject(result);
                    String sta  = jobj.getString("status");

                    if("success".equals(sta))
                    {
                        gt3GeetestUtils.gt3TestFinish();
                    }else
                    {
                        gt3GeetestUtils.gt3CloseButton();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            /**
             * 验证全部走完的回调，用于弹出完成框
             */

            @Override
            public void gt3DialogSuccess() {

                    GT3Toast.show("验证成功", getApplicationContext());

            }

            /**
             * 验证过程中出现错误
             */

            @Override
            public void gt3DialogOnError(String error) {
                }
		
        });
``` 
5.加载验证码
```java
       （band模式下）--点击后会有一个加载框，中间有一个gif在转动
        gt3GeetestUtils =new GT3GeetestUtilsBind(Main3Activity.this);
        gt3GeetestUtils.gtDologo(captchaURL, validateURL,null);//加载验证码之前判断有没有logo
	//点击按钮调用
	gt3GeetestUtils.getGeetest(Main3Activity.this);
	
       （unband模式下）--拥有极验的自定义控件
	gt3GeetestUtils =  GT3GeetestUtils.getInstance(MainActivity.this);
	gt3GeetestUtils.getGeetest(captchaURL,validateURL,null);
```



以上是常规的集成，由于客户的需求，SDK提供了相应的接口，下面是接口的详细说明




# 接口详细说明（分2种模式讲解）

1.gt3CloseDialog 验证码弹出后左下角的X按键的接口回调，一般来说用不到

2.gt3CancelDialog 验证码弹出后点击屏幕任意一处弹框关闭的接口回调，一般来说用不到

3.gt3DialogReady 验证码弹准备完成，所有请求正常，准备弹出的接口回调，一般用于统计是否正常弹出验证码

4.gt3FirstGo 验证码的第一个接口（api1）开始请求的接口回调，一般用于判断是否进入到验证码的流程

5.gt3FirstResult api1请求正常，且拿到的了JSON数据，在参数里面返回，一般用于查看api1返回数据是否正常

6.captchaApi1 往api1后面追加自定义参数且这里的参数是以get形式提交，传递一个map集合即可，用于向api1里面添加请求参数，用的比较少，可以可以直接在设置api1的时候设置在后面，可以不用在这里追加（ private static final String captchaURL = "http://www.geetest.com/demo/gt/register-click?xx=xx&vv=vv"）

7.gtSetIsCustom 用于设置自定义api2的开关，如果你想自定义api2，一定要把return值设置为ture

8.gt3GetDialogResult 这个回调有两个，其中有一个只有返回String result，这个用于不自定义api2，执行完验证操作后的数据结果返回，其他一个有返回boolean bol, String result这个用于自定义api2，执行完验证操作后的数据结果返回。且自定义api2拿到验证结果数据后需要请求客户自己的api2，去做结果效验

9.gt3SecondResult 用于向api2里面put参数，传递一个map集合即可，一般用于不自定义api2但是需要传递额外的参数，可以使用该接口回调完成。

10.gt3DialogSuccessResult api2二次效验完成后的结果，用于客户根据不同结果做出不同的处理，
gt3GeetestUtils.gt3TestFinish();--验证完成（bind，unbind）
gt3GeetestUtils.gt3TestClose();--验证失败（bind）
gt3GeetestUtils.gt3CloseButton();--验证失败（unbind）

11.gt3AjaxResult 获取本次验证的验证类型，一般用的比较少，在统计验证类型次数上会用到
fullpage--一点既过
click--选字大图
slide--滑动验证

12.gt3DialogOnError 当验证出现错误时会在该接口返回错误码，错误码显示在弹出框右下角（bind）.自定义按键右下角（unbind）,用于追溯错误，后面有列出常用验证码

13.gtOnClick 在unbind模式下特有，点击自定义按键后的回调，在自定义api1时，在该方法里面做自定义请求




# 常用问题
1.有设置验证弹框点击周围不消失的方法吗？

答：gt3GeetestUtils.setDialogTouch(true);方法可以设置弹框是否可以点击周围取消

2.自定义api1如何定义

答：bind模式下
   在您点击需要启动验证的按钮后，自行做网络请求，拿到网络请求的结果后调用如下两个方法即可
   gt3GeetestUtils.gtSetApi1Json(parmas);
   gt3GeetestUtils.getGeetest(Main3Activity.this);
  
   unbind模式下
   首先在调用getGeetest之前调用一下gt3GeetestUtils.getISonto();方法，代码如下
   ```java
      gt3GeetestUtils =  GT3GeetestUtils.getInstance(MainActivity.this);
      gt3GeetestUtils.getISonto();
      gt3GeetestUtils.getGeetest(captchaURL,validateURL,null);
```
   然后在gtOnClick回调里，自行做网络请求，拿到网络请求的结果后调用如下一个方法即可
   gt3GeetestUtils.gtSetApi1Json(parmas);
   
 注：parmas为您自定义api1的返回结果，格式参考：
```java
{\"success\":1,\"challenge\":\"4a5cef77243baa51b2090f7258bf1368\",\"gt\":\"019924a82c70bb123aae90d483087f94\",\"new_captcha\":true}"
```

3.unbind模式下我验证完毕一轮后还想再次验证，可是按键点击不了怎么回事？

答：在gt3DialogSuccessResult里面调用Gt3GeetestTestMsg.setCandotouch(true);即可

4.为什么我所有代码都写好了，验证码就是弹不出？

答：目前导致验证码无法弹出的情况分如下几种
  1.SDK经过了混淆，所以不要在对其进行混淆
  2.3.0的SDK是否使用了2.0的gt值
  3.api1数据返回格式是否按照SDK标准返回（参考上面的parmas）
  4.手机是否连接了网络代理

5.验证码弹出后，验证完成后为什么弹框没有消失？

答：您需要在gt3DialogSuccessResult回调里面做二次验证结果处理，参考 接口详细说明-10




# 常用错误码 
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

   无网络状态下滑动报错
    
12.初始化错误 211

   验证码初始化回调用2个接口，找找这里的问题

13.初始化错误 222

   网络请求时，此时已经断网
    
14.challenge错误  _02

   challenge 过时，或者重复使用
    
15.challenge错误  _22

   服务器未检测到challenge
    
16.gt错误  _31

   服务器未检测到gt  




以上是比较常见的错误码


其他使用方法和前面保持一致，请参考前面的SDK的使用方法
说明


   本SDK包含了2个SDK合成版本 ，代码已经混淆，不需要再次混淆。 同时极验会继续努力，给予您更好的SDK
