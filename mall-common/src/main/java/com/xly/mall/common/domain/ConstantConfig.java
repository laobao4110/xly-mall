package com.xly.mall.common.domain;

public class ConstantConfig {

    public static String uploadFileUrl;
    public static String exportFileUrl;
    public static String downloadStatementUrl;
    public static String imageDomain;
    public static String baseDomain;
    public static String staticCommonDomain;
    public static String staticDomain;
    public static String serviceDomain;
    public static String imageOldDomain;
    public static Boolean exceptionLogDetail;

    public void setImageOldDomain(String imageOldDomain) {
        ConstantConfig.imageOldDomain = imageOldDomain;
    }

    public void setDownloadStatementUrl(String downloadStatementUrl) {
        ConstantConfig.downloadStatementUrl = downloadStatementUrl;
    }

    public void setUploadFileUrl(String uploadFileUrl) {
        ConstantConfig.uploadFileUrl = uploadFileUrl;
    }

    public void setExportFileUrl(String exportFileUrl) { ConstantConfig.exportFileUrl = exportFileUrl; }

    public void setImageDomain(String imageDomain) {
        ConstantConfig.imageDomain = imageDomain;
    }

    public void setBaseDomain(String baseDomain) {
        ConstantConfig.baseDomain = baseDomain;
    }

    public void setStaticCommonDomain(String staticCommonDomain) {
        ConstantConfig.staticCommonDomain = staticCommonDomain;
    }

    public void setStaticDomain(String staticDomain) {
        ConstantConfig.staticDomain = staticDomain;
    }

    public void setServiceDomain(String serviceDomain) {
        ConstantConfig.serviceDomain = serviceDomain;
    }

    public String getBaseDomain() {
        return baseDomain;
    }

    public String getStaticCommonDomain() {
        return staticCommonDomain;
    }

    public String getStaticDomain() {
        return staticDomain;
    }

    public String getServiceDomain() {
        return serviceDomain;
    }

    public void setExceptionLogDetail(Boolean exceptionLogDetail) {
        ConstantConfig.exceptionLogDetail = exceptionLogDetail;
    }
}
