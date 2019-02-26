package me.zhyd.hunter.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2019/2/26 17:44
 * @since 1.8
 */
@Data
public class ImageLink {

    /**
     * 正常img标签的src连接
     */
    private String srcLink;
    /**
     * 当网站采用了懒加载时，originalLink表示真正的连接
     */
    private String originalLink;
}
