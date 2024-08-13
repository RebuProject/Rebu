package com.rebu.feed.repository;

import com.rebu.feed.dto.FeedSearchDto;
import com.rebu.feed.dto.FeedSearchedDto;

import java.util.List;

public interface FeedRepositoryCustom {

    List<FeedSearchedDto> searchFeeds(FeedSearchDto dto);
}
