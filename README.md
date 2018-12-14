# gt3-android-sdk

# 概述与资源

极验验证3.0 Android SDK提供给集成Android原生客户端开发的开发者使用。
集成极验验证3.0的时，需要先了解极验验证3.0的 [产品结构](http://docs.geetest.com/install/overview/#产品结构)。并且必须要先在您的后端搭建相应的**服务端SDK**，并配置从[极验后台]()获取的`<gt_captcha_id>`和`<geetest_key>`用来配置您集成了极验服务端sdk的后台。

> 注意：相对3.X.X版本改动较大

# 环境需求

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
	// 务必在oncreate方法里初始化
	GT3GeetestUtils gt3GeetestUtils = new GT3GeetestUtils(this);
	```

	> 注意： 务必在oncreate生命周期配置

3. 配置GT3ConfigBean

	```
	// 配置bean文件，也可在oncreate初始化
	gt3ConfigBean = new GT3ConfigBean();
	// 设置验证模式，1：bind，2：unbind
	gt3ConfigBean.setPattern(1);
	// 设置点击灰色区域是否消失，默认不消息
	gt3ConfigBean.setCanceledOnTouchOutside(false);
	// 设置debug模式，开代理可调试
	gt3ConfigBean.setDebug(false);
	// 设置语言，如果为null则使用系统默认语言
	gt3ConfigBean.setLang(null);
	// 设置webview加载超时
	gt3ConfigBean.setTimeout(9000);
	// 设置webview请求超时
	gt3ConfigBean.setWebviewTimeout(6000);
	// 设置api1地址
	gt3ConfigBean.setApi1(captchaURL);
	// 设置api2地址
	gt3ConfigBean.setApi2(validateURL);
	// 设置回调监听
	gt3ConfigBean.setListener(new GT3Listener());
	gt3GeetestUtils.init(gt3ConfigBean);
	// 开启验证
    gt3GeetestUtils.startCustomFlow();
	```

4. 调用验证接口

	```java
	// 开启验证
   gt3GeetestUtils.getGeetest();

	```
5. 回调监听接口实现

	```java

	gt3ConfigBean.setListener(new GT3Listener() {

	    /**
	     * api1结果回调
	     * @param result
	     */
	    @Override
	    public void onApi1Result(String result) {
	        Log.e(TAG, "GT3BaseListener-->onApi1Result-->" + result);
	    }

	    /**
	     * 验证码加载完成
	     * @param duration 加载时间和版本等信息，为json格式
	     */
	    @Override
	    public void onDialogReady(String duration) {
	        Log.e(TAG, "GT3BaseListener-->onDialogReady-->" + duration);
	    }

	    /**
	     * 验证结果
	     * @param result
	     */
	    @Override
	    public void onDialogResult(String result) {
	        Log.e(TAG, "GT3BaseListener-->onDialogResult-->" + result);
	        // 开启api2逻辑
            new RequestAPI2().execute(result);
	    }

	    /**
	     * api2回调
	     * @param result
	     */
	    @Override
	    public void onApi2Result(String result) {
	        Log.e(TAG, "GT3BaseListener-->onApi2Result-->" + result);
	    }

	    /**
	     * 统计信息，参考接入文档
	     * @param result
	     */
	    @Override
	    public void onStatistics(String result) {
	        Log.e(TAG, "GT3BaseListener-->onStatistics-->" + result);
	    }

	    /**
	     * 验证码被关闭
	     * @param num 1 点击验证码的关闭按钮来关闭验证码, 2 点击屏幕关闭验证码, 3 点击返回键关闭验证码
	     */
	    @Override
	    public void onClosed(int num) {
	        Log.e(TAG, "GT3BaseListener-->onClosed-->" + num);
	    }

	    /**
	     * 验证成功回调
	     * @param result
	     */
	    @Override
	    public void onSuccess(String result) {
	        Log.e(TAG, "GT3BaseListener-->onSuccess-->" + result);
	    }

	    /**
	     * 验证失败回调
	     * @param errorBean 版本号，错误码，错误描述等信息
	     */
	    @Override
	    public void onFailed(GT3ErrorBean errorBean) {
	        Log.e(TAG, "GT3BaseListener-->onFailed-->" + errorBean.toString());
	    }

	    @Override
	    public void onButtonClick() {
	        new RequestAPI1().execute();
	    }

    });

	```

5. 其他生命周期

	```java
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    /**
	     * TODO 务必调用
	     * 页面关闭时释放资源
	     */
	    gt3GeetestUtils.destory();
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

6. 统计接口解释 onStatistics

	```
	{
	    "gt":"019924a82c70bb123aae90d483087f94", // 商家标识
	    "challenge":"340e47b635f855267c222549fa6cab11", // 流水号
	    "success":"true", // 是否宕机
	    "a1":"1", // api1接口成功， 0为失败，下同
	    "t":"1", // gettype接口成功
	    "g":"1", // get接口成功
	    "a":"1", // ajax
	    "r":"1", // 验证码显示成功
	    "re":"slide", // 验证类型，若有此字段，表示结果成功
	    "os":"android",
	    "mo":"VKY-AL00",
	    "ver":"8.0.0",
	    "net":"WIFI",
	    "build":"1",
	    "release":"1.0",
	    "vendor":"com.example.gt3",
	    "time":"1531397269605",
	    "imei":"unknown",
	    "gt3":"4.0.3" // 极验SDK版本
	}

	```

> 以上为Bind模式，其他模式请详见demo

# 常见问题

## 日志打印

SDK提供部分日志，TAG为Geetest。且输出在sd卡的 Geetest/sensebot_log.txt

## Q & A

### 1. 验证码弹出后，验证完成后为什么弹框未消失？

答：需要在onDialogResult回调里面做二次验证结果处理，参考demo

### 2. 验证码弹出后，遇到横竖屏切换 ？

答：

1. 在mainifest里面，添加需要弹出验证码的activity上配置android:configChanges="orientation|keyboardHidden|screenSize"

2. 在验证页面添加

	```
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        gt3GeetestUtils.changeDialogLayout();
    }

	```

### 3. 如何获取SDK的版本号？

答：gt3GeetestUtils.getVersion()

### 4. SDK语言适配如何处理 ？

答：gt3ConfigBean.setLang(null);为使用系统语言。
  若不为null，如："zh"--简体中文,"zh-rHK","zh-tw"-繁体中文，"en"--英文，"id"--印尼语，"ja"--日文。  

### 5. 弹出验证框内容显示不全或字体过大导致界面移位 ？

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

### 6. 导入SDK报错 ？

答： 检查SDK编译版本和BuildTool版本是否在25以上， 检查compile包名是否正确


# 常见错误码
ErrorCode | Description | 备注
---|--- | ---
200 | 服务被forbidden |
201 | SKD内部异常 | 请联系我们
202 | 解析json错误 | 请查看日志
204 | 验证码加载超时，默认15s |
204_1 | webview加载错误 | 请查看日志
204_2 | SSLError，开启代理失败或者时间问题 | 若开启代理，请设置debug为true
204_3 | 系统缺省webview组件 | 请在[Google Store](https://play.google.com/store/apps/details?id=com.google.android.webview&hl=zh&referrer=utm_source%3Dgoogle%26utm_medium%3Dorganic%26utm_term%3Dgoogle%E5%95%86%E5%BA%97%E4%B8%8B%E8%BD%BD&pcampaignid=APPU_1_OII_W7W6I8v_rQGxnY-4Cg) 安装 Android System Webview
205 | api1错误 | 请查看日志
206 | gettype错误 | 请查看日志
207 | get错误 | 请查看日志
208 | ajax错误 | 请查看日志
209 | api2错误 | 请查看日志
_01 | 刷新过多 |
_12 | 校验错误过多 |
_02 | challenge 过时，或者重复使用 |
_22 | 服务器未检测到challenge |
_31 | captcha_id错误 |
_XX | 后端错误 |
_1XX | 前端报错 |

# 更新日志
- **4.0.3:** 适配beeline验证形式
- **4.0.2:** rebuild SDK
- **3.5.7.4:** 适配ldpi、mdpi、hdpi分辨率机型；增加debug调试开关；增加webview超时设置；修复陀螺仪异常；添加Loading成功回调<br>
- **3.5.6:** rebuild SDK<br>
- **3.5.5:** 添加`gzip`优化；添加`TLS1.1`版本兼容；修复部分bug<br>
- **3.5.4:** 增加日志输出；修复部分bug
- **3.5.3:** 新增webview组件被删除提示；新增unbind模式获取字段完整性
- **3.5.2:** 完善demo注释；
- **3.5.1:** 国际化新增日语
- **3.5.0:** 国际化新增印尼语；
- **3.4.9:** 删除国际化语言appname
- **3.4.8:** 修改重试多次文案；修复部分bug
- **3.4.7:** 修复部分bug
- **3.4.6:** 参数加密
- **3.4.5:** 增加解析字段判断，防止异常
- **3.4.4:** 修复部分bug
- **3.4.3:** 修复部分bug
- **3.4.2:** 英文适配；webview生命周期优化；修改ajax部分参数；接口回调切换到主线程；新增加载loading接口；优化SDK
- **3.3.2:** 优化webview，防止白屏
- **3.3.1:** 优化SDK
- **3.3.0:** 优化网络框架；优化混淆；新增销毁资源接口；优化SDK对外接口
- **3.2.12:** 数据采集限制条数；新增webview设置
- **3.2.11:** 优化GT3CallBacks，防止更多消耗资源
- **3.2.10:** 修复部分bug；
- **3.2.9:** 修复5.0以下崩溃问题；修复横竖屏切换内存泄露；去掉部分接口
- **3.2.8:** 新增统计接口`getGeetestStatisticsJson()`；新增debug接口
- **3.2.7:** 新增获取版本号接口`getVersion`;新增加载webview超时；跳转网络超时为5s；取消`gt3DialogSuccess()`接口
- **3.2.6:** 修复横竖屏切换异常；优化SDK防止内存泄露；缓存`uuid`
- **3.2.5:** 新增上行androidID及uuid；修复部分bug
- **3.2.4:** api1及api2新增cookie
- **3.1.3:** 优化UI线程
- **3.1.2:** 修改为只请求一次api1
- **3.0.2:** 添加api1接口

> 极验会继续努力，给予您更好的服务
