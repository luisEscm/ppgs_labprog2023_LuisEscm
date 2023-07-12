import React from 'react';

export default function FiltrosDocente({titulo, itens, filtroItem, onItemChange, 
                                filtroAnoIni, onAnoIniChange,
                                filtroAnoFim, onAnoFimChange, 
                                onSearch}){
    
    const lstSelect = itens.map((item) => (
        <option key={item.id} value={item.id}>{item.nome}</option>
        )
    )

    return(
        <>
        <h5 className="mb-2">Filtros</h5>
        <form action="#">
            <div className="row">
            <div className="col-md-10">
                <div className="row">
                <div className="col-4">
                    <div className="form-group">
                    <label>{titulo}</label>
                    <select className="form-control" style={{width: '100%'}}
                        onChange={(e) => {onItemChange(e.target.value)}} 
                        value={filtroItem}
                        >
                        {/*As opções obtidas por parâmetro*/}
                        {lstSelect}
                    </select>
                    </div>
                </div>
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
