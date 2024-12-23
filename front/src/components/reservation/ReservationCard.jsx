import styled from "styled-components";
import { HiOutlineChevronRight } from "react-icons/hi";
import { BASE_IMG_URL } from "../../util/commonFunction";
import defaultImg from "../../assets/images/img.webp";
import { useNavigate } from "react-router-dom";

const Wrapper = styled.div`
  display: flex;
  justify-content: center;
`;
const GridContainer = styled.div`
  display: grid;
  grid-template-columns: 3fr 5fr;
  height: 150px;
  width: 85%;
  max-width: 500px;
  background: ${(props) =>
    props.theme.value === "light"
      ? "linear-gradient(0deg,rgba(255, 255, 255, 1) 65%,rgba(243, 237, 250, 1) 100%)"
      : "linear-gradient(0deg, rgba(154,154,154,1) 0%, rgba(120,120,120,1) 100%)"};

  box-shadow: ${(props) =>
    props.theme.value === "light"
      ? "rgba(0, 0, 0, 0.15) 0px 6px 0px, rgba(0, 0, 0, 0.23) 0px 6px 6px;"
      : "none"};
  margin-top: 0.75rem;
  margin-bottom: 0.5rem;
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
    max-width: 90px;
    max-height: 90px;
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
`;

const TitleText = styled.div`
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  @media (max-width: 768px) {
    font-size: 14px;
  }
`;

const DesignerWrapper = styled.div`
  padding-top: 0.3rem;
  color: ${(props) => (props.theme.value === "light" ? "#ef4f91" : "#cfcfcf")};
  font-weight: 600;
  @media (max-width: 768px) {
    font-size: 14px;
  }
`;

const ServiceTimeText = styled.div`
  padding-left: 0.3rem;
  color: ${(props) => props.theme.primary};
  font-weight: 600;
`;

const PriceText = styled.div`
  padding-left: 0.3rem;
  color: ${(props) => props.theme.primary};
  font-weight: 600;
`;

const MenuWrapper = styled.div`
  padding-top: 0.3rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  color: ${(props) => (props.theme.value === "light" ? "#943AEE" : "#e9d7fb")};
  font-weight: 600;
  @media (max-width: 768px) {
    font-size: 12px;
  }
`;

const ServiceTimeWrapper = styled.div`
  display: flex;
  flex-direction: row;
  padding-top: 0.3rem;
  font-size: 14px;
  color: ${(props) => (props.theme.value === "light" ? "#666666" : "#cfcfcf")};
  @media (max-width: 768px) {
    font-size: 12px;
  }
`;

const PriceWrapper = styled.div`
  display: flex;
  flex-direction: row;
  padding-top: 0.3rem;
  font-size: 14px;
  color: ${(props) => (props.theme.value === "light" ? "#666666" : "#cfcfcf")};
  @media (max-width: 768px) {
    font-size: 12px;
  }
`;

export default function ReservationCard({ Card }) {
  if (!Card) {
    console.log("카드 컴포넌트 불러오기 실패");
    return null;
  }

  const navigate = useNavigate();

  return (
    <Wrapper>
      <GridContainer>
        <PhotoSection>
          <Photo src={Card.img ? BASE_IMG_URL + "/" + Card.img : defaultImg} />
        </PhotoSection>
        <Content>
          <TitleWrapper>
            <TitleText>{Card.title}</TitleText>
          </TitleWrapper>
          <MenuWrapper>{Card.menu}</MenuWrapper>
          <DesignerWrapper>{Card.designer}</DesignerWrapper>
          <ServiceTimeWrapper>
            시술시간 :<ServiceTimeText> {Card.serviceTime}</ServiceTimeText>분
          </ServiceTimeWrapper>
          <PriceWrapper>
            가격 : <PriceText> {Card.price}</PriceText>원
          </PriceWrapper>
        </Content>
      </GridContainer>
    </Wrapper>
  );
}
