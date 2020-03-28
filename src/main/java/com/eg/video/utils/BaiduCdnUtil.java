package com.eg.video.utils;

import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.cdn.CdnClient;
import com.baidubce.services.cdn.model.*;
import com.baidubce.services.cdn.model.cache.GetPrefetchStatusResponse;
import com.baidubce.services.cdn.model.cache.PrefetchTask;

import java.util.List;

/**
 * 百度cdn工具类
 *
 * @time 2020-03-23 09:13
 */
public class BaiduCdnUtil {
    private static String endPoint = "http://cdn.baidubce.com";
    private static String accessKeyId = "0b43a2d2f6fa4ca38a2c934196a6fa5a";
    private static String secretAccessKy = "266c6941efa8433dbdc5d61f9f7344e2";
    private static BceClientConfiguration config = new BceClientConfiguration()
            .withCredentials(new DefaultBceCredentials(accessKeyId, secretAccessKy))
            .withEndpoint(endPoint);
    private static CdnClient cdnClient = new CdnClient(config);

    /**
     * 刷新
     *
     * @param url
     * @return
     */
    public static PurgeResponse refresh(String url) {
        PurgeRequest request = new PurgeRequest()
                .addTask(new PurgeTask().withUrl(url));
        return cdnClient.purge(request);
    }

    /**
     * 根据任务id查询刷新结果
     *
     * @param id
     * @return
     */
    public static GetPurgeStatusResponse queryRefresh(String id) {
        return cdnClient.getPurgeStatus(new GetPurgeStatusRequest().withId(id));
    }

    /**
     * 预热
     *
     * @param url
     * @return
     */
    public static PrefetchResponse preload(String url) {
        PrefetchRequest request = new PrefetchRequest()
                .addTask(new PrefetchTask().withUrl(url));
        return cdnClient.prefetch(request);
    }

    /**
     * 预热，url列表
     *
     * @param urlList
     * @return
     */
    public static PrefetchResponse preload(List<String> urlList) {
        PrefetchTask prefetchTask = new PrefetchTask();
        for (String url : urlList) {
            prefetchTask.withUrl(url);
        }
        PrefetchRequest request = new PrefetchRequest().addTask(prefetchTask);
        return cdnClient.prefetch(request);
    }

    /**
     * 根据任务id查询预热结果
     *
     * @param id
     * @return
     */
    public static GetPrefetchStatusResponse queryPreload(String id) {
        return cdnClient.getPrefetchStatus(new GetPrefetchStatusRequest().withId(id));
    }

    public static void main(String[] args) {
//        PrefetchResponse prefetchResponse = preload("http://cdn.baidu.qbserver.cn/Enduro-MX-Racing.mp4");
//        System.out.println(prefetchResponse);

        GetPrefetchStatusResponse getPrefetchStatusResponse = queryPreload("eJwFwUkNADAIBEBFJFCu7a9WCAH/EjpjLpF67rPZ5Y4lXhUyOAhdQz2VKEC07QMHGgvG");
        System.out.println(getPrefetchStatusResponse);
    }
}
