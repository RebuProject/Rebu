import { useNavigate } from "react-router-dom";
import { useState } from "react";
import axios from "axios";
import styled from "styled-components";
import { IoMdInformationCircleOutline } from "react-icons/io";
import { ButtonStyles, ButtonHover } from "../components/common/ButtonLogin";
import "./Login.css";
import { BASE_URL } from "./Signup";
import { updatePassword } from "../features/common/userSlice";

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

const Tooltip = styled.div`
  position: absolute;
  background-color: whitesmoke;
  color: gray;
  padding: 0.5rem;
  border-radius: 1rem;
  top: -2.5rem;
  transform: translateX(-50%);
  min-width: 200px;
  opacity: 0;
  visibility: hidden;
  transition: opacity 0.2s, visibility 0.2s;
`;

const InfoIconContainer = styled.div`
  cursor: pointer;
  position: relative;

  &:hover ${Tooltip} {
    opacity: 1;
    visibility: visible;
  }
`;

const SmallButtonHover = styled(ButtonHover)`
  width: 30%;
`;

const PwdIcon = styled("div")`
  display: flex;
  flex-direction: row;
  justify-self: stretch;
  align-items: center;
`;

const Msg = styled.p`
  font-size: 11px;
  padding: 0;
  margin: 0;
  color: ${(props) => (props.isValid ? "blue" : "red")};
`;

const ChangePassword = () => {
  const nav = useNavigate();
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  <>
    <Container>
      <ButtonBack />
      <LoginTitle
        text={"비밀번호 변경"}
        description={"가입할 때 사용하신 이메일로 인증코드를 보내드릴게요"}
      />
    </Container>
    {step === 1 && (
      <SignupForm1
        formData={formData}
        handleChange={handleChange}
        nextStep={nextStep}
        purpose={"changePassword"}
        buttonTitle={"변경하기"}
      />
    )}
    {/* {step === 2 && (
      <SignupForm2
        formData={formData}
        handleChange={handleChange}
        setFormData={setFormData}
        handleSubmit={handleSubmit}
      />
    )} */}
  </>;
};

// const [emailMsg, setEmailMsg] = useState(""); //pTag
// const [emailRegexValid, setEmailRegexValid] = useState(false); //이메일형식
// const [isEmailValid, setIsEmailValid] = useState(false); //이메일중복
// const [isEmailVerified, setIsEmailVerified] = useState(false); //이메일인증 확인
// const [isChecking, setIsChecking] = useState(false);
// const [emailVerifyCode, setEmailVerifyCode] = useState(""); //이메일 인증코드
// const [emailVeriMsg, setEmailVeriMsg] = useState(""); //이메일 인증코드 input 밑에 글
// const [passwordMsg, setPasswordMsg] = useState("");
// const [isPasswordValid, setIsPasswordValid] = useState(false);
// const [passwordConfirm, setPasswordConfirm] = useState(""); // 비밀번호 확인
// const [passwordConfirmMsg, setPasswordConfirmMsg] = useState(""); //
// const [emptyFieldsMsg, setEmptyFieldsMsg] = useState({
//   email: false,
//   emailVerifyCode: false,
//   password: false,
//   passwordConfirm: false,
// });

// // Function for validating email regex
// const validateEmailRegex = (email) => {
//   const emailRegex = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@(?:\w+\.)+\w+$/;
//   return emailRegex.test(email);
// };

// // Email change handler
// const handleEmailChange = (e) => {
//   const email = e.target.value;
//   setFormData({ ...formData, email });

//   if (validateEmailRegex(email)) {
//     setEmailMsg("유효한 이메일입니다.");
//     setEmailRegexValid(true);
//   } else {
//     setEmailMsg("유효하지 않은 이메일 형식입니다.");
//     setEmailRegexValid(false);
//   }

//   setEmptyFieldsMsg((prev) => ({ ...prev, email: false }));
// };

// // Password change handler
// const handlePasswordChange = (e) => {
//   const password = e.target.value;
//   setFormData({ ...formData, password });

//   if (validatePassword(password)) {
//     setPasswordMsg("유효한 비밀번호입니다.");
//     setIsPasswordValid(true);
//   } else {
//     setPasswordMsg(
//       "비밀번호는 8~15자의 영문, 숫자, 특수문자 조합이어야 합니다."
//     );
//     setIsPasswordValid(false);
//   }

//   // 비밀번호 확인 필드와 비교
//   if (passwordConfirm && password !== passwordConfirm) {
//     setPasswordConfirmMsg("비밀번호가 일치하지 않습니다.");
//   } else if (passwordConfirm) {
//     setPasswordConfirmMsg("비밀번호가 일치합니다.");
//   }

//   setEmptyFieldsMsg((prev) => ({ ...prev, password: false }));
// };

// // Password confirmation change handler
// const handlePasswordConfirmChange = (e) => {
//   const confirmPassword = e.target.value;
//   setPasswordConfirm(confirmPassword);

//   if (confirmPassword !== formData.password) {
//     setPasswordConfirmMsg("비밀번호가 일치하지 않습니다.");
//   } else {
//     setPasswordConfirmMsg("비밀번호가 일치합니다.");
//   }

//   setEmptyFieldsMsg((prev) => ({ ...prev, passwordConfirm: false }));
// };

// // Email verification handler
// const handleVerifyEmail = async () => {
//   const access = localStorage.getItem("access");

//   if (formData.email && isEmailValid) {
//     try {
//       const response = await axios.post(
//         `${BASE_URL}/api/auths/email/send`,
//         {
//           email: formData.email,
//           purpose: "changePassword",
//         },
//         {
//           headers: {
//             "Content-Type": "application/json",
//             // 'access': access,
//           },
//         }
//       );

//       if (response.data.code === "1A01") {
//         alert("인증 이메일이 발송되었습니다. 이메일을 확인해주세요.");
//         setIsEmailVerified(true);
//       } else {
//         alert(response.data.message || "이메일 발송에 실패했습니다.");
//       }
//     } catch (error) {
//       console.error("이메일 인증 오류:", error);
//       alert("이메일 인증 중 오류가 발생했습니다.");
//     } finally {
//       setIsChecking(false);
//     }
//   } else {
//     alert("유효한 이메일을 입력해주세요.");
//   }
// };

// // Email verification code handler
// const handleEmailVerifyCodeChange = (e) => {
//   const newCode = e.target.value.slice(0, 6); // 6자리로 제한
//   setEmailVerifyCode(newCode);
//   setEmptyFieldsMsg((prev) => ({ ...prev, emailVerifyCode: false }));
// };

// // Handle submit
// const handleChangePassword = async (event) => {
//   event.preventDefault();

//   if (
//     isEmailVerified &&
//     isPasswordValid &&
//     formData.password === passwordConfirm
//   ) {
//     try {
//       await updatePassword(formData.password);
//       alert("비밀번호가 성공적으로 변경되었습니다.");
//       nav("/main");
//     } catch (error) {
//       alert("비밀번호 변경 중 오류가 발생했습니다.");
//       console.error("비밀번호 변경 오류:", error);
//     }
//   } else {
//     alert("모든 필드를 올바르게 입력하고 이메일 인증을 완료해주세요.");
//   }
// };

// // Password validation
// const validatePassword = (password) => {
//   const passwordRegex =
//     /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,15}$/;
//   return passwordRegex.test(password);
// };

// return (
//   <Container>
//     <form onSubmit={handleChangePassword}>
//       <div className="form">
//         <Div>
//           <div
//             className="emailBox"
//             style={{ maxWidth: "100%", justifyContent: "start" }}
//           >
//             <label htmlFor="email" className="label">
//               Email address
//             </label>
//             <input
//               type="email"
//               id="email"
//               className="loginInput"
//               value={formData.email}
//               onChange={handleEmailChange}
//               placeholder="이메일을 입력하세요"
//               required
//             />
//           </div>
//           <SmallButtonHover
//             type="button"
//             onClick={handleVerifyEmail}
//             disabled={!formData.email || !isEmailValid || isChecking}
//           >
//             코드요청
//           </SmallButtonHover>
//         </Div>
//         <Msg isValid={isEmailValid}>
//           {isChecking ? "확인 중..." : emailMsg}
//         </Msg>

//         <Div>
//           <div
//             className="emailBox"
//             style={{ maxWidth: "100%", justifyContent: "start" }}
//           >
//             <label htmlFor="emailCode" className="label">
//               Email verification code
//             </label>
//             <input
//               type="text"
//               id="emailCode"
//               className="loginInput"
//               value={emailVerifyCode}
//               onChange={handleEmailVerifyCodeChange}
//               placeholder="6자리 인증번호를 입력하세요"
//               required
//             />
//           </div>
//           <SmallButtonHover type="button" onClick={handleVerifyEmailCode}>
//             {isEmailVerified ? "인증완료" : "인증요청"}
//           </SmallButtonHover>
//         </Div>
//         <p style={{ fontSize: "12px", padding: "0", margin: "0" }}>
//           {emailVeriMsg}
//         </p>

//         <Div>
//           <div
//             className="emailBox"
//             style={{ maxWidth: "100%", justifyContent: "start" }}
//           >
//             <label htmlFor="password" className="label">
//               Password
//             </label>
//             <input
//               type="password"
//               id="password"
//               className="loginInput"
//               value={formData.password}
//               onChange={handlePasswordChange}
//               placeholder="비밀번호를 입력하세요"
//               required
//             />
//           </div>
//           <InfoIconContainer>
//             <IoMdInformationCircleOutline size="30" color="gray" />
//             <Tooltip>
//               비밀번호 유효성검사 (영문, 숫자, 특수문자 조합으로 이루어진
//               8~15자의 문자열로 이루어진 비밀번호)
//             </Tooltip>
//           </InfoIconContainer>
//         </Div>
//         <Msg isValid={isPasswordValid}>{passwordMsg}</Msg>

//         <div
//           className="emailBox"
//           style={{ maxWidth: "100%", justifyContent: "start" }}
//         >
//           <label htmlFor="passwordConfirm" className="label">
//             Password verification
//           </label>
//           <input
//             type="password"
//             id="passwordConfirm"
//             className="loginInput"
//             value={passwordConfirm}
//             onChange={handlePasswordConfirmChange}
//             placeholder="비밀번호를 한 번 더 확인해주세요"
//             required
//           />
//         </div>
//         <Msg isValid={passwordConfirm === formData.password}>
//           {passwordConfirmMsg}
//         </Msg>

//         <div style={{ display: "flex", justifyContent: "end" }}>
//           <SubmitButton type="submit">변경하기</SubmitButton>
//         </div>
//       </div>
//     </form>
//   </Container>
// );
// };

export default ChangePassword;
