import axios from 'axios';
import React, { useState } from 'react';

const ModalForm = ({ isOpen, onClose, token,boardId }) => {
  const [invitee, setInvitee] = useState('');
  const [description, setDescription] = useState('');

  const handleSubmit=(e)=>{
    e.preventDefault()
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    const body={
        boardId:Number(boardId&&boardId),
        invitee,
        description
    }
    console.log(body)
    axios.post(`http://localhost:2000/collab/invite/create`,body)
    .then((res)=>{
      console.log(res.data)
      return null;
    })
    .catch((err)=>{
      console.log(err.message)
    })
  }


  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center overflow-x-hidden overflow-y-auto outline-none focus:outline-none">
      <div className="absolute inset-0 bg-black opacity-50 blur"></div>
      <div className="relative w-auto max-w-3xl mx-auto my-6">
      <div className="border-0 rounded-lg shadow-lg relative flex flex-col w-full bg-white outline-none focus:outline-none">
          <div className="flex items-start justify-between p-5 border-b border-solid border-blueGray-200 rounded-t">
            <h3 className="text-3xl font-semibold">
              Modal Form
            </h3>
            <button
              className="p-1 ml-auto bg-transparent border-0 text-black opacity-5 float-right text-3xl leading-none font-semibold outline-none focus:outline-none"
              onClick={onClose}
            >
              <span className="text-3xl block">×</span>
            </button>
          </div>
          <div className="relative p-6 flex-auto">
            <form>
              <div className="mb-4">
                <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="invitee">
                  Invitee
                </label>
                <input
                  className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  id="invitee"
                  type="text"
                  placeholder="e.g. abc@xyz.com"
                  value={invitee}
                  onChange={(e) => setInvitee(e.target.value)}
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="description">
                  Description
                </label>
                <input
                  className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  id="description"
                  type="text"
                  placeholder="Enter description..."
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                />
              </div>
            </form>
          </div>
          <div className="flex items-center justify-end p-6 border-t border-solid border-blueGray-200 rounded-b">
            <button
              className="text-red-500 background-transparent font-bold uppercase px-6 py-2 text-sm outline-none focus:outline-none mr-1 mb-1"
              type="button"
              onClick={onClose}
            >
              Close
            </button>
            <button
              className="bg-green-500 text-white active:bg-green-600 font-bold uppercase text-sm px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1"
              type="button"
              onClick={handleSubmit}
            >
              Send invite!
            </button>
          </div>
        </div>
      </div>
    </div>   
  );
};

export default ModalForm;
