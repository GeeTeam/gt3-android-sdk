# gt3-android-sdk

# 概述与资源

极验验证3.0 Android SDK提供给集成Android原生客户端开发的开发者使用。
集成极验验证3.0的时，需要先了解极验验证3.0的 [产品结构](http://docs.geetest.com/install/overview/#产品结构)。并且必须要先在您的后端搭建相应的**服务端SDK**，并配置从[极验后台]()获取的`<gt_captcha_id>`和`<geetest_key>`用来配置您集成了极验服务端sdk的后台。


# 环境要去

条目	|资源 			
------	|------------	
开发目标|4.0以上	
开发环境|Android Studio 2.1.3
编译版本|25以上
系统依赖|`v7包`
sdk三方依赖|无	


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

1. 在拖入`.aar`到libs文件夹后, 还要检查`.aar`是否被添加到**Library**,要在项目的build.gradle下添加如下代码：
	
	```java
	repositories {
	    flatDir {
	        dirs 'libs'
	    }
	}
	
	```
	
	并且要手动将aar包添加依赖：
	
	```java
	compile(name: 'geetest_sensebot_android_vx.y.z', ext: 'aar')
	``` 
 
2. 在AndroidManifest.xml文件中添加权限

	```java
	<uses-permission android:name="android.permission.INTERNET" />
	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
	<uses-permission android:name="android.permission.READ_PHONE_STATE" />
	```
	
3. 混淆规则
	
	```
	# 极验SDK已经混淆处理，请勿再次混淆
	-dontwarn com.geetest.sdk.**
	-keep class com.geetest.sdk.**{*;}
	
	```


	
## 名词解释
1. api1 & api2: [见数据通信流程图](https://docs.geetest.com/install/overview/flowchart)
2. Bind模式: 以需要触发按钮为参考，将极验逻辑绑定在此按钮上
3. UnBind模式: 将极验验证逻辑绑定在极验自定义Button上
	
## 配置接口

开发者集成客户端sdk前, 必须先在您的服务器上搭建相应的 **服务端SDK** ，配置 **api1及api2** 。这里以服务端 **api1及api2** 配置成功，客户端开发步骤为例，如下以Bind模式，非自定义api1及2为例：

1. 定义服务端配置好API1&API2

	```java
	 /**
	 * api1，需替换成自己的服务器URL
	 */
	private static final String captchaURL = "http://www.geetest.com/demo/gt/register-slide";
	/**
	 * api2，需替换成自己的服务器URL
	 */
	private static final String validateURL = "http://www.geetest.com/demo/gt/validate-slide";
	    
	```
2. 配置初始化接口

	```java
	GT3GeetestUtilsBind gt3GeetestUtils = new GT3GeetestUtilsBind(MainBindActivity.this);
	// 设置是否可以点击Dialog灰色区域关闭验证码
	gt3GeetestUtils.setDialogTouch(true);
	// 设置debug模式，开代理抓包可使用，默认关闭，生产环境务必设置为false
	gt3GeetestUtils.setDebug(true);
	// 设置加载webview超时时间，单位毫秒，默认15000，仅且webview加载静态文件超时，不包括之前的http请求
	gt3GeetestUtils.setTimeout(15000);
	// 设置webview请求超时(用户点选或滑动完成，前端请求后端接口)，单位毫秒，默认10000
	gt3GeetestUtils.setWebviewTimeout(10000);
	```
	
	> 注意： 务必在oncreate生命周期配置

3. 调用验证接口
	
	```java
	// 第四个参数为语言，若为null 则默认为系统语言
	// 第五个参数为回调监听类
	gt3GeetestUtils.getGeetest(MainBindActivity.this, captchaURL, validateURL, null, gt3GeetestBindListener)
	
	```
4. 回调监听接口实现

	```java
	
	// 第四个参数为语言，若为null 则默认为系统语言
	// 第五个参数为回调监听类
	gt3GeetestUtils.getGeetest(MainBindActivity.this, captchaURL, validateURL, null, new GT3GeetestBindListener() {
	
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
	    @Override
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
	                    // 设置loading消失回调
	                    gt3GeetestUtils.setGtCallBack(new GTCallBack() {
	                        @Override
	                        public void onCallBack() {
	                            // 跳转其他页面操作等
	                        }
	                    });
	                } else {
	                    gt3GeetestUtils.gt3TestClose();
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	        } else {
	            gt3GeetestUtils.gt3TestClose();
	        }
	    }
	
	    /**
	     * @param error 返回错误码，具体解释见GitHub文档
	     */
	    @Override
	    public void gt3DialogOnError(String error) {
	        Log.i(TAG, "gt3DialogOnError-->" + error);
	    }
	});
	
	```
	
	> 注意： 默认api1为get请求，添加数据为拼接在url上，默认api2为post请求，上行数据为Form表单格式， 如果此时api1、api2无法满足需求，如：添加header，请自行自定义api1及api2，详情见demo

5. 其他生命周期
	
	```java
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
	
	```
	
6. 统计接口解释 gt3GeetestStatisticsJson
	
	```
	{
	    "gt":"019924a82c70bb123aae90d483087f94", 
	    "challenge":"340e47b635f855267c222549fa6cab11",
	    "success":"true", // 不做最终参考标准
	    "a1":"1", // api1接口成功， 0为失败，下同
	    "t":"1", // gettype接口成功
	    "g":"1", // get接口成功
	    "a":"1", // webview加载成功
	    "r":"1", // webview 显示成功
	    "re":"slide",
	    "os":"android",
	    "mo":"VKY-AL00",
	    "ver":"8.0.0",
	    "net":"WIFI",
	    "build":"1",
	    "release":"1.0",
	    "vendor":"com.example.gt3",
	    "time":"1531397269605",
	    "imei":"unknown",
	    "gt3":"3.5.7.4" // 极验SDK版本
	}
	
	```
	
> 以上为Bind模式，非自定义API1和API2，其他模式请详见demo

# 常见问题

## 日志打印

SDK提供部分日志，TAG为Geetest。且输出在sd卡的 Geetest/sensebot_log.txt

## Q && A

### 1. 如何理解自定义API1、API2 ？

答：非自定义API1，API2，SDK内部处理其请求，当无法需求时，客户自己处理API1和API2请求

### 2. 如何自定义API1、API2？

答：见demo

### 3. UnBind模式下，验证完毕一轮后还想再次验证，可是按键无法点击？

答：在gt3DialogSuccessResult里面调用Gt3GeetestTestMsg.setCandotouch(true)即可

### 4. 验证码弹出后，验证完成后为什么弹框未消失？

答：需要在gt3DialogSuccessResult回调里面做二次验证结果处理，参考demo

### 5. 验证码弹出后，遇到横竖屏切换 ？

答：

1. 在mainifest里面，添加需要弹出验证码的activity上配置android:configChanges="orientation|keyboardHidden|screenSize"

2. 在验证页面添加

```
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
    
```

### 6. 如何获取SDK的版本号？

答：gt3GeetestUtils.getVersion()
   
### 7. SDK语言适配如何处理 ？

答：目前安卓控件语言是跟随系统语言变化，支持英语，繁体，简体，印尼语，日语（后续会陆续新增韩语等）。但是验证码webview里面的语言由于是前端页面，所以适配需要传递一个参数给前端，参数是在getGeetest方法第4个表示语言，这里以"en"（英文）为例。
   例子：gt3GeetestUtils.getGeetest(MainActivity.this,captchaURL, validateURL,"en",new GT3GeetestBindListener(){});<br>
  传参说明：""--国语，"en"--英文，"id"--印尼语，"ja"--日文。
  

### 8. 弹出验证框内容显示不全或字体过大导致界面移位 ？

答：该问题是手机开了老人机模式导致，解决方案有<br>
    1.把手机字体大小设置回成常规大小<br>
    2.在使用极验的activity重写该方法
   
```java
@Override  
public Resources getResources() {  
	Resources res = super.getResources();    
	Configuration config=new Configuration();    
	config.setToDefaults();    
	res.updateConfiguration(config,res.getDisplayMetrics());  
	return res;  
}
```

### 9. 导入SDK报错 ？

答： 检查SDK编译版本和BuildTool版本是否在25以上， 检查compile包名是否正确
    

# 常用错误码 

### 1.webViewError 204

   204: webview加载超时，默认15s<br>
   204\_1: webview加载出现的错误, 详情见log<br>
   204\_2: 开启代理， 若想开启代理测试，请设置debug为true。 或者手机本身webview无法加载https链接<br>
   204\_3: 系统缺省webview组件，请在[Google Store](https://play.google.com/store/apps/details?id=com.google.android.webview&hl=zh&referrer=utm_source%3Dgoogle%26utm_medium%3Dorganic%26utm_term%3Dgoogle%E5%95%86%E5%BA%97%E4%B8%8B%E8%BD%BD&pcampaignid=APPU_1_OII_W7W6I8v_rQGxnY-4Cg) 安装 Android System Webview

### 2.httpError 205

   api1接口返回为null，查看api1的参数（因为get接口，不兼容特殊字符）或地址是否有误，网络保持畅通，及时查看log日志

### 3.httpError 206

   gettype接口返回为null，查看gettype的参数和地址是否有误，网络保持畅通

### 4.httpError 207

   getphp接口返回有误，请查看是否重复使用challenge，及时查看log报错信息

### 5.httpError 208

   ajax接口返回返回为null，查看ajax的参数和地址是否有误，网络保持畅通
    
### 6.httpError 209

   api2接口返回返回为null，查看api2的参数和地址是否有误，网络保持畅通

### 7.尝试过多 _01

   连续刷新5次

### 8.尝试过多 _12

   连续验证错误6次

### 9.web请求错误 _105

  无网络状态下滑动报错

### 10.服务被forbidden 200

   网络请求时，此时服务被forbidden
    
### 11.challenge错误  _02

   challenge 过时，或者重复使用
    
### 12.challenge错误 _22

  服务器未检测到challenge
    
### 13.gt错误  _31

  服务器未检测到gt  

以上是常见的错误码


# 主要版本迭代 

===版本号以GT3GainIp的getPhoneInfo方法中的gt3参数为主===

版本说明：0.0.0 --> 接口变更和比较大的该动.新增功能.迭代功能和bug的修复

**3.5.7.4** <br>
1.适配ldpi,mdpi,hdpi分比率机型<br>
2.增加debug开关<br>
3.增加webview超时设置<br>
4.修复陀螺仪抛出异常数据bug
5.添加Success Loading页面成功回调

**3.5.6** <br>
1.rebuild SDK<br>

**3.5.5** <br>
1.添加gzip优化<br>
2.添加TLS1.1版本兼容<br>
3.修复部分bug<br>

**3.5.4** <br>
1.增添日志输出<br>
2.修复部分bug<br>

**3.5.3** <br>
1.新增unbind模式下手机自带组件webview被删除的错误提示<br>
2.新增unbind模式下获取字段的完整性<br>

**3.5.2** <br>
1.Demo注释更加详细清晰<br>
2.修改了部分静态字段<br>

**3.5.1** <br>
1.国际化新增日语<br>

**3.5.0** <br>
1.国际化新增印尼语<br>
2.部分文件位置跟换（跟换新包时，AS自动导下新路径即可）

**3.4.9** <br>
1.修改sdk下国际化内部的app_name

**3.4.8** <br>
1.对重试多次文案的修改
2.修复一个settext有可能为空的异常

**3.4.7** <br>
1.对所有runOnUiThread依赖activity做非空判断

**3.4.6** <br>
1.参数加密

**3.4.5** <br>
1.对后台以及前端的错误码做字段保护，防止前端后台返回为null导致移动端崩溃

**3.4.4** <br>
1.dimiss非空判断

**3.4.3** <br>
1.优化一个有可能导致的null异常

**3.4.2** <br>
1.英文全适配<br>
2.控制台中不影响使用的部分警告log解决<br>
3.webview生命周期优化<br>
4.接口回调都提到主线程<br>
5.修改部分ajax请求参数<br>
6.网络请求类中去掉了对Value的编码格式<br>
7.为客户提供了一个弹出loading加载框的方法<br>
8.接口采用抽象类的形式，客户可以选择调用<br>
9.部分接口名以及方法名的修改<br>

**3.3.2** <br>
1.优化webview，在webview的onResume生命周期中添加resumeTimers方法，防止webview白屏

**3.3.1**<br> 
1.代码整理，稳定发布

**3.3.0** <br>
1.新的网络请求框架<br>
2.新的混淆方式<br>
3.gt3GeetestUtils.cancelUtils();的加入<br>
4.错误码抛出提到主线程<br>
5.外部接口的精简和优化<br>
6.bind模式下调用方法的改变，去掉了dologo()<br>

**3.2.12** <br>
对数据采集做了条数限制，优化了webview的超时代码

**3.2.11** <br>
对GT3CallBacks类进行了优化，主要是注册和注销以及生命周期把控这块

**3.2.10** <br>
1.在5.0以下会stoploading处报错<br>  
2.bind模式下webview在为空直接设置到view会有问题<br> 
3.点击弹框的小感叹号会崩溃的问题，ViewGroup的优化（部分界面会有问题）

**3.2.9**<br>
修复在5.0版本以下会崩溃的问题。同时在横竖屏切换下，没有进行dialog的关闭，来防止内存泄漏,去掉rege_21 gt3FirstGo两个接口,unbind模式下

**3.2.8**<br>
新增判断所有api请求是否成功的方法 方法名：getGeetestStatisticsJson()新增调试模式接口，不管几次点击都是走所有的网络请求，不会漏掉get 和gettype  方法名：setdebug()

**3.2.7**<br>
新增获取版本号的方法：getVersion 新增设置webview超时的时长：setTimeout 网络库的请求超时时长从10s调到5s,并去掉了gt3DialogSuccess()接口

**3.2.6**<br>
修复了竖屏状态下突然横屏导致的大图显示异常的问题，对callback类进行重写，防止内存泄漏，对uuid进行缓存，添加了一个获取版本号的方法

**3.2.5**<br>
getPhoneInfo加入了2个参数，分别为androidId,uuid，修复api1上get参数时 没传参数路径后会多一个?的问题

**3.2.4**<br>
api1加入cookie 同时api2传入该cookie，多暴露二个接口记录弹出成功率和验证类型

**3.1.3**<br>
UI线程优化

**3.1.2**<br>
版为整个流程只请求一次api1

**3.0.2**<br>
版为带初始化API1的版本

> 极验会继续努力，给予您更好的服务
