import React, { forwardRef } from 'react';
import {useState} from 'react';
import {useEffect} from 'react';
import { useImperativeHandle } from 'react';





function TabelaForm({categorias, tabela, paginaSite}, ref) {
    const [paginaAtual, setPagAtual] = useState(1)
    const [botoesVisi, setBotoesVisi] = useState([1]);

    
    const botoes =  botoesVisi.map((i, key) => {
        
        if(i === paginaAtual){
            return <li key={key} className="page-item active"><a href={ paginaSite } className="page-link" onClick= {() => controles.goTo(i)}>{ i }</a></li>
        }else{
            return <li key={key} className="page-item"><a href={ paginaSite } className="page-link" onClick= {() => controles.goTo(i)}>{ i }</a></li>
        }
    });
    const quantPagina = 5;
    const estado = {
        quantPagina,
        maxBotoesVisiveis: 5,
        totalPagina: Math.ceil(tabela.length / quantPagina)
    }

    useImperativeHandle(ref, () => ({
        vaPara: (e) => {controles.goTo(e);
    },}));

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
            <li key="Anterior" className="page-item"><a href={ paginaSite } className="page-link" onClick={() => controles.prev()}>Anterior</a></li>
            { botoes }
            <li key="Proximo" className="page-item"><a href={ paginaSite } className="page-link" onClick={() => controles.next()}>Próxima</a></li>
            </ul>
        </div>
        </div>
  );
}

export default forwardRef(TabelaForm);
