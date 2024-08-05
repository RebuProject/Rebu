package com.rebu.profile.employee.controller.dto;

import com.rebu.profile.employee.dto.ChangeWorkingIntroDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeWorkingIntroRequest {
    private String workingIntroduction;

    public ChangeWorkingIntroDto toDto(String nickname) {
        return ChangeWorkingIntroDto.builder()
                .workingIntroduction(workingIntroduction)
                .nickname(nickname)
                .build();
    }
}
