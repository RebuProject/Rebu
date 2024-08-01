package com.rebu.feed.service;

import com.rebu.common.util.ListUtils;
import com.rebu.feed.entity.Feed;
import com.rebu.feed.entity.Hashtag;
import com.rebu.feed.repository.HashtagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    public void createHashTags(List<String> tags, Feed feed) {
        if(tags == null || tags.isEmpty())
            return;
        List<Hashtag> hashtag = ListUtils.applyFunctionToElements(tags, element-> Hashtag.builder().tag(element).feed(feed).build());
        hashtagRepository.saveAll(hashtag);
    }

    public void deleteHashTags(Long id) {
        hashtagRepository.deleteByFeedId(id);
    }

}
