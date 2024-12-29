import React, { useState } from "react";
import { Routes, Route } from "react-router-dom";

import { Navbar, Feed, PinDetail, CreatePin, Search } from "../components";
import BoardDetail from "../components/BoardDetail";

const Pins = ({ user }) => {
  const [searchTerm, setSearchTerm] = useState("");
  console.log(user)
  return (
    <div>
      <div className="p-2 bg-gray-50 dark:bg-gray-900 shadow ">
        <Navbar
          searchTerm={searchTerm}
          setSearchTerm={setSearchTerm}
          user={user && user}
        />
      </div>
      <div className="h-full">
        <Routes>
          <Route path="/" element={<Feed />} />
          <Route path="/category/:categoryId" element={<Feed />} />
          <Route
            path="/pin-detail/:pinId"
            element={<PinDetail user={user} />}
          />
          <Route
            path="/board-detail/:boardId"
            element={<BoardDetail user={user} />}
          />
          <Route
            path="/create-pin"
            element={<CreatePin user={user} />}
          />
          <Route
            path="/search"
            element={
              <Search searchTerm={searchTerm} setSearchTerm={setSearchTerm} />
            }
          />
        </Routes>
      </div>
    </div>
  );
};

export default Pins;
