import React, { useEffect, useState } from "react";
import { HiLogout as LogOutIcon } from "react-icons/hi";
import { useParams, useNavigate } from "react-router-dom";
import { FcInvite } from "react-icons/fc";
import { RiUserFollowFill } from "react-icons/ri";
import { MdOutlinePersonAddAlt1 } from "react-icons/md";

import MasonryLayout from "./MasonryLayout";
import Spinner from "./Spinner";
import axios from "axios";
import RequestModal from "./RequestsModal";
import BoardInvitationModal from "./BoardInvitationModal";
import FollowerList from "./FollowerList";

const activeBtnStyles =
  "bg-red-500 text-white font-bold p-2 rounded-full w-20 outline-none";
const notActiveBtnStyles =
  "bg-primary mr-4 text-black font-bold p-2 rounded-full w-20 outline-none";

const UserProfile = () => {
  const [user, setUser] = useState();
  const [pins, setPins] = useState();
  const [boards, setBoards] = useState();
  const [token, setToken] = useState();
  const [text, setText] = useState("Pins");
  const [activeBtn, setActiveBtn] = useState("pin");
  const [showModal, setShowModal] = useState(false);
  const [creater, setCreater] = useState();
  const [followRequests, setFollowRequest] = useState();
  const [followers, setFollowers] = useState();
  const [followings, setFollowings] = useState();
  const [boardInvitations, setBoardInvitations] = useState();
  const [requestModal, setRequestModal] = useState(false);
  const [invitationModal, setInvitationModal] = useState(false);
  const [showList, setShowList] = useState(null);
  const navigate = useNavigate();
  const { userId } = useParams();

  useEffect(() => {
    const User =
      localStorage.getItem("user") !== "undefined"
        ? JSON.parse(localStorage.getItem("user"))
        : localStorage.clear();
    setUser(User);
    const token = localStorage.getItem("token");
    setToken(token);
  }, []);
  console.log(followers);
  console.log(followings);
  useEffect(() => {
    if (user && token) {
      const fetchData = async () => {
        try {
          console.log(token);
          const headers = {
            Authorization: `Bearer ${token}`,
          };

          const followersResponse = await axios.get(
            `http://localhost:2000/collab/follow/followers?followee=${userId}`,
            { headers }
          );
          const followersList = await axios.get(
            "http://localhost:2000/auth/fetchByUserIds",
            {
              params: { userIds: followersResponse.data },
              paramsSerializer: (params) => {
                return `userIds=${params.userIds.join(",")}`;
              },
            }
          );
          setFollowers(followersList.data);

          const followingsResponse = await axios.get(
            `http://localhost:2000/collab/follow/followings?followee=${userId}`
          );
          console.log(followingsResponse.data);
          const followingList = await axios.get(
            "http://localhost:2000/auth/fetchByUserIds",
            {
              params: { userIds: followingsResponse.data },
              paramsSerializer: (params) => {
                return `userIds=${params.userIds.join(",")}`;
              },
            }
          );
          console.log(followingList);
          setFollowings(followingList.data);

          const requestsResponse = await axios.get(
            `http://localhost:2000/collab/follow/requests`,
            { headers }
          );
          const requestsList = await axios.get(
            "http://localhost:2000/auth/fetchByUserIds",
            {
              params: { userIds: requestsResponse.data },
              paramsSerializer: (params) => {
                return `userIds=${params.userIds.join(",")}`;
              },
            }
          );
          setFollowRequest(requestsList.data);

          const invitationsResponse = await axios.get(
            "http://localhost:2000/collab/invite/invitations",
            { headers }
          );
          console.log(invitationsResponse);
          setBoardInvitations(invitationsResponse.data);
        } catch (error) {
          console.log(error.response.data);
          // Handle error, show error message to the user, etc.
        }
      };

      fetchData();
    }
  }, [user, token]);

  useEffect(() => {
    axios
      .get(`http://localhost:2000/auth/userbyid?userId=${userId}`)
      .then((res) => {
        setCreater(res.data);
      });
  }, [userId]);

  useEffect(() => {
    const url =
      text === "Pins"
        ? `http://localhost:2000/content/pin/getpins?createdBy=${userId}`
        : `http://localhost:2000/content/board/getboards?userId=${userId}`;

    axios
      .get(url)
      .then((res) => {
        if (text === "Pins") {
          setPins(res.data);
        }
        if (text === "Boards") {
          setBoards(res.data);
        }
      })
      .catch((err) => {
        console.log(err.message);
      });
  }, [text, userId]);

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    navigate("/auth");
  };

  const handleConfirm = (followerId) => {
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    axios
      .post(
        `http://localhost:2000/collab/follow/confirm-request?follower=${followerId}`
      )
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        console.log(err.response.data);
      });
  };

  const handleDelete = (followerId) => {
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    axios
      .delete(
        `http://localhost:2000/collab/follow/delete-request?follower=${followerId}`
      )
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        console.log(err.response.data);
      });
  };

  const toggleRequestModal = () => {
    setRequestModal(!requestModal);
  };

  const toggleInvitationModal = () => {
    setInvitationModal(!invitationModal);
  };

  const removeItem = (inviter) => {
    const updatedItems = boardInvitations.filter((i) => i.inviter !== inviter);
    setBoardInvitations(updatedItems);
  };

  const acceptInvitation = (inviteBoardId, inviter) => {
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    axios
      .put(
        `http://localhost:2000/collab/invite/accept?boardId=${inviteBoardId}`
      )
      .then((res) => {
        console.log(res.data);
        removeItem(inviter);
      })
      .catch((err) => {
        console.log(err.response.data);
      });
  };

  const rejectInvitation = (inviteBoardId, inviter) => {
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    axios
      .delete(
        `http://localhost:2000/collab/invite/reject?boardId=${inviteBoardId}`
      )
      .then((res) => {
        console.log(res.data);
        removeItem(inviter);
      })
      .catch((err) => {
        console.log(err.response.data);
      });
  };

  const followHandler = () => {
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    axios
      .post(`http://localhost:2000/collab/follow/request?followee=${userId}`)
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        console.log(err.response.data);
      });
  };
  const isFollowing=()=>{
    let temp=true;
    followings?.forEach(e => {
      if(Number(e?.userId)===Number(user?.userId)){
        temp=false
      }
    });
    return temp
  }
  return (
    <div className="relative pb-2 h-full justify-center items-center">
      {requestModal && (
        <RequestModal
          requests={followRequests}
          closeModal={toggleRequestModal}
          handleConfirm={handleConfirm}
          handleDelete={handleDelete}
        />
      )}
      {invitationModal && (
        <BoardInvitationModal
          boardInvitations={boardInvitations}
          closeModal={toggleInvitationModal}
          acceptInvitation={acceptInvitation}
          rejectInvitation={rejectInvitation}
        />
      )}
      {showList === "followers" && followers.length > 0 && (
        <FollowerList
          setShowList={setShowList}
          followers={followers}
          title="Followers"
        />
      )}
      {showList === "followings" && followings.length > 0 && (
        <FollowerList
          setShowList={setShowList}
          followers={followings}
          title="Followings"
        />
      )}
      <div className="flex flex-col pb-5">
        <div className="relative flex flex-col mb-7">
          <div className="flex flex-col justify-center items-center">
            <img
              className=" w-full h-370 2xl:h-510 shadow-lg object-cover"
              src="https://www.infosys.com/content/dam/infosys-web/en/the-economist-group/home-banner.jpg"
              alt="user-pic"
            />
            <img
              className="rounded-full w-20 h-20 -mt-10 shadow-xl object-cover"
              src={creater?.image}
              alt={creater?.username}
            />
          </div>
          <h1 className="font-bold text-3xl text-center dark:text-white mt-3">
            {creater?.username}
          </h1>
          <div className="flex justify-center">
            <p
              onClick={() => {
                setShowList("followers");
              }}
              className="cursor-pointer"
            >
              Followers: {followers?.length}
            </p>
            <p
              onClick={() => {
                setShowList("followings");
              }}
              className="cursor-pointer ml-3"
            >
              Following: {followings?.length}{" "}
            </p>
            {((Number(userId) !== Number(user?.userId))&& isFollowing()) && (
              <MdOutlinePersonAddAlt1
                onClick={followHandler}
                className="text-black w-6 h-6 ml-2 rounded-lg cursor-pointer"
              />
            )}
          </div>
          <div className="absolute top-0 z-1 right-0 p-2">
            {token && (
              <div className="flex sm:flex-col space-x-3 lg:space-x-0 md:space-x-0">
                <LogOutIcon
                  className="text-white w-10 h-10 bg-red-600 rounded-lg cursor-pointer mt-3"
                  onClick={logout}
                />
                <FcInvite
                  className="text-white w-10 h-10 bg-red-600 rounded-lg cursor-pointer mt-3"
                  onClick={toggleInvitationModal}
                />
                <RiUserFollowFill
                  className="text-white w-10 h-10 bg-red-600 rounded-lg cursor-pointer mt-3"
                  onClick={toggleRequestModal}
                />
              </div>
            )}
          </div>
        </div>
        <div className="text-center mb-7">
          <button
            type="button"
            onClick={(e) => {
              setText("Pins");
              setActiveBtn("pin");
            }}
            className={`${
              activeBtn === "pin" ? activeBtnStyles : notActiveBtnStyles
            }`}
          >
            Pins
          </button>
          <button
            type="button"
            onClick={(e) => {
              setText("Boards");
              setActiveBtn("boards");
            }}
            className={`${
              activeBtn === "boards" ? activeBtnStyles : notActiveBtnStyles
            }`}
          >
            Boards
          </button>
        </div>

        <div className="px-2">
          {text === "Pins" && (
            <MasonryLayout pins={pins && pins} boards={null} />
          )}
          {text === "Boards" && (
            <MasonryLayout pins={null} boards={boards && boards} />
          )}
        </div>

        {pins?.length === 0 && (
          <div className="flex justify-center font-bold items-center w-full text-1xl mt-2">
            No Pins Found!
          </div>
        )}
      </div>
    </div>
  );
};

export default UserProfile;
