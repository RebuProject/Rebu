import Calendar from "react-calendar";
import styled from "styled-components";
import React, { useState, useEffect, useMemo } from "react";
import { useLocation } from "react-router-dom";
import ReservationForm from "./ReservationForm";
import ReservationCard from "./ReservationCard";
import { BASE_URL } from "../../util/commonFunction";
import axios from "axios";

const CalendarWrapper = styled.div`
  display: flex;
  justify-content: center;
  flex-direction: column;
  margin-left: 0.5rem;
  margin-right: 0.5rem;
`;

const StyledCalendar = styled(Calendar)`
  .react-calendar {
    width: 320px;
    max-width: 100%;
    background-color: ${(props) =>
      props.theme.value === "light" ? "#f0f0f0" : "#f0f0f0"};
    color: #222;
    border-radius: 8px;
    box-shadow: 0 12px 24px rgba(0, 0, 0, 0.2);
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.125em;
  }

  .react-calendar--doubleView {
    width: 700px;
  }

  .react-calendar--doubleView .react-calendar__viewContainer {
    display: flex;
    margin: -0.5em;
  }

  .react-calendar--doubleView .react-calendar__viewContainer > * {
    width: 50%;
    margin: 0.5em;
  }

  .react-calendar,
  .react-calendar *,
  .react-calendar *:before,
  .react-calendar *:after {
    box-sizing: border-box;
  }

  .react-calendar button {
    margin: 0;
    border: 0;
    outline: none;
  }

  .react-calendar button:enabled:hover {
    cursor: pointer;
  }

  .react-calendar__navigation {
    display: flex;
    height: 44px;
    margin-bottom: 1em;
  }

  .react-calendar__navigation button {
    color: #6f48eb;
    min-width: 44px;
    background: none;
    font-size: 24px;
    font-weight: 800;
    border: 0;
    margin-top: 8px;
  }

  .react-calendar__navigation button:disabled:first-of-type {
    visibility: hidden;
  }

  .react-calendar__navigation button:enabled:hover,
  .react-calendar__navigation button:enabled:focus {
    background-color: #f8f8fa;
  }

  .react-calendar__month-view__weekdays {
    display: flex;
    text-align: center;
    text-transform: uppercase;
    font-weight: bold;
    font-size: 0.75em;
    box-shadow: ${(props) => props.theme.boxShadow};
  }

  .react-calendar__month-view__weekdays__weekday {
    padding-top: 0.5em;
    padding-bottom: 0.5em;
    background-color: ${(props) => props.theme.secondary};
    text-decoration: none;
  }
  .react-calendar__month-view__weekdays__weekday abbr[title] {
    text-decoration: none;
  }
  .react-calendar__month-view__weekNumbers .react-calendar__tile {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 0.75em;
    font-weight: bold;
  }

  .react-calendar__month-view__weekdays__weekday--weekend abbr[title="일요일"] {
    color: #ff3a3a;
  }
  .react-calendar__month-view__weekdays__weekday--weekend abbr[title="토요일"] {
    color: ${(props) =>
      props.theme.value === "light" ? "#3a3dff" : "#7b7dff"};
  }

  .react-calendar__month-view__days__day--neighboringMonth {
    color: #d3d3d3;
    font-weight: 300;
  }
  .react-calendar__month-view__days__day--neighboringMonth:disabled {
    background: #fff;
  }

  .react-calendar__year-view .react-calendar__tile,
  .react-calendar__decade-view .react-calendar__tile,
  .react-calendar__century-view .react-calendar__tile {
    padding: 2em 0.5em;
  }

  .react-calendar__tile {
    max-width: 100%;
    padding: 10px 6.6667px;
    border: 0;
    box-shadow: rgba(0, 0, 0, 0.1) 1px 1px 1px 0px;
    background: ${(props) =>
      props.theme.value === "light" ? "#ffffff" : "#d1d1d1"};
    text-align: center;
    font-weight: 600;
    line-height: 36px;

    @media (max-width: 768px) {
    }
  }

  .react-calendar__tile:disabled {
    background-color: ${(props) =>
      props.theme.value === "light" ? "#c2c2c2" : "#333333"};
    color: ${(props) =>
      props.theme.value === "light" ? "#f0f0f0" : "#838383"};
  }

  .react-calendar__tile:enabled:hover,
  .react-calendar__tile:enabled:focus {
    background: #f8f8fa;
    color: #6f48eb;
    border-radius: 1px;
  }

  .react-calendar__tile--now {
    background: #646269;
    border-radius: 1px;
    font-weight: bold;
    color: #fff;
  }
  .re .react-calendar__tile--now:enabled:hover,
  .react-calendar__tile--now:enabled:focus {
    background: #6f48eb33;
    border-radius: 1px;
    font-weight: bold;
    color: ${(props) => props.theme.primary};
  }

  .react-calendar__tile--hasActive {
    background: ${(props) => props.theme.primary};
  }

  .react-calendar__tile--hasActive:enabled:hover,
  .react-calendar__tile--hasActive:enabled:focus {
    background: #f8f8fa;
  }

  .react-calendar__tile--active {
    background: ${(props) => props.theme.primary};
    border-radius: 1px;
    font-weight: bold;
    color: white;
  }

  .react-calendar__tile--active:enabled:hover,
  .react-calendar__tile--active:enabled:focus {
    background: ${(props) => props.theme.primary};
    color: white;
  }
`;

const SelectedWrapper = styled.div`
  padding-top: 1rem;
  padding-bottom: 1rem;
`;

const ReservationInfoText = styled.div`
  width: calc(100% - 1rem);
  font-size: 20px;
  font-weight: 600;
  padding-left: 1rem;
  padding-top: 1rem;
`;

const ReservationInfo = styled.div`
  border-bottom: 2px solid ${(props) => props.theme.primary};
  padding-bottom: 0.3rem;
`;

const SelectedTitle = styled.span`
  padding-left: 1rem;
  @media (min-width: 769px) {
    font-size: 18px;
  }
  font-weight: 700;
`;
const SelectedTime = styled.span`
  @media (min-width: 769px) {
    font-size: 18px;
  }
  font-weight: 500;
  color: ${(props) => props.theme.primary};
`;

//예약 정보
const schedulerData = [
  {
    startDate: "2024-08-03T14:30",
    endDate: "2024-08-03T17:00",
    title: "여성 펌",
  },
  {
    startDate: "2024-08-01T13:00",
    endDate: "2024-08-01T20:00",
    title: "그라데이션 네일(행사)",
  },
  {
    startDate: "2024-08-02T13:00",
    endDate: "2024-08-02T14:00",
    title: "남성 헤어 커트",
  },
];

// 가게의 시간 설정 정보 (예시)

const weekday = ["일", "월", "화", "수", "목", "금", "토"];
export default function ReservationCalendar() {
  const [date, setDate] = useState(new Date());
  const [chosenTime, setChosenTime] = useState(null);
  const [shopInfo, setShopInfo] = useState({});
  const [reservationData, setReservationData] = useState([]);
  const location = useLocation();
  const { info } = location.state;

  const card = {
    id: 1,
    img: shopInfo.imageSrc,
    title: shopInfo.name,
    menu: info.menuTitle,
    designer: info.workingName + " " + info.role,
    serviceTime: info.serviceTime,
    price: info.cost,
  };

  const reservationInfo = {
    shopNickname: info.shopNickname,
    menuId: info.menuId,
    employeeNickname: info.employeeNickname,
  };

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  useEffect(() => {
    axios
      .get(`${BASE_URL}/api/profiles/shops/${info.shopNickname}`, {
        headers: {
          "Content-Type": "application/json",
          access: `${localStorage.getItem("access")}`,
        },
      })
      .then((res) => {
        console.log(res);
        setShopInfo(res.data.body);
      });
  }, []);

  useEffect(() => {
    axios
      .get(
        `${BASE_URL}/api/profiles/employees/${info.employeeNickname}/period-schedule`,
        {
          headers: {
            "Content-Type": "application/json",
            access: `${localStorage.getItem("access")}`,
          },
          params: {
            "start-date": "2023-08-01",
            "end-date": "2025-08-30",
          },
        }
      )
      .then((response) => {
        console.log(response);
        setReservationData(response.data.body);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  const moment = require("moment");

  function convertDate(input) {
    // 입력 문자열을 moment.js로 파싱합니다
    const date = moment(input, "YYYY. M. D");

    // 형식을 YYYY-MM-DD로 변환합니다
    return date.format("YYYY-MM-DD");
  }

  const chosenDay = convertDate(date);
  const startTime = "09:00";
  const endTime = "18:00";

  const shopTimeInfo = {
    date: chosenDay,
    startTime: startTime,
    endTime: endTime,
    serviceDuration: info.serviceTime,
    intervalMinutes: reservationData.reservationInterval,
    schedulerData: reservationData,
  };

  return (
    <>
      <CalendarWrapper>
        <StyledCalendar
          onChange={setDate}
          minDate={new Date()}
          minDetail="month"
          next2Label={null}
          formatDay={(locale, date) => moment(date).format("D")}
          calendarType="gregory" // 일요일 부터 시작
          showNeighboringMonth={true} // 전달, 다음달 날짜 숨기기
          formatMonthYear={(locale, date) => moment(date).format("YYYY. MM")}
          tileDisabled={({ activeStartDate, date, view }) =>
            date.getDay() === 2
          } //타일 비활성화 함수
          tileContent={({ date, view }) =>
            view === "month" && date.getDay() === 0 ? "" : null
          } // 타일 내 컨텐츠
          onClickDay={(item) => {
            if (convertDate(item.toLocaleString()) !== chosenDay) {
              setChosenTime(null);
            }
          }}
          prev2Label={null}
          value={date}
        />
      </CalendarWrapper>
      <ReservationInfo>
        <ReservationInfoText>예약정보</ReservationInfoText>
        <ReservationCard Card={card}></ReservationCard>

        <SelectedWrapper
          style={{ visibility: chosenTime ? "visible" : "hidden" }}
        >
          <SelectedTitle>선택 날짜 : </SelectedTitle>
          <SelectedTime>
            {chosenDay +
              " (" +
              weekday[date.getDay()] +
              ") " +
              (chosenTime ? chosenTime : "")}
          </SelectedTime>
        </SelectedWrapper>
      </ReservationInfo>
      <ReservationForm
        timeInfo={shopTimeInfo}
        chosenTime={chosenTime}
        setChosenTime={setChosenTime}
        reservationData={reservationData}
        reservationInfo={reservationInfo}
      />
    </>
  );
}
