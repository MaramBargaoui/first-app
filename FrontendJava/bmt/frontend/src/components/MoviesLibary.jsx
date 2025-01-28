import React, { useState } from "react";
import axios from "axios";


const MoviesLibrary = () => {
  const [movies, setMovies] = useState([]);


  const fetchMovies = async () => {
    const response = await axios.get("/api/movies");
    setMovies(response.data);
  };


  return (
    <div className="p-10">
      <h2 className="text-3xl font-bold">Browse Movie Library</h2>
      <button onClick={fetchMovies} className="mt-4 px-4 py-2 bg-green-600 text-white rounded">
        Load Movies
      </button>
      <ul className="mt-6">
        {movies.map((movie) => (
          <li key={movie.id} className="mb-2">
            {movie.title} ({movie.releaseYear})
          </li>
        ))}
      </ul>
    </div>
  );
};


export default MoviesLibrary;



