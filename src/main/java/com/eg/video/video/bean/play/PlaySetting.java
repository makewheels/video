package com.eg.video.video.bean.play;

import lombok.Data;

/**
 * 播放设置
 */
@Data
public class PlaySetting {
    private int volume = 67;//默认音量
    private boolean autoFullscreen = false;//是否自动全屏
    private boolean autoPlay = false;//是否自动播放
}
