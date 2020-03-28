package com.eg.video.video;

import com.baidubce.services.cdn.model.cache.GetPrefetchStatusResponse;
import com.baidubce.services.cdn.model.cache.PrefetchStatus;
import com.eg.video.utils.BaiduCdnUtil;
import com.eg.video.utils.Constants;
import com.eg.video.video.bean.CdnPreload;
import com.eg.video.video.bean.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

/**
 * 定时任务：查看cdn预热状态
 *
 * @time 2020-03-28 19:23
 */
@Configuration
@EnableScheduling
public class CheckCdnPreload {
    @Autowired
    private VideoService videoService;

    /**
     * 检查所有视频
     */
    @Scheduled(fixedRate = 60000)
    public void checkAllVideos() {
        List<Video> videoList = videoService.findVideosByVideoFileNotNull();
        for (Video video : videoList) {
            CdnPreload cdnPreload = video.getCdnPreload();
            //如果预热已完成则跳过
            if (cdnPreload.isPreloadFinished()) {
                continue;
            }
            String oldState = cdnPreload.getState();
            //查询预热状态
            String cdnPreloadId = cdnPreload.getPreloadId();
            GetPrefetchStatusResponse response = BaiduCdnUtil.queryPreload(cdnPreloadId);
            PrefetchStatus prefetchStatus = response.getDetails().get(0);
            String status = prefetchStatus.getStatus();
            //已完成
            if (status.equals("completed")) {
                cdnPreload.setState(Constants.CDN_STATE_FINISH);
                Date finishedAt = prefetchStatus.getFinishedAt();
                cdnPreload.setFinishTime(finishedAt);
                cdnPreload.setPreloadFinished(true);
                videoService.save(video);
            } else if (status.equals("in-progress")) {
                //正在进行
                int progress = prefetchStatus.getProgress();
                String newState = Constants.CDN_STATE_LOADING + "(" + progress + "%)";
                if (oldState.equals(newState) == false) {
                    cdnPreload.setState(newState);
                    videoService.save(video);
                }
            } else {
                //等待或者发生错误
                if (oldState.equals(status) == false) {
                    cdnPreload.setState(status);
                    videoService.save(video);
                }
            }
        }
    }
}
