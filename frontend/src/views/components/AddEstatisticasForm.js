import React from 'react';
import {useState} from 'react';
import {useEffect} from 'react';

export default function AddEstatisticasForm({ enviar }){
    const [qtdGrad, setQtdGrad] = useState(0)
    const [qtdMest, setQtdMest] = useState(0)
    const [qtdDout, setQtdDout] = useState(0)

    const graduado = "graduado"
    const mestrado = "mestrado"
    const doutorado = "doutorado"

    const handleChange = (event) => {
        if(event.target.value < 0){
            if(event.target.name === graduado){
                setQtdGrad(0);
            }else{
                if(event.target.name === mestrado){
                    setQtdMest(0);
                }else{
                    setQtdDout(0);
                }
            }
        }else{
            if(event.target.name === graduado){
                setQtdGrad(event.target.value);
            }else{
                if(event.target.name === mestrado){
                    setQtdMest(event.target.value);
                }else{
                    setQtdDout(event.target.value);
                }
            }
        }
        console.log(event.target.value);
    }

    const handleSubmit = (e) =>{
        e.preventDefault()
        enviar(qtdGrad, qtdMest, qtdDout)
    }
    return(
        <div>
            <div id="addEstatisticas" className="modal fade">
                <div className="modal-dialog">
                <div className="modal-content">
                    <form onSubmit={handleSubmit}>
                    <div className="modal-header">						
                        <h4 className="modal-title">Informar Estatísticas</h4>
                        <button type="button" className="close" data-dismiss="modal" aria-hidden="true">×</button>
                    </div>
                    <div className="modal-body">					
                        <div className="form-group">
                        <label>Qtd. alunos de Graduação:</label>
                        <input name={graduado} type="number" value={ qtdGrad } className="form-control" onChange={handleChange} />
                        </div>
                        <div className="form-group">
                        <label>Qtd. Alunos do Mestrado:</label>
                        <input name={mestrado} type="number" value={ qtdMest } className="form-control" onChange={handleChange} />
                        </div>
                        <div className="form-group">
                        <label>Qtd. Alunos do Doutorado:</label>
                        <input name={doutorado} type="number" value={ qtdDout } className="form-control" onChange={handleChange} />
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