package com.eg.video.video;

import com.eg.video.video.bean.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VideoRepository extends MongoRepository<Video, String> {
    List<Video> findVideoByKey(String key);

}
