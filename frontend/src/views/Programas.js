import React from 'react';
import {useEffect} from 'react';
import {useState} from 'react';
import axios from 'axios';
import Navbar from './components/Navbar';
import Header from './components/Header';
import Filtros from './components/Filtros';
import Indicadores from './components/Indicadores';
import GraficoProducao from './components/GraficoProducao';
import Tabela from './components/Tabela';

const client = axios.create({
    baseURL: "http://localhost:8080/api/programa/" 
});

export default function Home(){
    const [programas, setProgramas] = useState([])
    const [progSel, setProgSel] = useState(1);
    const [anoIni, setAnoIni] = useState(2019);
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

    function getFiltro(){
        client.get(`filtroPPG`).then((response) => { console.log(response.data)
            setProgramas(response.data)
           }).catch(error => {
               console.log(error.response)
           }); 
    }

    // eslint-disable-next-line
    useEffect( () => {
        document.body.classList.add('hold-transition', 'layout-top-nav');
        onSearch();
        getFiltro();
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
            <td> <a href={`#/docentes/${i.id}`}>Mais</a> </td>
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
                        <Filtros titulo="Programa:" itens={programas}
                                filtroItem={progSel} onItemChange={setProgSel} 
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
