package com.thinkive.common.authority.support;

/**
 * @Description ${DESCRIPTION}
 * @Author dengchangneng
 * @Create 2018-04-08-14:33
 **/
public class SimpleResponse {

    private Object content;

    public SimpleResponse(Object content) {
        this.content = content;
    }

    public SimpleResponse() {
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
