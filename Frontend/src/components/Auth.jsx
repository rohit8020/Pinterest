import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Auth = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [username, setUsername] = useState("");
  const [isLogin,setIsLogin] = useState(true);
  const [confirmPassword, setConfirmPassword] = useState("");
  const [image, setImage] = useState("");
  const [error, setError] = useState(false);
  const [success,setSuccess]=useState(false);
  const [message, setMessage] = useState('');
  const navigate=useNavigate();

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handleConfirmPasswordChange = (e) => {
    setConfirmPassword(e.target.value);
  };

  const handleSwitchChange=(e)=>{
    e.preventDefault()
    setIsLogin(!isLogin)
}

  const handlePasteFromClipboard = async () => {
    try {
      const clipboardData = await navigator.clipboard.readText();
      setImage(clipboardData);
    } catch (error) {
      console.log(error.message);
    }
  };

  const handleClick = async () => {
    const url=(isLogin)?"http://localhost:2000/auth/token":"http://localhost:2000/auth/register"
    const body=(isLogin)?({email,password}):({email,password,username,image})
    try {
      const response = await axios.post(url, body);
      setSuccess(true)
      setError(false)
      if(isLogin){
        const token = response?.data;
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        const user=await axios.get("http://localhost:2000/auth/userbyemail?email="+email)
        localStorage.setItem("user",JSON.stringify(user.data))
        localStorage.setItem("token",token)
        navigate("/home")
        setMessage("Logged In Successfully!")
      }else{
        setMessage(response?.data)
        setIsLogin(!isLogin)
      }
    } catch (error) {
      setError(true);
      setMessage(error.response?.data)
    }
  };

  

  return (
    <div className="flex justify-center items-center h-screen">
      
      <form className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 sm:w-80 lg:w-1/3">
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
        {!(isLogin) && ((image === "") ? (
          <div className="flex justify-center items-center">
            <label
              className="block text-gray-700 text-sm font-bold mb-2"
              htmlFor="image2"
            >
              Paste Image Url
            </label>
            <img
              className="h-8 w-8 border-solid border-4 ml-2 hover:border-dotted hover:cursor-pointer"
              name="image2"
              src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnZD1bHuy8k0uOSnWuD7ewK7xP_7nkl5SyZUofKQQzMQ&s"
              alt="Url Not Correct!"
              onClick={handlePasteFromClipboard}
            />
          </div>
        ) : (
          <div className="flex justify-center items-center h-16">
            <img
              className="rounded-full h-full w-16"
              src={image}
              alt="Url Not Correct!"
            />
          </div>
        ))}
        {!(isLogin) && (<div className="mb-4">
          <label
            className="block text-gray-700 text-sm font-bold mb-2"
            htmlFor="email"
          >
            Username
          </label>
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="username"
            type="text"
            placeholder="Username e.g. abcd1234"
            value={username}
            onChange={handleUsernameChange}
          />
        </div>)}
        <div className="mb-4">
          <label
            className="block text-gray-700 text-sm font-bold mb-2"
            htmlFor="email"
          >
            Email
          </label>
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="email"
            type="email"
            placeholder="Email e.g. abc@xyz.com"
            value={email}
            onChange={handleEmailChange}
          />
        </div>
        <div className="mb-4">
          <label
            className="block text-gray-700 text-sm font-bold mb-2"
            htmlFor="password"
          >
            Password
          </label>
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="password"
            type="password"
            placeholder="Password"
            value={password}
            onChange={handlePasswordChange}
          />
        </div>
        {!(isLogin) && (<div className="mb-6">
          <label
            className="block text-gray-700 text-sm font-bold mb-2"
            htmlFor="confirm-password"
          >
            Confirm Password
          </label>
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="confirm-password"
            type="password"
            placeholder="Confirm Password"
            value={confirmPassword}
            onChange={handleConfirmPasswordChange}
          />
        </div>)}
        <div className="flex items-center justify-between">
          <button
            className="bg-red-600 hover:bg-red-800 text-white w-full font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            type="button"
            onClick={handleClick}
          >
            {(isLogin)?"Log In":"Sign Up"}
          </button>
        </div>
        <div className="mt-4 text-center">
          <button
            onClick={handleSwitchChange}
            className="text-indigo-600 hover:underline focus:outline-none"
          >
            {(isLogin) ? 'Create an account' : 'Already have an account?'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default Auth;
