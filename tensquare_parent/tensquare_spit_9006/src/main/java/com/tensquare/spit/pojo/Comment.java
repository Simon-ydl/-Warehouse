package com.tensquare.spit.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "comment")
public class Comment implements Serializable {

    @Id
    private String id;
    private String content;
    private String articleid;
    private String userid;
    private String parentid;
    private Date publishdate;

    public Comment(String id, String content, String articleid, String userid, String parentid, Date publishdate) {
        this.id = id;
        this.content = content;
        this.articleid = articleid;
        this.userid = userid;
        this.parentid = parentid;
        this.publishdate = publishdate;
    }

    public Comment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }
}
