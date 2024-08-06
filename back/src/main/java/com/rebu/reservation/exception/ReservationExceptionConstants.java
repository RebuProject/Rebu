package com.rebu.reservation.exception;

import com.rebu.common.exception.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationExceptionConstants implements ExceptionConstants {
    NOTFOUND("0S00"), STARTDATETIME_MISMATCH("0S01"), REQUEST_MISMATCH("0S02");
    private final String code;
}
