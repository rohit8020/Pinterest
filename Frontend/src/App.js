import React, { useEffect, useState } from "react";
import { Routes, Route,useNavigate } from "react-router-dom";
import { Auth } from "./components";
import Home from "./container/Home";
import RequestModal from "./components/RequestsModal";
import ModalForm from "./components/ModalForm";

const App = () => {

  const navigate=useNavigate()
  useEffect(() => {
    const User =
      localStorage.getItem("user") !== "undefined"
        ? JSON.parse(localStorage.getItem("user"))
        : localStorage.clear();

    if(!User){
      navigate("/auth")
    }
  }, []);

  return (
    <Routes>
      <Route path="auth" element={<Auth/>}/>
      <Route path="/*" element={<Home />} />
    </Routes>
  );
};

export default App;
