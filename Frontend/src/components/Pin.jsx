import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { v4 as uuidv4 } from "uuid";
import { MdDownloadForOffline } from "react-icons/md";
import { AiTwotoneDelete } from "react-icons/ai";
import { BsFillArrowUpRightCircleFill } from "react-icons/bs";
import axios from "axios";


const Pin = ({ pin }) => {
  const [postHovered, setPostHovered] = useState(false);
  const [creater,setCreater]=useState()


  const navigate = useNavigate();

  const user =
    localStorage.getItem("user") !== "undefined"
      ? JSON.parse(localStorage.getItem("user"))
      : localStorage.clear();

  const deletePin = (id) => {
    
  };

  useEffect(()=>{
    axios.get(`http://localhost:2000/auth/userbyid?userId=${pin?.createdBy}`)
    .then(res=>{
      setCreater(res.data)
    })
  },[pin?.createdBy])



  return (
    <div className="m-2">
      <div
        onMouseEnter={() => setPostHovered(true)}
        onMouseLeave={() => setPostHovered(false)}
        onClick={() => navigate(`/pin-detail/${pin.pinId}`)}
        className=" relative cursor-zoom-in w-auto hover:shadow-lg rounded-lg overflow-hidden transition-all duration-500 ease-in-out"
      >
        <img
          className="rounded-lg w-full "
          src={pin?.image}
          alt="user-post"
        />
        {postHovered && (
          <div
            className="absolute top-0 w-full h-full flex flex-col justify-between p-1 pr-2 pt-2 pb-2 z-50"
            style={{ height: "100%" }}
          >
            <div className="flex items-center justify-between">
              <div className="flex gap-2">
                <a
                  href={`${pin.image?.asset?.url}?dl=`}
                  download
                  onClick={(e) => {
                    e.stopPropagation();
                  }}
                  className="bg-white w-9 h-9 p-2 rounded-full flex items-center justify-center text-dark text-xl opacity-75 hover:opacity-100 hover:shadow-md outline-none"
                >
                  <MdDownloadForOffline />
                </a>
              </div>
            </div>
            <div className=" flex justify-between items-center gap-2 w-full">
              {pin.link?.slice(8).length > 0 ? (
                <a
                  href={pin?.destination}
                  target="_blank"
                  className="bg-white flex items-center gap-2 text-black font-bold p-2 pl-4 pr-4 rounded-full opacity-70 hover:opacity-100 hover:shadow-md"
                  rel="noreferrer"
                >
                  {" "}
                  <BsFillArrowUpRightCircleFill />
                  {pin?.destination?.slice(8, 17)}...
                </a>
              ) : undefined}
              {pin.createdBy === user?.userId && (
                <button
                  type="button"
                  onClick={(e) => {
                    e.stopPropagation();
                    deletePin(pin.pinId);
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

export default Pin;
