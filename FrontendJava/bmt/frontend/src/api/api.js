import React, { useEffect, useState } from "react";
import { fetchMoviesLibrary } from "./api";


const MoviesLibrary = () => {
  const [movies, setMovies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);


  useEffect(() => {
    const loadMovies = async () => {
      try {
        const data = await fetchMoviesLibrary();
        setMovies(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };


    loadMovies();
  }, []);


  if (loading) return <p>Loading movies...</p>;
  if (error) return <p>Error: {error}</p>;


  return (
    <div>
      <h1>Movies Library</h1>
      <ul>
        {movies.map((movie) => (
          <li key={movie.id}>{movie.title}</li>
        ))}
      </ul>
    </div>
  );
};

