import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

import MasonryLayout from "./MasonryLayout";
import Spinner from "./Spinner";
import axios from "axios";
import ModalForm from "./ModalForm";
import { MdSend } from "react-icons/md";

const BoardDetail = ({ user }) => {
  const { boardId } = useParams();
  const [pins, setPins] = useState();
  const [boardDetail, setBoardDetail] = useState();
  const [creater, setCreater] = useState();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const token=localStorage.getItem('token')
  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  useEffect(() => {
    axios
      .get(`http://localhost:2000/content/board/get?boardId=${boardId}`)
      .then((res) => {
        const { pins, ...boardData } = res.data;
        setBoardDetail(boardData);
        setPins(pins);
      })
      .catch((err) => {
        console.log(err.message);
      });
  }, [boardId]);

  useEffect(() => {
    if (boardDetail) {
      axios
        .get(
          `http://localhost:2000/auth/userbyid?userId=${boardDetail.createdBy}`
        )
        .then((res) => {
          console.log(res.data);
          setCreater(res.data);
        });
    }
  }, [boardDetail]);

  if (!boardDetail) {
    return <Spinner message="Showing pin" />;
  }

  return (
    <>
      {isModalOpen && <ModalForm isOpen={isModalOpen} onClose={closeModal} token={token} boardId={boardId} />}
      {boardDetail && (
        <div
          className="flex xl:flex-row rounded dark:bg-gray-800 dark:text-white flex-col m-auto bg-white"
          style={{ maxWidth: "1500px" }}
        >
            
          <div className="flex p-2 justify-center items-center md:items-start flex-initial">
            <img
              className="rounded"
              src={boardDetail?.coverImage}
              alt="user-post"
            />
          </div>
          <div className="w-full p-5 flex-1 xl:min-w-620">
            <div>
              <h1 className="text-4xl font-bold break-words mt-3">
                {boardDetail.title}
              </h1>
              <p className="mt-3">{boardDetail?.summary}</p>
            </div>
            <Link
              to={`/user-profile/${creater?.userId}`}
              className="flex gap-2 mt-5 items-center bg-transparent rounded-lg "
            >
              <img
                src={creater?.image}
                className="w-10 h-10 rounded-full"
                alt="user-profile"
              />
              <p className="font-bold dark:text-white">{creater?.username}</p>
            </Link>
          </div>
          <MdSend onClick={openModal} className=" cursor-pointer w-8 h-8 mr-5 mt-4"/>
          
        </div>
      )}
      {pins?.length > 0 && (
        <h2 className="text-center font-bold text-2xl mt-8 mb-4">
          Pins from the board...
        </h2>
      )}
      {pins ? (
        <MasonryLayout pins={pins} />
      ) : (
        <Spinner message="Loading more pins" />
      )}
    </>
  );
};

export default BoardDetail;
