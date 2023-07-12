import React from 'react';

export default function InfoEstatica({valuesForm, handleInputChange}){

    return(
        <div className="col-md-6">
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
                    <label>Local:</label>
                    <input type="text" value={valuesForm.nomeLocal || ""} className="form-control" required name="nomeLocal" onChange={handleInputChange} />
                </div>
                <div>
                    <label>Ano:</label>
                    <input type="number" value={valuesForm.ano || 2023} className="form-control" requiered name="ano" onChange={handleInputChange} />
                </div>
                <div className="form-group">
                    <label>Qualis:</label>
                    <select className="custom-select" required value={valuesForm.qualis || "A1"} name="qualis" onChange={handleInputChange}>
                        <option value="A1">A1</option>
                        <option value="A2">A2</option>
                        <option value="A3">A3</option>
                        <option value="A4">A4</option>
                        <option value="B1">B1</option>
                        <option value="B2">B2</option>
                        <option value="B3">B3</option>
                        <option value="B4">B4</option>
                    </select>
                </div>
            </div>
        </div>
        </div>

    );
}
