package com.rebu.reviewkeyword.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewKeyword {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String keyword;

    public void changeKeyword(String keyword) {
        this.keyword = keyword;
    }
}
