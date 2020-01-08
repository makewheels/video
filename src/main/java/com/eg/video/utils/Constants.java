package com.eg.video.utils;

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
            DOMAIN = "cookierobot.tk";
            //本地存储根路径
            LOCAL_FILE_ROOT_PATH = "/video-project";
        }
    }

    //保存视频的文件夹名
    public static String VIDEO_FOLDER_NAME = "video";
    //项目名
    public static String PROJECT_NAME = "video";
    //http协议
    private static String HTTP_PROTOCOL = "http";
    //文件服务器端口
    private static String FILE_SERVER_PORT = "8080";
    //主服务器BASE_URL
    public static String BASE_URL = HTTP_PROTOCOL + "://" + DOMAIN + "/" + PROJECT_NAME;
    //文件存储服务器BASE_URL
    public static String FILE_SERVER_BASE_URL = HTTP_PROTOCOL + "://" + DOMAIN + ":"
            + FILE_SERVER_PORT;
}
