import React from 'react';

const RequestModal = ({ requests, closeModal, handleConfirm,handleDelete }) => {
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-white p-8 rounded-lg">
        <h2 className="text-2xl font-bold mb-4">Requests</h2>
        <div className="grid grid-cols-1 gap-4">
          {requests?.map((request) => (
            <div key={request.userId} className="flex items-center justify-between border-b py-2">
              <img src={request.image} alt="Profile" className="w-10 h-10 rounded-full" />
              <p className="ml-4">{request.username}</p>
              <button
                onClick={() => handleConfirm(request.userId)}
                className="px-4 py-2 bg-blue-500 text-white rounded-md"
              >
                Confirm
              </button>
              <button
                onClick={() => handleDelete(request.userId)}
                className="px-4 py-2 bg-blue-500 text-white rounded-md"
              >
                Delete
              </button>
            </div>
          ))}
        </div>
        <button onClick={closeModal} className="mt-4 px-4 py-2 bg-gray-300 rounded-md">
          Close
        </button>
      </div>
    </div>
  );
};

export default RequestModal;
