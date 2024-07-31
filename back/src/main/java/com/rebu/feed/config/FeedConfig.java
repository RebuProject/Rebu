package com.rebu.feed.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Component
public class FeedConfig {
    @Value("${feed.cnt_image_max_limit}")
    public int cntImageMaxLimit;

    @Value("${feed.storage_base_url}")
    private String baseUrl;
}
