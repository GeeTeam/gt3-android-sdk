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

[Github: gt3-android-sdk]()

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



可能遇到的错误码请参考后面的列表: [error code list]()
