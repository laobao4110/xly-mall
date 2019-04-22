package com.xly.mall.common.base.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result implements Serializable {
    private static final long serialVersionUID = 1998428067166208629L;
    private String code;
    private String description;
    private boolean isSuccess;
    private Map<String, Object> resultMap = new HashMap();

    @JSONField(serialize = false) //为null时不会序列化这个值
    private boolean useDateFormat = false;
    @JSONField(serialize = false) //为null时不会序列化这个值
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public Result() {
    }

    public Result(String code, String description, boolean isSuccess) {
        this.code = code;
        this.description = description;
        this.isSuccess = isSuccess;
    }

    public Result(ResultCodeEnum resultCode, boolean isSuccess) {
        this.code = resultCode.getCode();
        this.description = resultCode.getDescription();
        this.isSuccess = isSuccess;
    }

    @JSONField(serialize = false)
    public void setPage(Integer size, Collection<?> e) {
        this.setProperty("iTotalDisplayRecords", size);
        this.setProperty("rows", e);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getResultMap() {
        return this.resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isUseDateFormat() {
        return this.useDateFormat;
    }

    public void setUseDateFormat(boolean useDateFormat) {
        this.useDateFormat = useDateFormat;
    }

    public Object getProperty(String propertyName) {
        return this.resultMap.get(propertyName);
    }

    @JSONField(serialize = false)
    public Object setProperty(String propertyName, Object propertyValue) {
        return this.resultMap.put(propertyName, propertyValue);
    }

    public void removeProperty(String propertyName) {
        this.resultMap.remove(propertyName);
    }

    public void removeAllProperty() {
        this.resultMap.clear();
    }

    @JSONField(serialize = false)
    public void setCode(ResultCodeEnum resultCode) {
        this.setCode(resultCode.getCode());
        this.setDescription(resultCode.getDescription());
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public static String toJSONString(Result result) {
        if (result.isUseDateFormat()) {
            String dateFormat = result.getDateFormat();
            if (org.jsoup.helper.StringUtil.isBlank(dateFormat)) {
                dateFormat = JSON.DEFFAULT_DATE_FORMAT;
            }

            return JSON.toJSONStringWithDateFormat(result, dateFormat, new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect});
        } else {
            return JSON.toJSONString(result, new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect});
        }
    }

    public String toString() {
        return "Result [code=" + this.code + ", description=" + this.description + ", isSuccess=" + this.isSuccess + ", resultMap=" + this.resultMap + ", useDateFormat=" + this.useDateFormat + ", dateFormat=" + this.dateFormat + "]";
    }
}
