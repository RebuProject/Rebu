import styled from "styled-components";
import ButtonSmall from "../common/ButtonSmall";
import { HiOutlineChevronRight } from "react-icons/hi";
import ButtonDisabled from "../common/ButtonDisabled";
import { formatDateTime } from "../../util/commonFunction";
import { BASE_IMG_URL } from "../../util/commonFunction";
import { Link, NavLink, useNavigate } from "react-router-dom";
import { Navigate } from "react-router-dom";

const GridContainer = styled.div`
  display: grid;
  grid-template-columns: 2fr 3fr;
  height: 175px;
  max-width: 500px;
  width: calc(100% - 4rem);
  @media (max-width: 768px) {
    width: calc(100% - 2rem);
  }
  background: ${(props) =>
    props.theme.value === "light"
      ? "linear-gradient(0deg,rgba(255, 255, 255, 1) 65%,rgba(243, 237, 250, 1) 100%)"
      : "linear-gradient(0deg, rgba(154,154,154,1) 0%, rgba(120,120,120,1) 100%)"};

  box-shadow: ${(props) =>
    props.theme.value === "light"
      ? "rgba(0, 0, 0, 0.15) 0px 6px 0px, rgba(0, 0, 0, 0.23) 0px 6px 6px;"
      : "none"};
  margin-top: 0.75rem;
  margin-bottom: 3rem;
  margin-left: 1rem;
  margin-right: 1rem;
  border-radius: 0.7rem;
`;

const PhotoSection = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  grid-column: 1/2;
  width: 100%;
`;

const Photo = styled.img`
  width: 150px;
  height: 150px;
  max-width: 120px;
  max-height: 120px;

  @media (max-width: 768px) {
    max-width: 100px;
    max-height: 100px;
  }

  border-radius: 8rem;
`;

const Content = styled.div`
  grid-column: 2/4;
  padding-top: 1rem;
  padding-right: 1rem;
`;

const TitleWrapper = styled.div`
  display: grid;
  grid-template-columns: 4fr 1fr;
  justify-content: space-between;
  font-weight: 600;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  border-bottom: ${(props) =>
    props.theme.value === "light" ? "1px solid black" : "1px solid #a3a3a3"};
  padding-bottom: 0.2rem;

  &:hover {
    color: ${(props) =>
      props.theme.value === "light" ? "lightgrey" : "#c1c1c1"};
  }
`;

const TitleText = styled.div`
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  @media (max-width: 768px) {
    font-size: 14px;
    padding-top: 3%;
  }
`;

const DateWrapper = styled.div`
  padding-top: 1%;
  font-size: 12px;
  color: ${(props) => (props.theme.value === "light" ? "#666666" : "#cfcfcf")};
  @media (max-width: 768px) {
    font-size: 10px;
    padding-top: 3%;
  }
`;

const MenuWrapper = styled.div`
  padding-top: 2%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  color: ${(props) => (props.theme.value === "light" ? "#943AEE" : "#e9d7fb")};
  font-weight: 600;
  @media (max-width: 768px) {
    font-size: 16px;
    padding-top: 3%;
  }
`;

const PriceWrapper = styled.div`
  display: flex;
  flex-direction: row;
  padding-top: 3%;
  font-size: 14px;
  color: ${(props) => (props.theme.value === "light" ? "#666666" : "#cfcfcf")};
  @media (max-width: 768px) {
    font-size: 12px;
    padding-top: 4%;
  }
`;

const DesignerWrapper = styled.div`
  padding-top: 0.3rem;
  color: ${(props) => (props.theme.value === "light" ? "#ef4f91" : "#cfcfcf")};
  font-weight: 600;
  @media (max-width: 768px) {
    font-size: 16px;
    padding-top: 5%;
  }
`;

export default function Reservation({ info, button }) {
  const navigate = useNavigate();
  return (
    <GridContainer>
      <PhotoSection>
        <Photo src={BASE_IMG_URL + info.img} />
      </PhotoSection>
      <Content>
        <TitleWrapper>
          <TitleText onClick={() => navigate(`/profile/${info.nickname}/SHOP`)}>
            {info.title}
            <HiOutlineChevronRight></HiOutlineChevronRight>
          </TitleText>
        </TitleWrapper>
        <DesignerWrapper>{info.designer}</DesignerWrapper>
        <MenuWrapper>{info.menu}</MenuWrapper>
        <PriceWrapper>{info.price.toLocaleString()} ￦</PriceWrapper>
        <DateWrapper>{formatDateTime(info.date)}</DateWrapper>
      </Content>
    </GridContainer>
  );
}
