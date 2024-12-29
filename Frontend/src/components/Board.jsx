import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AiTwotoneDelete } from "react-icons/ai";
import axios from "axios";

const Board = ({ board }) => {
  const [postHovered, setPostHovered] = useState(false);
  const [creater, setCreater] = useState();
  const [user, setUser] = useState();
  const navigate = useNavigate();

  const deleteBoard = (id) => {};

  useEffect(() => {
    const User =
      localStorage.getItem("user") !== "undefined"
        ? JSON.parse(localStorage.getItem("user"))
        : localStorage.clear();
    setUser(User);
    if (board) {
      axios
        .get(`http://localhost:2000/auth/userbyid?userId=${board.createdBy}`)
        .then((res) => {
          setCreater(res.data);
        })
        .catch((err) => {
          console.log(err.message);
        });
    }
  }, [board]);

  return (
    <div className="m-2">
      <div
        onMouseEnter={() => setPostHovered(true)}
        onMouseLeave={() => setPostHovered(false)}
        onClick={() => navigate(`/board-detail/${board.boardId}`)}
        className=" relative cursor-zoom-in w-auto hover:shadow-lg rounded-lg overflow-hidden transition-all duration-500 ease-in-out"
      >
        <p className="absolute top-2 left-2 text-white font-bold bg-black bg-opacity-50 p-2 rounded">
          {board.summary}
        </p>
        <img
          className="rounded-lg w-full "
          src={board?.coverImage}
          alt="user-post"
        />
        {postHovered && (
          <div
            className="absolute top-0 w-full h-full flex flex-col justify-between p-1 pr-2 pt-2 pb-2 z-50"
            style={{ height: "100%" }}
          >
            <div className=" flex justify-between items-center gap-2 w-full">
              {board.createdBy === user?.userId && (
                <button
                  type="button"
                  onClick={(e) => {
                    e.stopPropagation();
                    deleteBoard(board.boardId);
                  }}
                  className="bg-white p-2 rounded-full w-8 h-8 flex items-center justify-center text-dark opacity-75 hover:opacity-100 outline-none"
                >
                  <AiTwotoneDelete />
                </button>
              )}
            </div>
          </div>
        )}
      </div>
      <Link
        to={`/user-profile/${creater?.id}`}
        className="flex gap-2 mt-2 items-center"
      >
        <img
          className="w-8 h-8 rounded-full object-cover"
          src={creater?.image}
          alt="user-profile"
        />
        <p className="font-semibold capitalize dark:text-white ">
          {creater?.username}
        </p>
      </Link>
    </div>
  );
};

export default Board;
