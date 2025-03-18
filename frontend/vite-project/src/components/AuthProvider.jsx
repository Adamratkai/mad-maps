import {createContext, useState} from "react";
import axios from "axios";

export const AuthContext = createContext();

export const AuthProvider = ({children}) => {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(localStorage.getItem("token") || null);
    const [error, setError] = useState(null);

    const login = async (email, password) => {
        try {
            const response = await axios.post("/traveller/login", {email, password});
            setUser(response.data.user)
            setToken(response.data.token);
        } catch (error) {
            setError(error);
        }
    }

    const logout = () => {
        setUser(null);
        setToken(null);
        localStorage.removeItem("token");
    }

    const register = async (email, password, username) => {
        try {
            const response = await axios.post("/traveller/register", {username, email, password});
        } catch (error) {
            setError(error);
        }
    }

    return (
        <AuthContext.Provider value={{user, setUser, login, register, logout, error, token}}>
            {children}
        </AuthContext.Provider>
    )
}