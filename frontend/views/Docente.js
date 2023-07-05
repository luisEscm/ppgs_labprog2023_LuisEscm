import React from 'react';
import {useEffect} from 'react';
import {useState} from 'react';
import axios from 'axios';
import Navbar from './Navbar';
import Header from './Header';
import FiltrosDocente from './FiltrosDocente';
import Indicadores from './Indicadores';
import GraficoProducao from './GraficoProducao';
import Tabela from './Tabela';

export default function Docente({ nome }){
    const [anoIni, setAnoIni] = useState(1990);
    const [anoFim, setAnoFim] = useState(2023);

    const [indicadores, setIndicadores] = useState({});
    const [graficoPeriodicos, setGraficoPeri] = useState({});
    const [graficoCongresso, setGraficoCong] = useState({});
    const [orientacoes, setOrientacoes] = useState([{}]);
    const [artigos, setArtigos] = useState([{}]);
    const [tecnicas, setTecnicas] = useState([{}]);


    function onSearch() {

    }

    // eslint-disable-next-line
    useEffect( () => {
        document.body.classList.add('hold-transition', 'layout-top-nav');
        onSearch();
        }, []
    )

    const linhasOrientacoes = orientacoes.map( (i, key) =>
    (
        <tr key={key}>
            <td>{i.discente}</td>
            <td>{i.titulo}</td>
            <td>{i.tipo}</td>
            <td>{i.ano}</td>
        </tr>
    ));

    const categoriasOrientacoes = (
    <tr>
        <th>Discente</th>
        <th>Título</th>
        <th>Tipo</th>
        <th>Ano</th>
    </tr>
    );

    const linhasArtigos = artigos.map( (i, key) =>
    (
        <tr key={key}>
            <td>{i.titulo}</td>
            <td>{i.local}</td>
            <td>{i.tipo}</td>
            <td>{i.qualis}</td>
            <tc>{i.ano}</tc>
        </tr>
    ));

    const categoriasArtigos = (
    <tr>
        <th>Título</th>
        <th>Local</th>
        <th>Tipo</th>
        <th>Qualis</th>
        <th>Ano</th>
    </tr>
    );

    const linhasTecnicas = tecnicas.map( (i, key) =>
    (
        <tr key={key}>
            <td>{i.titulo}</td>
            <td>{i.tipo}</td>
            <td>{i.ano}</td>
        </tr>
    ));

    const categoriasTecnicas = (
    <tr>
        <th>Título</th>
        <th>Tipo</th>
        <th>Ano</th>
    </tr>
    );
    
    return(
        <div className="wrapper">
            <Navbar />
            <div className="content-wrapper">
                <Header titulo= { nome } />
                {/*<!-- Main content -->*/}
                <div className="content">      
                <div className="container">
                    <div className="container-fluid">
                        <FiltrosDocente filtroAnoIni={anoIni} onAnoIniChange={setAnoIni}
                                filtroAnoFim={anoFim} onAnoFimChange={setAnoFim}
                                onSearch={onSearch}/>
                        <Indicadores dados={indicadores}/>
                        <GraficoProducao titulo="Produção em Periódicos vs Qualis" infoGraf={ graficoPeriodicos } />
                        <GraficoProducao titulo="Produção em Congressos vs Qualis" infoGraf={ graficoCongresso } />
                        <Tabela categorias={ categoriasOrientacoes } tabela={ linhasOrientacoes } titulo="Orientações" />
                        <Tabela categorias={ categoriasArtigos } tabela={ linhasArtigos } titulo="Artigos" />
                        <Tabela categorias={ categoriasTecnicas } tabela={ linhasTecnicas } titulo="Técnicas" />
                    </div>
                </div>
                </div>
            </div>
    </div>

    );
}