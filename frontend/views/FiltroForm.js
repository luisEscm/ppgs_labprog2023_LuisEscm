import React from 'react';

export default function Filtros({docentes, filtroProd, onProdChange,
                                onSearch}){
    
    const lstSelect = docentes.map((docente) => (
        <option value={docente.id}>{docente.nome}</option>
        )
    )

    return(
        <>
        <h5 className="mb-2"></h5>
        <form action="#">
            <div className="row">
            <div className="col-md-10">
                <div className="row">
                <div className="col-4">
                    <div className="form-group">
                    <label>Docente:</label>
                    <select className="form-control" style={{width: '100%'}}
                        onChange={(e) => onProdChange(e.target.value)} 
                        value={filtroProd}
                        >
                        {/*As opções obtidas por parâmetro*/}
                        {lstSelect}
                    </select>
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
