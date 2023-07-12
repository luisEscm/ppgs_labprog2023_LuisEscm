import React from 'react';
import {useEffect} from 'react';
import {useState} from 'react';
import { useContext } from 'react';
import { AuthContext } from '../contexts/AuthContext';

export default function Login(){
    const { login } = useContext(AuthContext)

    const [usuario, setUsuario] = useState("")
    const [senha, setSenha] = useState("")

    const handleSubmit = (e) =>{
        e.preventDefault()
        login(usuario, senha);
    }

    return(
        <div>
        <div className="card card-outline card-primary">
            <div className="card-header">
            <h3 className="card-title"></h3>
            </div>
            <div className="card-body">
                <meta charSet="utf-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <title>Login</title>
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" />
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
                <link rel="stylesheet" href="login.css" />
                <div className="login-form">
                    <form onSubmit={handleSubmit}>
                    <h2 className="text-center">SPPg - Login</h2>
                    <div className="form-group">
                        <input type="text" className="form-control" placeholder="Usuário" value={usuario} onChange={(e) => setUsuario(e.target.value)} required="required" />
                    </div>
                    <div className="form-group">
                        <input type="password" className="form-control" placeholder="Senha" value={senha} onChange={(e) => setSenha(e.target.value)} required="required" />
                    </div>
                    <div className="form-group">
                        <button type="submit" className="btn btn-primary btn-block">Login</button>
                    </div>
                    </form>
                </div>
            </div>
        </div>
        </div>

    );
}