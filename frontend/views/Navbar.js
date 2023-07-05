import React from 'react';

export default function Navbar() {
    return(
        <nav className="main-header navbar navbar-expand-md navbar-light navbar-white">
            <div className="container">
            <a href="#/home" className="navbar-brand">
                <span className="brand-text font-weight-light">SPPG</span>
            </a>
            <button className="navbar-toggler order-1" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse order-3" id="navbarCollapse">              
                <ul className="navbar-nav">
                <li className="nav-item">
                    <a href="#/programas" className="nav-link">Programas</a>
                </li>
                <li className="nav-item">
                    <a href="#/docentes" className="nav-link">Docentes</a>
                </li>
                <li className="nav-item">
                    <a href="#/producoes" className="nav-link">Produções</a>
                </li>
                <li className="nav-item">
                    <a href="#/tecnicas" className="nav-link">Tecnicas</a>
                </li>
                </ul>
            </div>
            </div>
        </nav>
    );
}