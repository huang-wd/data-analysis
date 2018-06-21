package edu.bjtu.analysis.dto;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by baohehe
 * 用户上报日志
 */

public class Message implements Serializable {

    /**
     * query_string :
     * geoip_country_code : CN
     * request_time : 2017-08-23T12:57:05+08:00
     * request : POST /user_behavior HTTP/1.1
     * http_cookie :
     * geoip_country_name : China
     * timestamp : 23/Aug/2017:12:57:05 +0800
     * geoip_latitude : 28.5500
     * http_user_agent : Fiil/2.5.0(Android;HUAWEI HUAWEI CAZ-AL10 24;Scale/3.0)
     * http_x_forwarded_for :
     * remote_addr : 106.5.48.135
     * geoip_country_code3 : CHN
     * geoip_city_country_name : China
     * geoip_longitude : 115.9333
     * request_method : POST
     * http_referrer :
     * request_body : {"osv":"24","appv":"2.5.0","event":"works_play_on_switch","app":"sy","os":"android OS","item_id":"95541","platform":"android","item_long":"0","imei":"","brand":"HUAWEI","event_time":"2017-08-23T12:57:04+0800","ostype":"HUAWEI CAZ-AL10","udid":"655ef317a9ba4bb29c59c5e579696db9","item_play_long":"0","channel":"huawei1","user_id":"516864"}
     * geoip_city_country_code3 : CHN
     * geoip_city_continent_code : AS
     * geoip_city_country_code : CN
     * remote_user :
     * geoip_city : Nanchang
     */

    @JSONField(name = "query_string")
    private String queryString;

    private String geoip_country_code;
    private String request_time;
    private String request;
    private String http_cookie;
    private String geoip_country_name;
    private String timestamp;
    private String geoip_latitude;
    private String http_user_agent;
    private String http_x_forwarded_for;
    private String remote_addr;
    private String geoip_country_code3;
    private String geoip_city_country_name;
    private String geoip_longitude;
    private String request_method;
    private String http_referrer;

    @JSONField(name = "request_body")
    private String requestBody;

    private String geoip_city_country_code3;
    private String geoip_city_continent_code;
    private String geoip_city_country_code;
    private String remote_user;
    private String geoip_city;
    private String referer;

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getGeoip_country_code() {
        return geoip_country_code;
    }

    public void setGeoip_country_code(String geoip_country_code) {
        this.geoip_country_code = geoip_country_code;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getHttp_cookie() {
        return http_cookie;
    }

    public void setHttp_cookie(String http_cookie) {
        this.http_cookie = http_cookie;
    }

    public String getGeoip_country_name() {
        return geoip_country_name;
    }

    public void setGeoip_country_name(String geoip_country_name) {
        this.geoip_country_name = geoip_country_name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getGeoip_latitude() {
        return geoip_latitude;
    }

    public void setGeoip_latitude(String geoip_latitude) {
        this.geoip_latitude = geoip_latitude;
    }

    public String getHttp_user_agent() {
        return http_user_agent;
    }

    public void setHttp_user_agent(String http_user_agent) {
        this.http_user_agent = http_user_agent;
    }

    public String getHttp_x_forwarded_for() {
        return http_x_forwarded_for;
    }

    public void setHttp_x_forwarded_for(String http_x_forwarded_for) {
        this.http_x_forwarded_for = http_x_forwarded_for;
    }

    public String getRemote_addr() {
        return remote_addr;
    }

    public void setRemote_addr(String remote_addr) {
        this.remote_addr = remote_addr;
    }

    public String getGeoip_country_code3() {
        return geoip_country_code3;
    }

    public void setGeoip_country_code3(String geoip_country_code3) {
        this.geoip_country_code3 = geoip_country_code3;
    }

    public String getGeoip_city_country_name() {
        return geoip_city_country_name;
    }

    public void setGeoip_city_country_name(String geoip_city_country_name) {
        this.geoip_city_country_name = geoip_city_country_name;
    }

    public String getGeoip_longitude() {
        return geoip_longitude;
    }

    public void setGeoip_longitude(String geoip_longitude) {
        this.geoip_longitude = geoip_longitude;
    }

    public String getRequest_method() {
        return request_method;
    }

    public void setRequest_method(String request_method) {
        this.request_method = request_method;
    }

    public String getHttp_referrer() {
        return http_referrer;
    }

    public void setHttp_referrer(String http_referrer) {
        this.http_referrer = http_referrer;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getGeoip_city_country_code3() {
        return geoip_city_country_code3;
    }

    public void setGeoip_city_country_code3(String geoip_city_country_code3) {
        this.geoip_city_country_code3 = geoip_city_country_code3;
    }

    public String getGeoip_city_continent_code() {
        return geoip_city_continent_code;
    }

    public void setGeoip_city_continent_code(String geoip_city_continent_code) {
        this.geoip_city_continent_code = geoip_city_continent_code;
    }

    public String getGeoip_city_country_code() {
        return geoip_city_country_code;
    }

    public void setGeoip_city_country_code(String geoip_city_country_code) {
        this.geoip_city_country_code = geoip_city_country_code;
    }

    public String getRemote_user() {
        return remote_user;
    }

    public void setRemote_user(String remote_user) {
        this.remote_user = remote_user;
    }

    public String getGeoip_city() {
        return geoip_city;
    }

    public void setGeoip_city(String geoip_city) {
        this.geoip_city = geoip_city;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}