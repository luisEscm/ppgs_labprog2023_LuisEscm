import React from 'react';

export default function InfoNaoEstatica({valuesForm, handleInputChange, tituloPerH5, tituloISSNSigla}){

    return(
        <div className="col-md-6">
        <div className="card card-outline card-primary">
            <div className="card-header">
            <h3 className="card-title"></h3>
            </div>
            <div className="card-body">
                <div className="form-group">
                    <label>Tipo</label>
                    <div className="custom-control custom-radio">
                        <input type="radio" className="custom-control-input" required value="P" name="tipo" id="P" onClick={handleInputChange} checked={valuesForm.tipo === "P"} />
                        <label for="P" className="custom-control-label" >Periódico</label>
                    </div>
                    <div className="custom-control custom-radio">
                        <input type="radio" className="custom-control-input" required value="C" name="tipo" id="C" onClick={handleInputChange} checked={valuesForm.tipo === "C"} />
                        <label for="C" className="custom-control-label" >Congresso</label>
                    </div>
                </div>
                <div>
                    <label>{`${tituloPerH5}: `}</label>
                    <input type="number" step="any" value={valuesForm.percentilouh5 || 0} className="form-control" required name="percentilouh5" onChange={handleInputChange} />
                </div>
                <div>
                    <label>{`${tituloISSNSigla}: `}</label>
                    <input type="text" value={valuesForm.issnSigla || ""} className="form-control" required name="issnSigla" onChange={handleInputChange} />
                </div>
            </div>
        </div>
        </div>

    );
}
