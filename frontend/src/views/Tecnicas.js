import React from 'react';
import {useEffect} from 'react';
import {useState} from 'react';
import {useRef} from 'react';
import axios from 'axios';
import Loading from './components/Loading';
import Navbar from './components/Navbar';
import Header from './components/Header';
import Filtro from './components/FiltroForm';
import Tabela from './components/TabelaForm';
import AddEstat from './components/AddEstatisticasForm';
import AddOrien from './components/AddOrientacoesForm';
import { useNavigate } from 'react-router-dom';
import Alertsucces from './components/AlertSucces';
import ErrorAlert from './components/ErrorAlert';

const client = axios.create({
    baseURL: "http://localhost:8080/api/docente/" 
});

export default function FormProducao(){
    const navigate = useNavigate();

    const [atualizando, setAtualizando] = useState(true);
    const [docentes, setDocentes] = useState([])
    const [oriePEnviar, setOriePEnviar] = useState([])
    const [orientacoes, setOrientacoes] = useState([])
    const [filtroId, setFiltroId] = useState(0); //id de pesquisa
    const [tecn, setTecn] = useState([]);
    const [tecnEscolhida, setTecnEscolhida] = useState(1)
    const tabelaFormRef = useRef(null);

    const [avisoSucces, setAvisoSucces] = useState(false)
    const [avisoError, setAvisoError] = useState(false)
    const [errorMensage, setErrorMensage] = useState("")

    const goTo = (e) => {tabelaFormRef.current.vaPara(e)}

    async function onSearch(){
        setAtualizando(true);
        atualizacao();
    }

    function atualizacao() {
        setTimeout(() =>
        {client.get(`tecnicas/${filtroId}`)
                .then(
                    (response) => {
                        console.log(response.data)
                        setTecn(response.data)
                        setAtualizando(false);
                        goTo(1)
                    }
                ).catch(error => {
                    console.log(error.response);
                });
        }, 1000,);
    }
    
    const linhas = tecn.map( (i, key) =>
    (
        <tr key={key}>
            <td>{i.ano}</td>
            <td>{i.docente}</td>
            <td><a href={`#/newTecnica/${i.id}`}>{i.titulo}</a></td>
            <td>{i.financiadora}</td>
            <td>{i.orientacao}</td>
            <td>{i.gmd}</td>
            <td>
                <a href="#addOrientacao" className="edit" data-toggle="modal"><i className="material-icons" data-toggle="tooltip" title="Orientações" onClick={() => {setTecnEscolhida(i.id)}}>Orientações</i></a>
                <label> | </label>
                <a href="#addEstatisticas" className="data" data-toggle="modal"><i className="material-icons" data-toggle="tooltip" title="Estatísticas" onClick={() => (setTecnEscolhida(i.id))}>Estatisticas</i></a>
            </td>
        </tr>
    ));

    const categorias = (
    <tr>
        <th>Ano</th>
        <th>Docente</th>                        						
        <th>Título</th>
        <th>Financiadora</th>	
        <th>Orientação?</th>	
        <th>Estatísticas</th>
        <th>Adicioanar</th>
    </tr>
    );

    function getFiltro() {
        client.get(`docentesProducaoFiltro`)
        .then(
            (response) => {
                console.log(response.data)
                setDocentes(response.data)
            }
        ).catch(error => {
            console.log(error.response);
        });
    }

    function enviarOri(){
        client.put(`addOrientacoesTecn`, {ids: oriePEnviar, idProd: tecnEscolhida})
                .then(
                    (response) => {
                        console.log(response.data)
                        setAvisoSucces(true)
                    }
                ).catch(error => {
                    console.log(error.response);
                    setAvisoError(true)
                    setErrorMensage("Ouve um erro ao enviar as orientações")
                })
    }

    function enviarEstat(grad, mestrado, doutorado){
        client.put(`addEstatisticasTecn`, {qtdGrad: grad,qtdMestrado: mestrado,qtdDoutorado: doutorado,idProd: tecnEscolhida})
                .then(
                    (response) => {
                        console.log(response.data)
                        setAvisoSucces(true)
                    }
                ).catch(error => {
                    console.log(error.response)
                    setAvisoError(true)
                    setErrorMensage("Ouve um erro ao enviar as estátiscas")
                })
    }

    function getOrientacoes(){
        client.get(`orientacoesProducao`)
                .then(
                    (response) => {
                        console.log(response.data)
                        setOrientacoes(response.data)
                    }
                ).catch(error => {
                    console.log(error.response);
                });
    }

    useEffect( () => {
        document.body.classList.add('hold-transition', 'layout-top-nav');
        getOrientacoes();
        onSearch();
        getFiltro();
        }, []
    )

    return(
        <div className="wrapper">
            <Navbar />
            <div className="content-wrapper">
                <Header titulo="Gerenciar dados de Tecnicas" />
                {/*<!-- Main content -->*/}
                <div className="content">      
                <div className="container">
                    <div className="container-fluid">
                        <Alertsucces visivel={avisoSucces} setVisivel={setAvisoSucces} mensagem="Enviado" />
                        <ErrorAlert visivel={avisoError} setVisivel={setAvisoError} mensagem={errorMensage} />
                        <Filtro docentes={ docentes } filtroProd={ filtroId } onProdChange={ setFiltroId } onSearch={ onSearch } />
                        {atualizando && <Loading />}
                        <button type="button" className="btn btn-block btn-outline-primary btn-lg" onClick={() => { navigate("/newTecnica/0") }} >Adicionar Tecnica</button>
                        <Tabela categorias={ categorias } tabela={ linhas } paginaSite="#/tecnicas" ref={tabelaFormRef}/>
                        <AddEstat enviar={enviarEstat} />
                        <AddOrien pEnviar={ oriePEnviar } setParaEnviar={ setOriePEnviar } orientacoes={ orientacoes } enviar={ enviarOri }/>
                    </div>
                </div>
                </div>
            </div>
        </div>
    );
}

