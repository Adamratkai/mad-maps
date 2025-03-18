import './App.css'
import Navbar from "./components/Navbar.jsx";
import {createBrowserRouter, Navigate, RouterProvider} from "react-router";
import HomePage from "./components/HomePage.jsx";
import ErrorPage from "./components/ErrorPage.jsx";
import Trip from "./components/Trip.jsx";
import Login from "./components/Login.jsx";
import Register from "./components/Register.jsx";
import {AuthContext, AuthProvider} from "./components/AuthProvider.jsx";
import {useContext} from "react";

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
    const {user} = useContext(AuthContext);
    if (!user) {
        return <Navigate to="/"/>;
    } else {
        return children;
    }
}
const router = createBrowserRouter([
    {path: "/", element: <Layout><HomePage/></Layout>},
    {path: "/trip", element: <ProtectedRoute><Layout><Trip/></Layout></ProtectedRoute>},
    {path: "*", element: <ErrorPage/>},
    {path: "/login", element: <Layout><Login/></Layout>},
    {path: "/register", element: <Layout><Register/></Layout>},
]);

function App() {
    return (
        <AuthProvider>
            <RouterProvider router={router}></RouterProvider>
        </AuthProvider>
    );
}

export default App
