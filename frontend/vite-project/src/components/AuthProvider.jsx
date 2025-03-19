import {createContext, useState} from "react";
import axios from "axios";

export const AuthContext = createContext();

export const AuthProvider = ({children}) => {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(localStorage.getItem("token") || null);
    const [error, setError] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(localStorage.getItem("token")!==null || false);
    const login = async (email, password) => {
        try {
            const response = await axios.post("/api/traveller/login", {email, password});
            setUser(response.data.username)
            setToken(response.data.token);
            setIsLoggedIn(true)
            localStorage.setItem("token", response.data.token);
        } catch (error) {
            setError(error);
        }
    }

    const logout = () => {
        setUser(null);
        setToken(null);
        setIsLoggedIn(false);
        localStorage.removeItem("token");
    }

    const register = async (username, email, password) => {
        try {
            await axios.post("/api/traveller/register", {username, email, password});
            setError(null);
        } catch (error) {
            setError(error.message);
        }
    }

    return (
        <AuthContext value={{user, setUser, login, register, logout, error, token,isLoggedIn}}>
            {children}
        </AuthContext>
    )
}