import React from 'react';

export default function InfoTecnica({valuesForm, handleInputChange}){

    return(
        <div className="col-md">
        <div className="card card-outline card-primary">
            <div className="card-header">
            <h3 className="card-title"></h3>
            </div>
            <div className="card-body">
                <div>
                    <label>Título: </label>
                    <input type="text" name="titulo" placeholder="Título" value={valuesForm.titulo || ""} className="form-control form-control-border border-width-2" required onChange={handleInputChange} />
                </div>
                <div>
                    <label>Financiadora:</label>
                    <input type="text" value={valuesForm.financiadora || ""} className="form-control" required name="financiadora" onChange={handleInputChange} />
                </div>
                <div>
                    <label>Ano:</label>
                    <input type="number" value={valuesForm.ano || 2023} className="form-control" requiered name="ano" onChange={handleInputChange} />
                </div>
                <div>
                    <label>Tipo:</label>
                    <input type="text" value={valuesForm.tipo || ""} className="form-control" required name="tipo" onChange={handleInputChange} />
                </div>
                <div>
                <label>Outras Informações:</label>
                    <textarea value={valuesForm.outrasInformacoes || ""} className="form-control" required name="outrasInformacoes" onChange={handleInputChange} />
                </div>
            </div>
        </div>
        </div>

    );
}
