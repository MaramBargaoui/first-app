import React from "react";


const IMDBRatings = () => {
  const ratingsData = [
    { label: "Average Ratings", description: "Visualize average IMDB ratings" },
    { label: "Number of Votes", description: "Explore ratings by number of votes" },
    { label: "Movies Ratings", description: "Discover ratings for each movie" },
  ];


  return (
    <div className="p-6 bg-white shadow-md rounded-lg">
      <h2 className="text-2xl font-semibold text-center mb-4">
        Consult IMDB Ratings
      </h2>
      <div className="grid grid-cols-3 gap-4">
        {ratingsData.map((item, index) => (
          <div
            key={index}
            className="p-4 bg-gray-100 text-center rounded shadow-sm"
          >
            <h3 className="text-lg font-bold text-indigo-600">{item.label}</h3>
            <p className="text-gray-600">{item.description}</p>
          </div>
        ))}
      </div>
      <div className="mt-6">
        <img
          src="https://source.unsplash.com/featured/?ratings"
          alt="Ratings Visualization"
          className="w-full rounded-md shadow-md"
        />
      </div>
    </div>
  );
};


export default IMDBRatings;

