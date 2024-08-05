package com.rebu.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentNestedReadAllDto {
    private String imageSrc;
    private String nickname;
    private LocalDateTime createAt;
    private String content;
    private Long commentId;
    private Integer likeCount;
}