<p align="center">
	<a href="https://gitee.com/yadong.zhang/blog-hunter"><img src="https://images.gitee.com/uploads/images/2019/0227/101844_0ef06016_784199.png" width="400"></a>
</p>
<p align="center">
	<strong>Blog Hunter <code>/'hʌntɚ/</code>: 博客猎手，基于webMagic的博客爬取工具</strong>
</p>
<p align="center">
	<a target="_blank" href="https://search.maven.org/search?q=g:%22me.zhyd%22%20AND%20a:%22JustAuth%22">
		<img src="https://img.shields.io/badge/Maven Central-1.0.3-blue.svg" ></img>
	</a>
	<a target="_blank" href="https://gitee.com/yadong.zhang/blog-hunter/blob/master/LICENSE">
		<img src="https://img.shields.io/badge/License-MIT-yellow.svg" ></img>
	</a>
	<a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
		<img src="https://img.shields.io/badge/JDK-1.8+-green.svg" ></img>
	</a>
</p>

-------------------------------------------------------------------------------

博客猎手，基于webMagic的博客爬取工具，支持慕课、csdn、iteye、cnblogs、掘金和V2EX等各大主流博客平台。**博客千万条，版权第一条。狩猎不规范，亲人两行泪。**

## 主要功能

- **多个平台**：该项目内置了慕课、csdn、iteye、cnblogs、掘金、V2EX、oschina等多个主流的博客平台
- **单篇抓取**：只需指定一个文章连接，即可自动抓取文章内容
- **列表抓取**：只需简单的配置，就可快速抓取列表文章
- **程序可控**：可选择根据抓取的链接数或者程序运行的时间停止程序
- **字符流输出**：可配合前端，实现实时打印程序日志的功能
- **多线程**：支持多线程抓取，效率更高

## 快速使用

#### 添加依赖

```xml
<dependency>
    <groupId>me.zhyd.hunter</groupId>
    <artifactId>blog-hunter</artifactId>
    <version>1.0.3</version>
</dependency>
```

#### 抓取单篇文章

```java
String url = "https://www.cnblogs.com/zhangyadong/p/oneblog.html";
boolean convertImage = true;
HunterProcessor hunter = new BlogHunterProcessor(url, convertImage);
CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
```

- `url` 实际待抓取的文章地址
- `convertImage` 是否转存图片，当选择true时会在结果中返回该文中的所有图片链接

**返回结果**

```json
[{
	"author": "HandsomeBoy丶",
	"content": "xx",
	"description": "xx",
	"imageLinks": [{
		"originalLink": "https://images2018.cnblogs.com/blog/631092/201809/631092-20180911093741389-1090581462.png",
		"srcLink": "https://images2018.cnblogs.com/blog/631092/201809/631092-20180911093741389-1090581462.png"
	}, {
		"originalLink": "https://img.shields.io/badge/MySQL-5.6.4-green.svg",
		"srcLink": "https://img.shields.io/badge/MySQL-5.6.4-green.svg"
	}],
	"releaseDate": 1536630780000,
	"source": "https://www.cnblogs.com/zhangyadong/p/oneblog.html",
	"tags": ["其他"],
	"title": "推荐一款自研的Java版开源博客系统OneBlog"
}]
```

`imageLink` 包含两个属性：`originalLink`,`srcLink`。其中`srcLink`为目标网站的`src`属性中的值，而`originalLink`表示真实的图片路径，之所以这么处理是因为有些网站使用了图片懒加载技术，`src`中并不是真实的图片地址。

#### 抓取文章列表（只抓两篇文章）

```java
HunterConfig config = HunterConfigContext.getHunterConfig(Platform.IMOOC);
config.setUid("1175248")
        .setExitWay(ExitWayEnum.URL_COUNT)
        .setCount(2);
HunterProcessor hunter = new BlogHunterProcessor(config);
CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
```
**运行结果**

```
16:52:27,098  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/259921" target="_blank">springboot之一文带你搞懂Scheduler定时器(修订-详尽版)</a> -- 慕冬雪 -- 2018-11-08 17:31:00
16:52:28,543  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/259252" target="_blank">springboot整合Mybatis+Mapper+Pagehelper(修订-详尽版)</a> -- 慕冬雪 -- 2018-11-05 21:02:00
```

#### 抓取文章列表（程序运行10秒后停止）

```java
HunterConfig config = HunterConfigContext.getHunterConfig(Platform.CSDN);
config.setUid("u011197448")
        .setExitWay(ExitWayEnum.DURATION)
        .setCount(10);
HunterProcessor hunter = new BlogHunterProcessor(config);
System.out.println("程序开始执行：" + new Date());
CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
System.out.println("程序执行完毕：" + new Date());
```
**运行结果**

```
程序开始执行：Mon Mar 04 16:56:56 CST 2019
16:56:59,274  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://blog.csdn.net/u011197448/article/details/83901306" target="_blank">springboot整合Freemark模板(详尽版)</a> -- 七彩狼 -- 2018-11-09 17:45:56
16:57:00,634  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://blog.csdn.net/u011197448/article/details/82022098" target="_blank">DBlog开源博客新增博客迁移功能（支持多个站点）</a> -- 七彩狼 -- 2018-08-24 17:16:24
16:57:01,862  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://blog.csdn.net/u011197448/article/details/81233178" target="_blank">【超赞】推荐一波优秀的开发工具</a> -- 七彩狼 -- 2018-07-27 10:40:31
16:57:03,080  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://blog.csdn.net/u011197448/article/details/80563567" target="_blank">消息称微软计划收购GitHub，估值超50亿美元</a> -- 七彩狼 -- 2018-06-04 10:11:12
16:57:04,356  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://blog.csdn.net/u011197448/article/details/79484624" target="_blank">Springboot + Freemarker项目中使用自定义注解</a> -- 七彩狼 -- 2018-03-08 15:04:50
16:57:05,638  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://blog.csdn.net/u011197448/article/details/79142519" target="_blank">StringRedisTemplate常用操作</a> -- 七彩狼 -- 2018-01-23 17:35:22
16:57:06,879  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://blog.csdn.net/u011197448/article/details/79142428" target="_blank">JS异常(intermediate value)(intermediate value)(...) is not a function</a> -- 七彩狼 -- 2018-01-23 17:30:15
程序执行完毕：Mon Mar 04 16:57:07 CST 2019
```

#### 高级使用

```java
HunterConfig config = HunterConfigContext.getHunterConfig(Platform.IMOOC);
// set会重置，add会追加
config.setEntryUrls("https://www.imooc.com/u/1175248/articles")
        .addEntryUrl("https://www.imooc.com/u/4321686/articles")
        // 设置程序退出的方式
        .setExitWay(ExitWayEnum.URL_COUNT)
        // 设定抓取120秒， 如果所有文章都被抓取过了，则会提前停止
        .setCount(20)
        // 每次抓取间隔的时间
        .setSleepTime(100)
        // 失败重试次数
        .setRetryTimes(3)
        // 针对抓取失败的链接 循环重试次数
        .setCycleRetryTimes(3)
        // 开启的线程数
        .setThreadCount(5)
        // 开启图片转存
        .setConvertImg(true);
HunterProcessor hunter = new BlogHunterProcessor(config);
CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
```

**运行结果**

```
16:58:44,510  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/280679" target="_blank">【硬核优惠】三月涨薪季，过关斩将，“职”由你！</a> -- 慕课网官方_运营中心 -- 2019-03-01 11:58:00
16:58:44,512  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/259252" target="_blank">springboot整合Mybatis+Mapper+Pagehelper(修订-详尽版)</a> -- 慕冬雪 -- 2018-11-05 21:02:00
16:58:44,510  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/280748" target="_blank">慕课网每周干货福利礼包（第二十棒）</a> -- 慕课网官方_运营中心 -- 2019-03-01 17:30:00
16:58:44,544  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/259921" target="_blank">springboot之一文带你搞懂Scheduler定时器(修订-详尽版)</a> -- 慕冬雪 -- 2018-11-08 17:31:00
16:58:44,571  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/258641" target="_blank">springboot整合Freemark模板(修订-详尽版)</a> -- 慕冬雪 -- 2018-11-02 21:05:00
16:58:45,138  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/280368" target="_blank">直播 | 价值99元的2019前端面试课，限时免费听！</a> -- 慕课网官方_运营中心 -- 2019-02-27 11:45:00
16:58:45,140  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/254337" target="_blank">一次糟心的排错历程</a> -- 慕冬雪 -- 2018-10-15 11:47:00
16:58:45,142  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/279570" target="_blank">慕课网每周干货福利礼包（第十九棒）</a> -- 慕课网官方_运营中心 -- 2019-02-22 16:11:00
16:58:45,156  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/279579" target="_blank">一文读懂慕课专栏，文末福利！</a> -- 慕课网官方_运营中心 -- 2019-02-22 18:35:00
16:58:45,191  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/250819" target="_blank">SpringBoot项目实战（10）：自定义freemarker标签</a> -- 慕冬雪 -- 2018-09-28 14:01:00
16:58:45,698  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/279428" target="_blank">【慕课有约】bobo老师：算法就是一场“游戏”，攻关打Boss（上）</a> -- 慕课网官方_运营中心 -- 2019-02-21 13:54:00
16:58:45,707  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/279293" target="_blank">【面试技巧系列一】备战金三银四，涨薪先人一步</a> -- 慕课网官方_运营中心 -- 2019-02-20 15:54:00
16:58:45,727  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/70886" target="_blank">DBlog开源博客新增博客迁移功能（支持多个站点）</a> -- 慕冬雪 -- 2018-08-24 17:33:00
16:58:45,955  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/78650" target="_blank">详细介绍如何自研一款"博客搬家"功能</a> -- 慕冬雪 -- 2018-09-13 13:25:00
16:58:46,095  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/70439" target="_blank">echarts统计图中世界国家汉化表及汉化方式</a> -- 慕冬雪 -- 2018-08-22 13:58:00
16:58:46,128  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/49002" target="_blank">【注意】恕我直言，我想教你抓取慕课的文章！</a> -- 慕冬雪 -- 2018-07-31 18:33:00
16:58:46,173  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/278482" target="_blank">慕课网每周干货福利礼包（第十八棒）</a> -- 慕课网官方_运营中心 -- 2019-02-15 15:28:00
16:58:46,258  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/277960" target="_blank">【中奖公告】012期：程序员们，你妈催你相亲/结婚/生娃了吗？</a> -- 慕课网官方_运营中心 -- 2019-02-12 11:26:00
16:58:46,388  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/41600" target="_blank">DBlog建站之Websocket的实际使用方式</a> -- 慕冬雪 -- 2018-07-05 14:50:00
16:58:46,565  INFO HunterPrintWriter:38 - [ hunter ]  <a href="https://www.imooc.com/article/276553" target="_blank">大神云集——Redis命令实现源码分析</a> -- 慕课网官方_运营中心 -- 2019-01-30 15:21:00
```

#### 停止爬虫

创建Hunter时指定`uuid`，本例使用`当前用户的id`作为`uuid`
```java
HunterProcessor hunter = new BlogHunterProcessor(config, writerUtil, userId);
CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
```

停止爬虫

```java
Hunter spider = Hunter.getHunter(userId);
spider.stop();
```

**注意**

部分网站没有配置`Keywords`，所以在运行单元测试时如果碰到`Keywords`内容为空，可以忽略。如果是`title`、`content`等内容为空，请检查配置文件中的`xpath`匹配规则是否正确。

更多使用方式请参考文档...

## 配置信息

|  字段  |  释义  |  数据类型  |  默认  |  必填  |  备注  |
| :------------: | :------------: | :------------: | :------------: | :------------: | :------------ |
|  titleRegex  |  标题的匹配规则(`xpath`)  |  string  |  -  |  √  |  -  |
|  contentRegex  |  内容的匹配规则(`xpath`)  |  string  |  -  |  √  |  -  |
|  releaseDateRegex  |  发布日期的匹配规则(`xpath`)  |  string  |  -  |  √  |  -  |
|  authorRegex  |  作者的匹配规则(`xpath`)  |  string  |  -  |  √  |  -  |
|  targetLinksRegex  |  待抓取的url的匹配规则(`regex`)  |  string  |  -  |  √  |  -  |
|  tagRegex  |  标签的匹配规则(`xpath`)  |  string  |  -  |  ×  |  -  |
|  keywordsRegex  |  文章关键词的匹配规则(`xpath`)  |  string  |  `//meta[@name=keywords]/@content`  |  ×  |  -  |
|  descriptionRegex  |  文章描述的匹配规则(`xpath`)  |  string  |  `//meta[@name=description]/@content`  |  ×  |  -  |
|  domain  |  网站根域名  |  string  |  -  |  √  |  -  |
|  charset  |  网站编码  |  string  |  `UTF-8`  |  ×  |  -  |
|  single  |  是否抓取的单个文章  |  bool  |  `false`  |  ×  |  -  |
|  sleepTime  |  每次抓取等待的时间  |  int  |  `1000`  |  ×  |  -  |
|  retryTimes  |  抓取失败时重试的次数  |  int  |  `2`  |  ×  |  -  |
|  cycleRetryTimes  |  循环重试次数  |  int  |  `2`  |  ×  |  抓取失败时重试的次数用完后依然未抓取成功时，循环重试  |
|  threadCount  |  线程个数  |  int  |  `1`  |  ×  |  -  |
|  entryUrls  |  抓取入口地址  |  list  |  -  |  √  |  -  |
|  exitWay  |  程序退出的方式  |  string  |  `URL_COUNT`  |  ×  |  `DEFAULT`:默认方式,直到将所有匹配到的url抓取完成才会退出<br>`DURATION` 按照程序持续的时间，默认`60秒`<br>`URL_COUNT` 按照抓取的条数，默认`10条`|
|  count  |  对应退出方式  |  int  |  -  |  ×  |  exitWay = `DURATION` 时默认`60`<br>exitWay = `URL_COUNT` 时默认`10`|
|  cookies  |  网站的Cookie  |  list  |  -  |  ×  |  当有些网站必须需要登录时，可以指定该值，用以绕过登录 |
|  headers  |  http请求的header  |  map  |  -  |  ×  |  有些网站存在防盗链时，可能需要指定header |
|  ua  |  http请求的User-agent  |  String  |  -  |  ×  |  随机生成，不建议用mobile端的ua，因为有些网站根据ua自动跳转移动端和pc端链接，可能导致抓取失败 |
|  uid  |  博客平台的用户id  |  String  |  -  |  ×  |  一般为用户个人中心里url后的一串随机字符串 |
|  onlyThisAuthor  |  是否只抓取指定的uid用户  |  bool  |  -  |  ×  |  保留字段，暂时无用 |
|  ajaxRequest  |  是否为ajax渲染的页面  |  bool  |  -  |  ×  |  保留字段，暂时无用 |
|  convertImg  |  是否转存图片  |  bool  |  -  |  ×  |  当选择true时会自动过滤原文中的img链接并返回，调用端可选择将图片下载后替换掉原来的图片 |
|  proxyList  |  代理的列表  |  list  |  -  |  ×  |  保留字段，暂时无用 |
|  proxyType  |  代理的类型  |  enum  |  -  |  ×  |  保留字段，暂时无用 |

## 交流

|  微信(备注:`hunter加群`)  |  欢迎关注公众号  |
| :------------: | :------------: |
| <img src="https://gitee.com/yadong.zhang/static/raw/master/wx/wx.png" width="170"/> | <img src="https://gitee.com/yadong.zhang/static/raw/master/wx/wechat_account.jpg" width="200" /> |


## 致谢

- [WebMagic](https://gitee.com/flashsword20/webmagic): 一个简单而又强大的爬虫框架
- [Hutool](https://gitee.com/loolly/hutool): 一个优秀的Java工具包
- [OneBlog](https://gitee.com/yadong.zhang/DBlog): 一个牛逼的Java开源博客
