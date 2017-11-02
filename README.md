# gt3-android-sdk

# 概述与资源

极验验证3.0 Android SDK提供给集成Android原生客户端开发的开发者使用。
集成极验验证3.0的时，需要先了解极验验证3.0的 [产品结构](http://docs.geetest.com/install/overview/#产品结构)。并且必须要先在您的后端搭建相应的**服务端SDK**，并配置从[极验后台]()获取的`<gt_captcha_id>`和`<geetest_key>`用来配置您集成了极验服务端sdk的后台。


# 环境

编译环境需要23或以上


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
       compile(name: 'gt3geetest_sdk', ext: 'aar')

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
4.加载验证码

```java
       （band模式下）--点击后会有一个加载框，中间有一个gif在转动
        //在您acitvity的onCreate方法里面调用（初始化）
        gt3GeetestUtils =new GT3GeetestUtilsBind(Main3Activity.this);
	//点击想调用验证码的按键，加载验证码
	gt3GeetestUtils.getGeetest(Main3Activity.this,captchaURL, validateURL,null);
	
       （unband模式下）--拥有极验的自定义控件
        //在您acitvity的onCreate方法里面调用（初始化）
	gt3GeetestUtils =  GT3GeetestUtils.getInstance(MainActivity.this);
	//直接加载验证码，不需要按键触发，因为该模式下提供自定义按键
	gt3GeetestUtils.getGeetest(captchaURL,validateURL,null);
```


5.在onCreate中调用gt3GeetestUtils.setGtListener接口，具体用法查看demo，下面也会列出

```java
  gt3GeetestUtils.setGtListener(new GT3GeetestUtilsBind.GT3Listener() {
     
            /**
             * num 1 点击验证码的关闭按钮来关闭验证码
             * num 2 点击屏幕关闭验证码
             * num 3 点击返回键关闭验证码
             */
            @Override
            public void gt3CloseDialog(int  num) {
            }

            /**
             * 验证码加载准备完成
             * 此时弹出验证码
             */
            @Override
            public void gt3DialogReady() {

            }

            /**
             * 拿到第一个url（API1）返回的数据
             */
            @Override
            public void gt3FirstResult(JSONObject jsonObject) {
            }


            /**
             * 往API1请求中添加参数
             * 添加数据为Map集合
             * 添加的数据以get形式提交
             */
            @Override
            public Map<String, String> captchaApi1() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("t", System.currentTimeMillis()+"");
                return map;
            }

            /**
             * 设置是否自定义第二次验证ture为是 默认为false(不自定义)
             * 如果为false这边的的完成走gt3GetDialogResult(String result)
             * 如果为true这边的的完成走gt3GetDialogResult(boolean a, String result)
             * result为二次验证所需要的数据
             */
            @Override
            public boolean gtSetIsCustom() {
                return false;
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
            public void gt3GetDialogResult(boolean stu, String result) {

                if (stu) {
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
                     //  二次验证成功调用 gt3GeetestUtils.gt3TestFinish();
                     //  二次验证失败调用 gt3GeetestUtils.gt3TestClose();
                     */
                }
            }

            /**
             * 需要做验证统计的可以打印此处的JSON数据
             * JSON数据包含了极验每一步的运行状态
             */
            @Override
            public void gt3GeetestStatisticsJson(JSONObject jsonObject) {
            }

            /**
             * 往二次验证里面put数据
             * put类型是map类型
             * 注意map的键名不能是以下三个：geetest_challenge，geetest_validate，geetest_seccode
             */
            @Override
            public Map<String, String> gt3SecondResult() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("testkey","12315");
                return map;
            }

            /**
             * 二次验证完成的回调
             * result为验证后的数据
             * 根据二次验证返回的数据判断此次验证是否成功
             * 二次验证成功调用 gt3GeetestUtils.gt3TestFinish();
             * 二次验证失败调用 gt3GeetestUtils.gt3TestClose();
             */
            @Override
            public void gt3DialogSuccessResult(String result) {

                if(!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jobj = new JSONObject(result);
                        String sta = jobj.getString("status");
                        if ("success".equals(sta)) {
                            gt3GeetestUtils.gt3TestFinish();
                        } else {
                            gt3GeetestUtils.gt3TestClose();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    gt3GeetestUtils.gt3TestClose();
                }
            }

            /**
             * 验证过程错误
             * 返回的错误码为判断错误类型的依据
             */

            @Override
            public void gt3DialogOnError(String error) {
                gt3GeetestUtils.cancelAllTask();

            }
        });
    }
``` 



以上是常规的集成，由于客户的需求，SDK提供了相应的接口，下面是接口的详细说明




# 接口详细说明（分2种模式讲解）

1. gt3CloseDialog 验证码弹出后被人为关闭的回调，一般来说用不到

2. gt3DialogReady 验证码弹准备完成，所有请求正常，准备弹出的接口回调，一般用于统计是否正常弹出验证码

3. gt3FirstResult api1请求正常，且拿到的了JSON数据，在参数里面返回，一般用于查看api1返回数据是否正常

4. captchaApi1 往api1后面追加自定义参数且这里的参数是以get形式提交，传递一个map集合即可，用于向api1里面添加请求参数，用的比较少，可以可以直接在设置api1的时候设置在后面，可以不用在这里追加（ private static final String captchaURL = "http://www.geetest.com/demo/gt/register-click?xx=xx&vv=vv"）

5. gtSetIsCustom 用于设置自定义api2的开关，如果你想自定义api2，一定要把return值设置为ture

6. gt3GetDialogResult 这个回调有两个，其中有一个只有返回String result，这个用于不自定义api2，执行完验证操作后的数据结果返回，其他一个有返回boolean bol, String result这个用于自定义api2，执行完验证操作后的数据结果返回。且自定义api2拿到验证结果数据后需要请求客户自己的api2，去做结果效验

7. gt3SecondResult 用于向api2里面put参数，传递一个map集合即可，一般用于不自定义api2但是需要传递额外的参数，可以使用该接口回调完成。

8. gt3DialogSuccessResult api2二次效验完成后的结果，用于客户根据不同结果做出不同的处理，
	
	gt3GeetestUtils.gt3TestFinish();--验证完成（bind，unbind）
	
	gt3GeetestUtils.gt3TestClose();--验证失败（bind）
	
	gt3GeetestUtils.gt3CloseButton();--验证失败（unbind）

9. gt3DialogOnError 当验证出现错误时会在该接口返回错误码，错误码显示在弹出框右下角（bind）.自定义按键右下角（unbind）,用于追溯错误，后面有列出常用验证码

10. gtOnClick 在unbind模式下特有，点击自定义按键后的回调，在自定义api1时，在该方法里面做自定义请求




# 常用问题

### 1.有设置验证弹框点击周围不消失的方法吗？

答：gt3GeetestUtils.setDialogTouch(true);方法可以设置弹框是否可以点击周围取消

### 2.什么是自定义api？

答：API1,API2接口默认是交给SDK内部请求的，客户不需要操作,自定义的话需要自行做网络请求


### 3.自定义api1如何定义？

答：bind模式下
   在您点击需要启动验证的按钮后，自行做网络请求，拿到网络请求的结果后调用如下两个方法即可
   gt3GeetestUtils.gtSetApi1Json(parmas);
   gt3GeetestUtils.getGeetest(Main3Activity.this,captchaURL, validateURL,null);
  
  
   unbind模式下
   首先在调用getGeetest之前调用一下gt3GeetestUtils.getISonto();方法，代码如下
   
    gt3GeetestUtils =  GT3GeetestUtils.getInstance(MainActivity.this);
    gt3GeetestUtils.getISonto();
    gt3GeetestUtils.getGeetest(captchaURL,validateURL,null);

   然后在gtOnClick回调里，自行做网络请求，拿到网络请求的结果后调用如下一个方法即可
   gt3GeetestUtils.gtSetApi1Json(parmas);
   
 注：parmas为您自定义api1的返回结果，格式参考：

{\"success\":1,\"challenge\":\"4a5cef77243baa51b2090f7258bf1368\",\"gt\":\"019924a82c70bb123aae90d483087f94\",\"ne  w_captcha\":true}"


### 4.unbind模式下我验证完毕一轮后还想再次验证，可是按键点击不了怎么回事？

答：在gt3DialogSuccessResult里面调用Gt3GeetestTestMsg.setCandotouch(true);即可

### 5.为什么我所有代码都写好了，验证码就是弹不出？

答：目前导致验证码无法弹出的情况分如下几种

  a.SDK经过了混淆，所以不要在对其进行混淆
  
  b.3.0的SDK是否使用了2.0的gt值
  
  c.api1数据返回格式是否按照SDK标准返回（参考上面的parmas）
  
  d.手机是否连接了网络代理

### 6.验证码弹出后，验证完成后为什么弹框没有消失？

答：您需要在gt3DialogSuccessResult回调里面做二次验证结果处理，参考 接口详细说明-10

### 7.验证码弹出后，我横竖屏疯狂切换为什么会有内存泄漏？

答：您的需求比较的奇怪，不过SDK也有考虑到这块

1.在项目清单里面在需要弹出验证码的activity上加上android:configChanges="orientation|keyboardHidden|screenSize"

2.在需要弹出验证码的activity上加上

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

### 7.有设置验证框超时时间的方法吗？

答：gt3GeetestUtils.setTimeout(15000),设置为毫秒

### 8.如何检测SDK的版本号？

答：gt3GeetestUtils.getVersion(),获取当前SDK的版本号

### 9.如何理解自定义接口？

答：最简单的集成方式是传入API1 API2其他所有交给SDK内部处理，但是一些SDK内部不能完成的数据传输，客户针对自己的需求进行自定义API这个时候客户所编写的代码会变多，目前只开放API1 API2的自定义，其他是SDK内部接口，不能提供给客户自定义。


# 常用错误码 
### 1.timeoutError 201

   全局网络请求超时，请检查网络连接

### 2.webViewError 204

   webview加载出现的错误

### 3.httpError 205

   api1接口返回为null，查看api1的参数和地址是否有误，网络保持畅通

### 4.httpError 206

   gettype接口返回为null，查看gettype的参数和地址是否有误，网络保持畅通

### 5.httpError 207

   getphp接口返回为null，查看getphp的参数和地址是否有误，网络保持畅通

### 6.httpError 208

   ajax接口返回返回为null，查看ajax的参数和地址是否有误，网络保持畅通
    
### 7.httpError 209

   api2接口返回返回为null，查看api2的参数和地址是否有误，网络保持畅通

### 8.尝试过多 _01

   连续刷新5次

### 9.尝试过多 _12

   连续验证错误6次

### 10.web请求错误 _105

  无网络状态下滑动报错
    
### 11.初始化错误 211

   验证码初始化回调用2个接口，找找这里的问题

### 12.服务被forbidden 200

   网络请求时，此时服务被forbidden
    
### 13.challenge错误  _02

   challenge 过时，或者重复使用
    
### 14.challenge错误  _22

  服务器未检测到challenge
    
### 15.gt错误  _31

  服务器未检测到gt  

以上是常见的错误码

# 版本迭代 

===版本号以GT3GainIp的getPhoneInfo方法中的gt3参数为主===

版本说明：0.0.0 --> 接口变更和比较大的该动.新增功能.迭代功能和bug的修复

**3.3.1** 
1.代码整理，稳定发布

**3.3.0** 
1.新的网络请求框架
2.新的混淆方式
3.gt3GeetestUtils.cancelUtils();的加入
4.错误码抛出提到主线程
5.外部接口的精简和优化
6.bind模式下调用方法的改变，去掉了dologo()

**3.2.11** 对数据采集做了条数限制，优化了webview的超时代码

**3.2.11** 对GT3CallBacks类进行了优化，主要是注册和注销以及生命周期把控这块

**3.2.10** 修复三个问题，1.在5.0以下会stoploading处报错  2.bind模式下webview在为空直接设置到view会有问题 3.点击弹框的小感叹号会崩溃的问题，ViewGroup的优化（部分界面会有问题）

**3.2.9** 修复在5.0版本以下会崩溃的问题。同时在横竖屏切换下，没有进行dialog的关闭，来防止内存泄漏,去掉rege_21 gt3FirstGo两个接口,unbind模式下

**3.2.8** 新增判断所有api请求是否成功的方法 方法名：getGeetestStatisticsJson()新增调试模式接口，不管几次点击都是走所有的网络请求，不会漏掉get 和gettype  方法名：setdebug()

**3.2.7** 新增获取版本号的方法：getVersion 新增设置webview超时的时长：setTimeout 网络库的请求超时时长从10s调到5s,并去掉了gt3DialogSuccess()接口

**3.2.6** 修复了竖屏状态下突然横屏导致的大图显示异常的问题，对callback类进行重写，防止内存泄漏，对uuid进行缓存，添加了一个获取版本号的方法

**3.2.5** getPhoneInfo加入了2个参数，分别为androidId,uuid，修复api1上get参数时 没传参数路径后会多一个?的问题

**3.2.4** api1加入cookie 同时api2传入该cookie，多暴露二个接口记录弹出成功率和验证类型

**3.1.3** UI线程优化

**3.1.2** 版为整个流程只请求一次api1

**3.0.2** 版为带初始化API1的版本

   
>本SDK包含了2个SDK合成版本 ，代码已经混淆，不需要再次混淆。
   同时极验会继续努力，给予您更好的SDK
