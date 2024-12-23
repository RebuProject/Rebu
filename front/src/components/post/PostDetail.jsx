import React, { useState, useRef, useEffect, useCallback } from "react";
import styled from "styled-components";
import { useLocation, useNavigate } from "react-router-dom";
import {
  FaRegHeart,
  FaHeart,
  FaRegComment,
  FaRegBookmark,
  FaBookmark,
  FaRegStar,
} from "react-icons/fa";
import { FiMoreVertical } from "react-icons/fi";
import { MdPlace } from "react-icons/md";
import { RiSendPlaneLine } from "react-icons/ri";
import { IoIosArrowForward, IoIosArrowBack } from "react-icons/io";
import PostDelete from "./PostDeleteModal";
import PostComment from "./PostComment";
import PostModifyModal from "./PostModifyModal";
import ModalPortal from "../../util/ModalPortal";
import axios from "axios";
import { BASE_URL } from "../../views/Signup";
import nullImg from "../../assets/images/img.webp";

const PostWrapper = styled.div`
  background-color: ${(props) =>
    props.theme.value === "light" ? "#fbf8fe" : "#404040"};
  width: 70%;
  @media (max-width: 425px) {
    width: 80%;
  }
  border: 1px solid #dbdbdb;
  border-radius: 8px;
  margin-top: 10px;
  margin-bottom: 10px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
  padding: 0px 20px;
  display: flex;
  flex-direction: column;
`;

const PostHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding-top: 10px;
  padding-bottom: 10px;
  position: relative;
  @media (max-width: 375px) {
    font-size: 14.5px;
  }
`;

const ProfileImage = styled.img`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
`;

const ProfileDetails = styled.div`
  display: flex;
  flex-direction: column;
`;

const Username = styled.span`
  font-weight: bold;
  font-size: 17px;
  @media (max-width: 375px) {
    font-size: 15px;
  }
`;

const Location = styled.span`
  display: flex;
  color: ${(props) => (props.theme.value === "light" ? "#8e8e8e" : "#ffffff")};
  text-decoration: underline;
  align-items: center;
  cursor: pointer;
`;

const LocationIcon = styled(MdPlace)`
  display: flex;
`;

const ShopName = styled.span`
  display: flex;
`;

const IconBox = styled.div`
  color: #943aee;
  font-size: 25px;
  margin-top: 10px;
  position: relative;
  cursor: pointer;
  @media (max-width: 375px) {
    font-size: 20px;
  }
`;

const DropdownMenu = styled.div`
  position: absolute;
  top: 45px;
  right: 3%;
  padding: 5px;
  z-index: 1;
  border-radius: 10px;
  background-color: ${(props) =>
    props.theme.value === "light" ? "#ffffff" : "#e5e5e5"};
  color: black;
  border: 1px solid #ccc;
  box-shadow: 0 4px 8px rgba(21, 17, 17, 0.1);
  width: 140px;
  display: ${(props) => (props.show ? "block" : "none")};
`;

const DropdownItem = styled.div`
  padding: 10px;
  cursor: pointer;
  &:hover {
    border-radius: 5px;
    background-color: #f0f0f0;
  }
`;

const PostActions = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding-top: 5px;
  @media (max-width: 375px) {
    font-size: 15px;
  }
`;

const ActionIcon = styled.div`
  margin-left: 5px;
  margin-right: 8px;
  font-size: 1.2em;
  cursor: pointer;
`;

const CommentIcon = styled(FaRegComment)`
  color: #943aee;
`;

const ShareIcon = styled(RiSendPlaneLine)`
  width: 22px;
  height: 22px;
  margin-top: 2px;
  cursor: pointer;
`;

const Likes = styled.div`
  margin: 5px;
  font-weight: bold;
  font-size: 15px;
  @media (max-width: 375px) {
    font-size: 14px;
  }
`;

const PostDescription = styled.div`
  font-size: 16px;
  margin-left: 5px;
  padding-top: 10px;
  @media (max-width: 375px) {
    font-size: 14.5px;
  }
`;

const HashtagContainer = styled.div`
  display: flex;
  flex-direction: row;
  margin-top: 5px;
`;

const PostHashtag = styled.div`
  font-size: 15px;
  margin-left: 5px;
  padding-top: 10px;
  @media (max-width: 375px) {
    font-size: 14px;
  }
`;

const BottomWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin-left: 5px;
  margin-top: 10px;
  @media (max-width: 375px) {
    font-size: 14.5px;
  }
`;

const CommentText = styled.div`
  margin: 10px 0px;
  text-decoration: underline;
  cursor: pointer;
`;

const PostTime = styled.span`
  color: ${(props) => (props.theme.value === "light" ? "#8e8e8e" : "#ffffff")};
  font-size: 0.9em;
  margin-top: 10px;
  margin-bottom: 10px;
  margin-right: 10px;
`;

const Rating = styled.div`
  display: flex;
  align-items: center;
  margin-right: 10px;
  font-size: 1.1rem;
  color: #ffcc00;
  @media (max-width: 375px) {
    font-size: 16px;
  }
`;

const RatingText = styled.span`
  color: ${(props) => (props.theme.value === "light" ? "#777777" : "#ffffff")};
`;

const CommentList = styled.div`
  padding: 0 10px;
  margin-bottom: 10px;
  max-height: ${(props) => (props.expanded ? "auto" : "0")};
  overflow: hidden;
  transition: max-height 0.3s ease;
`;

const SlideImg = styled.div`
  width: 100%;
  /* height: 95%; */
  padding-top: 100%; /* 정사각형을 만들기 위해 패딩 */
  position: relative;
  overflow: hidden;
  margin-bottom: 5px;
`;

const SlideBack = styled(IoIosArrowBack)`
  position: absolute;
  width: 25px;
  height: 100%;
  top: 0;
  left: 0;
  color: #5b5b5b;
  /* background-color: rgba(141, 141, 141, 0.5); */
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.5s ease;
  z-index: 1;
`;

const SlideFront = styled(IoIosArrowForward)`
  position: absolute;
  width: 25px;
  height: 100%;
  top: 0;
  right: 0;
  color: #5b5b5b;
  /* background-color: rgba(141, 141, 141, 0.5); */
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.5s ease;
  z-index: 1;
`;

const PostImage = styled.img`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover; /* 이미지가 잘리지 않게 조정 */
`;

const DotsWrapper = styled.div`
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
`;

const Dot = styled.div`
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: ${(props) => (props.active ? "#943AEE" : "#c5c5c5")};
  margin: 0 4px;
  z-index: 0;
`;

const timeSince = (date) => {
  const milliSeconds = new Date() - date;
  const seconds = milliSeconds / 1000;
  if (seconds < 60) return `방금 전`;
  const minutes = seconds / 60;
  if (minutes < 60) return `${Math.floor(minutes)}분 전`;
  const hours = minutes / 60;
  if (hours < 24) return `${Math.floor(hours)}시간 전`;
  const days = hours / 24;
  if (days < 7) return `${Math.floor(days)}일 전`;
  const weeks = days / 7;
  if (weeks < 5) return `${Math.floor(weeks)}주 전`;
  const months = days / 30;
  if (months < 12) return `${Math.floor(months)}개월 전`;
  const years = days / 365;
  return `${Math.floor(years)}년 전`;
};

const PostDetail = ({ information = {}, currentUser, loginUser, type }) => {
  const location = useLocation();
  const navigate = useNavigate();
  const updatedPost = location.state?.post;
  const modifyPostId = location.state?.postId;
  const [showDropdown, setShowDropdown] = useState(
    Array(information.length).fill(false)
  );
  const [postModifyModalOpen, setPostModifyModalOpen] = useState(false);
  const [PostDeleteModalOpen, setPostDeleteModalOpen] = useState(false);
  const [postId, setPostId] = useState(null);
  const [selectedPost, setSelectedPost] = useState(null);
  const [isCommnetActive, setIsCommentActive] = useState(
    Array(information.length).fill(false)
  );
  const [posts, setPosts] = useState(
    information ? information.map((post) => ({ ...post, currentIndex: 0 })) : {}
  );
  const [expandedComments, setExpandedComments] = useState(
    Array(information.length).fill(false)
  );
  const dropdownRefs = useRef([]);
  const [comment, setComment] = useState([]);
  const [shopdata, setShopData] = useState([]);
  const [page, setPage] = useState(0);
  console.log(information);
  console.log(posts);

  const nextSlide = useCallback((index) => {
    setPosts((prevPosts) => {
      const updatedPosts = [...prevPosts];
      const length = updatedPosts[index].feed.imageSrcs.length;
      updatedPosts[index].currentIndex =
        updatedPosts[index].currentIndex === length - 1
          ? updatedPosts[index].currentIndex
          : updatedPosts[index].currentIndex + 1;
      return updatedPosts;
    });
  }, []);

  const prevSlide = useCallback((index) => {
    setPosts((prevPosts) => {
      const updatedPosts = [...prevPosts];
      const length = updatedPosts[index].feed.imageSrcs.length;
      updatedPosts[index].currentIndex =
        updatedPosts[index].currentIndex === 0
          ? updatedPosts[index].currentIndex
          : updatedPosts[index].currentIndex - 1;
      return updatedPosts;
    });
  }, []);

  const ModifyModalOpen = (id) => {
    const post = posts.find((post) => post.feed.feedId === id);
    setSelectedPost(post);
    setPostModifyModalOpen(true);
  };

  const handleLikeToggle = useCallback((feedId, index) => {
    const access = localStorage.getItem("access");

    const isCurrentlyLiked = posts[index].isLiked;

    // 좋아요 취소
    if (isCurrentlyLiked) {
      axios
        .delete(`${BASE_URL}/api/likes/feed/${feedId}`, {
          headers: {
            access: access,
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          console.log("좋아요 취소");
          setPosts((prevPosts) => {
            const updatedPosts = [...prevPosts];
            updatedPosts[index].isLiked = false;
            updatedPosts[index].feed.likeCnt -= 1;
            return updatedPosts;
          });
        })
        .catch((error) => {
          console.log("좋아요 취소 오류 발생:", error);
        });
    } // 좋아요
    else {
      axios
        .post(
          `${BASE_URL}/api/likes/feed`,
          {
            feedId: feedId,
          },
          {
            headers: {
              access: access,
              "Content-Type": "application/json",
            },
          }
        )
        .then((response) => {
          console.log("좋아요 성공");
          setPosts((prevPosts) => {
            const updatedPosts = [...prevPosts];
            updatedPosts[index].isLiked = true;
            updatedPosts[index].feed.likeCnt += 1;
            return updatedPosts;
          });
        })
        .catch((error) => {
          console.log("좋아요 오류 발생:", error);
        });
    }
  }, []);

  const handleScrapToggle = useCallback((feedId, index) => {
    // 스크랩 api 호출
    const access = localStorage.getItem("access");

    const isCurrentScraped = posts[index].isScraped;

    // 스크랩 취소
    if (isCurrentScraped) {
      axios
        .delete(`${BASE_URL}/api/scraps/${feedId}`, {
          headers: {
            access: access,
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          console.log("스크랩 취소");
          setPosts((prevPosts) => {
            const updatedPosts = [...prevPosts];
            updatedPosts[index].isScraped = false;
            return updatedPosts;
          });
        })
        .catch((error) => {
          console.log("스크랩 취소 오류 발생:", error);
        });
    } // 스크랩
    else {
      axios
        .post(
          `${BASE_URL}/api/scraps`,
          {
            feedId: feedId,
          },
          {
            headers: {
              access: access,
              "Content-Type": "application/json",
            },
          }
        )
        .then((response) => {
          console.log("스크랩 성공");
          setPosts((prevPosts) => {
            const updatedPosts = [...prevPosts];
            updatedPosts[index].isScraped = true;
            return updatedPosts;
          });
        })
        .catch((error) => {
          console.log("스크랩 오류 발생:", error);
        });
    }

    setPosts((prevPosts) => {
      const updatedPosts = [...prevPosts];
      updatedPosts[index].isScraped = !updatedPosts[index].isScraped;
      return updatedPosts;
    });
  }, []);

  const handleMoreOptionToggle = useCallback((index) => {
    setShowDropdown((prevShowDropdown) => {
      const updatedShowDropdown = [...prevShowDropdown];
      updatedShowDropdown[index] = !updatedShowDropdown[index];
      return updatedShowDropdown;
    });
  }, []);

  const handleClickOutside = useCallback((event) => {
    dropdownRefs.current.forEach((ref, index) => {
      if (ref && !ref.contains(event.target)) {
        setShowDropdown((prevShowDropdown) => {
          const updatedShowDropdown = [...prevShowDropdown];
          updatedShowDropdown[index] = false;
          return updatedShowDropdown;
        });
      }
    });
  }, []);

  const handlePostSave = (updatedPost, index) => {
    setPosts((prevPosts) => {
      const newPosts = [...prevPosts];
      newPosts[index] = updatedPost;
      return newPosts;
    });
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [handleClickOutside]);

  const postDeleteModalOpen = (id) => {
    setPostDeleteModalOpen(true);
    setPostId(id);
  };

  const handleDelete = (deletedPostId) => {
    // 삭제된 게시글을 제외한 나머지 게시글로 상태 업데이트
    setPosts(posts.filter((post) => post.feedId !== deletedPostId));
  };

  const closeModal = () => {
    setPostDeleteModalOpen(false);
    setShowDropdown(Array(information.length).fill(false));
    setPostModifyModalOpen(false);
  };

  const toggleComments = useCallback(
    (index) => {
      setExpandedComments((prevExpandedComments) => {
        const updatedExpandedComments = Array(information.length).fill(false);
        updatedExpandedComments[index] = !prevExpandedComments[index];
        return updatedExpandedComments;
      });
      setIsCommentActive((prevIsCommentActive) => {
        const updatedIsCommentActive = Array(information.length).fill(false);
        updatedIsCommentActive[index] = !prevIsCommentActive[index];
        return updatedIsCommentActive;
      });
    },
    [information.length]
  );

  const handleShopNameClick = (nickname) => {
    navigate(`/profile/${nickname}/SHOP`);
  };

  const scrollDown = () => {
    window.scrollBy({
      top: 200,
      left: 0,
      behavior: "smooth",
    });
  };

  return (
    <>
      {posts &&
        posts.map((item, index) => (
          <PostWrapper key={index}>
            <PostHeader>
              <div style={{ display: "flex", alignItems: "center" }}>
                {item.writer.profileImageSrc === null ? (
                  <ProfileImage src={nullImg} alt="Porfile" />
                ) : (
                  <ProfileImage
                    src={
                      "https://www.rebu.kro.kr/data/" +
                      item.writer.profileImageSrc
                    }
                    alt="Profile"
                  />
                )}

                <ProfileDetails>
                  <Username>{item.writer.nickname}</Username>
                  <Location>
                    {item.shop && <LocationIcon />}
                    <ShopName
                      onClick={() =>
                        handleShopNameClick(item.shop?.shopNickname)
                      }
                    >
                      {item.shop?.shopName}
                    </ShopName>
                  </Location>
                </ProfileDetails>
              </div>

              <div style={{ position: "relative" }}>
                {item.writer.nickname === loginUser ? (
                  <IconBox onClick={() => handleMoreOptionToggle(index)}>
                    <FiMoreVertical />
                  </IconBox>
                ) : (
                  <IconBox
                    onClick={() => handleScrapToggle(item.feed.feedId, index)}
                  >
                    {item.isScraped ? <FaBookmark /> : <FaRegBookmark />}
                  </IconBox>
                )}
                <DropdownMenu
                  ref={(el) => (dropdownRefs.current[index] = el)}
                  show={showDropdown[index]}
                >
                  <DropdownItem
                    onClick={() => ModifyModalOpen(item.feed.feedId)}
                  >
                    게시글 수정
                  </DropdownItem>
                  {postModifyModalOpen && selectedPost && (
                    <ModalPortal>
                      <PostModifyModal
                        postModifyModalOpen={postModifyModalOpen}
                        closeModal={closeModal}
                        post={selectedPost}
                        currentUser={currentUser}
                        index={index}
                        feedId={item.feed.feedId}
                        onSave={handlePostSave}
                      />
                    </ModalPortal>
                  )}
                  <hr style={{ margin: "5px 0px" }} />
                  <DropdownItem
                    onClick={() => postDeleteModalOpen(item.feed.feedId)}
                  >
                    게시글 삭제
                  </DropdownItem>
                  {PostDeleteModalOpen && (
                    <ModalPortal>
                      <PostDelete
                        nickname={loginUser}
                        PostDeleteModalOpen={PostDeleteModalOpen}
                        closeModal={closeModal}
                        postId={postId}
                        type={type}
                        onDelete={() => handleDelete(postId)}
                      />
                    </ModalPortal>
                  )}
                </DropdownMenu>
              </div>
            </PostHeader>

            <SlideImg>
              <SlideBack onClick={() => prevSlide(index)} />
              <SlideFront onClick={() => nextSlide(index)} />
              {item.feed.imageSrcs?.map((slide, imgIndex) => (
                <PostImage
                  key={imgIndex}
                  src={"https://www.rebu.kro.kr/data/" + slide}
                  alt={`Slide ${imgIndex}`}
                  style={{
                    display: imgIndex === item.currentIndex ? "block" : "none",
                  }}
                />
              ))}
              <DotsWrapper>
                {item.feed.imageSrcs?.map((_, imgIndex) => (
                  <Dot key={imgIndex} active={imgIndex === item.currentIndex} />
                ))}
              </DotsWrapper>
            </SlideImg>

            <PostActions>
              <div style={{ display: "flex", alignItems: "center" }}>
                <ActionIcon
                  onClick={() => handleLikeToggle(item.feed.feedId, index)}
                >
                  {item.isLiked ? (
                    <FaHeart style={{ color: "red" }} />
                  ) : (
                    <FaRegHeart />
                  )}
                </ActionIcon>
                <ActionIcon>
                  {isCommnetActive[index] ? (
                    <CommentIcon onClick={() => toggleComments(index)} />
                  ) : (
                    <FaRegComment
                      onClick={() => {
                        toggleComments(index);
                        scrollDown();
                      }}
                    />
                  )}
                </ActionIcon>
                <ActionIcon>
                  <ShareIcon />
                </ActionIcon>
              </div>

              {item.feed.rating ? (
                <Rating>
                  <FaRegStar />
                  &nbsp;
                  <RatingText>{item.feed.rating}</RatingText>
                </Rating>
              ) : (
                ""
              )}
            </PostActions>
            <Likes>좋아요 {item.feed.likeCnt}개</Likes>
            <PostDescription>
              {updatedPost &&
              modifyPostId === index &&
              item.writer.nickname === loginUser
                ? updatedPost.feed.content
                : item.feed.content}
            </PostDescription>
            <HashtagContainer>
              {item.feed.hashtags.map((hashtag) => (
                <PostHashtag>#{hashtag}</PostHashtag>
              ))}
            </HashtagContainer>
            <BottomWrapper>
              <CommentText
                onClick={() => {
                  toggleComments(index);
                  scrollDown();
                }}
              >
                댓글 {item.feed.commentCnt}개
              </CommentText>
              <PostTime>{timeSince(new Date(item.feed.createAt))}</PostTime>
            </BottomWrapper>
            <CommentList expanded={expandedComments[index]}>
              {expandedComments[index] && (
                <PostComment
                  currentUser={currentUser}
                  information={information}
                  posts={posts}
                  setPosts={setPosts}
                  index={index}
                  feedId={item.feed.feedId}
                />
              )}
            </CommentList>
          </PostWrapper>
        ))}
    </>
  );
};

export default PostDetail;
