import React, { useState } from 'react';
import { Link } from 'react-router-dom';
const BoardInvitationModal = ({ boardInvitations, closeModal, acceptInvitation, rejectInvitation }) => {
  
  
  return (
    <>
    (<div className="fixed top-0 left-0 w-full h-full flex items-center justify-center bg-gray-900 bg-opacity-50 z-50">
    <div className="bg-white p-4 md:p-8 rounded-lg shadow-lg max-w-md w-full md:max-w-lg lg:max-w-xl overflow-y-auto">
      <h2 className="text-2xl font-bold mb-4">Board Invitations</h2>
      {boardInvitations?.map((invitation) => (
        <div key={Math.random()} className="mb-4">
          <h3 className="text-xl font-bold mb-2">{invitation.boardName}</h3>
          <p className="text-gray-600 mb-2">Invited by: <Link to={`/user-profile/${invitation.inviterId}`} className="text-blue-500 cursor-pointer">{invitation.inviter}</Link></p>
          <p className="text-gray-700 mb-4 max-h-40 overflow-y-auto">{invitation.description}</p>
          <button onClick={() => acceptInvitation(invitation.boardId,invitation.inviter)} className="px-4 py-2 bg-blue-500 text-white rounded-md">Accept Invitation</button>
          <button onClick={() => rejectInvitation(invitation.boardId,invitation.inviter)} className="px-4 py-2 bg-blue-500 text-white rounded-md">Reject Invitation</button>
        </div>
      ))}
      <button onClick={closeModal} className="mt-4 px-4 py-2 bg-gray-300 rounded-md">Close</button>
    </div>
  </div>) 
    </>
  );
};

export default BoardInvitationModal;
