import React from "react";


const MoviesRevenues = () => {
  const revenueData = [
    { label: "Budget", value: "100M" },
    { label: "Net Revenue", value: "50M" },
    { label: "Profit", value: "25M" },
  ];


  return (
    <div className="p-6 bg-white shadow-md rounded-lg">
      <h2 className="text-2xl font-semibold text-center mb-4">
        Discover Movie Revenues
      </h2>
      <div className="grid grid-cols-3 gap-4">
        {revenueData.map((item, index) => (
          <div
            key={index}
            className="p-4 bg-gray-100 text-center rounded shadow-sm"
          >
            <h3 className="text-xl font-bold text-indigo-600">{item.value}</h3>
            <p className="text-gray-600">{item.label}</p>
          </div>
        ))}
      </div>
      <div className="mt-6">
        <img
          src="https://source.unsplash.com/featured/?chart"
          alt="Revenue Chart"
          className="w-full rounded-md shadow-md"
        />
      </div>
    </div>
  );
};


export default MoviesRevenues;



