package com.eg.video.video;

import com.eg.video.video.bean.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoDao extends MongoRepository<Video, String> {
    Video findVideoByKey(String key);

}
