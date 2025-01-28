import React from "react";


const Login = () => {
  return (
    <div className="max-w-md mx-auto p-10">
      <h2 className="text-2xl font-bold">Log In / Sign Up</h2>
      <form className="mt-4">
        <div className="mb-4">
          <label className="block mb-2">Email:</label>
          <input type="email" className="w-full border px-3 py-2 rounded" />
        </div>
        <div className="mb-4">
          <label className="block mb-2">Password:</label>
          <input type="password" className="w-full border px-3 py-2 rounded" />
        </div>
        <button className="px-4 py-2 bg-blue-600 text-white rounded">
          Log In
        </button>
      </form>
    </div>
  );
};


export default Login;



