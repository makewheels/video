package com.eg.video.utils;

public class Constants {
    //本地存储根路径
    public static String LOCAL_FILE_ROOT_PATH = "D:/temp/video-project";
    //保存视频的文件夹名
    public static String VIDEO_FOLDER_NAME = "video";
    //项目名
    public static String PROJECT_NAME = "video";
    //域名
    //    public static String DOMAIN = "qbserver.cn";
    private static String DOMAIN = "localhost";
    //http协议
    private static String HTTP_PROTOCOL = "http";
    //api服务器BASE_URL
    public static String BASE_URL = HTTP_PROTOCOL + "://" + DOMAIN + "/" + PROJECT_NAME;
    //文件存储服务器BASE_URL
    public static String FILE_STORAGE_BASE_URL = BASE_URL;
    //请求文件指向
    public static String REQUEST_FILE_URI = "video-project-files";
}
