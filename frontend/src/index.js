import React from 'react';
import ReactDOM from 'react-dom/client';
import Programas from './views/Programas';
import reportWebVitals from './reportWebVitals';
//import Docente from './views/Docente';
//import FormProducao from './views/FormProducao';
//import Login from './views/Login';
//import Programa from "./prof/Programa";
import Rotas from './Rotas';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Rotas />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
