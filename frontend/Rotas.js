import React from 'react'
import {Route, Routes, HashRouter} from 'react-router-dom'

import Docente from './views/Docente'
import Programas from './views/Programas'
import FormProducao from './views/FormProducao'

function Rotas() {
    return (
        <HashRouter>
            <Routes>
                <Route path="/programas" element={<Programas/>} /> 
                <Route path="/docentes" element={<Docente />} />
                <Route path="/producoes" element={<FormProducao />} />
            </Routes>
        </HashRouter>
    )
}

export default Rotas;