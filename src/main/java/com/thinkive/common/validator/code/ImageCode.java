package com.thinkive.common.validator.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Description ${DESCRIPTION}
 * @Author dengchangneng
 * @Create 2018-04-03-13:59
 **/
public class ImageCode {

    private BufferedImage image;

    private String code;

    private LocalDateTime expireTime;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        this.image = image;
        this.code = code;
        this.expireTime = expireTime;
    }


    public ImageCode(BufferedImage image, String code, int expireIn) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ImageCode() {
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
