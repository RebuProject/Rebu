package com.rebu.feed.service;

import com.rebu.common.util.FileUtils;
import com.rebu.common.util.ListUtils;
import com.rebu.feed.config.FeedConfig;
import com.rebu.feed.entity.Feed;
import com.rebu.feed.entity.FeedImage;
import com.rebu.feed.repository.FeedImageRepository;
import com.rebu.storage.exception.FileUnsaveableException;
import com.rebu.storage.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FeedImageService {
    private final FeedConfig feedConfig;
    private final StorageService storageService;
    private final FeedImageRepository feedImageRepository;

    public void createFeedImages(List<MultipartFile> files, Feed feed){
        Map<String, byte[]> map = generateImageMap(files);
        List<String> paths = storageService.uploadFiles(map, String.format("%s/%d", feedConfig.getBaseUrl(), feed.getId()));
        List<FeedImage> feedImages = ListUtils.applyFunctionToElements(paths, element->(FeedImage.builder().src(element).feed(feed).build()));
        feedImageRepository.saveAll(feedImages);
    }

    public void deleteFeedImages(Long feedId){
        feedImageRepository.deleteByFeedId(feedId);
        storageService.removeDirectory(String.format("%s/%d", feedConfig.getBaseUrl(), feedId));
    }

    private Map<String, byte[]> generateImageMap(List<MultipartFile> images) {
        Map<String, byte[]> map = new HashMap<>();
        try {
            for(int i=0; i<images.size(); i++){
                MultipartFile file = images.get(i);
                if(file.getOriginalFilename() == null)
                    throw new FileUnsaveableException();
                String extension = FileUtils.getExtension(file.getOriginalFilename());
                String filename = String.format("%d.%s", i, extension);
                map.put(filename, file.getBytes());
            }
        } catch (IOException e) {
            throw new FileUnsaveableException();
        }
        return map;
    }
}
