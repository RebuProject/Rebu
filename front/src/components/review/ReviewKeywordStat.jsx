import styled, { keyframes } from "styled-components";
import { useState, useEffect } from "react";
import axios from "axios";

const keywordList = [
  {
    id: 1,
    keyword: "원하는 스타일로 잘해줘요",
    imgURL: "style.png",
  },
  {
    id: 2,
    keyword: "시술이 꼼꼼해요",
    imgURL: "ggomggom.png",
  },
  {
    id: 3,
    keyword: "친절해요",
    imgURL: "kind.png",
  },
  {
    id: 4,
    keyword: "디자인 추천을 잘해줘요",
    imgURL: "recommend.png",
  },
  {
    id: 5,
    keyword: "디자인이 다양해요",
    imgURL: "design.png",
  },
  {
    id: 6,
    keyword: "가격이 합리적이에요",
    imgURL: "price.png",
  },
  {
    id: 7,
    keyword: "매장이 청결해요",
    imgURL: "clean.png",
  },
  {
    id: 8,
    keyword: "상담이 자세해요",
    imgURL: "consult.png",
  },
  {
    id: 9,
    keyword: "대화가 즐거워요",
    imgURL: "conversation.png",
  },
  {
    id: 10,
    keyword: "맞춤케어를 잘해줘요",
    imgURL: "care.png",
  },
];

const Container = styled.div`
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  & > * {
    flex: 1 1 50%;
    @media (max-width: 768px) {
      flex: 1 1 90%;
    }
  }
`;

const StatBar = styled.div`
  height: 34px;
  border: 1px solid #999999;
  border-radius: 100rem;
  display: grid;
  grid-template-columns: 1fr 5fr 1fr;
  align-items: center;
  box-sizing: border-box;
  margin: 5px;
  position: relative;
`;

const grow = keyframes`
  from {
    width: 0;
  }
  to {
    width: ${(props) => props.percent}%;
  }
`;

const InsideBar = styled.div`
  position: absolute;
  width: ${(props) => props.percent}%;
  height: 33px;
  background-color: ${(props) =>
    props.theme.value === "light" ? "#e3cbfb" : "#b475f3"};
  border-radius: 100rem;
  animation: ${grow} 2s forwards;
`;

const StatIcon = styled.img`
  z-index: 1;
  max-width: 32px;
  max-height: 32px;
  padding-left: 1rem;
`;

const KeywordText = styled.div`
  z-index: 2;
  padding-left: 1rem;
`;

const StatNum = styled.div`
  z-index: 3;
  padding-right: 1rem;
`;

export default function ReviewKeywordStat({ reviewNum, nickname }) {
  const [data, setData] = useState([]);

  const BASE_URL = "https://www.rebu.kro.kr";
  useEffect(() => {
    axios
      .get(`${BASE_URL}/api/review-keywords/count?nickname=${nickname}`, {
        headers: {
          "Content-Type": "application/json",
          Access: localStorage.getItem("access"),
        },
      })
      .then((response) => {
        console.log(response);
        setData(response.data.body);
        return response.data.body;
      })
      .then((jsondata) => {
        console.log(jsondata);
        const total = jsondata.reduce((acc, item) => acc + item.count, 0);
        const newEntity = jsondata.map((item) => {
          const keywordItem = keywordList.find(
            (keyword) => keyword.keyword === item.keyword
          );
          return {
            ...item,
            imgURL: keywordItem.imgURL,
            percent: ((item.count / reviewNum) * 100).toFixed(2),
          };
        });

        // count 기준으로 정렬하여 상위 5개만 선택
        const top5Entity = newEntity
          .sort((a, b) => b.count - a.count)
          .slice(0, 5);

        setData(top5Entity);
      })
      .catch((error) => {
        console.log(Error);
      });
  }, [reviewNum]);

  return (
    <Container>
      <hr style={{ border: "0.5px solid #943aee" }} />
      {data && data.length > 0 ? (
        data.map((item) => (
          <StatBar key={item.content}>
            <StatIcon
              src={process.env.PUBLIC_URL + "/keyword/" + item.imgURL}
            />
            <KeywordText>{item.keyword}</KeywordText>
            <StatNum>{item.count}</StatNum>
            <InsideBar percent={item.percent}></InsideBar>
          </StatBar>
        ))
      ) : (
        <div>Loading...</div>
      )}
      <hr style={{ border: "0.5px solid #943aee" }} />
    </Container>
  );
}
