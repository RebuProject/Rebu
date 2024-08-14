import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import axios from 'axios';
import { BASE_URL } from '../../views/Signup';

const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
`;

const ModalContent = styled.div`
  background-color: ${(props) =>
      props.theme.value === "light" ? '#ffffff' : '#e5e5e5'};
  padding: 20px;
  border-radius: 8px;
  width: 300px;
  max-width: 80%;
`;

const ModalHeader = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-bottom: 20px;
`;

const ModalText = styled.h3`
  text-align: center;
  margin-top: 20px;
  margin-bottom: 30px;
`;

const CloseButton = styled.button`
  background: transparent;
  border: none;
  font-size: 20px;
  cursor: pointer;
  &:hover {
    color: #943AEE;
  }
`;

const ToggleContainer = styled.div`
  position: relative;
  width: 60px;
  margin: 1rem auto;
  display: flex;
  justify-content: center;
  cursor: pointer;

  > .toggle-container {
      width: 60px;
      height: 30px;
      border-radius: 30px;
      background-color: ${(props) =>
      props.theme.value === "light" ? '#c4c4c4' : '#bbbbbb'};
    }
    
    //.toggle--checked 클래스가 활성화 되었을 경우의 CSS를 구현
  > .toggle--checked {
      background-color: #943AEE;
      transition : 0.5s;
    }

  > .toggle-circle {
      position: absolute;
      text-align: center;
      margin: 0px;
      top: 1px;
      left: 1px;
      width: 30px;
      height: 28px;
      border-radius: 30px;
      background-color: rgb(255,254,255);
      transition : 0.5s;
    }

    //.toggle--checked 클래스가 활성화 되었을 경우의 CSS를 구현
    > .toggle--checked {
        left: 28px;
        transition : 0.5s;
      }
`;


const SecretMode = ({secretModalOpen, closeModal, currentUser, setProfile}) => {
  const [isSecret, setisSecret] = useState(currentUser.isPrivate);


  const toggleHandler = async () => {
    try {
      const access = localStorage.getItem('access');
      // 백엔드 API 엔드포인트 주소
      const url = `${BASE_URL}/api/profiles/${currentUser.nickname}/is-private`;

      // 업데이트할 데이터
      // const updatedData = {
      //   introduction: tempIntroduce,
      // };

      const headers = {
        "Content-Type": "application/json",
        "access" : access
      }

      // PATCH 요청 보내기
      const response = await axios.patch(url, {
        isPrivate: isSecret,
      }, {headers});
      console.log(isSecret)
      // 성공 시 추가로 처리할 작업이 있다면 여기에 작성
      console.log('공개범위가 수정되었습니다:', response.data);
      setisSecret(!isSecret);
      
    } catch (error) {
      // 에러 처리 로직 작성
      console.error('공개범위 수정에 실패했습니다:', error);
    }
    setProfile({ ...currentUser, isPrivate: isSecret })
  };
    

  return (
    <>
    {secretModalOpen && (
    <ModalOverlay>
        <ModalContent>
          <ModalHeader>
            <CloseButton onClick={closeModal}>&times;</CloseButton>
          </ModalHeader>
          <ModalText>내 스크랩 항목 비공개</ModalText>
          <ToggleContainer onClick={toggleHandler}>
            <div className={`toggle-container ${isSecret ? "toggle--checked" : null }`}></div>
            <div className={`toggle-circle ${isSecret ? "toggle--checked" : null }`}></div>
          </ToggleContainer>
        </ModalContent>
      </ModalOverlay>
      )}
    </>
  )
};

export default SecretMode;