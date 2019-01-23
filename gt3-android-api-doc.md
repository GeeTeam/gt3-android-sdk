---
title: android-sensebot-api
type: android-sensebot-api
order: 0
---


**SenseBot Android API Document**

2019 1.22 edited

# GT3GeetestUtils

SenseBot的主要外部调用接口

## Method

### GT3GeetestUtils(Context)

管理类`GT3GeetestUtils`构造方法

**Declaration**

```java
public GT3GeetestUtils(Context context)
```

**Parameters**

Param	|Type | Description |
------	|-----|-----|
context|Context|上下文

### init(GT3ConfigBean bean)

初始化

**Declaration**

```java
public void init(GT3ConfigBean bean)
```

**Parameters**

Param	|Type | Description |
------	|-----|-----|
bean| GT3ConfigBean |配置bean

### startCustomFlow()

开启验证

**Declaration**

```java
public void startCustomFlow()
```

### getGeetest()

继续验证

**Declaration**

```java
public void getGeetest()
```

### dismissGeetestDialog()

关闭正在运行的Dialog

**Declaration**

```java
public void dismissGeetestDialog()
```

### showSuccessDialog()

弹出验证成功的弹框

**Declaration**

```java
public void showSuccessDialog()
```

**Discussion**

该方法在拿到二次验证结果后，如果验证成功则调用该方法

### showFailedDialog()

弹出验证失败的弹框

**Declaration**

```java
public void showFailedDialog()
```

**Discussion**

该方法在拿到二次验证结果后，如果验证失败则调用该方法

### getVersion()

获取当前SDK的版本号

**Declaration**

```java
public String getVersion()
```

**Return Value**

返回版本号，类型为`String`

### destory()

用于释放上下文以及各种资源

**Declaration**

```java
public void destory()
```

**Discussion**

该方法在客户使用的验证码界面的onDestroy生命周期中调用

### changeDialogLayout()

横竖屏切换

**Declaration**

```java
public void changeDialogLayout()
```

**Discussion**

该方法在横竖屏切换生命周期onConfigurationChanged中调用

## GT3ConfigBean

配置类

### setApi1Json(JSONObject)

获取客户请求api1返回的的数据

**Declaration**

```java
public void setApi1Json(JSONObject json)
```
**Parameters**

Param	|Type | Description |
------	|-----|-----|
json	|JSONObject|客户请求api1返回的的数据

**Discussion**

该方法用于设置api1获取数据，注意参数json格式：

```
{
	"success" : 1,
	"challenge" : "4a5cef77243baa51b2090f7258bf1368",
	"gt" : "019924a82c70bb123aae90d483087f94",
	"new_captcha" : true
}
```

以上`success`，`challenge`，`gt`，`new_captcha`这4个参数为必须参数，客户可以添加新参数，但是不能删减，以免导致无法加载验证码

### setTimeout(int)

设置加载webview超时时间，单位毫秒，默认10000

**Declaration**

```java
public void setTimeout(int time)
```

**Parameters**

Param	|Type | Description |
------	|-----|-----|
time|int|毫秒数 如:10000

### setWebviewTimeout(int)

设置webview请求超时时间，单位毫秒，默认10000

**Declaration**

```java
public void setWebviewTimeout(int time)
```

**Parameters**

Param	|Type | Description |
------	|-----|-----|
time|int|毫秒数 如:10000

### setLang(String)

设置前端显示语言，默认支持11种

**Declaration**

```java
public void setLang(String lang)
```

**Parameters**

Param	|Type | Description |
------	|-----|-----|
lang|String|语言 如:zh-cn

### setDebug(boolean)

设置debug模式,线上版本请关闭

**Declaration**

```java
public void setDebug(boolean debug)
```

**Parameters**

Param	|Type | Description |
------	|-----|-----|
debug | boolean | debug模式

### setCanceledOnTouchOutside(boolean)

设置点击灰色区域是否消失，默认不消失

**Declaration**

```java
public void setCanceledOnTouchOutside(boolean cancel)
```

**Parameters**

Param	|Type | Description |
------	|-----|-----|
cancel | boolean | true为消失

### setListener(GT3Listener)

设置回调监听

**Declaration**

```java
public void setListener(GT3Listener listener)
```

**Parameters**

Param	|Type | Description |
------	|-----|-----|
listener | GT3Listener | GT3Listener为监听类

# GT3Listener

回调监听类

### onDialogReady()

验证码加载完成

**Declaration**

```java
public void onDialogReady(String duration)
```

**Parameters**

Param	|Type | Description |
------	|-----|-----|
duration | String | 加载时间和版本等信息，为json格式

### onClosed(int)

验证码被关闭

**Declaration**

```java
public void onClosed(int  num)
```

**Parameters**

Param	|Type 	| Description |
------	|-----	|-------------|
num		|int	|1 点击验证码的关闭按钮来关闭验证码, 2 点击屏幕关闭验证码, 3 点击返回键关闭验证码

### onApi1Result(String)

api1结果回调

**Declaration**

```java
public void onApi1Result(String result)
```

**Parameters**

Param	|Type 	| Description |
------	|-----	|-------------|
result |String|客户api1返回的数据

### onDialogResult(String)

验证结果回调

**Declaration**

```java
public void onDialogResult(String result)
```

**Parameters**

Param	|Type | Description |
------	|-----|-------------|
result|String|验证结果

**Discussion**

二次验证需要的三个参数geetest_challenge，geetest_validate，geetest_seccode，以上三个参数为必传参数，客户可以自行添加数据但不得删减

### onApi2Result(String)

Api2结果回调

**Declaration**

```java
public void onApi2Result(String result)
```

**Parameters**

Param	|Type | Description |
------	|-----|-------------|
result|String|客户api2返回的数据

### onSuccess(String)

验证成功回调

**Declaration**

```java
public void onSuccess(String result)
```

**Parameters**

Param	|Type | Description |
------	|-----|-------------|
result|String|验证成功回调数据


### onFailed(GT3ErrorBean)

验证失败回调

**Declaration**

```java
public void onFailed(GT3ErrorBean errorBean)
```

**Parameters**

Param	|Type | Description |
------	|-----|-------------|
errorBean | GT3ErrorBean | 验证失败回调数据

**Discussion**

验证错误码后面详细说明

### onButtonClick()

button被点击，验证开始

**Declaration**

```java
public void onButtonClick()
```

**Discussion**

用于监听button按键被点击

# GT3GeetestButton

UnBind模式自定义Button

## Method

### setGeetestUtils(GT3GeetestUtils)

设置GT3GeetestUtils对象

**Declaration**

```java
public void setGeetestUtils(GT3GeetestUtils geetestUtils)
```

**Parameters**

Param	|Type | Description |
------	|-----|-------------|
geetestUtils | GT3GeetestUtils | 关联对象

**Discussion**

将GT3GeetestUtils和GT3GeetestButton关联