package edu.bjtu.analysis.dto;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by baohehe
 * 用户行为
 */
public class UserBehavior implements Serializable {

    /**
     * osv : 24
     * appv : 2.5.0
     * event : works_play_on_switch
     * app : sy
     * os : android OS
     * item_id : 95541
     * platform : android
     * item_long : 0
     * imei :
     * brand : HUAWEI
     * event_time : 2017-08-23T12:57:04+0800
     * ostype : HUAWEI CAZ-AL10
     * udid : 655ef317a9ba4bb29c59c5e579696db9
     * item_play_long : 0
     * channel : huawei1
     * user_id : 516864
     * item_type : vod
     */

    @JSONField(name = "user_id")
    private String userId;

    @JSONField(name = "item_long")
    private String itemLong;

    private String item;

    @JSONField(name = "item_id")
    private String itemId;

    @JSONField(name = "item_type")
    private String itemType;

    @JSONField(name = "item_play_long")
    private String itemPlayLong;

    @JSONField(name = "event_time")
    private String eventTime;

    private String platform;
    private String channel;
    private String referer;

    private String osv;
    private String appv;
    private String event;
    private String app;
    private String os;
    private String ostype;
    private String udid;
    private String imei;
    private String brand;

    private String ip;

    @JSONField(name = "url")
    private String page;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemLong() {
        return itemLong;
    }

    public void setItemLong(String itemLong) {
        this.itemLong = itemLong;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemPlayLong() {
        return itemPlayLong;
    }

    public void setItemPlayLong(String itemPlayLong) {
        this.itemPlayLong = itemPlayLong;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOsv() {
        return osv;
    }

    public void setOsv(String osv) {
        this.osv = osv;
    }

    public String getAppv() {
        return appv;
    }

    public void setAppv(String appv) {
        this.appv = appv;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOstype() {
        return ostype;
    }

    public void setOstype(String ostype) {
        this.ostype = ostype;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
