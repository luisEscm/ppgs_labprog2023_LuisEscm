import React from 'react';
import {useState} from 'react';
import {useEffect} from 'react';




export default function TabelaForm({categorias, tabela, client}) {
    const [paginaAtual, setPagAtual] = useState(1)
    const [botoesVisi, setBotoesVisi] = useState([])

    
    const botoes =  botoesVisi.map((i, key) => {
        
        if(i === paginaAtual){
            return <li className="page-item active"><a href="#/producoes" className="page-link" onClick= {() => controles.goTo(i)}>{ i }</a></li>
        }else{
            return <li className="page-item"><a href="#/producoes" className="page-link" onClick= {() => controles.goTo(i)}>{ i }</a></li>
        }
    });
    
    const quantPagina = 5
    const estado = {
        quantPagina,
        maxBotoesVisiveis: 5,
        totalPagina: Math.ceil(tabela.length / quantPagina)
    }

    function atualizarBotoes(erro){
        const {maxL, maxR} = calcularMaxED(erro)
        let lista = []

        for(let page = maxL; page <= maxR; page++ ){
            lista.push(page)
        }
        setBotoesVisi(lista)
    }

    function calcularMaxED(erro){
        const { maxBotoesVisiveis } = estado
        let maxL = ((paginaAtual + erro) - Math.floor(maxBotoesVisiveis / 2))
        let maxR = ((paginaAtual + erro) + Math.floor(maxBotoesVisiveis / 2))
        if (maxL < 1) {
            maxL = 1
            maxR = (maxL + (maxBotoesVisiveis - 1))
            if (maxR > estado.totalPagina) maxR = (estado.totalPagina)
        }

        if (maxR > estado.totalPagina) {
            maxL = (estado.totalPagina - (maxBotoesVisiveis - 1))
            maxR = (estado.totalPagina)

            if(maxL < 1) maxL = 1
        }

        return {maxL, maxR}
    }

    useEffect( () => {
        atualizarBotoes(0);
        }, []
    )

    let controles = {
        next(){
            let pagina = (paginaAtual + 1)

            if(pagina > estado.totalPagina){
                pagina--
            }

            setPagAtual(pagina)
            atualizarBotoes(1)
        },
        prev(){
            let pagina = (paginaAtual - 1)

            if(pagina < 1){
                pagina = 1
            }

            setPagAtual(pagina)
            atualizarBotoes((-1))

        },
        goTo(pag){
            if(pag < 1){
                pag = 1
            }

            if(pag > estado.totalPagina){
                pag = estado.totalPagina
            }

            setPagAtual(pag)
            atualizarBotoes((pag - paginaAtual))
        }
    }

    const base = (paginaAtual-1)*quantPagina
    const fim = base + quantPagina

  return (
        <div>
        <table className="table table-striped table-hover">
            <thead>
                { categorias }
            </thead>
            <tbody>
                { tabela.slice(base, fim) }
            </tbody>
        </table>
        <div className="clearfix">
            <div className="hint-text">Exibindo <b>{ quantPagina }</b> de <b>{ tabela.length }</b> entradas</div>
            <ul className="pagination">
            <li className="page-item"><a href="#/producoes" className="page-link" onClick={() => controles.prev()}>Anterior</a></li>
            { botoes }
            <li className="page-item"><a href="#/producoes" className="page-link" onClick={() => controles.next()}>Próxima</a></li>
            </ul>
        </div>
        <div id="addOrientacao" className="modal fade">
            <div className="modal-dialog">
            <div className="modal-content">
                <form>
                <div className="modal-header">						
                    <h4 className="modal-title">Informando Orientações</h4>
                    <button type="button" className="close" data-dismiss="modal" aria-hidden="true">×</button>
                </div>
                <div className="modal-body">					
                    <div className="form-group">
                    <label>Selecione:</label>
                    <br />
                    <input type="checkbox" name="aluno1" /> 
                    <label htmlFor="aluno1"> José da Silva</label>
                    <br />
                    <input type="checkbox" name="aluno2" /> 
                    <label htmlFor="aluno2"> Ribamar José</label>
                    <br />
                    <input type="checkbox" name="aluno3" /> 
                    <label htmlFor="aluno3"> Marcio Silva</label>
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
        <div id="addEstatisticas" className="modal fade">
            <div className="modal-dialog">
            <div className="modal-content">
                <form>
                <div className="modal-header">						
                    <h4 className="modal-title">Informar Estatísticas</h4>
                    <button type="button" className="close" data-dismiss="modal" aria-hidden="true">×</button>
                </div>
                <div className="modal-body">					
                    <div className="form-group">
                    <label>Qtd. alunos de Graduação:</label>
                    <input type="text" className="form-control" />
                    </div>
                    <div className="form-group">
                    <label>Qtd. Alunos do Mestrado:</label>
                    <input type="email" className="form-control" />
                    </div>
                    <div className="form-group">
                    <label>Qtd. Alunos do Doutorado:</label>
                    <input type="email" className="form-control" />
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


