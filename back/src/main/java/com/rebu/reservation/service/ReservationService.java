package com.rebu.reservation.service;

import com.rebu.common.aop.annotation.Authorized;
import com.rebu.menu.entity.Menu;
import com.rebu.menu.exception.MenuNotFoundException;
import com.rebu.menu.repositoy.MenuRepository;
import com.rebu.profile.employee.entity.EmployeeProfile;
import com.rebu.profile.employee.exception.EmployeeProfileNotIncludeException;
import com.rebu.profile.employee.repository.EmployeeProfileRepository;
import com.rebu.profile.entity.Profile;
import com.rebu.profile.enums.Type;
import com.rebu.profile.exception.ProfileNotFoundException;
import com.rebu.profile.exception.ProfileUnauthorizedException;
import com.rebu.profile.repository.ProfileRepository;
import com.rebu.profile.shop.entity.ShopProfile;
import com.rebu.profile.shop.repository.ShopProfileRepository;
import com.rebu.reservation.dto.ReservationCreateDto;
import com.rebu.reservation.dto.ReservationStatusDeleteDto;
import com.rebu.reservation.dto.ReservationStatusModifyDto;
import com.rebu.reservation.entity.Reservation;
import com.rebu.reservation.exception.ReservationNotAcceptableException;
import com.rebu.reservation.exception.ReservationNotFoundException;
import com.rebu.reservation.exception.ReservationStatusMismatchException;
import com.rebu.reservation.exception.ReservationStatusNotChangeableException;
import com.rebu.reservation.repository.ReservationRepository;
import jakarta.validation.Valid;
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
    public void create(@Valid ReservationCreateDto dto){
        Profile profile = profileRepository.findByNickname(dto.getNickname()).orElseThrow(ProfileNotFoundException::new);
        ShopProfile shop = shopProfileRepository.findByNickname(dto.getShopNickname()).orElseThrow(ProfileNotFoundException::new);
        EmployeeProfile employee = employeeProfileRepository.findByNickname(dto.getShopNickname()).orElseThrow(ProfileNotFoundException::new);
        if(!shop.equals(employee.getShop()))
            throw new EmployeeProfileNotIncludeException();
        Menu menu = menuRepository.findById(dto.getMenuId()).orElseThrow(MenuNotFoundException::new);
        Integer timeTaken = menu.getTimeTaken();
        List<Reservation> list = reservationRepository.findByEmployeeProfileAndStartDateTimeBetween(employee, dto.getStartDateTime().minusMinutes(timeTaken), dto.getStartDateTime().plusMinutes(timeTaken));
        if(!list.isEmpty())
            throw new ReservationNotAcceptableException();

        Reservation reservation = dto.toEntity(profile, shop, employee, menu);
        // TODO. 직원 매장 부재 제약 조건
        // TODO. 직원 매장 영업 제약 조건

        reservationRepository.save(reservation);
    }

    @Authorized(allowed = {Type.EMPLOYEE})
    public void modifyReservationStatus(@Valid ReservationStatusModifyDto dto) {
        EmployeeProfile employee = employeeProfileRepository.findByNickname(dto.getNickname()).orElseThrow(ProfileNotFoundException::new);
        Reservation reservation = reservationRepository.findById(dto.getReservationId()).orElseThrow(ReservationNotFoundException::new);
        if(!employee.equals(reservation.getEmployeeProfile()))
            throw new ProfileUnauthorizedException();

        switch(dto.getReservationStatus()){
            case ACCEPTED: checkModifyReservationStatusToAccepted(reservation); break;
            case REFUSED: checkModifyReservationStatusToRefused(reservation); break;
            case NOSHOW: checkModifyReservationStatusToNoshow(reservation); break;
            default: throw new ReservationStatusMismatchException();
        }
        reservation.changeReservationStatus(dto.getReservationStatus());
    }

    @Authorized(allowed = {Type.COMMON})
    public void deleteReservationStatus(@Valid ReservationStatusDeleteDto dto) {
        Reservation reservation = reservationRepository.findById(dto.getReservationId()).orElseThrow(ReservationNotFoundException::new);
        Profile profile = profileRepository.findByNickname(dto.getNickname()).orElseThrow(ProfileNotFoundException::new);
        if(!reservation.getProfile().equals(profile))
            throw new ProfileUnauthorizedException();
        if(!(reservation.getReservationStatus() == Reservation.ReservationStatus.RECEIVED))
            throw new ReservationStatusNotChangeableException();
        reservation.changeReservationStatus(Reservation.ReservationStatus.CANCLED);
    }

    private void checkModifyReservationStatusToAccepted(Reservation reservation) {
        if(!(reservation.getReservationStatus() == Reservation.ReservationStatus.RECEIVED))
            throw new ReservationStatusNotChangeableException();
        if(!reservation.getStartDateTime().isBefore(LocalDateTime.now()))
            throw new ReservationStatusNotChangeableException();
    }

    private void checkModifyReservationStatusToRefused(Reservation reservation) {
        if(!(reservation.getReservationStatus() == Reservation.ReservationStatus.RECEIVED))
            throw new ReservationStatusNotChangeableException();
        if(!reservation.getStartDateTime().isBefore(LocalDateTime.now()))
            throw new ReservationStatusNotChangeableException();
    }

    private void checkModifyReservationStatusToNoshow(Reservation reservation) {
        if(!(reservation.getReservationStatus() == Reservation.ReservationStatus.ACCEPTED))
            throw new ReservationStatusNotChangeableException();
        if(reservation.getStartDateTime().isBefore(LocalDateTime.now()))
            throw new ReservationStatusNotChangeableException();
    }
}
