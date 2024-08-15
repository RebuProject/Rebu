import React, { useState } from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { IoMdInformationCircleOutline } from "react-icons/io";
import { ButtonStyles, ButtonHover } from "../components/common/ButtonLogin";
import ButtonBack from "../components/common/ButtonBack";
import LoginTitle from "../components/common/LoginTitle";
import "./Login.css";
import { BASE_URL } from "./Signup";
import { verifyPassword, withdrawal } from "../features/auth/authSlice";
import { useSelector } from "react-redux";

const Container = styled.div`
  align-items: center;
  margin: 2rem 3rem;
`;

const Div = styled.div`
  display: flex;
  justify-content: space-around;
`;

const PasswordDiv = styled(Div)`
  &.password-field {
    margin-bottom: 0.5rem;
  }
`;

const SmallButton = styled.button`
  ${ButtonStyles}
  width: 30%;
  justify-content: end;
  white-space: nowrap;
  margin-left: 10px;
  font-size: 11px;
`;

const SubmitButton = styled.button`
  ${ButtonStyles}
  width: auto;
  white-space: nowrap;
  margin-left: 10px;
  font-size: 11px;
  height: 50px;
  padding: 1rem;
`;

const SmallButtonHover = styled(ButtonHover)`
  width: 30%;
`;

const Msg = styled.p`
  font-size: 11px;
  padding: 0;
  margin: 0;
  color: ${(props) => (props.isValid ? "blue" : "red")};
`;

const Withdrawal = ({ purpose = "withdrawal", buttonTitle = "탈퇴하기" }) => {
  const navigate = useNavigate();
  const nickname = useSelector((state) => state.auth.nickname);
  const [formData, setFormData] = useState({
    password: "",
  });
  const [isChecking, setIsChecking] = useState(false);
  const [passwordMsg, setPasswordMsg] = useState("");
  const [isPasswordValid, setIsPasswordValid] = useState(false);
  const [passwordConfirm, setPasswordConfirm] = useState(""); // 비밀번호 확인
  const [passwordConfirmMsg, setPasswordConfirmMsg] = useState("");
  const [emptyFieldsMsg, setEmptyFieldsMsg] = useState({
    password: false,
    passwordConfirm: false,
  });

  const handleChange = (input) => (e) => {
    setFormData((prev) => ({ ...prev, [input]: e.target.value }));
  };

  const handlePasswordChange = (e) => {
    const password = e.target.value;
    handleChange("password")(e);
    if (validatePassword(password)) {
      setPasswordMsg("유효한 비밀번호입니다.");
      setIsPasswordValid(true);
    } else {
      setPasswordMsg(
        "비밀번호는 8~15자의 영문, 숫자, 특수문자 조합이어야 합니다."
      );
      setIsPasswordValid(false);
    }
    // 비밀번호 확인 필드와 비교
    if (passwordConfirm && password !== passwordConfirm) {
      setPasswordConfirmMsg("비밀번호가 일치하지 않습니다.");
    } else if (passwordConfirm) {
      setPasswordConfirmMsg("비밀번호가 일치합니다.");
    }
    setEmptyFieldsMsg((prev) => ({ ...prev, password: false }));
  };

  const handlePasswordBlur = () => {
    if (!formData.password) {
      setEmptyFieldsMsg((prev) => ({ ...prev, password: true }));
    }
  };

  const validatePassword = (password) => {
    const passwordRegex =
      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,15}$/;
    return passwordRegex.test(password);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsChecking(true);
    const access = localStorage.getItem("access");

    const response = verifyPassword(formData.password, purpose);
    if (response.success) {
      try {
        withdrawal(nickname);
      } catch (error) {
        console.error("Error withdrawing:", error);
      }
    }
  };

  return (
    <Container>
      <ButtonBack />
      <LoginTitle
        text={"회원탈퇴를"}
        description={"위해 비밀번호를 한번 더 확인해주세요."}
      />

      <form onSubmit={handleSubmit}>
        <div className="form">
          {/* 비밀번호 */}
          <Div>
            <div
              className="emailBox"
              style={{ maxWidth: "100%", justifyContent: "start" }}
            >
              <label htmlFor="password" className="label">
                Password
              </label>
              <input
                type="password"
                id="password"
                className="loginInput"
                value={formData.password}
                onChange={handlePasswordChange}
                onBlur={handlePasswordBlur}
                placeholder="비밀번호를 입력하세요"
                required
              />
            </div>
          </Div>
          <Msg isValid={isPasswordValid}>{passwordMsg}</Msg>
          {emptyFieldsMsg.password && (
            <Msg isValid={false}>내용을 입력하세요</Msg>
          )}

          <div style={{ display: "flex", justifyContent: "end" }}>
            <SubmitButton type="submit">
              {isChecking ? "확인 중..." : buttonTitle}
            </SubmitButton>
          </div>
        </div>
      </form>
    </Container>
  );
};

export default Withdrawal;
