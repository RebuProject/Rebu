package com.rebu.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AlarmInviteEmployeeUpdateDto {
    private Long alarmId;
    private String nickName;
    private Boolean isAccept;
    private String role;
}
