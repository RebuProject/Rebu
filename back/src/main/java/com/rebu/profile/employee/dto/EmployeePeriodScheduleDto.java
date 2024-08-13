package com.rebu.profile.employee.dto;

import com.rebu.absence.dto.AbsenceDto;
import com.rebu.absence.entity.Absence;
import com.rebu.common.util.ListUtils;
import com.rebu.menu.dto.MenuDto;
import com.rebu.menu.entity.Menu;
import com.rebu.reservation.dto.ReservationDto;
import com.rebu.reservation.entity.Reservation;
import com.rebu.workingInfo.dto.WorkingInfoDto;
import com.rebu.workingInfo.entity.WorkingInfo;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePeriodScheduleDto {
    @Setter
    private EmployeeProfileDto employeeProfile;
    private final List<WorkingInfoDto> workingInfos = new ArrayList<>();
    private final List<AbsenceDto> absences = new ArrayList<>();
    private final List<ReservationDto> reservations = new ArrayList<>();
    private final List<MenuDto> menus = new ArrayList<>();
}
