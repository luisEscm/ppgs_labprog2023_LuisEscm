import React from 'react';
import {useEffect} from 'react';
import {useState} from 'react';

export default function Login(){
    return(
        <div>
            <meta charSet="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <title>Login</title>
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" />
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
            <link rel="stylesheet" href="login.css" />
            <div className="login-form">
                <form action="/examples/actions/confirmation.php" method="post">
                <h2 className="text-center">SPPg - Login</h2>       
                <div className="form-group">
                    <input type="text" className="form-control" placeholder="Usuário" required="required" />
                </div>
                <div className="form-group">
                    <input type="password" className="form-control" placeholder="Senha" required="required" />
                </div>
                <div className="form-group">
                    <button type="submit" className="btn btn-primary btn-block">Login</button>
                </div>
                </form>
            </div>
            </div>

    );
}