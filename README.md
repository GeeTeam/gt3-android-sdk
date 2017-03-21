# gt3-android-sdk
#概述与资源

极验验证3.0 Android SDK提供给集成Android原生客户端开发的开发者使用, SDK不依赖任何第三方库。

##环境需求

条目||
----|------
开发目标|Android 4.0+
开发环境|Android Studio 2.1.3
系统依赖|无
sdk三方依赖|无

##资源下载

条目||
-------------|--------------
产品结构流程  |[http://]()
SDK下载链接 |[gt3-android-sdk](https://github.com/GeeTeam/gt3-android-sdk/)
SDK接口文档   |[gt3-android-sdk](https://github.com/GeeTeam/gt3-android-sdk/)
错误码列表   |[http://]()
Q&A         |[http://]()

#安装

##获取SDK

###通过添加依赖获取SDK

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


###使用`git`命令从Github获取

```
git clone https://github.com/GeeTeam/gt3-android-sdk.git
```

###手动下载获取
使用从github下载`.zip`文件获取最新的sdk。

[Github: gt3-android-sdk]()

##导入SDK
如果你**不是**使用**添加依赖**, 将获取的`.aar`文件拖拽到工程中的libs文件夹下。

![import](./img/import.png)

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
       compile(name: 'aar文件夹的名字', ext: 'aar')

```

![libraries](./img/libraries.png)

##配置接口

Android sdk主要提供以下接口:

1. 配置验证初始化
2. 启动验证
3. 获取验证结果
4. 处理错误的代理

参考极验验证3.0的[产品结构流程](), 必须要先在您的后端搭建相应的**服务端SDK**，并配置从[极验后台]()获取的`<gt_captcha_id>`和`<geetest_key>`, 并且将配置的接口`API1`和`API2`放入客户端的初始化方法中。

创建验证实例, 并且创建展示验证的方法:

要实例化`GtDialog`并且要注册相应的监听事件.

```java
 private GT3GtDialog dialog;
    private void openGtTest(Context ctx, JSONObject params) {
        dialog = new GT3GtDialog(context, gt, challenge, click, api_server, static_servers, static_servers2, result);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                geetestMananger.startclose();
                dialog.dismiss();
            }
        });
        dialog.setGtListener(new GT3GtDialog.GtListener() {

            @Override
            public void gtResult(boolean success, String result) {

                if (success) {
                    toastMsg("client captcha succeed:" + result);
                    mGtAppValidateTask = new GtAppValidateTask();
                    mGtAppValidateTask.execute(result);
                } else {
                    //TODO 验证失败
                }
            }

            @Override
            public void gtCallClose() {
            }

            @Override
            public void gtCallReady(Boolean status) {


                if (status) {
                    //TODO 验证加载完成

                } else {
                    //TODO 验证加载超时,未准备完成
                }
            }

            @Override
            public void gtError() {
            }

        });

    }   
```

##编译并运行你的工程

编译你的工程, 体验全新的极验验证3.0！

![build](./img/build.png)

轻轻点击你集成的验证按钮, 如此自此自然, 如此传神。

`1.`未激活

![sample1](./img/sample1.jpg)

`2.`激活后

![sample2](./img/sample2.jpg)

#代码示例

##初始化

```java
GeetestUtils geetestUtils = new GeetestUtils(MainActivity.this);
geetestUtils.getGeetest();

```

##激活验证

通过**UI操作**点击验证按钮或使用初始化方式激活验证:

```java
GeetestUtils geetestUtils = new GeetestUtils(MainActivity.this);
geetestUtils.getGeetest();
```

##处理验证结果

只有完成二次验证后, 本次验证才是完整完成。

```java
  class GtAppValidateTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                JSONObject res_json = new JSONObject(params[0]);

                Map<String, String> validateParams = new HashMap<>();

                validateParams.put("geetest_challenge", res_json.getString("geetest_challenge"));

                validateParams.put("geetest_validate", res_json.getString("geetest_validate"));

                validateParams.put("geetest_seccode", res_json.getString("geetest_seccode"));

                String response = captcha.submitPostData(validateParams, "utf-8");

                //TODO 验证通过, 获取二次验证响应, 根据响应判断验证是否通过完整验证

                return response;

            } catch (Exception e) {

                e.printStackTrace();
            }

            return "invalid result";
        }

        @Override
        protected void onPostExecute(String params) {
            //TODO 验证成功
        }

    }
```

##处理验证错误

验证过程中可能发生一些不可避免的错误, 您可以通过在下面的代理方法中进行处理:

```java
 public void submitPostDataTimeout() {
                    mGtAppValidateTask.cancel(true);
                    toastMsg("submit error");

                }
```

可能遇到的错误码请参考后面的列表: [error code list]()
