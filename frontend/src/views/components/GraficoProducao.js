import React from 'react'
//import { Chart } from "react-google-charts";
//import { render } from "react-dom";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Bar } from 'react-chartjs-2';


ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

const config = {
  responsive              : true,
  scales: {
    xAxes: [{}],
    yAxes: [{}]
  }
}

export default function GraficoProducao({ titulo, infoGraf }) {
  const dados = {
    labels  : infoGraf.anos,
    datasets: [
      {
        label               : 'A1',
        data                : infoGraf.qtvA1,
        backgroundColor     : '#4dc9f6'
      },
      {
        label               : 'A2',
        data                : infoGraf.qtvA2,
        backgroundColor     : '#f67019'
      },
      {
        label               : 'A3',
        data                : infoGraf.qtvA3,
        backgroundColor     : '#537bc4'
      },
      {
        label               : 'A4',
        data                : infoGraf.qtvA4,
        backgroundColor     : '#acc236'
      },
    ]
  }
  return (
      <div className="card card-gray">
          <div className="card-header">
              <h3 className="card-title">{ titulo }</h3>

              <div className="card-tools">
                  <button type="button" className="btn btn-tool" data-card-widget="collapse">
                  <i className="fas fa-minus"></i>
                  </button>
                  <button type="button" className="btn btn-tool" data-card-widget="remove">
                  <i className="fas fa-times"></i>
                  </button>
              </div>
          </div>
          <div className="card-body">
              <div className="chart">
                  <Bar options={config} data={dados} />
              </div>
          </div>
      </div>
  );

}






/*const data = [
  ["Ano", "A1", "A2", "A3", "A4"],
  ["2023", 9, 0, 12, 0],
  ["2022", 33, 8, 26, 30],
  ["2021", 30, 13, 24, 49],
  ["2020", 26, 17, 46, 25],
  ["2019", 17, 6, 20, 55],
];

const options = {
  chartArea: { width: "50%" },
  isStacked: true,
  hAxis: {
    title: "Qualis",
    minValue: 0,
  },
  vAxis: {
    title: "Ano",
  },
};

export default function GraficoProducao(){
      return (
        <div className="card card-gray">
            <div className="card-header">
            <h3 className="card-title">Produção vs Qualis</h3>
            <div className="card-tools">
                <button type="button" className="btn btn-tool" data-card-widget="collapse">
                <i className="fas fa-minus" />
                </button>
                <button type="button" className="btn btn-tool" data-card-widget="remove">
                <i className="fas fa-times" />
                </button>
            </div>
            </div>
            <div className="card-body">
            <div className="chart">
                <GraficoDesenho />
            </div>
            </div>
            {/* /.card-body *}
        </div>
  
    );
}

function GraficoDesenho(){
    return(
      <Chart
        chartType="ColumnChart"
        width="100%"
        height="400px"
        data={data}
        options={options}
          />
    );
  }*/