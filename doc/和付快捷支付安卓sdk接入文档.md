## 和付支付安卓sdk接入文档

### 一、快速集成
#### 导入sdk
##### gradle导入方式
1、在项目中的build.gradle中添加jcenter远程仓库

```
buildscript {
    repositories {
        jcenter()
    }
}
allprojects {
    repositories {
        jcenter()
    }
}
```
2、在module中的build.gradle中添加sdk远程依赖

```
dependencies{
	compile 'me.andpay.ma.sdk:fastpay:1.0.1'
	compile 'me.andpay.ma.sdk:fastpay-ext-eco:1.0.0'
}
```

##### maven导入方式
```
<dependency>
  <groupId>me.andpay.ma.sdk</groupId>
  <artifactId>fastpay</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
<dependency>
  <groupId>me.andpay.ma.sdk</groupId>
  <artifactId>fastpay-ext-eco</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

##### 下载sdk导入
将下载的fastpay-1.0.1.aar拷贝到module的libs目录，并在module的build.gradle中填下如下配置：

```
repositories{
    flatDir{
    	dirs 'libs'
    }
}
dependencies{
	compile(name:'fastpay-1.0.1',ext:'aar')
	compile(name:'fastpay-ext-eco-1.0.0',ext:'aar')
}
```
#### sdk权限要求
所有权限均已在sdk中声明，sdk已经适配了6.0的动态权限申请，接入方无需为权限申请添加额外代码（请知晓：启动sdk时不会强制获取权限，但是在交易过程中，为确保交易安全，可能会强制获取部分权限，权限获取失败可能导致流程无法进行）

##### 1、通用权限
```
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
```
##### 2、定位权限

```
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
```
##### 3、读写权限

```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```
##### 4、相机权限

```
<uses-permission android:name="android.permission.CAMERA"/>
```

### 二、发起支付
#### 初始化sdk
在使用sdk前需要对sdk进行初始化配置，初始化配置只需要进行一次,建议放在Activity onCreate方法中

```
FastPaySdkInnerManager.init(FastPaySdkConfig config)
```
#### 启动sdk
其中params主要是启动sdk的参数配置，可以包含以下参数：

mobileNo：用户手机号（可选）

longitude：经度（可选，由于sdk未依赖第三方包，定位可能失败而导致无法交易）

latitude：纬度（可选）

```
FastPaySdkManager.startFastPayModule(Activity activity,Map<String,Object> params);
```
#### sdk事件回调
sdk在运行过程中会发布一些关键性的事件，接入方可以注册监听器来捕获这些事件

```
FastPaySdkManager.registerFastPayEventListener(FastPayEventListener eventListener);
```
事件类型：

```
交易成功：eventType: TS, eventData:{txnId}(map的键值)
交易失败：eventType: TF, eventData:{message}
```


### 注意事项
#### 环境切换
实例app中有stage1、pro两个环境可供测试，每次切换环境前，请先重启app(杀掉app重新打开)，进入app后点击相关环境按钮，即可切换到相应的环境

#### applicationId配置
sdk会对接入方的app进行过滤，未知的app将无法正常使用，所以，接入方需联系我司，提供接入方android app的applicationId，只有我司配置接入权限之后，才能正常使用

#### 混淆配置
如果项目中有代码混淆配置，请将以下配置加入混淆规则中：

```
-keepclassmembers class me.andpay.ma.sdk.**{
    *;
}
-keepclassmembers class com.payeco.android.plugin.**{
    *;
}
```

#### 日志
初始化sdk时，debug参数默认为false，将debug模式打开后，sdk将输出更加详细的日志





