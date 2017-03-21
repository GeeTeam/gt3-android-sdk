# gt3-android-sdk
# 概述与资源

极验验证3.0 Android SDK提供给集成Android原生客户端开发的开发者使用。

# 安装

## 获取SDK

### 通过添加依赖获取SDK

首先在工程中的`build.gradle`中添加以下代码

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
下一步，在项目中的`build.gradle`中添加以下代码

```
dependencies {
		compile 'com.github.GeeTeam:gt3-android-sdk'
	}
```


### 使用`git`命令从Github获取

```
git clone https://github.com/GeeTeam/gt3-android-sdk.git
```

### 手动下载获取
使用从github下载`.zip`文件获取最新的sdk。

[Github: gt3-android-sdk](https://github.com/GeeTeam/gt3-android-sdk.zip)

## 导入SDK
如果你**不是**使用**添加依赖**, 将获取的`.aar`文件拖拽到工程中的libs文件夹下。

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
### 由于传出的值要用EventBus接收，所以要添加EventBus的依赖
```java
   compile 'org.greenrobot:eventbus:3.0.0'

```
### 在AndroidManifest.xml文件中添加权限
```java
     <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />

```
### 设置服务端URL
```java
     // 设置获取id，challenge，success的URL，需替换成自己的服务器URL
    private static final String captchaURL = "http://192.168.1.208:9977/gt/register1";
    // 设置二次验证的URL，需替换成自己的服务器URL
    private static final String validateURL = "http://192.168.1.208:9977/gt/form-validate1";

```
### 可以自定义设置显示的文字和自定义View的长度
```java
    //设置正常显示的文字
    private static final String normalText = "点击按钮进行验证";
    //设置失败显示的文字
    private static final String failText = "请求失败";
    //设置等待显示的文字
    private static final String waitText = "请完成上方验证";
    //设置成功显示的文字
    private static final String successText = "验证成功";
    //内部的半径  以下单位都为dp,如果用px的话，本sdk内部有DensityUtil方法px2dip()可以将px转换成dp
    private static final int internalRadius = 12;
    //外部的半径
    private static final int externalRadius = 30;
    //呼吸内部的半径
    private static final int BreatheRadius = 25;
    //完成加载的点的半径
    private static final int waitRadius = 5;
    //成功的半径
    private static final int successRadius = 24;
    //报错的半径
    private static final int failRadius = 16;
    //宕机状态三角的直角边长
    private static final int downTimePath = 25;
    //报错内部线的长度的一半
    private static final int failLine = 6;

```
### 验证码加载开始
```java
     new GT3GeetestUtils(MainActivity.this).getGeetest();

```

### 验证码加载成功
```java
      @Subscribe(threadMode = ThreadMode.MAIN)
    public void test(String a) {
        if (a.equals("dosuccess")) {
            //验证码验证成功后的操作

        }
    }

```

可能遇到的错误码请参考后面的列表: [error code list]()
