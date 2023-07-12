import React, {createContext, useState, useEffect} from "react";

import { useNavigate } from "react-router-dom";

export const AuthContext = createContext();

export const AuthProvider = ({children}) => {
    const navigate = useNavigate();
    const [user, setUser] = useState(null)
    const [carregando, setCarregando] = useState(true)

    useEffect(() => {
        const recoveredUser = localStorage.getItem("user")

        if(recoveredUser) {
            setUser(JSON.parse(recoveredUser))
        }

        setCarregando(false)
    }, [])

    const login = (usuario, senha) => {

        const loggedUser = {
            usuario
        }

        localStorage.setItem("user", JSON.stringify(usuario))

        if(senha === "244466666@" && usuario === "AdminSPPG"){
            setUser(loggedUser.usuario)
            navigate("/programas")
        }
    }

    const logout = () => {
        localStorage.removeItem("user")
        setUser(null)
        navigate("/home")
    }

    return (
        <AuthContext.Provider value={{authenticated: !!user, user, carregando, login, logout}}>
            {children}
        </AuthContext.Provider>
    );
}
