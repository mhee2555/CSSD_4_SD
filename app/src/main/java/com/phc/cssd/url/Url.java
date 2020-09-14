package com.phc.cssd.url;

/**
 * Created by LABTOP on 6/30/2016.
 */
public class Url {
    //public static final String URL_ = "http://phc.dyndns.biz:8181/cssd/";
    //public static final String URL = "http://192.168.1.163:80/cssd/";
   // public static final String URL = "http://fai01.dyndns.biz:6161/cssd/";
    //public static final String URL = "http://192.168.1.64:80/cssd/";

    public static final String URL = getUrl.xUrl;
    public static final String URL_MOBILE = getUrl.xUrl;

    public static String getImageURL(){
        return URL + "cssd_image/";
    }

    public static String getImageURLMobile(){
        return URL_MOBILE + "images/";
    }

}
