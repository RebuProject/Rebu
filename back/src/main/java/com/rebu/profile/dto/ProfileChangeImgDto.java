package com.rebu.profile.dto;

import com.rebu.profile.validation.annotation.ProfileImg;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ProfileChangeImgDto {
    @ProfileImg
    private MultipartFile profileImage;
}
