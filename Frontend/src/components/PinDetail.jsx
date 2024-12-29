import React, { useEffect, useState } from "react";
import { MdDownloadForOffline } from "react-icons/md";
import { Link, useParams } from "react-router-dom";

import MasonryLayout from "./MasonryLayout";
import Spinner from "./Spinner";
import axios from "axios";

const PinDetail = ({ user }) => {
  const { pinId } = useParams();
  const [pins, setPins] = useState();
  const [pinDetail, setPinDetail] = useState();
  const [creater, setCreater] = useState();

  console.log("PinId:" + pinId);
  useEffect(() => {
    axios
      .get(`http://localhost:2000/content/pin/get?pinId=${pinId}`)
      .then((res) => {
        setPinDetail(res.data);
      })
      .catch((err) => {
        console.log(err.message);
      });
  }, [pinId]);

  useEffect(() => {
    if (pinDetail) {
      axios
        .get(`http://localhost:2000/content/pin/getpins?tags=${pinDetail.tags}`)
        .then((res) => {
          setPins(res.data);
        })
        .catch((err) => {
          console.log(err.response.data);
        });
    }
  }, [pinDetail]);

  useEffect(() => {
    if (pinDetail) {
      axios
        .get(
          `http://localhost:2000/auth/userbyid?userId=${pinDetail.createdBy}`
        )
        .then((res) => {
          console.log(res.data)
          setCreater(res.data);
        });
    }
  }, [pinDetail]);

  if (!pinDetail) {
    return <Spinner message="Showing pin" />;
  }

  return (
    <>
      {pinDetail && (
        <div
          className="flex xl:flex-row rounded dark:bg-gray-800 dark:text-white flex-col m-auto bg-white"
          style={{ maxWidth: "1500px" }}
        >
          <div className="flex p-2 justify-center items-center md:items-start flex-initial">
            <img className="rounded" src={pinDetail?.image} alt="user-post" />
          </div>
          <div className="w-full p-5 flex-1 xl:min-w-620">
            <div className="flex items-center justify-between">
              <div className="flex gap-2 items-center">
                <a
                  href={`${pinDetail?.image}?dl=`}
                  download
                  className="bg-secondaryColor dark:bg-gray-200 dark:text-gray-900 p-2 text-xl rounded-full flex items-center justify-center text-dark opacity-75 hover:opacity-100"
                >
                  <MdDownloadForOffline />
                </a>
              </div>
              <a href={pinDetail.destination} target="_blank" rel="noreferrer">
                {pinDetail.destination?.slice(8)}
              </a>
            </div>
            <div>
              <h1 className="text-4xl font-bold break-words mt-3">
                {pinDetail.title}
              </h1>
              <p className="mt-3">{pinDetail?.description}</p>
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
              <p className="font-bold dark:text-white">
                {creater?.username}
              </p>
            </Link>
          </div>
        </div>
      )}
      {pins?.length > 0 && (
        <h2 className="text-center font-bold text-2xl mt-8 mb-4">
          More like this
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

export default PinDetail;
