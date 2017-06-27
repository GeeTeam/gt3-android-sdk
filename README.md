# gt3-android-sdk

# master分支为不带Button的验证码,dv-master分支为带Button的验证码,develop分支为两个整合优化版。

# 本分支为不带Button的验证码，请按需求进行demo下载

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
<dependency>
  <groupId>gt3bind.android</groupId>
  <artifactId>sdk</artifactId>
  <version>1.1.0</version>
  <type>pom</type>
</dependency>
```
使用gradle

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
| captchaURL|设置获取id，challenge，success的URL，需替换成自己的服务器URL|
|validateURL|设置二次验证的URL，需替换成自己的服务器URL|

```java
        new GT3GeetestUrl().setCaptchaURL(captchaURL);
        new GT3GeetestUrl().setValidateURL(validateURL);
        gt3GeetestUtils = new GT3GeetestUtils(MainActivity.this);
        gt3GeetestUtils.gtDologo();
```
### 注册EventBus进行消息分发

```java
 compile 'org.greenrobot:eventbus:3.0.0'

```
### 点击按钮验证码加载开始
```java
    gt3GeetestUtils.getGeetest();

```

### 验证码加载过程中的接口


```java
  
   gt3GeetestUtils.setGtListener(new GT3GeetestUtils.GT3Listener() {
            //点击验证码的关闭按钮来关闭验证码
            @Override
            public void gt3CloseDialog() {
             
            }

            //点击屏幕关闭验证码
            @Override
            public void gt3CancelDialog() {
               
            }

            //验证码加载失败
            @Override
            public void gt3DialogError() {

            }

            //验证码加载准备完成
            @Override
            public void gt3DialogReady() {

            }

            //拿到验证返回的结果,此时还未进行二次验证
            @Override
            public void gt3GetDialogResult(String result) {

            }

            //验证码验证成功
            @Override
            public void gt3DialogSuccess() {
            
            }

            //验证码验证失败
            @Override
            public void gt3DialogFail() {

            }

            //二次验证请求之后的结果
            @Override
            public void gt3DialogSuccessResult(String result) {

            }
        });

```
