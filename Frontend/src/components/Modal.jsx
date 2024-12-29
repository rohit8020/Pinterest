import React, { useState } from 'react';

const Modal = () => {
  const [isLogin, setIsLogin] = useState(true);

  const toggleForm = () => {
    setIsLogin(!isLogin);
  };

  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <div className="w-96 p-8 bg-white shadow-md rounded-lg">
        {isLogin ? (
          <h2 className="text-2xl font-bold mb-4">Login</h2>
        ) : (
          <h2 className="text-2xl font-bold mb-4">Sign Up</h2>
        )}
        <form className="space-y-4">
          <div>
            <label htmlFor="email" className="block text-sm font-medium text-gray-700">
              Email
            </label>
            <input
              type="email"
              id="email"
              name="email"
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
            />
          </div>
          <div>
            <label htmlFor="password" className="block text-sm font-medium text-gray-700">
              Password
            </label>
            <input
              type="password"
              id="password"
              name="password"
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
            />
          </div>
          {!isLogin && (
            <div>
              <label htmlFor="confirmPassword" className="block text-sm font-medium text-gray-700">
                Confirm Password
              </label>
              <input
                type="password"
                id="confirmPassword"
                name="confirmPassword"
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
              />
            </div>
          )}
          <div>
            <button
              type="submit"
              className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
            >
              {isLogin ? 'Login' : 'Sign Up'}
            </button>
          </div>
        </form>
        <div className="mt-4 text-center">
          <button
            onClick={toggleForm}
            className="text-indigo-600 hover:underline focus:outline-none"
          >
            {isLogin ? 'Create an account' : 'Already have an account?'}
          </button>
        </div>
      </div>
    </div>
  );
};

export default Modal;
