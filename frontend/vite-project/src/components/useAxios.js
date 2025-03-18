import axios from "axios";
import { AuthContext } from "./AuthProvider.jsx";
import { useContext } from "react";

const useAxios = () => {
    const { token } = useContext(AuthContext);

    const axiosInstance = axios.create({
        baseURL: "http://localhost:8080/api",
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
