import React from "react";
import { Scatter } from "react-chartjs-2";


const CorrelationCalculator = () => {
  const data = {
    datasets: [
      {
        label: "Correlation",
        data: [{ x: 1, y: 2 }, { x: 2, y: 4 }],
        backgroundColor: "rgba(75,192,192,0.6)",
      },
    ],
  };


  return (
    <div className="p-10">
      <h2 className="text-3xl font-bold">Correlation Calculator</h2>
      <Scatter data={data} />
    </div>
  );
};


export default CorrelationCalculator;



