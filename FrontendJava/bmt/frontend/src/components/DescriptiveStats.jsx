import React, { useState } from "react";
import axios from "axios";


const DescriptiveStats = () => {
  const [stats, setStats] = useState({});


  const fetchStats = async () => {
    const response = await axios.get("/api/stats");
    setStats(response.data);
  };


  return (
    <div className="p-10">
      <h2 className="text-3xl font-bold">Descriptive Statistics</h2>
      <button onClick={fetchStats} className="mt-4 px-4 py-2 bg-blue-600 text-white rounded">
        Load Statistics
      </button>
      <div className="mt-6">
        {Object.entries(stats).map(([key, value]) => (
          <p key={key}>
            <strong>{key}:</strong> {value}
          </p>
        ))}
      </div>
    </div>
  );
};


export default DescriptiveStats;



