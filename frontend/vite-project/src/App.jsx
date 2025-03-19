import './App.css'
import Navbar from "./components/Navbar.jsx";
import {createBrowserRouter, Navigate, RouterProvider} from "react-router";
import HomePage from "./pages/HomePage.jsx";
import ErrorPage from "./pages/ErrorPage.jsx";
import TripPage from "./pages/TripPage.jsx";
import LoginPage from "./pages/LoginPage.jsx";
import RegisterPage from "./pages/RegisterPage.jsx";
import {useContext} from "react";
import {AuthContext, AuthProvider} from "./components/AuthProvider.jsx";

function Layout({children}) {
    return (
        <div>
            <Navbar/>
            <div className="text-center justify-center items-center flex">
                {children}
            </div>
        </div>
    );
}

const ProtectedRoute = ({children}) => {
    const {token} = useContext(AuthContext);
    if (!token) {
        return <Navigate to="/login"/>;
    } else {
        return children;
    }
}
const router = createBrowserRouter([
    {path: "/", element: <Layout><HomePage/></Layout>},
    {path: "/trip", element: <ProtectedRoute><Layout><TripPage/></Layout></ProtectedRoute>},
    {path: "*", element: <ErrorPage/>},
    {path: "/login", element: <Layout><LoginPage/></Layout>},
    {path: "/register", element: <Layout><RegisterPage/></Layout>},
]);

function App() {
    return (
        <AuthProvider>
            <RouterProvider router={router}></RouterProvider>
        </AuthProvider>
    );
}

export default App
