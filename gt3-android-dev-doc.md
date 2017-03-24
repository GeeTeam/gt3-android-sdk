#Android SDK Documentation

文件名				|说明
----------------	|----------
GT3Geetest.java		|验证的网络层管理器
GT3GeetestButton.java|自定义的LinearLayout
GT3GeetestUtils.java  |封装的验证码响应工具
GT3GeetestView.java  |自定义View


#GT3Geetest

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

##GT3Geetest()

###abstract

GT3Geetest的初始化方法

###declaration

~~~java

public GT3Geetest(String captchaURL, String validateURL);
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|---------------------
`captchaURL`	|`String`		|从网站主服务器获取验证启动数据的接口(api_1)
`validateURL`	|`String`		|从网站主服务器进行二次验证的接口(api_2)

###returns

类型			|说明
-------------	|----------------------
`Geetest`		|geetest验证网络管理的实例

##checkServer()

###abstract

检测服务器状态

###declaration

~~~java

public JSONObject checkServer();
~~~

###parameters

无

###returns

无
##checkRealServer()

###abstract

检测服务器状态

###declaration

~~~java

public JSONObject checkRealServer();
~~~

###parameters

无

###returns

无
##getphpServer()

###abstract

检测get.php状态

###declaration

~~~java

public JSONObject getphpServer();
~~~

###parameters

无

###returns

无
##getajaxServer()

###abstract

检测get.ajax状态

###declaration

~~~java

public JSONObject getajaxServer();
~~~

###parameters

无

###returns

无
##setGeetestListener()

###abstract

注册验证网络事件监听

###discussion

包含两个回调方法

###declaration

~~~java

public void setGeetestListener(GeetestListener listener);
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`listener`	|`GeetestListener`|验证的网络监听器

###returns

无

###seealso

~~~java

public interface GeetestListener {
    void readContentTimeout();//从api_1获取验证启动数据超时
    void submitPostDataTimeout();//向api_2提交二次验证数据
    void receiveInvalidParametes();//检测请求的JSON是否为无效参数
}
~~~

##getGt()

###abstract

获取验证id

###discussion

从极验后台获取的验证id

###declaration

~~~java

public String getGt();
~~~

###parameters

无

###returns

类型			|说明
-------------	|----------------------
`String`		|用于启动验证的id

##getChallenge()

###abstract

获取验证challenge

###discussion

每个challenge只能用于请求一次验证

###declaration

~~~java

public String getChallenge();
~~~

###parameters

无

###returns

类型			|说明
-------------	|----------------------
`String`		|用于启动验证的challenge

##getSuccess()

###abstract

获取极验验证的服务状态

###discussion

`ture`/`false` 请求正常验证/静态验证

###declaration

~~~java

public boolean getSuccess();
~~~

###parameters

无

###returns

类型			|说明
-------------	|----------------------
`boolean`		|`true`/`false` 验证服务正常/异常

##setTimeout()

###abstract

配置验证数据请求超时时限

###discussion

默认10000ms

###declaration

~~~java

public void setTimeout(int timeout);
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`timeout`		|`int`			|验证的超时时限

###returns

无

##submitPostData()

###abstract

提交二次验证测数据

###discussion

此方法包装的请求必须为`POST`类型

###declaration

~~~java

public String submitPostData(Map<String, String> params, String encode);
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`params`		|`Map<String, String>`|二次验证参数
`encode`		|`String`		|编码格式

###returns

类型			|说明
-------------	|----------------------
`String`		|返回的二次验证结果

#GT3GeetestButton

**Public Mehtods**

方法名				|说明
----------------	|----------------
`isNetworkAvailable(_)`|网络工具
`test(_)`	|改变整个ViewGroup的状态

##isNetworkAvailable()

###abstract

判断网络是否可用

###discussion

UI请在主线程操作

###declaration

~~~java

 public static boolean isNetworkAvailable(Context context)；
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|---------------------
`context`		|`Context`	|activity上下文

###returns

类型			|说明
-------------	|----------------------
boolean	|true/false

##test()

###abstract

注册验证视图事件监听

###discussion

ui操作

###declaration

~~~java

  public void test(String a)；
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`a`	|`String`	|验证的视图监听

###returns

无

#GT3GeetestUtils

**Public Mehtods**

方法名				|说明
----------------	|----------------
`getGeetest(_)`|验证加载启动
`canWork(_,_)`	|点击进行的操作
`startDownTime()`	|宕机点击下进行的操作

##getGeetest()

###abstract

验证加载启动

###discussion
无
###declaration

~~~java

  public void getGeetest() ；
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|---------------------
无		|	|

###returns

类型			|说明
-------------	|----------------------
无	|

##canWork()

###abstract

点击进行的操作

###discussion

点击操作

###declaration

~~~java

  public void canWork(String angle, String getXY)；
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|-------------
`angle`	|`String`	|滑动轨迹信息
`getXY`	|`String`	|验证码的坐标
###returns

无

##startDownTime()

###abstract

宕机状态下点击进行的操作

###discussion

点击操作

###declaration

~~~java

  public void startDownTime()；
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|-------------
无	|	|

###returns

无

#GT3GeetestView

**Public Mehtods**

方法名				|说明
----------------	|----------------
`normal()`|普通状态
`start()`	|开始状态
`startdown()`	|呼吸状态
`startcircle()`	|陀螺仪状态
`startscanning()`	|扫描状态
`startsfinish()`	|完成等待状态
`startsuccess()`	|验证成功状态
`startfail()`	|验证失败状态
##normal()

###abstract

普通状态

###discussion
无
###declaration

~~~java

  public void normal() ；
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|---------------------
无		|	|

###returns

类型			|说明
-------------	|----------------------
无	|
##start()

###abstract

开始状态

###discussion
无
###declaration

~~~java

  public void start() ；
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|---------------------
无		|	|

###returns

类型			|说明
-------------	|----------------------
无	|
##startdown()

###abstract

呼吸状态

###discussion
无
###declaration

~~~java

  public void startdown() ；
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|---------------------
无		|	|

###returns

类型			|说明
-------------	|----------------------
无	|
##startcircle()

###abstract

陀螺仪状态

###discussion
无
###declaration

~~~java

  public void startcircle() ；
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|---------------------
无		|	|

###returns

类型			|说明
-------------	|----------------------
无	|

##startscanning()

###abstract

扫描状态

###discussion
无
###declaration

~~~java

  public void startscanning() ；
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|---------------------
无		|	|

###returns

类型			|说明
-------------	|----------------------
无	|

##startsfinish()

###abstract

完成等待状态

###discussion
无
###declaration

~~~java

  public void startfinish() ；
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|---------------------
无		|	|

###returns

类型			|说明
-------------	|----------------------
无	|


##startsuccess()

###abstract

验证成功状态

###discussion
无
###declaration

~~~java

  public void startsuccess() ；
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|---------------------
无		|	|

###returns

类型			|说明
-------------	|----------------------
无	|
##startfail()

###abstract

验证失败状态

###discussion
无
###declaration

~~~java

  public void startfail() ；
~~~

###parameters

参数名			|类型			|说明
-------------	|------------	|---------------------
无		|	|

###returns

类型			|说明
-------------	|----------------------
无	|
