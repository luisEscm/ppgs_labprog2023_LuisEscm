import React from 'react';
import {useEffect} from 'react';
import {useState} from 'react';
import { useRef } from 'react';
import axios from 'axios';
import Navbar from './components/Navbar';
import Header from './components/Header';
import CardTabela from './components/CardTabela';
import InfoEstatica from './components/InfoEstatica';
import InfoNaoEstatica from './components/InfoNaoEstatica';
import EstatisticasProducao from './components/EstatisticasProducao';
import AlertSucces from './components/AlertSucces';
import ErrorAlert from './components/ErrorAlert.js';
import { useParams } from 'react-router-dom';

const client = axios.create({
    baseURL: "http://localhost:8080/api/docente/" 
});

export default function AddProducao(){
    const { id } = useParams()
    const idProd = Number.parseInt(id, 10)

    const [formValues, setFormValues] = useState({titulo: "",
                                                tipo: "P",
                                                percentilouh5: 0.0,
                                                issnSigla: "",
                                                nomeLocal: "",
                                                ano: 2023,
                                                qualis: "A1",
                                                qtdGrad: 0,
                                                qtdMestrado: 0,
                                                qtdDoutorado: 0})

    const [docEscolhidos, setDocEscolhidos] = useState([])
    const [orieEscolhidos, setOrieEscolhidos] = useState([])

    const [tituloPerH5, setTituloPerH5] = useState("Percentil")
    const [tituloISSNSigla, setTituloISSNSigla] = useState("ISSN")
    const [carregando, setCarregando] = useState(true)

    const [docentes, setDocentes] = useState([])
    const [orientacoes, setOrientacoes] = useState([])

    const [avisoSucces, setAvisoSucces] = useState(false)
    const [avisoError, setAvisoError] = useState(false)
    const [errorMensage, setErrorMensage] = useState("")

    const tabelaDocentesRef = useRef(null);
    const tabelaOrientacoesRef = useRef(null);

    const goToDoc = (e) => {tabelaDocentesRef.current.vaPara(e)}
    const goToOrie = (e) => {tabelaOrientacoesRef.current.vaPara(e)}

    const handleInputChange = (e) => {
        const {name, value, type} = e.target;

        if(type === "radio"){
            if(value === "P"){
                setTituloPerH5("Percentil")
                setTituloISSNSigla("ISSN")
            }else{
                setTituloPerH5("h5")
                setTituloISSNSigla("Sigla")
            }
        }
        let newValue;

        if(type === "number"){
            if(value < 0){
                if(name === "percentilouh5"){
                    newValue = 0.0
                }else{
                    newValue = 0
                }
                e.target.value = 0
            }else{
                if(name === "percentilouh5"){
                    newValue = parseFloat(value)
                }else{
                    newValue = parseInt(value)
                }
            }
        }else{
            newValue = value
        }

        setFormValues({ ... formValues, [name]: newValue});
    }

    function getListas(){
        setCarregando(true)
        setTimeout(() => {
            client.get(`docentesNewProd`)
                    .then((response) => {
                        console.log(response.data)
                        setDocentes(response.data)
                        setCarregando(false)
                        goToDoc(1)
            }
        ).catch(error => {
            console.log(error.response);
        });
            client.get(`orientacoesNewProd`)
            .then((response) => {
                console.log(response.data)
                setOrientacoes(response.data)
                setCarregando(false)
                goToOrie(1)
                }
        ).catch(error => {
            console.log(error.response);
        });
        if(!((idProd === 0) || (isNaN(idProd)))){
            client.get(`getProd/${idProd}`)
            .then((response) => {
                console.log(response.data)
                const data = response.data;
                setFormValues({titulo: data.titulo,
                                tipo: data.tipo,
                                percentilouh5: data.percentilouh5,
                                issnSigla: data.issnSigla,
                                nomeLocal: data.nomeLocal,
                                ano: data.ano,
                                qualis: data.qualis,
                                qtdGrad: data.qtdGrad,
                                qtdMestrado: data.qtdMestrado,
                                qtdDoutorado: data.qtdDoutorado})
                setDocEscolhidos(data.idDocentes)
                setOrieEscolhidos(data.idOrientacoes)
                }
            ).catch(error => {
                console.log(error.response);
            });}
        },1000,)
    }

    function enviarProd(){
        setTimeout(() => {
            client.post(`newProducao`, {titulo: formValues.titulo, nomeLocal: formValues.nomeLocal, tipo: formValues.tipo, qualis:formValues.qualis,
                                        issnSigla: formValues.issnSigla, ano: formValues.ano, percentilouh5: formValues.percentilouh5, qtdGrad: formValues.qtdGrad,
                                        qtdMestrado: formValues.qtdMestrado, qtdDoutorado: formValues.qtdDoutorado, idDocentes: docEscolhidos, idOrientacoes: orieEscolhidos})
                                    .then((response) => {
                                        console.log(response.data)
                                        setAvisoSucces(true)
                                    
                                    }).catch(error => {
                                        console.log(error.response)
                                        setAvisoError(true)
                                        setErrorMensage("Ocorreu um problema durante o envio")
                                    })
        },1000,)
    }

    function atualizarProd(){
        setTimeout(() => {
            client.put(`atualizaProd`, {id: idProd, titulo: formValues.titulo, nomeLocal: formValues.nomeLocal, tipo: formValues.tipo, qualis:formValues.qualis,
                                        issnSigla: formValues.issnSigla, ano: formValues.ano, percentilouh5: parseFloat(formValues.percentilouh5), qtdGrad: formValues.qtdGrad,
                                        qtdMestrado: formValues.qtdMestrado, qtdDoutorado: formValues.qtdDoutorado, idDocentes: docEscolhidos, idOrientacoes: orieEscolhidos})
                                    .then((response) => {
                                        console.log(response.data)
                                        setAvisoSucces(true)
                                    
                                    }).catch(error => {
                                        console.log(error.response)
                                        setAvisoError(true)
                                        setErrorMensage("Ocorreu um problema durante o envio")
                                    })
        },1000,)
    }

    // eslint-disable-next-line
    useEffect( () => {
        document.body.classList.add('hold-transition', 'layout-top-nav');
        getListas();
        }, []
    )

    function atualizarListaEnviar(lista, setLista){
        const pEnviarDoc = (lista.map((item) => {
            if(item.enviar){
                return item.id
            }else{
                return null
            }})).filter(item => (!(item === null)))
        
        setLista(pEnviarDoc)
    }

    const docenteBase = (
        <tr>
            <th>Docente</th>
            <th>Lattes</th>                        						
            <th>Escolhido</th>
        </tr>
    )

    const docenteTabela = docentes.map((docente, key) =>
    (        <tr key={key}>
                <th>{docente.nome}</th>
                <th>{docente.lattes}</th>
                <th><input type="checkbox" name="docentes" required={docEscolhidos.length === 0} checked={docEscolhidos.includes(docente.id)} onChange={(e) => {docente.enviar = e.target.checked; atualizarListaEnviar(docentes, setDocEscolhidos)}}/></th>
            </tr>
    ))

    const orientacaoBase = (
        <tr>
            <th>Título</th>
            <th>Orientador</th>
            <th>Tipo</th>
            <th>Instituição</th>
            <th>Curso</th>
            <th>Ano</th>                        						
            <th>Escolhido</th>
        </tr>
    )

    const orientacaoTabela = orientacoes.map((orientacao, key) =>
    (        <tr key={key}>
                <th>{orientacao.titulo}</th>
                <th>{orientacao.orientador}</th>
                <th>{orientacao.tipo}</th>
                <th>{orientacao.instituicao}</th>
                <th>{orientacao.curso}</th>
                <th>{orientacao.ano}</th> 
                <th><input type="checkbox" name="orientacoes" checked={orieEscolhidos.includes(orientacao.id)} onChange={(e) => {orientacao.enviar = e.target.checked; atualizarListaEnviar(orientacoes, setOrieEscolhidos)}}/></th>
            </tr>
    ))

    const handleSubmit = (e) => {
        e.preventDefault();
        if(idProd === 0 || isNaN(idProd)){
            enviarProd();
        }
        else{
            atualizarProd();
        }
    }

{/* <div className="alert alert-success alert-dismissible">
  <button type="button" className="close" data-dismiss="alert" aria-hidden="true">×</button>
  <h5><i className="icon fas fa-check" /> Alert!</h5>
  Success alert preview. This alert is dismissable.
</div> */}


    return (
        <div className="wrapper">
            <Navbar />
            <div className="content-wrapper">
                <Header titulo="Produção" />
                {/*<!-- Main content -->*/}
                <div className="content">      
                <div className="container">
                    <div className="container-fluid">
                        <AlertSucces visivel={avisoSucces} setVisivel={setAvisoSucces} mensagem="Enviado" />
                        <ErrorAlert visivel={avisoError} setVisivel={setAvisoError} mensagem={errorMensage} />
                        <form onSubmit={handleSubmit}>
                            {/* infoGeral */}
                            <div className="row">
                                <InfoEstatica valuesForm={formValues} handleInputChange={handleInputChange} />
                                <InfoNaoEstatica valuesForm={formValues} handleInputChange={handleInputChange} tituloPerH5={tituloPerH5} tituloISSNSigla={tituloISSNSigla} />
                            </div>
                            {/* estatisticas */}
                            <div className="row">
                                <EstatisticasProducao valuesForm={formValues} handleInputChange={handleInputChange} />
                            </div>
                            <div className="row">
                                {/* Docentes */}
                                <CardTabela tabela={docenteTabela} categorias={docenteBase} paginaSite={`#/newProducao/${idProd}`} titulo="Docentes" carregando={carregando} ref={tabelaDocentesRef} />
                            </div>
                            <div className="row">
                                {/* Orientacoes */}
                                <CardTabela tabela={orientacaoTabela} categorias={orientacaoBase} paginaSite={`#/newProducao/${idProd}`} titulo="Orientações" carregando={carregando} ref={tabelaOrientacoesRef} />
                            </div>
                            <button type="submit" data-target="#modal-success" data-toggle="modal" className="btn btn-primary" >Submit</button>
                        </form>
                    </div>
                </div>
                </div>
            </div>
        </div>

    );
}