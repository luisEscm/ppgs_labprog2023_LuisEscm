import React from 'react';
import {useEffect} from 'react';
import {useState} from 'react';
import { useRef } from 'react';
import axios from 'axios';
import Navbar from './components/Navbar';
import Header from './components/Header';
import CardTabela from './components/CardTabela';
import InfoTecnica from './components/infoTecnica';
import EstatisticasProducao from './components/EstatisticasProducao';
import AlertSucces from './components/AlertSucces';
import ErrorAlert from './components/ErrorAlert.js';
import { useParams } from 'react-router-dom';

const client = axios.create({
    baseURL: "http://localhost:8080/api/docente/" 
});

export default function AddTecnica(){
    const { id } = useParams()
    const idTecn = Number.parseInt(id, 10)

    const [formValues, setFormValues] = useState({tipo: "",
                                                titulo: "",
                                                outrasInformacoes: "",
                                                financiadora: "",
                                                ano: 2023,
                                                qtdGrad: 0,
                                                qtdMestrado: 0,
                                                qtdDoutorado: 0})

    const [docEscolhidos, setDocEscolhidos] = useState([])
    const [orieEscolhidos, setOrieEscolhidos] = useState([])

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

        let newValue;

        if(type === "number"){
            if(value < 0){
                newValue = 0
                e.target.value = 0
            }else{
                newValue = parseInt(value)
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
        if(!((idTecn === 0) || (isNaN(idTecn)))){
            client.get(`getTecn/${idTecn}`)
            .then((response) => {
                console.log(response.data)
                const data = response.data;
                setFormValues({titulo: data.titulo,
                                tipo: data.tipo,
                                financiadora: data.financiadora,
                                outrasInformacoes: data.outrasInformacoes,
                                ano: data.ano,
                                qtdGrad: data.qtdGrad,
                                qtdMestrado: data.qtdMestrado,
                                qtdDoutorado: data.qtdDoutorado})
                setDocEscolhidos(data.idsDocentes)
                setOrieEscolhidos(data.idsOrientacoes)
                }
            ).catch(error => {
                console.log(error.response);
                setAvisoError(true)
                setErrorMensage("Ouve um erro ao recuperar a Tecnica")
            });}
        },1000,)
    }

    function enviarProd(){
        setTimeout(() => {
            client.post(`newTecnica`, {tipo: formValues.tipo, titulo: formValues.titulo, ano: formValues.ano,
                                        financiadora: formValues.financiadora, outrasInformacoes: formValues.outrasInformacoes, qtdGrad: formValues.qtdGrad,
                                        qtdMestrado: formValues.qtdMestrado, qtdDoutorado: formValues.qtdDoutorado, idsDocentes: docEscolhidos, idsOrientacoes: orieEscolhidos})
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
            client.put(`atualizaTecn`, {id: idTecn, tipo: formValues.tipo, titulo: formValues.titulo, ano: formValues.ano,
                                        financiadora: formValues.financiadora, outrasInformacoes: formValues.outrasInformacoes, qtdGrad: formValues.qtdGrad,
                                        qtdMestrado: formValues.qtdMestrado, qtdDoutorado: formValues.qtdDoutorado, idsDocentes: docEscolhidos, idsOrientacoes: orieEscolhidos})
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
        if(idTecn === 0 || isNaN(idTecn)){
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
                                <InfoTecnica valuesForm={formValues} handleInputChange={handleInputChange} />
                            </div>
                            {/* estatisticas */}
                            <div className="row">
                                <EstatisticasProducao valuesForm={formValues} handleInputChange={handleInputChange} />
                            </div>
                            <div className="row">
                                {/* Docentes */}
                                <CardTabela tabela={docenteTabela} categorias={docenteBase} paginaSite={`#/newTecnica/${idTecn}`} titulo="Docentes" carregando={carregando} ref={tabelaDocentesRef} />
                            </div>
                            <div className="row">
                                {/* Orientacoes */}
                                <CardTabela tabela={orientacaoTabela} categorias={orientacaoBase} paginaSite={`#/newTecnica/${idTecn}`} titulo="Orientações" carregando={carregando} ref={tabelaOrientacoesRef} />
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