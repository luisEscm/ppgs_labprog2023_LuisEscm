import React from 'react';
import {useEffect} from 'react';
import {useState} from 'react';
import {useParams} from 'react-router-dom';
import axios from 'axios';
import Navbar from './components/Navbar';
import Header from './components/Header';
import FiltrosDocente from './components/Filtros';
import Indicadores from './components/Indicadores';
import GraficoProducao from './components/GraficoProducao';
import Tabela from './components/Tabela';

const client = axios.create({
    baseURL: "http://localhost:8080/api/docente/"
});

const urlPag = 'http://localhost:3000/#/docentes/'

export default function Docente(){
    const { id } = useParams();
    const [docIds, setDocIds] = useState([]);
    const [selDocId, setSelDocId] = useState(Number.parseInt(id, 10));
    const [anoIni, setAnoIni] = useState(2019);
    const [anoFim, setAnoFim] = useState(2023);

    const [indicadores, setIndicadores] = useState({});
    const [graficoPeriodicos, setGraficoPeri] = useState({});
    const [graficoCongresso, setGraficoCong] = useState({});
    const [orientacoes, setOrientacoes] = useState([]);
    const [artigos, setArtigos] = useState([]);
    const [tecnicas, setTecnicas] = useState([]);
    const [docNome, setDocNome] = useState();


    function onSearch() {
        client.get(`indicadores/${selDocId}/${anoIni}/${anoFim}`).then((response) => { console.log(response.data)
                setIndicadores(response.data)}).catch(error => {
                    console.log(error.repsonse)
                });
        client.get(`docenteBase/${selDocId}`).then((response) => { console.log(response.data)
                    setDocNome(response.data.nome)
                   }).catch(error => {
                       console.log(error.response)
                   });
        client.get(`graficoPeriodico/${selDocId}/${anoIni}/${anoFim}`).then((response) => { console.log(response.data)
                    setGraficoPeri(response.data)
                   }).catch(error => {
                       console.log(error.response)
                   });
        client.get(`graficoCongresso/${selDocId}/${anoIni}/${anoFim}`).then((response) => { console.log(response.data)
                    setGraficoCong(response.data)
                   }).catch(error => {
                       console.log(error.response)
                   });
        client.get(`orientacoesDocente/${selDocId}/${anoIni}/${anoFim}`).then((response) => { console.log(response.data)
                    setOrientacoes(response.data)
                   }).catch(error => {
                       console.log(error.response)
                   });
        client.get(`tecnicasDocente/${selDocId}/${anoIni}/${anoFim}`).then((response) => { console.log(response.data)
                    setTecnicas(response.data)
                   }).catch(error => {
                       console.log(error.response)
                   });
        client.get(`producoesDocente/${selDocId}/${anoIni}/${anoFim}`).then((response) => { console.log(response.data)
                    setArtigos(response.data)
                   }).catch(error => {
                       console.log(error.response)
                   });
        updateUrl(`${urlPag}${selDocId}`)
    }

    function getFiltro() {
        client.get(`docentesFiltro`).then((response) => { console.log(response.data)
            setDocIds(response.data)
           }).catch(error => {
               console.log(error.response)
           });
    }

    function updateUrl(newUrl) {
        window.location.href = newUrl;
    }

    // eslint-disable-next-line
    useEffect( () => {
        document.body.classList.add('hold-transition', 'layout-top-nav');
        getFiltro();
        
        if(!((selDocId === 0) || (isNaN(selDocId)))){
            onSearch();
        }else{
            updateUrl(`${urlPag}1`);
            window.location.reload();
        }
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
            <tc>{`${i.ano}`}</tc>
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
                <Header titulo={`Docente:${docNome}`}  />
                {/*<!-- Main content -->*/}
                <div className="content">      
                <div className="container">
                    <div className="container-fluid">
                        <FiltrosDocente titulo="Docente:" itens={docIds}
                                filtroItem={selDocId} onItemChange={setSelDocId}
                                filtroAnoIni={anoIni} onAnoIniChange={setAnoIni}
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