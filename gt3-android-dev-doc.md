---
title: android-testbutton-api
type: android-testbutton-api
order: 0
---


**GTTestButton Android API Document**

2017 11.30 edited

# GT3GeetestUtilsBind

GTTestButton的主要外部调用接口(bind模式)

## Method

### GT3GeetestUtilsBind(Context)

获取管理类`GT3GeetestUtilsBind`的实例对象

**Declaration**

```java
public GT3GeetestUtilsBind(Context context)
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-----|
context|Context|上下文

### getGeetest(Context,String,String,String,GT3GeetestBindListener)

开启验证码

**Declaration**

```java
public void getGeetest(final Context context, String api1, String api2, String lang, GT3GeetestBindListener listener)
```

**Parameters**

Param	|Type 		| Description 	|	
------	|---------|---------------	|
context|Context	|上下文					
api1	|String	|客户后台服务器配置,用来获取gt，challenge参数的api接口
api2	|String	|客户后台服务器配置,用于二次验证的api接口
lang	|String	|验证码语言，默认传null跟随系统语言
listener|GT3GeetestBindListener|验证码外部接口实例

### gt3Dismiss()

关闭正在运行的Dialog

**Declaration**

```java
public void gt3Dismiss()
```

### gt3TestFinish()

弹出验证成功的弹框

**Declaration**

```java
public void gt3TestFinish()
```

**Discussion**

该方法在拿到二次验证结果后，如果验证结果中status字段为success则调用该方法

### gt3TestClose()

弹出验证失败的弹框

**Declaration**

```java
public void gt3TestClose()
```

**Discussion**

该方法在拿到二次验证结果后，如果验证结果中status字段为fail则调用该方法

### getVersion()

获取当前SDK的版本号

**Declaration**

```java
public String getVersion()
```

**Return Value**

返回版本号，类型为`String`

### setTimeout(int)

设置加载验证码的webview的超时时间，默认10秒

**Declaration**

```java
public void setTimeout(int time)
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-----|
time|int|毫秒数 如:15000|	

### cancelUtils()

用于释放上下文以及各种资源

**Declaration**

```java
public void cancelUtils()
```

**Discussion**

该方法在客户使用的验证码界面的onDestroy生命周期中调用

### showLoadingDialog(Context, String)

弹出极验加载框

**Declaration**

```java
public void showLoadingDialog(Context context, String lang)
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-----|
context|Context|上下文|	
lang|String|验证码语言，默认传null跟随系统语言|	

**Discussion**

该方法很少用到，仅用于自定义api1时考虑使用

### gtSetApi1Json(JSONObject)

用于获取客户请求api1返回的的数据

**Declaration**

```java
public void gtSetApi1Json(JSONObject json)
```
**Parameters**

Param	|Type | Description | 			
------	|-----|-----|
json|JSONObject|客户请求api1返回的的数据

**Discussion**

该方法用于自定义api1时必定使用，注意参数json格式：

```
{
	"success" : 1,
	"challenge" : "4a5cef77243baa51b2090f7258bf1368",
	"gt" : "019924a82c70bb123aae90d483087f94",
	"new_captcha" : true
}
```

以上`success`，`challenge`，`gt`，`new_captcha`这4个参数为必须参数，客户可以添加新参数，但是不能删减，以免导致无法加载验证码

### setDialogTouch(boolean)

设置点击弹出框周围,弹出框是否消失

**Declaration**

```java
public void setDialogTouch(boolean bol)
```
**Parameters**

Param	|Type | Description | 			
------	|-----|-----|
bol|boolean|true 是 false 否

**Discussion**

该方法比较常用，建议设置成false

### cancelAllTask()

关闭所有异步加载

**Declaration**

```java
public void cancelAllTask()
```

## Listener

### gt3DialogReady()

监听弹框是否正常弹出

**Declaration**

```java
public void gt3DialogReady()
```

**Discussion**

验证码正常弹出会进该接口 反之不进

### gt3CloseDialog(int)

用于监听弹框消失

**Declaration**

```java
public void gt3CloseDialog(int  num)
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-----|
num|int|1 点击验证码的关闭按钮来关闭验证码，2 点击屏幕关闭验证码，3 点击返回键关闭验证码	

### gt3FirstResult(JSONObject)

用于获取客户api1返回的数据

**Declaration**

```java
public void gt3FirstResult(JSONObject jsonObject) 
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-----|
jsonObject|JSONObject|客户api1返回的数据

### gt3GetDialogResult(String)

用于获取客户api2返回的数据

**Declaration**

```java
public void gt3GetDialogResult(String result) 
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-----|
result|String|客户api2返回的数据

### gt3GetDialogResult(boolean, String)

用于获取客户api2返回的数据

**Declaration**

```java
public void gt3GetDialogResult(boolean stu, String result) 
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-----|
result|String|客户api2返回的数据
stu|boolean|判断是否获取到返回值 true：有 false：没有


**Discussion**

该方法用于自定义api1时使用，当stu为true，result会携带二次验证需要的三个参数geetest_challenge，geetest_validate，geetest_seccode，以上三个参数为必传参数，客户可以自行添加数据但不得删减，提交这些从参数到api2服务器即可

### gt3DialogSuccessResult(String)

用户操作验证码后的结果数据

**Declaration**

```java
public void gt3DialogSuccessResult(String result) 
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-----|
result|String|用户操作验证码后的结果数据


### gt3DialogOnError(String)

用户操作验证码后的结果数据

**Declaration**

```java
public void gt3DialogOnError(String error) 
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-----|
error|String|用户操作验证码后的结果数据

**Discussion**

验证错误码后面详细说明

### gt3SetIsCustom()

用户是否使用自定义二次验证

**Declaration**

```java
public boolean gt3SetIsCustom()
```

**Discussion**
使用自定义二次验证必须设置的接口

**Return Value**

返回给`gt3geetestutils`告知使用自定义二次验证, 类型为`boolean` true:是 false:不是（默认)

### gt3captchaApi1()

用于往api1里面添加提交参数

**Declaration**

```java
public Map<String, String> gt3captchaApi1()
```

**Discussion**

在不自定义api1的情况下，客户想往api1里面添加提交参数可以使用该方法，该方法为get提交

**Return Value**

返回给`gt3geetestutils`用于提交api1的参数, 类型为`Map<String, String>`

### gt3SecondResult()

用于往api2里面添加提交参数

**Declaration**

```java
public Map<String, String> gt3SecondResult()
```

**Discussion**

在不自定义api2的情况下，客户想往api2里面添加提交参数可以使用该方法，该方法为post提交

**Return Value**

返回给`gt3geetestutils`用于提交api2的参数, 类型为`Map<String, String>`

### gt3GeetestStatisticsJson(JSONObject)

用于统计单次验证每个接口状态和手机信息

**Declaration**

```java
public void gt3GeetestStatisticsJson(JSONObject result)
```

# GT3GeetestUtils

GTTestButton的主要外部调用接口(button模式)

## Method

### GT3GeetestUtils.getInstance(Context)

获取管理类`GT3GeetestUtils`的实例对象

**Declaration**

```java
public GT3GeetestUtils.getInstance(Context context)
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-----|
context|Context|上下文

### getGeetest(final Context, String, String, String, GT3GeetestListener)

开启验证码

**Declaration**

```java
public void getGeetest(final Context context,String api1,String api2,String lang,GT3GeetestListener listener)
```

**Parameters**

Param	|Type 	| Description | 			
------	|-----	|-------------|
context |Context|上下文
api1	|String|客户后台服务器配置,用来获取gt，challenge参数的api接口
api2	|String|客户后台服务器配置,用于二次验证的api接口
lang	|String|验证码语言，默认传null跟随系统语言
listener|GT3GeetestBindListener|验证码外部接口实例 

### gt3Dismiss()

关闭正在运行的Dialog

**Declaration**

```java
public void gt3Dismiss()
```

### gt3TestFinish()

弹出验证成功的弹框

**Declaration**

```java
public void gt3TestFinish()
```

**Discussion**

该方法在拿到二次验证结果后，如果验证结果中status字段为success则调用该方法

### gt3CloseButton()

弹出验证失败的弹框

**Declaration**

```java
public void gt3CloseButton()
```

**Discussion**

该方法在拿到二次验证结果后，如果验证结果中status字段为fail则调用该方法

### getVersion()

获取当前SDK的版本号

**Declaration**

```java
public String getVersion()
```

**Return Value**

返回版本号，类型为`String`

### setTimeout(int)

设置加载验证码的webview的超时时间，默认10秒

**Declaration**

```java
public void setTimeout(int time)
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-----|
time|int|毫秒数 如:15000	

### cancelUtils()

用于释放上下文以及各种资源

**Declaration**

```java
public void cancelUtils()
```

**Discussion**

该方法在客户使用的验证码界面的onDestroy生命周期中调用

### getISonto()

调用该方法提示SDK使用自定义api1模式

**Declaration**

```java
public void getISonto()
```

**Discussion**

该方法在客户使用自定义api1时调用，且必须是在onCreate生命周期中调用

### gtSetApi1Json(JSONObject)

用于获取客户请求api1返回的的数据

**Declaration**

```java
public void gtSetApi1Json(JSONObject json)
```
**Parameters**

Param	|Type | Description | 			
------	|-----|-----|
json	|JSONObject|客户请求api1返回的的数据

**Discussion**

该方法用于自定义api1时必定使用，注意参数json格式：

```
{
	"success" : 1,
	"challenge" : "4a5cef77243baa51b2090f7258bf1368",
	"gt" : "019924a82c70bb123aae90d483087f94",
	"new_captcha" : true
}
```

以上`success`，`challenge`，`gt`，`new_captcha`这4个参数为必须参数，客户可以添加新参数，但是不能删减，以免导致无法加载验证码

### setDialogTouch(boolean)

设置点击弹出框周围,弹出框是否消失

**Declaration**

```java
public void setDialogTouch(boolean bol)
```
**Parameters**

Param	|Type 	| Description | 			
------	|-----	|-------------|
bol		|boolean|true 是/false 否

**Discussion**

该方法比较常用，建议设置成false

### cancelAllTask()

关闭所有异步加载

**Declaration**

```java
public void cancelAllTask()
```

## Listener

### gt3DialogReady()

监听弹框是否正常弹出

**Declaration**

```java
public void gt3DialogReady()
```

**Discussion**

验证码正常弹出会进该接口 反之不进

### gt3CloseDialog(int)

用于监听弹框消失

**Declaration**

```java
public void gt3CloseDialog(int  num)
```

**Parameters**

Param	|Type 	| Description | 			
------	|-----	|-------------|
num		|int	|1 点击验证码的关闭按钮来关闭验证码，2 点击屏幕关闭验证码，3 点击返回键关闭验证码

### gt3FirstResult(JSONObject)

用于获取客户api1返回的数据

**Declaration**

```java
public void gt3FirstResult(JSONObject jsonObject) 
```

**Parameters**

Param	|Type 	| Description | 			
------	|-----	|-------------|
jsonObject|JSONObject|客户api1返回的数据

### gt3GetDialogResult(String)

用于获取客户api2返回的数据

**Declaration**

```java
public void gt3GetDialogResult(String result) 
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-------------|
result|String|客户api2返回的数据

### gt3GetDialogResult(boolean, String)

用于获取客户api2返回的数据

**Declaration**

```java
public void gt3GetDialogResult(boolean stu, String result) 
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-------------|
result|String|客户api2返回的数据
stu|boolean|判断是否获取到返回值 true：有 false：没有


**Discussion**

该方法用于自定义api1时使用，当stu为true，result会携带二次验证需要的三个参数geetest_challenge，geetest_validate，geetest_seccode，以上三个参数为必传参数，客户可以自行添加数据但不得删减，提交这些从参数到api2服务器即可

### gt3DialogSuccessResult(String)

用户操作验证码后的结果数据

**Declaration**

```java
public void gt3DialogSuccessResult(String result) 
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-------------|
result|String|用户操作验证码后的结果数据


### gt3DialogOnError(String)

用户操作验证码后的结果数据

**Declaration**

```java
public void gt3DialogOnError(String error) 
```

**Parameters**

Param	|Type | Description | 			
------	|-----|-------------|
error|String|用户操作验证码后的结果数据

**Discussion**

验证错误码后面详细说明

### gt3SetIsCustom()

用户是否使用自定义二次验证

**Declaration**

```java
public boolean gt3SetIsCustom()
```

**Discussion**
使用自定义二次验证必须设置的接口

**Return Value**

返回给`gt3geetestutils`告知使用自定义二次验证, 类型为`boolean` true:是 false:不是（默认)

### gt3captchaApi1()

用于往api1里面添加提交参数

**Declaration**

```java
public Map<String, String> gt3captchaApi1()
```

**Discussion**

在不自定义api1的情况下，客户想往api1里面添加提交参数可以使用该方法，该方法为get提交

**Return Value**

返回给`gt3geetestutils`用于提交api1的参数, 类型为`Map<String, String>`

### gt3SecondResult()

用于往api2里面添加提交参数

**Declaration**

```java
public Map<String, String> gt3SecondResult()
```

**Discussion**

在不自定义api2的情况下，客户想往api2里面添加提交参数可以使用该方法，该方法为post提交

**Return Value**

返回给`gt3geetestutils`用于提交api2的参数, 类型为`Map<String, String>`

### gtOnClick(boolean)

用于判断自定义button是否被点击

**Declaration**

```java
public void gtOnClick(boolean onclick)
```

**Parameters**

|Type | Description | 			
|-----|-------------|
|boolean|true 表示button被点击

**Discussion**

用于监听button按键是否被点击

# ErrorCode

## test-Button

`test-Button`产品的业务错误代码

ErrorCode	|Description
----------|------------
200			|ajax请求被forbidden
202			|验证码停用
204			|webview加载出现的错误或者超时
205			|api1接口错误或者返回为null
206			|gettype接口错误或者返回为null
207		   	|getphp接口错误或者返回为null
208			|ajax接口返回错误或者返回为null
