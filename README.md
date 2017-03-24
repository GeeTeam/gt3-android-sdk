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
## 初始化

### 在AndroidManifest.xml文件中添加权限
```java
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />

```
### 在对应的xml文件中添加控件并在代码中初始化

```java
    <com.example.sdk.GT3GeetestButton
        android:id="@+id/ll_btn_type"
        android:layout_width="290dp"
        android:layout_height="44dp"
        android:layout_centerHorizontal="true"
        android:layout_centerInParent="true"
        android:gravity="center"
        android:orientation="horizontal" />

```
```java
GT3GeetestButton gtbt=（GT3GeetestButton）findViewById(R.id.ll_btn_type);
```

### 设置服务端URL

| Attribute | Descripion |
| ------ | ------ |
| captchaURL|设置获取id，challenge，success的URL，需替换成自己的服务器URL|
|validateURL|设置二次验证的URL，需替换成自己的服务器URL|

```java
        new GT3GeetestUrl().setCaptchaURL(captchaURL);
        new GT3GeetestUrl().setValidateURL(validateURL);

```
### 注册GT3EventBus及注销

在生命周期开始进行初始化，在注销的时候进行销毁

```java
 @Override
    public void onCreate() {
        super.onCreate();
        GT3EventBus.getInstatnce().register(this);
    }
    
  @Override
    public void onDestroy() {
        super.onDestroy();
        GT3EventBus.getInstatnce().unregister(this);
    }

```
### 验证码加载开始
```java
     new GT3GeetestUtils(MainActivity.this).getGeetest();

```

### 验证码加载成功


```java
  
    public void onEventUI(String a) {
        if (a.equals("dosuccess")) {
            //验证码验证成功后的操作

        }
    }

```
