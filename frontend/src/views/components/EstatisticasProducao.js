import React from 'react';

export default function EstatisticasProducao({valuesForm, handleInputChange}){

    return(
        <div className="col-md">
        <div className="card card-gray">
            <div className="card-header">
                <h3 className="card-title">Estatisticas</h3>
                <div className="card-tools">
                    <button type="button" className="btn btn-tool" data-card-widget="collapse">
                    <i className="fas fa-minus"></i>
                    </button>
                </div>
            </div>
            <div className="card-body">
                <label>Qtd.Graduandos:</label>
                <input type="number" required className="form-control" value={valuesForm.qtdGrad || 0} name="qtdGrad" onChange={handleInputChange} />
                <label>Qtd.Mestrandos:</label>
                <input type="number" required className="form-control" value={valuesForm.qtdMestrado || 0} name="qtdMestrado" onChange={handleInputChange} />
                <label>Qtd.Doutorandos:</label>
                <input type="number" required className="form-control" value={valuesForm.qtdDoutorado || 0} name="qtdDoutorado" onChange={handleInputChange} />
            </div>
        </div>
        </div>
    );
}