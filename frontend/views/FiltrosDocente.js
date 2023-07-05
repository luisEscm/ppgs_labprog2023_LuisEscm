import React from 'react';

export default function FiltrosDocente({filtroAnoIni, onAnoIniChange,
                                filtroAnoFim, onAnoFimChange, 
                                onSearch}){

    return(
        <>
        <h5 className="mb-2">Filtros</h5>
        <form action="#">
            <div className="row">
            <div className="col-md-10">
                <div className="row">
                <div className="col-2">
                    <div className="form-group">
                    <label>Ano inicial:</label>
                    <input className="form-control"
                            value={filtroAnoIni} 
                            onChange={ (e) => onAnoIniChange(e.target.value)} />
                    </div>
                </div>
                <div className="col-2">
                    <div className="form-group">
                    <label>Ano Final:</label>
                    <input className="form-control"
                            value={filtroAnoFim} 
                            onChange={ (e) => onAnoFimChange(e.target.value)} />
                    </div>
                </div>
                <div className="col-2">
                    <div className="form-group">
                    <label></label>
                    <button type="button" className="btn btn-block btn-primary btn-lg" 
                                    onClick={onSearch}>Filtrar</button> 
                    </div>
                </div>
                </div>
            </div>
            </div>
        </form>
    </>
    );
}