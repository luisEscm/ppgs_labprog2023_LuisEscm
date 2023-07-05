import React from 'react';
import {useEffect} from 'react';
import {useState} from 'react';
import axios from 'axios';
import Navbar from './Navbar';
import Header from './Header';
import Filtros from './Filtros';
import Indicadores from './Indicadores';
import GraficoProducao from './GraficoProducao';
import Tabela from './Tabela';

const programas = [
    {id:1, nome:"PPGCC"},
    {id:2, nome:"DCCMAPI"},
]

const client = axios.create({
    baseURL: "http://localhost:8080/api/programa/" 
});

export default function Home(){
    const [progSel, setProgSel] = useState(1);
    const [anoIni, setAnoIni] = useState(1990);
    const [anoFim, setAnoFim] = useState(2023);
    
    const [indicadores, setIndicadores] = useState({});
    const [grafico, setGrafico] = useState({});
    const [docentes, setDocentes] = useState([{}]);

    function onSearch() {
        client.get(`indicadores?idPrograma=${progSel}&anoInicial=${anoIni}&anoFinal=${anoFim}`)
            .then(                
                (response) => {
                    console.log(response.data)
                    setIndicadores(response.data)
                }
            ).catch(error => {
                console.log(error.response);
            });
        client.get(`grafico?idPrograma=${progSel}&anoInicial=${anoIni}&anoFinal=${anoFim}`)
            .then(
                (response) => {
                    console.log(response.data)
                    setGrafico(response.data)
                }
            ).catch(error => {
                console.log(error.response);
            });
        client.get(`obterDocentesPrograma?idPrograma=${progSel}`)
            .then(
                (response) => {
                    console.log(response.data)
                    setDocentes(response.data)
                }
            ).catch(error => {
                console.log(error.response);
            });
    }

    // eslint-disable-next-line
    useEffect( () => {
        document.body.classList.add('hold-transition', 'layout-top-nav');
        onSearch();
        }, []
    )

    const linhas = docentes.map( (i, key) =>
    (
        <tr key={key}>
            <td>{i.nome}</td>
            <td>{i.a1}</td>
            <td>{i.a2}</td>
            <td>{i.a3}</td>
            <td>{i.a4}</td>
            <td>{i.b1}</td>
            <td>{i.b2}</td>
            <td>{i.b3}</td>
            <td>{i.b4}</td>
            <td> <a href="docente.html">Mais</a> </td>
        </tr>
    ));

    const categorias = (
    <tr>
        <td>Nome</td>
        <td>A1</td>
        <td>A2</td>
        <td>A3</td>
        <td>A4</td>
        <td>B1</td>
        <td>B2</td>
        <td>B3</td>
        <td>B4</td>
        <td>Detalhar</td>
    </tr>
    );

    return (
        <div className="wrapper">
            <Navbar />
            <div className="content-wrapper">
                <Header titulo="Programa" />
                {/*<!-- Main content -->*/}
                <div className="content">      
                <div className="container">
                    <div className="container-fluid">
                        <Filtros programas={programas}
                                filtroProg={progSel} onProgChange={setProgSel} 
                                filtroAnoIni={anoIni} onAnoIniChange={setAnoIni}
                                filtroAnoFim={anoFim} onAnoFimChange={setAnoFim}
                                onSearch={onSearch}/>
                        <Indicadores dados={indicadores}/>
                        <GraficoProducao titulo="Produção vs Qualis" infoGraf={ grafico } />
                        <Tabela categorias={ categorias } tabela={ linhas } titulo="Docentes" />
                    </div>
                </div>
                </div>
            </div>
        </div>

    );
}



/*
docentes
11 tem as produções 7, 8, 9
12 tem as produções 7, 9
13 tem as produções 10, 11

14 tem as produções 10, 12
22 tem as produções 22, 24
32 tem as produções 37, 39
10 tem as produções 33

program 1
5 produções
igeral 4,425
irestrito 4,425 2A1 2A2 1A3
inãorestrito 0

programa 2
7 produções
igeral 5,9
irestrito 5,9 3A1 0A2 4A3
inãorestrito 0
*/