import React from "react";
import { Link } from "react-router-dom";


const Home = () => {
  return (
    <div className="p-6 text-center">
      <h1 className="text-3xl font-bold mb-6">Welcome to Movie Analytics</h1>
      <div className="space-y-4">
        <Link to="/movies-library" className="text-blue-500">
          Explore Movies Library
        </Link>
        <br />
        <Link to="/imdb-ratings" className="text-blue-500">
          Explore IMDB Ratings
        </Link>
        <br />
        <Link to="/movies-revenues" className="text-blue-500">
          Explore Movies Revenues
        </Link>
      </div>
    </div>
  );
};


export default Home;



