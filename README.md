**项目说明** 

参考[Apache shiro安全系统 【SB】](http://www.sojson.com)（*可以稍许参考但是操作redis相关session的代码实在写的太烂,看完特别想骂人，然后其中VCache那个只是用来记录当前在线用户的跟jedisManager其实是一个东西，用两个对象分别去存取*）安全框架 等 不同开源 工程整理的一套“轮子”工程，方便初始开发阶段使用

mileworks-gen是基于SpringBoot，方便SpringBoot开发者使用，该版本要求JDK1.8, 逐渐会慢慢有Lambda写法

<br> 

**修改内容如下**
- 修改人人中shiro认证过程：添加*自定义认证*方式并支持ajax的请求  *在ShiroConfig类中配置,老式的办法是在类似shiro_base_auth.ini文件中配置类似漏斗形的认证配置，注意过滤的级别，现在工程全部由shiro接管包括filter相关操作*
- 添加系统多地登录的时候登出:  

	 
		基本原理：
		
			1.redis现在存储的是浏览器在没有刷新之前的session记录（即会一直刷新然后保存到最后一个状态的session）。  
			还是通过RedisShiroSessionDAO.java 类进行redis操作。
			2.每次刷新的时候通过ForceKickSessionFilter.java 过滤器通过实时的浏览器cookie中的session,   
			sessionId 去查询这个老的redis中是否存在有此session ,没有就标记要踢出 ,然后在onAccessDenied方法中  
			进行首页重定向。  
			
		补充：
			redis存储的初衷：现在踢出操作实际上不适合做分布式系统，因为部署到不同地方的session后期会反复更新到此redis  
			中，同时会反复踢出（当然逻辑可以解决），  这里session存储在redis中最终目的是让后期session在每个分布式系统  
			中能共享。  
			
	
- 保存浏览器session失效之前界面url，当下次登录直接返回之前界面
- 添加权限到按钮级别
- 添加多数据源支持
- 前端使用VUE来做数据双向绑定

<br>

**后期需要优化部分**
- shiro中继承AbstractSessionDAO后，执行doReadSession方法调用过于频繁，所以改为通过集成缓存Dao方式来实现session操作。
- 添加通过token进行数据交互，token全部自己生成，方便手机端、web端禁用cookies时候使用
- redis存储的key值是没有序列化的，所以在存储的时候有可能会覆盖，后期全部序列化key和value值
- swagger2 api文档自动生成：之前都是写死的

<br>


**项目结构** 
```
MileWorks-GEN
├─doc  项目SQL语句
├─common  公共
├─config  配置文件
├─modules 模块
│  ├─gen 代码生成器
│  ├─job 定时任务
│  ├─oss 文件存储
│  └─sys 系统管理(核心)
├─MileWorksGenApplication.java 项目启动类
│ 
├─resources 
│  ├─mapper     SQL文件
│  ├─template   代码生成器模板（可增加或修改相应模板）
│  ├─application.yml        全局配置文件
│  ├─application-dev.yml    开发环境配置文件
│  └─application-test.yml   测试环境配置文件
│  └─application-pro.yml    正式环境配置文件
│  └─generator.properties   代码生成器配置文件
│ 
├─webapp 
│  ├─statics   静态资源
│  ├─swagger   swagger ui
│  └─WEB-INF/views   系统页面
│     ├─modules      模块页面
│     ├─index.html   AdminLTE主题风格（默认主题）
│     └─index1.html  Layui主题风格
│

```

<br>

 **本地部署**
- 通过git下载源码
- 创建数据库mileworks_gen，数据库编码为UTF-8
- 执行doc/db.sql文件，初始化数据【按需导入表结构及数据】
- 修改application-dev.yml，更新MySQL账号和密码
- Eclipse、IDEA运行RenrenApplication.java，则可启动项目
- 项目访问路径：http://localhost:8080/mileworks_gen
- 账号密码：admin/admin
- swagger文档路径：http://localhost:8080/mileworks_gen/swagger/index.html

<br>

 **分布式部署**
- 分布式部署，需要安装redis，并配置application.yml里的redis信息
- 需要配置【mileworks.redis.open=true】，表示开启redis缓存
- 需要配置【mileworks.shiro.redis=true】，表示把shiro session存到redis里

<br>



