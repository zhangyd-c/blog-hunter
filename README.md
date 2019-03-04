<p align="center">
	<a href="https://gitee.com/yadong.zhang/blog-hunter"><img src="https://images.gitee.com/uploads/images/2019/0227/101844_0ef06016_784199.png" width="400"></a>
</p>
<p align="center">
	<strong>Blog Hunter <code>/'hʌntɚ/</code>: 博客猎手，基于webMagic的博客爬取工具</strong>
</p>
<p align="center">
	<a target="_blank" href="https://search.maven.org/search?q=g:%22me.zhyd%22%20AND%20a:%22JustAuth%22">
		<img src="https://img.shields.io/badge/Maven Central-1.0.0-blue.svg" ></img>
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

- **多个平台**：该项目内置了慕课、csdn、iteye、cnblogs、掘金和V2EX六个主流的博客平台
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
    <version>1.0.0</version>
</dependency>
```

#### 抓取单个文章

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


## 解决webMagic爬取https站点报错的问题

[http://www.cnblogs.com/vcmq/p/9484418.html](http://www.cnblogs.com/vcmq/p/9484418.html)

[https://github.com/code4craft/webmagic/issues/701](https://github.com/code4craft/webmagic/issues/701)
