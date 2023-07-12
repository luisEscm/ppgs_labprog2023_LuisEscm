import React from 'react';


export default function ErrorAlert({visivel, setVisivel, mensagem}){
    if(visivel){
        return(
            <div>
                <div className="alert alert-danger alert-dismissible">
                    <button type="button" className="close" onClick={() => setVisivel(false)} >×</button>
                    <h5><i className="icon fas fa-ban" /> Alert!</h5>
                        {mensagem}
                </div>
            </div>

        );
    }else{
        return null;
    }
}
