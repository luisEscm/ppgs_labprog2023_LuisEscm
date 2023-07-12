import React, {useState, useContext} from 'react'
import {Route, Routes, HashRouter, Navigate} from 'react-router-dom'

import Docente from './views/Docente'
import Programas from './views/Programas'
import FormProducao from './views/FormProducao'
import Tecnica from './views/Tecnicas'
import AddProducao from './views/AddProducao'
import Login from './views/Login'
import AddTecnica from './views/AddTecnica'

import { AuthProvider, AuthContext } from './contexts/AuthContext'

function Rotas() {
    const Private = ({ children }) => {
        const { authenticated, carregando } = useContext(AuthContext)

        if(carregando){
            return <div className="loading">carregando...</div>
        }
        if(!authenticated){
            return <Navigate to="/home" />
        }

        return children
    }

    return (
        <HashRouter>
            <AuthProvider>
                <Routes>
                    <Route path="/home" element={<Login />} />
                    <Route path="/programas" element={<Private><Programas/></Private>} /> 
                    <Route path="/docentes/:id" element={<Private><Docente /></Private>} />
                    <Route path="/producoes" element={<Private><FormProducao /></Private>} />
                    <Route path="/Tecnicas" element={<Private><Tecnica /></Private>} />
                    <Route path="/newProducao/:id" element={<Private><AddProducao /></Private>} />
                    <Route path="/newTecnica/:id" element={<Private><AddTecnica /></Private>} />
                </Routes>
            </AuthProvider>
        </HashRouter>
    )
}

export default Rotas;