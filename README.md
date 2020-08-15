# qingcheng
## 青橙电商系统项目笔记

一起学项目！

链接：https://pan.baidu.com/s/1rqnWIxHWftSLYWiKJY4K4Q 
提取码：`jjig`

### 一、走进电商

#### 1、技术特点

- 技术新、技术广。
- 分布式。
- 高并发、集群、负载均衡、高可用。
- 海量数据、业务复杂。
- 系统安全。

#### 2、电商模式

- `B2B`（Business to Business），商家与商家。
  - 比如：阿里巴巴，提供技术给其他公司。
- `C2C`（Customer to Customer），消费者个人间的电子商务行为。
  - 比如：淘宝，有很多店铺。
- `B2C`（Business to Customer），商对客，面向消费者销售产品。
  - 比如：青橙电商系统，网上购物。
- `B2B2C`，第一个B指的是商品或服务的供应商，第二个B指的是从事电子商务的企业，C则是消费者。
  - 比如：京东商城、天猫商城。
- `C2B`（Consumer to Business）即消费者到企业，新的商业模式。
  - 是一种消费者贡献价值（Create Value），企业和机构消费价值（Consume Value）。
  - 与之相反的供需模式（Demand Supply Model，`DSM`）。
    - 消费者根据自身需求定制产品和价格，主动参与产品设计、生产和定价等彰显消费者的个性需求。
    - 生产企业进行定制化生产。
    - 比如：海尔商城。
- `O2O`（`Online to Offline`）在线离线/线上线下。
  - 是指将线下的商务机会与互联网结合，让互联网成为线下交易的平台，这个概念早来源于美国。
  - 比如：美团、饿了吗。
- `F2C`（Factory to Customer），即从厂商到消费者的电子商务模式。

### 二、系统架构

#### 1、架构图

- `common`（父公共模块）
  - `common_service`（服务公共模块）
    - `service_goods`（商品服务模块）
    - `service_order`（订单服务模块）
    - `service_xxx`（...服务模块）
  - `common_web`（web公共模块）
    - `web_manager`（管理后台模块）
    - `common_cas`（`CAS`公共模块）
      - `web_portal`（前台主流程模块）
      - `web_center`（前台用户中心模块）

#### 2、技术栈

- spring
  - spring-context
  - spring-beans
  - spring-web
  - `spring-webmvc`
  - `spring-jdbc`
  - spring-aspects # 切面编程
  - `spring-jms` # Java 消息服务
  - spring-context-support # Spring context的扩展支持，用于`MVC`方面。
- security  # 安全框架，权限管理
  - spring-security-web
  - `spring-security-config`
  - `spring-security-taglibs`
  - `spring-security-cas`
- `Dubbo`
  - `dubbo` # 微服务框架
  - `zookeeper` # 服务注册中心
  - `zkclient` # `zookeeper`客户端
- test
  - spring-test
  - `junit`
- `JSON`
  - `jackson-core`
  - `jackson-databind`
  - `jackson-annotations`
  - `fastjson`
- XML
  - `dom4j`
  - `xml-apis` # Java的XML解析
- `javassist` # 低开销操作字节码的类库
- `kaptcha` # Google开源的生成验证码类库
- `commons-codec` # Apache开源的编解码包，如`MD5`
- `commons-fileupload` # Apache开源的文件上传组件
- `MyBatis`
  - `mybatis`
  - `mybatis-spring`
  - `mybatis-paginator` # 一个`GitHub`开源项目，用于Java后台获取分页数据、列表组件`mmgrid`前端展示。
  - `pagehelper` # 一个`GitHub`开源项目，用于数据库物理分页。
  - mapper # 通用mapper
  - `persistence-api` # 对象与表的映射（`ORM`）

- `MySQL`
  - `mysql-connector-java`
  - druid # Java中最好的连接池

- `Redis`
  - `jedis`
  - `spring-data-redis`
- `httpclient` # Apache开源，调HTTP接口的类库
- `aliyun-sdk-oss` # 支持阿里云对象存储的类库
- `elasticsearch` # 分布式的开源搜索和分析引擎
  - `elasticsearch-rest-high-level-client` # Java高级别REST客户端
- `RabbitMQ` # `AMQR`高级消息队列协议的实现
  - spring-rabbit

### 三、黑马架构师

#### 1、安装

下载地址：https://gitee.com/chuanzhiliubei/codeutil

免安装，解压到没有中文的目录下即可。

#### 2、生成代码

双击.bat文件运行，注意：java版本必须是1.8及以上。

- 登录数据库
- 选择数据库
- 选择模板
- 代码生成路径
- 项目名、包名（三级包名）、项目中文名。
- 生成代码

#### 3、生成的坑

> 文件名带时间戳

- 注意pom.xml文件有可能是pom_xxxxxx.xml。
- 注意service_xxx子项目下的实现类名带有时间戳。
- 总之，检查所生成的文件，看文件名是否正确，当然，直接运行是会报错的。

>导入模块

- 需要在父工程pom.xml中手动配置下，才能导入模块。

>配置文件

- 公共模块下的zk.properties
- service公共模块下的application-dao.xml导入pagehelper包
- web公共模块下的application-json.xml需要配置oss

### 四、业务术语

#### 1、规格参数

- 规格：用于区分同一商品的属性。比如：屏幕尺寸。
  - 规格选项：规格的可选值。比如：可选值有16寸、13寸等。

- 参数：用于描述商品的属性。比如：核数。
  - 参数选项：参数的可选值。比如：可选值有2核、4核等。

> 区分规格和参数：

- 比如：电脑（商品），16寸（规格）的电脑，核数（参数）是8。
- 同一商品，不同规格，参数都是一样的。
- 之所以区分规格和参数，是因为参数是不变的，规格是可变的。

> 模板（即规格参数）：

- 类似商品类型，用于汇总规格和参数的。
- 比如：手机、电视、笔记本。

#### 2、SPU&SKU

> SPU（Standard Product Unit，标准产品单位）

是商品信息聚合的最小单位，是一组可复用、易检索的标准化信息的**集合**。

例如：华为P20 pro就是一个SPU，与商家、颜色、款式、套餐无关。

> SKU（Stock Keeping Unit，库存量单位）

是库存进出计量的单位，可以是以件、盒、托盘等为单位。是物理上不可分割的**最小存货单元**。

例如：华为P20 pro 蓝宝石 64G就是一个SKU。

总结一句话：如果说华为P20 pro是一个商品（SPU），那么华为P20 pro 蓝宝石 64G就是这个商品的具体实体（SKU）

#### 3、流量统计

>IP

IP（独立IP）,即Internet Protocol。

- 一天内相同IP地址的客户端访问网站页面只会被统计为一次。
- 例如：公司很多人在局域网中同时打开网站，此时只能算一个独立IP。

> UV

UV（独立访客）即Unique Visitor，同一客户端（PC或移动端）访问网站被计为一个访客。

- 一天内相同的客户端访问同一网站只计一次UV。
- 注意：UV一般是以客户端Cookie等技术作为统计依据的，实际统计会有误差。

> PV

PV（访问量）即Page View，中文翻译为页面浏览，即页面浏览量或点击量。

- 访问一次网站，计一次PV。

### 五、SpringSecurity

- 认证：限制用户只能登陆才可以访问资源。
- 授权：限制用户必须有某种资源的访问权限才可以访问。

#### 1、引入依赖

~~~xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-web</artifactId>
    <version>${spring.version}</version>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-config</artifactId>
    <version>${spring.version}</version>
</dependency>
~~~

#### 2、web.xml

配置security过滤器

- 注意顺序：
  - 先filter后filter-mapping。
  - 先编码过滤，再安全过滤，再mvc过滤。

~~~xml
<filter>
    <filter-name>springSecurityFilterChain</filter-name>
    <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
</filter>
<filter-mapping>
    <filter-name>springSecurityFilterChain</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
~~~

#### 3、配置文件

> 标签头信息

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans:beans xmlns="http://www.springframework.org/schema/security"
             xmlns:beans="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
						http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd">
</beans:beans>
~~~

> 拦截规则

~~~xml
<!-- 页面拦截规则 -->
<http pattern="/login.html" security="none"></http>
<http>
    <!-- 所有资源必须有ROLE_ADMIN角色才可以访问-->
    <intercept-url pattern="/**" access="hasRole('ROLE_ADMIN')"></intercept-url>
    <!-- 实现表单登录 /login post -->
    <form-login login-page="/login.html"
                default-target-url="/main.html"
                authentication-failure-url="/login.html"
                authentication-success-handler-ref="loginHandler"/>
    <!-- 退出登录 /logout post -->
    <logout/>
    <!-- 关闭csrf验证 跨站请求伪造 token -->
    <csrf disabled="true"></csrf>
    <!-- 同源策略 -->
    <headers>
        <frame-options policy="SAMEORIGIN"></frame-options>
    </headers>
</http>
~~~

- 角色拦截
- 表单登录（默认地址：/login，且必须是Post请求）
  - 登录页、登录成功跳转页、认证失败页、认证处理器。
- 退出登录（默认地址：/logout，且必须是Post请求）
- 同源策略
  - 两个URL，相同ip、协议、地址、端口，则同源。
  - DENY：拒绝当前页加载任何frame页面。
  - SAMEORIGIN：frame页面地址只能为同源域名下的页面。
  - ALLOW-FROM：origin为允许frame加载的页面地址。
- csrf（跨站请求伪造，默认false开启，因为没有token，所以会报403）

> 认证管理器

```xml
<!-- 认证管理器 -->
<authentication-manager>
    <authentication-provider user-service-ref="userDetailService">
        <!-- <user-service>
             密码加密策略：noop不加密，bcrypt/MD5加密
            <user name="admin" password="$2a$10$0ZWPlETF0OXCTHV/kb2ueeyxP69E2B8q4lFDQyr4gs1uMFM18rWNi" authorities="ROLE_ADMIN"></user>
        </user-service> -->
        <password-encoder ref="bcryptEncoder"></password-encoder>
    </authentication-provider>
</authentication-manager>

<beans:bean id="bcryptEncoder" class="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder"></beans:bean>
```

> 查看加密策略

~~~
|-spring-security-core-5.0.5.RELEASE.jar
	|-org.springframework.security
		|-crypto
			|-factory
				|-PasswordEncoderFactories
~~~

> 登录成功回调

- `AuthenticationSuccessHandler`  # 接口

> 获取认证人

- `SecurityContextHolder.getContext().getAuthentication().getName();`

> 用户认证与授权服务

- `UserDetailsService`  # 接口

#### 4、RBAC模型

- 基于角色的访问控制（Role-Based Access Control）。

- 角色，即权限的集合。
- 为什么不基于权限，因为权限太多了，点不过来；一筐一筐的背，总比一个一个拿好。
- 用户-角色-权限-菜单（七张表，包括中间表）
  - 注意：菜单不是权限的集合，只是负责展示到页面上的分类。

### 作业

> 管理后台开发

- 图片库管理（第3章）
  - 展示相册的图片列表。✅
  - 图片上传及添加到相册。✅
  - 从相册中删除图片。✅
  - 新增相册。✅
  - 删除相册。✅
  - 修改相册。✅
- 删除与还原商品（第4章）
  - 商品列表展示。✅
    - 搜索条件：名称、分类、品牌。✅
    - 新增、修改。
    - 编辑SKU。✅
  - 逻辑删除。
  - 物理删除。
- 订单管理（第5章）
  - 订单列表。✅
  - 订单设置。✅
  - 订单详情。
  - 订单发货。
  - 退货与退款。
  - 订单超时自动处理。
    - 定时任务。
  - 合并订单与拆分订单。
- 交易统计（第6章）
  - 交易统计表。
  - 漏斗图。
  - 折线图。
- 修改密码（第7章）
  - 确认旧密码（BCrypt校验）✅
  - 存入新密码（BCrypt加密）✅
- 授权（第8章）
  - 给管理员设置多个角色。
  - 给角色分配权限。
  - 用户权限控制
    - 基于URL权限控制（配置）
    - 基于方法权限控制（注解）
  - 菜单筛选（隐藏没有权限的功能菜单）

### 总结回顾

#### 1、细节问题

>maven打包jar

视频上使用：`Plugins`->`install:install`（会报错）

使用：`Lifecycle`->`install`

> maven运行服务

`Plugins`->`tomcat7:run`

> 配置文件放错地方

报错：找不到，未注入。

~~~
Unsatisfied dependency expressed through field 'brandMapper'; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'com.qingcheng.dao.BrandMapper' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
~~~

我把`applicationContext-dao.xml`放到了web公共模块。

原本应该放在service公共模块的。

所以，导致缺少配置文件，扫描不到`brandMapper`。

>install没用

当有文件删除时，install不会将被删除文件剔除掉。

所以再次install之前，最好先clean下。

> 静态资源更新

不需要重启服务

#### 2、IDEA快捷键

>实现接口方法

选中实现类，Alt+Enter。

> 生成代码（getter、setter等）

Alt+Insert

> 生成返回变量

光标在分号后，Alt+Enter。

>代码行移动

Shift+Ctrl+上下键

> 搜索文件

按两下Shift

> 重命名文件

选中文件，Shift+F6

> rerun重启服务

Ctrl+F5

#### 3、通用Mapper

- 命名规律
  - insert、delete、update、select。
  - Selective # 忽略null
    - 例如：`insertSelective`，null值忽略，填充默认值，而不是覆盖。
    - 例如：`updateSelective`，null值忽略，还是原来的值，而不是覆盖。
  - Example # 条件Where
    - `Example example = new Example(Brand.class);`
    - `Example.Criteria criteria = example.createCriteria();`

#### 4、ES6语法

> let

~~~js
// 用var声明的变量，默认都是在函数的最上面，只要是在函数内调用这个变量都可以访问到。
// 而let的出现就是为了解决这一问题的，只在代码块中有效。
let name = '';
~~~

> const

~~~js
// const用于常量的定义，再给name赋值时就会报错。
const name = '';
~~~

> 模板

~~~js
// 在`反引号中可以使用${}拼接字符串。
var name = 'world'
console.log(`hello ${name}`)
~~~

> 箭头函数

~~~js
// 函数式编程特性
// 省略function、return
var sum = (a,b) => a+b;
// 等价于
var sum = function(a,b){
    return a + b;
}
~~~

#### 5、Echarts

官网：https://echarts.apache.org/zh/index.html

- 文档=>教程

- 文档=>配置项手册
- 实例=>官方实例

使用：

- 引入js文件。
- 划一块区域（div）
- `let myChart = echarts.init($('#main'));`
- `let option = {配置项}`
- `myChar.setOption(option);`

配置项说明：

- title：图表系列标题
- tooltip：悬浮提示

- legend：图例
- xAxis：X轴
- yAxis：Y轴
- series：图表系列数据
  - name：数据名称。
  - type：图表类型。
  - data：图表数据

#### 6、BCrypt

官网：http://www.mindrot.org/projects/jBCrypt/

~~~java
String gensalt = BCrypt.gensalt(); // 创建随机盐
String hashpw = BCrypt.hashpw("123456", gensalt); // 加密
boolean checkpw = BCrypt.checkpw("123456", hashpw); // 验证是否正确
~~~

#### 7、分布式ID生成

> UUID

- 优点：
  - 简单，使用方便。
  - 性能好，且全球唯一。
- 缺点：
  - 不可读，没有排序，增长趋势。
  - 使用字符串存储，数据量较大，查询的效率比较低。
- 使用：
  - `UUID.randomUUID();`

> Redis生成ID

- 优点：
  - 不依赖于数据库，灵活方便，且性能优于数据库。
  - 数字ID天然排序，对分页和排序很有帮助。
- 缺点：
  - 如果系统中没有Redis，则需要引入新的组件，增加系统复杂度。
  - 需要编码和配置的工作量比较大。
  - 网络传输造成性能下降。

> snowflake雪花算法

- 是Twitter开源的分布式ID生成算法，结果是一个long型的ID。
- 核心思想：
  - 12bit作为序列号。（序号）
  - 10bit作为工作机器id。（标识机器）
  - 41bit作为时间戳。（意味着，每秒产生4096个ID，如果超过这个数，则等待1秒再生成）
  - 1bit不可用为0。

- 使用：
  - `IdWorker idWorker = new IdWorker();`
  - `IdWorker idWorker = new IdWorker(1,1);`  # 机器id、序号
  - `idWorker.nextId();`

### 运行

#### 1、管理后台服务

- qingcheng_web_manager（http://localhost:9101、dubbo-port：默认20880）

- qingcheng_service_goods（tomcat-port：9001、dubbo-port：20881）
- qingcheng_service_order（tomcat-port：9002、dubbo-port：20882）
- qingcheng_service_system（tomcat-port：9003、dubbo-port：20883）



7









































