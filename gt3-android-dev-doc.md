# Android SDK Documentation

文件名				|说明
----------------	|----------
GT3GeetestBind.java		|验证的网络层管理器
GT3GtDialogBind.java|自定义的Dialog
GT3GtWebView.java  |自定义的WebView
GT3GeetestUtilsBind.java  |封装的验证码响应工具



# GT3GeetestBind

**Public Mehtods**

方法名				|说明
----------------	|----------------
`Geetest(_, _)`	|验证管理器初始化
`checkServer(_)`	|检测服务器状态，得到success
`checkRealServer(_)`	|第二次检测服务器状态,得到challenge与gt
`getphpServer(_)`	|检测get.php服务器状态
`getajaxServer(_)`	|检测get.ajax服务器状态
`readContFromGet(_)`	|get网络请求的获取
`getValidateURL(_)`	|初始化URL
`getRequestData(_,_)`	|post网络请求的Map
`setGeetestListener(_)`|注册验证事件监听
`getGt()`			|获取gt id
`getChallenge()`	|获取challenge
`getSuccess()`	|获取success
`setTimeout()`	|设置超时时限
`submitPostData(_, _)`|提交二次验证

## GT3Geetest()

**abstract**

GT3Geetest的初始化方法

**declaration**

~~~java

public GT3Geetest(String captchaURL, String validateURL，String lang);
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|---------------------
`captchaURL`	|`String`		|从网站主服务器获取验证启动数据的接口(api_1)
`validateURL`	|`String`		|从网站主服务器进行二次验证的接口(api_2)
`lang`	|`String`		|默认传null--中文（参数：zh,en）

### returns

类型			|说明
-------------	|----------------------
`Geetest`		|geetest验证网络管理的实例

## checkServer()

**abstract**

检测服务器状态，api1接口请求

**declaration**

~~~java

public JSONObject checkServer(String headers);
或
public JSONObject checkServer(JSONObject config);
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|---------------------
`headers`	|`String`		|附带在api1后面的参数
`config`	|`JSONObject`		|api1请求返回的值，仅用于自定义api1时候传（注）

### returns

类型			|说明
-------------	|----------------------
`JSONObject`		|请求api1返回的值


## gettypeServer()

**abstract**

获取ajax提交地址，gettype接口请求

**declaration**

~~~java

public JSONObject gettypeServer();
~~~

### returns

类型			|说明
-------------	|----------------------
`JSONObject`		|请求gettype返回的值

## getphpServer()

**abstract**

获取js文件名等资源文件，get接口请求

**declaration**

~~~java

public JSONObject getphpServer();
~~~

### returns

类型			|说明
-------------	|----------------------
`JSONObject`		|请求gettype返回的值

##getajaxServer()

**abstract**

检测get.ajax状态

###declaration

~~~java

public JSONObject getajaxServer();
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|---------------------
`mi`	|`String`		|手机参数以及网络状态等信息
`m`	|`String`		|陀螺仪信息
`l`	|`String`		|点击行为信息

### returns

类型			|说明
-------------	|----------------------
`JSONObject`		|请求ajax返回的值

## setGeetestListener()

**abstract**

注册验证网络事件监听

### seealso

~~~java

public interface GeetestListener {
    void readContentTimeout();//从api_1获取验证启动数据超时
    void submitPostDataTimeout();//向api_2提交二次验证数据
    void receiveInvalidParametes();//检测请求的JSON是否为无效参数
    void errorCode();//获取后端抛出的错误
}
~~~

**discussion**

包含四个回调方法,只贴出有使用到的

**declaration**

~~~java

public void errorCode(String error, String error_id);
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`error`	|`String`|网络请求中返回的错误文案
`error_id`	|`String`|网络请求中返回的错误码

## getGt()

**abstract**

获取验证id

**discussion**

从极验后台获取的验证id

**declaration**

~~~java

public String getGt();
~~~

### returns

类型			|说明
-------------	|----------------------
`String`		|用于启动验证的id

## getChallenge()

**abstract**

获取验证challenge

**discussion**

每个challenge只能用于请求一次验证

**declaration**

~~~java

public String getChallenge();
~~~

### returns

类型			|说明
-------------	|----------------------
`String`		|用于启动验证的challenge

## getSuccess()

**abstract**

获取极验验证的服务状态

**discussion**

`ture`/`false` 请求正常验证/静态验证

**declaration**

~~~java

public boolean getSuccess();
~~~

### returns

类型			|说明
-------------	|----------------------
`boolean`		|`true`/`false` 验证服务正常/异常

## setTimeout()

**abstract**

配置验证数据请求超时时限

**discussion**

默认10000ms

**declaration**

~~~java

public void setTimeout(int timeout);
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`timeout`		|`int`			|验证的超时时限

## submitPostData()

**abstract**

提交二次验证测数据

**discussion**

此方法包装的请求必须为`POST`类型

**declaration**

~~~java

public String submitPostData(Map<String, String> params, String encode);
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`params`		|`Map<String, String>`|二次验证参数
`encode`		|`String`		|编码格式

### returns

类型			|说明
-------------	|----------------------
`String`		|返回的二次验证结果


# GT3GtDialogBind

**Public Mehtods**

方法名				|说明
----------------	|----------------
`GT3GtDialogBind()`|初始化GT3GtDialogBind
`changeLayout()`	|设置dialog中的webview大小
`init()`	|初始化参数
`startfinish()`	|验证完成调用
`shakeDialog()`	|验证错误时窗口抖动
`JSInterface()`	|与前端交互接口
`setErrDialog()`	|错误码弹框
`setGtWebViewListener()`	|Webview的回调接口

## GT3GtDialogBind()

**abstract**

初始化GT3GtDialogBind

**declaration**

~~~java

  public void GT3GtDialogBind(Context context) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`context`		|`Context`			|上下文对象


## changeLayout()

**abstract**

设置dialog中的webview大小

**declaration**

~~~java

  public void changeLayout() ；
~~~

## init()

**abstract**

初始化参数

**declaration**

~~~java

  public void init(int time) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`time`		|`int`			|设置webview的超时时间

## startfinish()

**abstract**

验证完成UI更新

**declaration**

~~~java

  public void startfinish() ；
~~~

## shakeDialog()

**abstract**

验证错误时验证框抖动

**declaration**

~~~java

  public void shakeDialog() ；
~~~

## JSInterface()

**abstract**

与前端交互接口

**discussion**

~~~java

public interface GeetestListener {
    void gtCallBack();//获取与验证码交互数据
    void gtClose();//点击验证码关闭按钮触发
    void gtReady();//验证码资源准备完成
    void gt3Error();//验证码出错
}
~~~

**declaration**

~~~java

  public void gtCallBack(String code, String result) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`code`		|`String`			|验证码是否成功
`result`		|`String`			|验证码交互后返回的结果

## gt3Error()

~~~java

  public void gt3Error(String error) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`error`		|`String`			|验证码错误信息

##setErrDialog

**abstract**

验证码出现错误时弹出的错误弹框样式

**declaration**

~~~java

  public void setErrDialog(String error, String errorcode) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`error`		|`String`			|验证码错误文案
`errorcode`		|`String`			|验证码错误码


## setGtWebViewListener()

**abstract**

webview的接口回调

**discussion**

public interface GeetestListener {
    void gtCallReady();//验证码在webview中通知dialog的回调
}


**declaration**

~~~java

  public void gtCallReady(String error,Boolean status) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`error`		|`String`			|验证码错误文案
`status`		|`Boolean`			|是否错误


# GT3GtWebView

**Public Mehtods**

方法名				|说明
----------------	|----------------
`init()`	|初始化参数
`WebViewClientEx()`	|重写WebViewClient

## init()

**abstract**

初始化参数

**declaration**

~~~java

  public void init(Context context) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`context`		|`Context`			|上下文对象

## WebViewClientEx()

**abstract**

重写Webview的WebViewClient

**discussion**

~~~java

public interface WebViewClientEx {
    void onReceivedError();//webview加载报错
    void onReceivedSslError();//webview证书报错
    void onPageStarted();//webview加载开始
    void onPageFinished();//webview加载结束
}
~~~

**declaration**

~~~java

  public void onPageStarted(WebView view, String url) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`view`		|`WebView`			|当前的WebView
`url`		|`String`			|WebView的加载路径

# GT3GeetestUtilsBind

**Public Mehtods**

方法名				|说明
----------------	|----------------
`GT3GeetestUtilsBind()`	|实例化GT3GeetestUtilsBind
`cancelAllTask()`	|关闭所有异步
`gtDologo()`	|判断是否有logo
`getDialog()`	|获取当前使用的Dialog
`setDialogTouch()`	|控制点击屏幕是否可以关闭dialog
`gtSetApi1Json()`	|用于设置api1的返回结果，仅限于自定义api1使用
`getGeetest()`	|启动验证码
`openGtTest()`	|弹出验证码弹框
`gt3TestFinish()`	|验证成功的dialog样式
`gt3TestClose()`	|验证失败的dialog样式
`getGeetestStatisticsJson()`	|获取统计验证各个阶段状态的数据集合
`getVersion()`	|获取当前SDK的版本号
`setTimeout()`	|设置webview的超时时间
`GT3Listener()`	|提供给客户的接口回调（注）

**abstract**

这个类是外部类，包含所有的验证码外部方法和外部接口，客户选择性调用（注）

## GT3GeetestUtilsBind()

**abstract**

实例化GT3GeetestUtilsBind

**declaration**

~~~java

  public void GT3GeetestUtilsBind(Context context) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`context`		|`Context`			|上下文对象

## cancelAllTask()

**abstract**

关闭所有的异步请求

**declaration**

~~~java

  public void cancelAllTask() ；
~~~

## gtDologo()

**abstract**

判断是否有logo

**declaration**

~~~java

  public void gtDologo(String api1, String api2, String lang)；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`api1`		|`String`			|api1
`api2`		|`String`			|api2
`lang`		|`String`			|默认传null--中文（参数：zh,en）


## getDialog()

**abstract**

获取当前使用的Dialog

**declaration**

~~~java

  public void getDialog() ；
~~~

### returns

类型			|说明
-------------	|----------------------
`GT3GtDialogBind`		|返回当前使用的Dialog

## setDialogTouch()

**abstract**

控制点击屏幕是否可以关闭dialog

**declaration**

~~~java

  public void setDialogTouch(boolean a) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`a`		|`boolean`			|true可关闭，false不可关闭

## gtSetApi1Json()

**abstract**

用于设置api1的返回结果，仅限于自定义api1使用

**declaration**

~~~java

  public void gtSetApi1Json(JSONObject json) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`json`		|`JSONObject`			|传自行请求api1得到的json数据

## getGeetest()

**abstract**

启动验证码

**declaration**

~~~java

  public void getGeetest( Context context) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`context`		|`Context`			|上下文对象

## openGtTest()

**abstract**

弹出验证码框

**declaration**

~~~java

  public void openGtTest(int hight) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`hight`		|`int`			|验证码框的高宽比例

## gt3TestFinish()

**abstract**

验证成功的dialog样式

**declaration**

~~~java

  public void gt3TestFinish() ；
~~~

## gt3TestClose()

**abstract**

失败的dialog样式

**declaration**

~~~java

  public void gt3TestClose() ；
~~~

## getGeetestStatisticsJson()

**abstract**

获取统计验证各个阶段状态的数据集合

**declaration**

~~~java

  public void getGeetestStatisticsJson(String error) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`error`		|`String`			|验证码错误时返回的错误码

## getVersion()

**abstract**

获取当前SDK的版本号

**declaration**

~~~java

  public void getVersion() ；
~~~

### returns

类型			|说明
-------------	|----------------------
`String`		|返回当前SDK版本号

## setTimeout()

**abstract**

设置webview的超时时间

**declaration**

~~~java

  public void setTimeout(int timeout) ；
~~~

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`timeout`		|`int`			|设置webview的超时时间

## GT3Listener()

**abstract**

提供给客户的接口回调（注）

**discussion**

~~~java

public interface GT3Listener（） {
    void gt3CloseDialog();//点击了验证码上的取消按钮的回调
    void gt3DialogReady();//关闭验证码准备完成的回调
    void gt3FirstResult();//拿到api1请求结果的回调
    Map<String, String> gt3SecondResult();//往api2接口里面添加参数
    void gt3GetDialogResult();//拿到请求api2接口需要的数据 
    void gt3DialogOnError();//验证码出现错误的回调
    void gt3DialogSuccessResult();//请求api2接口后返回的结果
    void gt3AjaxResult();//请求ajax接口后返回的结果
    Map<String, String>  captchaApi1();///往api1接口里面添加参数
    void gtSetIsCustom();//设置是否自定义二次验证（true为是，反之不是）
    void gt3GeetestStatisticsJson();//统计数据
}
~~~

**declaration**

~~~java

  public void gt3CloseDialog() ；
~~~

**discussion**

点击了验证码上的取消按钮的回调


**declaration**

~~~java

  public void gt3FirstResult(JSONObject jsonObject) ；
~~~

**discussion**

返回api1请求后的结果

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`jsonObject`		|`JSONObject`			|返回api1请求后的结果

**declaration**

~~~java

  Map<String, String> gt3SecondResult() ；
~~~

**discussion**

往api2接口里面添加参数

### returns

类型			|说明
-------------	|----------------------
`Map<String, String>`		|添加的参数为map集合

**declaration**

~~~java

  public void gt3GetDialogResult(String result) ；
  或
  public void gt3GetDialogResult(boolean a，String result) ；（用于自定义api2）
~~~

**discussion**

二次验证需要的结果

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`result`		|`String`			|返回二次验证需要的结果
`a`		|`boolean`			|判断是否成功返回（true成功，反正失败，仅用于自定义二次验证）

**declaration**

~~~java

  public void gt3DialogOnError(String error) ；
~~~

**discussion**

验证码验证出现问题的回调

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`error`		|`String`			|返回验证错误码

**declaration**

~~~java

  public void gt3DialogSuccessResult(String result) ；
~~~

**discussion**

二次验证后得到的结果，验证流程基本走完

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`result`		|`String`			|返回二次验证后得到的结果

**declaration**

~~~java

  public void gt3AjaxResult(String result) ；
~~~

**discussion**

请求ajax接口得到的结果

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`result`		|`String`			|返回请求ajax接口得到的结果

**declaration**

~~~java

  Map<String, String> captchaApi1() ；
~~~

**discussion**

往api1接口里面添加参数

### returns

类型			|说明
-------------	|----------------------
`Map<String, String>`		|添加的参数为map集合

**declaration**

~~~java

  public void gtSetIsCustom() ；
~~~

**discussion**

设置是否使用自己的二次验证

**declaration**

~~~java

  public void gereg_21() ；
~~~

**discussion**

用于错误代码为21时的回调，重新启动SDK

**declaration**

~~~java

  public void gt3GeetestStatisticsJson(String result) ；
~~~

**discussion**

统计接口

### parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`result`		|`String`			|返回所有请求的统计数据









