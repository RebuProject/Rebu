package com.rebu.reservation.service;

import com.rebu.common.aop.annotation.Authorized;
import com.rebu.feed.entity.Feed;
import com.rebu.menu.entity.Menu;
import com.rebu.menu.exception.MenuNotFoundException;
import com.rebu.menu.repositoy.MenuRepository;
import com.rebu.profile.employee.entity.EmployeeProfile;
import com.rebu.profile.employee.exception.EmployeeProfileNotIncludeException;
import com.rebu.profile.employee.repository.EmployeeProfileRepository;
import com.rebu.profile.entity.Profile;
import com.rebu.profile.enums.Type;
import com.rebu.profile.exception.ProfileNotFoundException;
import com.rebu.profile.repository.ProfileRepository;
import com.rebu.profile.shop.entity.ShopProfile;
import com.rebu.profile.shop.repository.ShopProfileRepository;
import com.rebu.reservation.dto.ReservationCreateDto;
import com.rebu.reservation.entity.Reservation;
import com.rebu.reservation.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ProfileRepository profileRepository;
    private final ShopProfileRepository shopProfileRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final MenuRepository menuRepository;
    private final ReservationRepository reservationRepository;

    @Authorized(allowed = {Type.COMMON})
    public void create(ReservationCreateDto dto){
        Profile profile = profileRepository.findByNickname(dto.getNickname()).orElseThrow(ProfileNotFoundException::new);
        ShopProfile shop = shopProfileRepository.findByNickname(dto.getShopNickname()).orElseThrow(ProfileNotFoundException::new);
        EmployeeProfile employee = employeeProfileRepository.findByNickname(dto.getShopNickname()).orElseThrow(ProfileNotFoundException::new);
        if(!shop.equals(employee.getShop()))
            throw new EmployeeProfileNotIncludeException();
        Menu menu = menuRepository.findById(dto.getMenuId()).orElseThrow(MenuNotFoundException::new);
        Integer timeTaken = menu.getTimeTaken();
        List<Reservation> list = reservationRepository.findByEmployeeProfileAndStartDateTimeBetween(employee, dto.getStartDateTime().minusMinutes(timeTaken), dto.getStartDateTime().plusMinutes(timeTaken));
        if(!list.isEmpty())
            //TODO. 이미 예약이 있음
        // TODO. 직원 매장 부재
        // TODO. 직원 매장 운영
    }
}
