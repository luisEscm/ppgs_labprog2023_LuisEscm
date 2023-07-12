import React from 'react';
import {useState} from 'react';
import {useEffect} from 'react';

export default function AddEstatisticasForm({ pEnviar, setParaEnviar, orientacoes, enviar }){

    const linhas = orientacoes.map((orientacao, key) => (
        <><br /><input key={key} type="checkbox" name="aluno1" onChange={() => { orientacao.enviar = !orientacao.enviar; console.log(`valor: ${orientacao.enviar}`); editarEnvio()} } /><label htmlFor="aluno1"> { `Titulo:${orientacao.titulo}, Orientador:${orientacao.nome}` } </label></>))

    function editarEnvio(){
        const listaEnvio = orientacoes.map((orientacao) => {
            if(orientacao.enviar){
                console.log(orientacao.id)
                return orientacao.id
            }
            return null
        })
        setParaEnviar(listaEnvio.filter(item => (!(item === null))))
        console.log(pEnviar)
    }

    const handleSubmit = (e) => {
        e.preventDefault()
        enviar()
    }

    return(
        <div>
            <div id="addOrientacao" className="modal fade">
                <div className="modal-dialog">
                <div className="modal-content">
                    <form onSubmit={handleSubmit}>
                    <div className="modal-header">						
                        <h4 className="modal-title">Informando Orientações</h4>
                        <button type="button" className="close" data-dismiss="modal" aria-hidden="true">×</button>
                    </div>
                    <div className="modal-body">					
                        <div className="form-group">
                        <label>Selecione:</label>
                            { linhas }
                        </div>
                    </div>
                    <div className="modal-footer">
                        <input type="button" className="btn btn-default" data-dismiss="modal" defaultValue="Cancelar" />
                        <input type="submit" className="btn btn-info" defaultValue="Salvar" />
                    </div>
                    </form>
                </div>
                </div>
            </div>
        </div>
    );
}