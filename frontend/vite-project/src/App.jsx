import './App.css'
import Navbar from "./components/Navbar.jsx";
import {createBrowserRouter, RouterProvider} from "react-router";
import HomePage from "./components/HomePage.jsx";
import ErrorPage from "./components/ErrorPage.jsx";
import Trip from "./components/Trip.jsx";
import Login from "./components/Login.jsx";
import Register from "./components/Register.jsx";


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

const router = createBrowserRouter([
    {path: "/", element: <Layout><HomePage/></Layout>},
    {path: "/trip", element: <Layout><Trip/></Layout>},
    {path: "*", element: <ErrorPage/>},
    {path: "/login", element: <Layout><Login/></Layout>},
    {path: "/register", element: <Layout><Register/></Layout>},
]);

function App() {
    return (
        <RouterProvider router={router}></RouterProvider>
    );
}

export default App
