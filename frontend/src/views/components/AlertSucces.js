import React from 'react';


export default function Alertsucces({visivel, setVisivel, mensagem}){
    if(visivel){
        return(
        <div>
        <div className="alert alert-success alert-dismissible">
            <button type="button" className="close" onClick={() => setVisivel(false)}>×</button>
            <h5><i className="icon fas fa-check"> Alert!</i></h5>
                {mensagem}
            </div>
        </div>

        );
    }else{
        return null;
    }
}