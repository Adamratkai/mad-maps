import axios from "axios";
import { useContext } from "react";
import {AuthContext} from "./AuthProvider.jsx";

const useAxios = () => {
    const { token } = useContext(AuthContext);

    const axiosInstance = axios.create({
        baseURL: "/",
        headers: { "Content-Type": "application/json" },
    });
    axiosInstance.interceptors.request.use(
        (config) => {
            if (token) {
                config.headers["Authorization"] = `Bearer ${token}`;
            }
            return config;
        },
        (error) => Promise.reject(error)
    );

    return axiosInstance;
};

export default useAxios;
