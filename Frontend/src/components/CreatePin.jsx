import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { categories } from "../utils/data";
import Spinner from "./Spinner";
import axios from "axios";

const CreatePin = ({ user }) => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [link, setLink] = useState("");
  const [fields, setFields] = useState();
  const [category, setCategory] = useState("");
  const [otherCategory, setOtherCategory] = useState("");
  const [image, setImage] = useState("");
  const [boardId, setBoardId] = useState("");
  const [boardTitle, setBoardTitle] = useState("");
  const [boardSummary, setBoardSummary] = useState("");
  const [boardCoverImage, setBoardCoverImage] = useState("");
  const [isPin, setIsPin] = useState(true);
  const [token, setToken] = useState("");
  const [boards,setBoards]=useState([]);
  const [error, setError] = useState(false);
  const [success,setSuccess]=useState(false);
  const [message, setMessage] = useState('');

  const navigate = useNavigate();

  useEffect(()=>{
    const token = localStorage.getItem("token");
    console.log(token)
    setToken(token);
  },[])

  useEffect(() => {

    axios.get("http://localhost:2000/content/board/getboards?userId="+user.userId)
    .then(res=>{
      console.log(res.data)
      setBoards(res.data)
    })
    .catch(err=>{
      console.log(err.message)
    })

  }, [user.userId,isPin]);


  console.log(user)
  const handleTitleChange = (e) => {
    isPin ? setTitle(e.target.value) : setBoardTitle(e.target.value);
    setFields(false)
  };

  const handleDescriptionChange = (e) => {
    isPin ? setDescription(e.target.value) : setBoardSummary(e.target.value);
    setFields(false)
  };

  const handleLinkChange = (e) => {
    setLink(e.target.value);
    setFields(false)
  };

  const handleCategoryChange = (e) => {
    setCategory(e.target.value);
    setFields(false)
  };

  const handleOtherCategoryChange = (e) => {
    setOtherCategory(e.target.value);
    setFields(false)
  };

  const handleBoardChange=(e)=>{
    setBoardId(e.target.value)
    setFields(false)
  }

  const setImageNull = () => {
    isPin?(setImage("")):setBoardCoverImage("")    
    setFields(false)
  };

  const toggleForm = () => {
    setIsPin(!isPin);
    setSuccess(false)
    setError(false)
    setMessage("")
    setFields(false)
    // setImageNull()
  };

  const handlePasteFromClipboard = async () => {
    try {
      const clipboardData = await navigator.clipboard.readText();
      if (isPin) {
        console.log("pin")
        setImage(clipboardData);
      } else {
        console.log("board")
        console.log(clipboardData)
        setBoardCoverImage(clipboardData);
      }
      setFields(false)
    } catch (error) {
      console.log(error.message);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const url = isPin
        ? "http://localhost:2000/content/pin/create"
        : "http://localhost:2000/content/board/create";

      const body = isPin
        ? {
            title,
            image,
            description,
            createdBy: Number(user.userId),
            boardId: Number(boardId),
            link,
            tags: category === "others" ? otherCategory : category,
          }
        : {
            title: boardTitle,
            coverImage: boardCoverImage,
            createdBy: Number(user.userId),
            summary: boardSummary,
          };
      if(isPin){
          if(
          body.title===""||
          !body.boardId || 
          body.boardId===0 ||
          body.description==="" || 
          !body.createdBy || 
          body.createdBy===0 ||
          body.link==="" ||
          body.tags==="" ||
          body.image===""
          ){
            setFields(true)
            return
          }
      }else{
        if(
          body.title===""||
          body.summary==="" || 
          !body.createdBy || 
          body.createdBy===0 ||
          body.coverImage===""
          ){
            setFields(true)
            return 
          }
      }
      axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
      const response = await axios.post(url, body);
      setError(false)
      setSuccess(true)
      setMessage(response.data)
      if (!isPin) {
        toggleForm()
      }
    } catch (error) {
      setError(true)
      setMessage(error.response.data);
    }
  };

  return (
    <div className="flex flex-col  justify-center items-center mt-5 lg:h-4/5">
      {fields && (
        <p className="text-red-500 mb-5 text-xl transition-all duration-150 ease-in ">
          Please add all fields.
        </p>
      )}
      {success && (
          <div className="bg-green-100 border-l-4 border-green-500 text-green-700 p-4 mb-4" role="alert">
            <p className="font-bold">Success</p>
            <p>{message}</p>
          </div>
        )}
        {error && (
          <div className="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 mb-4" role="alert">
            <p className="font-bold">Error</p>
            <p>{message}</p>
          </div>
        )}
      <div className=" flex lg:flex-row dark:bg-gray-900 rounded flex-col justify-center items-center bg-white lg:p-5 p-3 lg:w-4/5  w-full">
        <div className="bg-secondaryColor rounded  p-3 flex flex-0.7 w-full">
          <div className=" flex justify-center items-center flex-col border-2 border-dotted border-gray-300 p-3 w-full h-420">
            <div>
              <label>
                <div className="flex flex-col items-center justify-center h-full w-full">
                  {((isPin && image==="") || (boardCoverImage==="" && !isPin)) ? (
                    <div className="flex justify-center items-center">
                      <label
                        className="block text-gray-700 text-lg font-bold mb-2"
                        htmlFor="image2"
                      >
                        Click to Paste Image Url
                      </label>
                      <img
                        className="h-12 w-12 border-solid border-4 ml-2 hover:border-dotted hover:cursor-pointer"
                        name="image2"
                        src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnZD1bHuy8k0uOSnWuD7ewK7xP_7nkl5SyZUofKQQzMQ&s"
                        alt="Url Not Correct!"
                        onClick={handlePasteFromClipboard}
                      />
                    </div>
                  ) : (
                    <div className="flex flex-col-reverse">
                      <img
                        src={isPin?image:boardCoverImage}
                        alt="uploaded-pic"
                        className="h-full w-full"
                      />
                      <div className="relative">
                        <img
                          src="https://cdn-icons-png.flaticon.com/512/70/70287.png"
                          alt="X"
                          className="h-6 w-6 hover:cursor-pointer absolute -top-5 -right-6"
                          onClick={setImageNull}
                        />
                      </div>
                    </div>
                  )}
                </div>
              </label>
            </div>
          </div>
        </div>

        <div className="flex flex-1 flex-col gap-6 lg:pl-5 mt-5 w-full">
          <input
            type="text"
            value={isPin?title:boardTitle}
            onChange={handleTitleChange}
            placeholder={`Add ${isPin ? "Pin" : "Board"} Title`}
            className="outline-none text-2xl rounded sm:text-3xl font-bold border-b-2 border-gray-200 p-2"
          />
          {user && (
            <div className="flex gap-2 mt-2 mb-2 items-center bg-transparent dark:text-white ">
              <img
                src={user.image}
                className="w-10 h-10 rounded-full"
                alt="user-profile"
              />
              <p className="font-bold">{user.username}</p>
            </div>
          )}
          <input
            type="text"
            value={isPin?description:boardSummary}
            onChange={handleDescriptionChange}
            placeholder={`Tell everyone what your ${
              isPin ? "Pin" : "Board"
            } is about`}
            className="outline-none text-base sm:text-lg border-b-2 rounded border-gray-200 p-2"
          />
          {isPin && (
            <input
              type="url"
              vlaue={isPin?link:boardCoverImage}
              onChange={handleLinkChange}
              placeholder="Add a destination link"
              className="outline-none text-base sm:text-lg rounded border-b-2 border-gray-200 p-2"
            />
          )}

          <div className="flex flex-col">
            {isPin && (
              <div>
                <p className="mb-2 font-semibold text:lg sm:text-xl">
                  Choose Pin Category
                </p>
                <select
                  onChange={handleCategoryChange}
                  className="outline-none w-4/5 text-base border-b-2 border-gray-200 p-2 rounded-md cursor-pointer"
                >
                  <option value="others" className="sm:text-bg bg-white">
                    Select Category
                  </option>
                  {categories?.map((item) => (
                    <option
                      className="text-base rounded border-0 outline-none capitalize bg-white text-black "
                      value={item.name}
                    >
                      {item.name}
                    </option>
                  ))}
                </select>
                {(category==="others")?(<input
              type="url"
              vlaue={otherCategory}
              onChange={handleOtherCategoryChange}
              placeholder="e.g. Dance,Music"
              className="outline-none text-base sm:text-lg rounded border-b-2 border-gray-200 p-2"
            />):""}               
              </div>
            )}
            <select
                  onChange={handleBoardChange}
                  className="outline-none w-4/5 text-base border-b-2 border-gray-200 p-2 rounded-md cursor-pointer"
                >
                  <option value="others" className="sm:text-bg bg-white">
                    Select Board or Create new Board!
                  </option>
                  {boards?.map((board) => (
                    <option
                      className="text-base rounded border-0 outline-none capitalize bg-white text-black "
                      value={board.boardId}
                    >
                      {board.title}
                    </option>
                  ))}
            </select>
            <div className="flex justify-between mt-5 w-full">
              <button
                type="button"
                onClick={toggleForm}
                className="bg-red-500 text-white font-bold p-2 rounded-full outline-none"
              >
                Create a {isPin ? "Board" : "Pin"} instead
              </button>
              <button
                type="button"
                onClick={handleSubmit}
                className="bg-red-500 text-white  font-bold p-2 rounded-full w-28 outline-none"
              >
                Save {isPin ? "Pin" : "Board"}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CreatePin;
