package com.eg.video.utils;

import java.io.File;

public class Constants {
    //域名
    private static String DOMAIN;
    //本地存储根路径
    public static String LOCAL_FILE_ROOT_PATH;

    static {
        //根据操作系统初始化参数
        String os = System.getProperty("os.name").toLowerCase();
        //如果是Windows
        if (os.startsWith("win")) {
            //域名
            DOMAIN = "localhost";
            //本地存储根路径
            LOCAL_FILE_ROOT_PATH = "D:/temp/video-project";
        } else {
            //如果是服务器Linux
            //域名
            DOMAIN = "baidu.server.qbserver.cn";
            //本地存储根路径
            LOCAL_FILE_ROOT_PATH = "/home/files/video-project";
        }
        //创建文件夹
        File folder = new File(LOCAL_FILE_ROOT_PATH);
        if (folder.exists() == false) {
            folder.mkdirs();
        }
    }

    //项目名
    private static String PROJECT_NAME = "videoapp";
    //http协议
    private static String HTTP_PROTOCOL = "http";
    //端口
    private static String PORT = "8082";
    //主服务器BASE_URL
    public static String BASE_URL = HTTP_PROTOCOL + "://" + DOMAIN + ":"
            + PORT + "/" + PROJECT_NAME;

    //cdn url
    public static String CDN_BASE_URL = "https://cdn.baidu.qbserver.cn/video-project";

    public static String CDN_STATE_LOADING = "loading";
    public static String CDN_STATE_FINISH = "finish";

}
