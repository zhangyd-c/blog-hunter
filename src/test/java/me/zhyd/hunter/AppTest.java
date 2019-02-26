package me.zhyd.hunter;

import com.alibaba.fastjson.JSONObject;
import me.zhyd.hunter.entity.VirtualArticle;
import me.zhyd.hunter.processor.BlogHunterProcessor;
import me.zhyd.hunter.processor.HunterProcessor;
import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;

public class AppTest {

    private void single(String url) {
        HunterProcessor hunter = new BlogHunterProcessor(url, true);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (null == list) {
            System.out.println("没获取到数据: " + url);
        } else {
            for (VirtualArticle virtualArticle : list) {
                System.out.println(String.format("%s - %s - %s", virtualArticle.getTitle(), virtualArticle.getAuthor(), virtualArticle.getSource()));
                System.out.println(virtualArticle.getImageLinks());
            }
        }
    }

    @Test
    public void singleTest() {
//        this.single("https://blog.csdn.net/h8y0bDJVUkwE1LboZlE/article/details/79599051");
//        this.single("https://www.cnblogs.com/lupeng2010/p/6811576.html");
//        this.single("https://www.imooc.com/article/10373");
        this.single("https://liuna718-163-com.iteye.com/blog/1601714");
    }
}
