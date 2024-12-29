import React from "react";

const FollowerList = ({ followers, setShowList, title }) => {
  return (
    <div className="fixed top-0 left-0 w-full h-full flex items-center justify-center bg-gray-900 bg-opacity-50 z-50">
        <div className="max-w-md mx-auto bg-white rounded-lg shadow-md p-4">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-bold text-gray-800">{title}</h2>
        <button
          onClick={() => setShowList(null)}
          className="text-sm text-blue-500 hover:underline"
        >
          Back
        </button>
      </div>
      <ul>
        {followers.map((follower) => (
          <li
            key={follower.userId}
            className="flex items-center space-x-4 p-2 hover:bg-gray-100 rounded-md"
          >
            <img
              src={follower.image}
              alt={`${follower.username}'s avatar`}
              className="w-12 h-12 rounded-full object-cover"
            />
            <span className="text-gray-700 font-medium">
              {follower.username}
            </span>
          </li>
        ))}
      </ul>
    </div>
    </div>
  );
};

export default FollowerList;
