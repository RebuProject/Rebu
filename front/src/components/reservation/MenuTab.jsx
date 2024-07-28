import styled from "styled-components";
import Img from "../../assets/images/img.webp";
import ModalNoBackground from "../common/ModalNoBackground";
import { useState } from "react";
import ButtonSmall from "../common/ButtonSmall";

export default function MenuTab({ Menu }) {
  const [isOpen, setIsOpen] = useState(false);
  const [modalContent, setModalContent] = useState(null);


  const ModalContentWrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  `
  const Wrapper = styled.div`
    padding-left: 1rem;
    padding-right: 1rem;
    height: 100%;
  `;

  const Grid = styled.div`
    display: grid;
    grid-template-columns: 1fr 1fr;
  `;

  const Card = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 1rem;
    cursor: pointer; /* 클릭 가능한 카드로 만들기 위해 추가 */
  `;

  const PhotoContainer = styled.img`
    width: 80%;
    border-radius: 0.3rem;
  `;

  const ContentContainer = styled.div`
  justify-content: start;
  align-self: start;
  padding-top : 1rem;
  padding-left : 11%;
  `;
  const ModalContent = styled.div`
    justify-content: start;
    align-self: start;
    padding-top : 1rem;
    padding-bottom: 1rem;
  
  `
  const ModalDescription = styled.div`
  padding-top : 1rem;
  padding-bottom : 1rem;
  `
  const MenuTitleContainer = styled.div`
    font-weight: 600;
    color: ${(props) => props.theme.text};
  `;

  const DurationContainer = styled.div`
    font-size: 0.9rem;
    color: ${(props) => props.theme.text};
  `;

  const CostContainer = styled.div`
    font-size: 0.8rem;
    color: ${(props) => props.theme.text};
  `;


  const MockData = [
    {
      id: 1,
      img: Img,
      title: "시그니처 세팅펌",
      description: "프리미엄 세팅펌 기계로 손상은 최대한 줄이고 자연스러운 펌이 가능합니다.",
      duration: "3h 30",
      cost: "110,000",
    },
    {
      id: 2,
      img: Img,
      title: "시그니처 S컬펌",
      description: "프리미엄 세팅펌 기계로 손상은 최대한 줄이고 자연스러운 펌이 가능합니다.",
      duration: "3h",
      cost: "100,000",
    },
    {
      id: 3,
      img: Img,
      title: "밀크브라운 염색",
      description: "프리미엄 세팅펌 기계로 손상은 최대한 줄이고 자연스러운 펌이 가능합니다.",
      duration: "2h",
      cost: "80,000",
    },
    {
      id: 4,
      img: Img,
      title: "밀크브라운 염색",
      description: "프리미엄 세팅펌 기계로 손상은 최대한 줄이고 자연스러운 펌이 가능합니다.",
      duration: "2h",
      cost: "80,000",
    },
    {
      id: 5,
      img: Img,
      title: "밀크브라운 염색",
      description: "프리미엄 세팅펌 기계로 손상은 최대한 줄이고 자연스러운 펌이 가능합니다.",
      duration: "2h",
      cost: "80,000",
    },
    {
      id: 6,
      img: Img,
      title: "밀크브라운 염색",
      description: "프리미엄 세팅펌 기계로 손상은 최대한 줄이고 자연스러운 펌이 가능합니다.",
      duration: "2h",
      cost: "80,000",
    },
    {
      id: 7,
      img: Img,
      title: "밀크브라운 염색",
      description: "프리미엄 세팅펌 기계로 손상은 최대한 줄이고 자연스러운 펌이 가능합니다.",
      duration: "2h",
      cost: "80,000",
    },
  ];

  function handleModalContent(item) {
    setIsOpen(true);
    setModalContent(
      <ModalContentWrapper>
        <PhotoContainer src={item.img} />
        <ModalContent>
          <MenuTitleContainer>{item.title}</MenuTitleContainer>
          <ModalDescription>{item.description}</ModalDescription>
          <DurationContainer>소요시간 : {item.duration}</DurationContainer>
          <CostContainer>가격 : {item.cost}</CostContainer>
        </ModalContent>
        <ButtonSmall button={{id: 1,
          onClick: ()=>(window.alert("예약하기")),
          highlight: true,
          title: "예약하기",
        }}/>
      </ModalContentWrapper>
    );
  }

  return (
    <>
      <ModalNoBackground isOpen={isOpen} setIsOpen={setIsOpen}>
        {modalContent}
      </ModalNoBackground>
      <Wrapper>
        <Grid>
          {MockData.map((item) => (
            <Card key={item.id} onClick={() => handleModalContent(item)}>
              <PhotoContainer src={item.img} />
              <ContentContainer>
                <MenuTitleContainer>{item.title}</MenuTitleContainer>
                <DurationContainer>소요시간 : {item.duration}</DurationContainer>
                <CostContainer>가격 : {item.cost}</CostContainer>
              </ContentContainer>
            </Card>
          ))}
        </Grid>
      </Wrapper>
    </>
  );
}