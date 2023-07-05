import React from 'react';

export default function Tabela ({ categorias, tabela, titulo }) {

  return (
    <div className="card">
        <div className="card-header">
            <h3 className="card-title">{ titulo }</h3>
        </div>
        
        <div className="card-body">
        <table id="example1" className="table table-bordered table-striped">
            <thead>
            { categorias }
            </thead>
            <tbody>
                { tabela }
            </tbody>
            <tfoot>
            { categorias }
            </tfoot>
        </table>
        </div>
    </div>


  );

}