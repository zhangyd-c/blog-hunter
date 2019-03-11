package me.zhyd.hunter.entity;

import lombok.Data;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
@Data
public class ImageLink {

    private String srcLink;

    public ImageLink(String srcLink) {
        this.srcLink = srcLink;
    }
}
