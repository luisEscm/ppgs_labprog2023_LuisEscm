import React from 'react';
import {useEffect} from 'react';
import {useState} from 'react';
import Navbar from './Navbar';
import Header from './Header';
import Filtro from './FiltroForm';
import Tabela from './TabelaForm';

export default function FormProducao(){
    const docentes = [
        {id:0, nome:"Selecione"},
        {id:1, nome:"Anselmo Paiva"},
        {id:2, nome:"Geraldo Braz"},
        {id:3, nome:"Simara Rocha"},
    ]
    const [prodSel, setProdSel] = useState(0);
    const [prod, setProd] = useState([
        {ano:2022, docente:"Geraldo Braz Junior", titulo:"Artigo sobre cadeia evolutiva", local:"Teste 1", orientacao:"Sim", graduados: 1, mestrado: 0, doutorado: 0},
        {ano:2021, docente:"Simara Rocha", titulo:"Aquele outro", local:"Teste 2", orientacao:"Sim", graduados: 1, mestrado: 1, doutorado: 0},
        {ano:2021, docente:"Geraldo Braz Junior", titulo:"Ainda sobre algo", local:"Revista Z", orientacao:"Não", graduados: 1, mestrado: 0, doutorado: 0},
        {ano:2020, docente:"Anselmo Paiva", titulo:"Dados ok", local:"Teste 5", orientacao:"Não", graduados: 1, mestrado: 0, doutorado: 1},
        {ano:2020, docente:"Simara Rocha", titulo:"E vamos nessak", local:"Teste 3", orientacao:"Não", graduados: 0, mestrado: 0, doutorado: 2},
        {ano:2020, docente:"Geraldo Braz Junior", titulo:"outra", local:"Teste 1", orientacao:"Sim", graduados: 1, mestrado: 0, doutorado: 0},
        {ano:2018, docente:"Geraldo Braz Junior", titulo:"putz", local:"Teste 2", orientacao:"Sim", graduados: 1, mestrado: 1, doutorado: 0},
        {ano:2018, docente:"Simara Rocha", titulo:"RAfael", local:"Revista Z", orientacao:"Sim", graduados: 1, mestrado: 0, doutorado: 0},
        {ano:2018, docente:"Geraldo Braz Junior", titulo:"Dados ruim", local:"Revista Z", orientacao:"Sim", graduados: 3, mestrado: 0, doutorado: 1},
        {ano:2017, docente:"Anselmo Paiva", titulo:"Vambora", local:"Teste 7", orientacao:"Não", graduados: 0, mestrado: 1, doutorado: 2},
        {ano:2016, docente:"Geraldo Braz Junior", titulo:"O saco", local:"Teste 2", orientacao:"Sim", graduados: 1, mestrado: 0, doutorado: 0},
        {ano:2016, docente:"Anselmo Paiva", titulo:"Talvez", local:"Teste 2", orientacao:"Não", graduados: 1, mestrado: 1, doutorado: 0},
        {ano:2015, docente:"Simara Rocha", titulo:"Ainda não", local:"Revista Z", orientacao:"Não", graduados: 1, mestrado: 0, doutorado: 0},
        {ano:2015, docente:"Simara Rocha", titulo:"Carar", local:"Teste 9", orientacao:"Sim", graduados: 1, mestrado: 7, doutorado: 1},
        {ano:2010, docente:"Anselmo Paiva", titulo:"EA", local:"Teste 1", orientacao:"Não", graduados: 6, mestrado: 0, doutorado: 2},
        {ano:2009, docente:"Anselmo Paiva", titulo:"EA denovo", local:"Teste 1", orientacao:"Não", graduados: 6, mestrado: 0, doutorado: 2}]);

    function onSearch() {

    }
    
    const linhas = prod.map( (i, key) =>
    (
        <tr key={key}>
            <td>{i.ano}</td>
            <td>{i.docente}</td>
            <td>{i.titulo}</td>
            <td>{i.local}</td>
            <td>{i.orientacao}</td>
            <td>{`${i.graduados}G|${i.mestrado}M|${i.doutorado}D`}</td>
            <td>
                <a href="#addOrientacao" className="edit" data-toggle="modal"><i className="material-icons" data-toggle="tooltip" title="Orientações">Orientações</i></a>
                <label> | </label>
                <a href="#addEstatisticas" className="data" data-toggle="modal"><i className="material-icons" data-toggle="tooltip" title="Estatísticas">Estatisticas</i></a>
            </td>
        </tr>
    ));

    const categorias = (
    <tr>
        <th>Ano</th>
        <th>Docente</th>                        						
        <th>Título</th>
        <th>Local</th>	
        <th>Orientação?</th>	
        <th>Estatísticas</th>				
        <th>Adicionar</th>
    </tr>
    );

    return(
        <div className="wrapper">
            <Navbar />
            <div className="content-wrapper">
                <Header titulo="Gerenciar dados de Produções" />
                {/*<!-- Main content -->*/}
                <div className="content">      
                <div className="container">
                    <div className="container-fluid">
                        <Filtro docentes={ docentes } filtroProd={ prodSel } onProdChange={ setProdSel } onSearch={ onSearch } />
                        <Tabela categorias={ categorias } tabela={ linhas } />
                    </div>
                </div>
                </div>
            </div>
        </div>
    );
}

