package com.boliangshenghe.jsontransform.entity;

import java.util.Date;

public class Jdata {
    private Integer id;

    private String eventid;

    private String title;

    private String location;

    private String time;

    private String content;

    private String weather;

    private String towncount;

    private String demaver;

    private String peoplesum;

    private String hazardcount;

    private String type;

    private Integer count;

    private Date creatorTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid == null ? null : eventid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather == null ? null : weather.trim();
    }

    public String getTowncount() {
        return towncount;
    }

    public void setTowncount(String towncount) {
        this.towncount = towncount == null ? null : towncount.trim();
    }

    public String getDemaver() {
        return demaver;
    }

    public void setDemaver(String demaver) {
        this.demaver = demaver == null ? null : demaver.trim();
    }

    public String getPeoplesum() {
        return peoplesum;
    }

    public void setPeoplesum(String peoplesum) {
        this.peoplesum = peoplesum == null ? null : peoplesum.trim();
    }

    public String getHazardcount() {
        return hazardcount;
    }

    public void setHazardcount(String hazardcount) {
        this.hazardcount = hazardcount == null ? null : hazardcount.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
    }
}