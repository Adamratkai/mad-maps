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
        <>
            <Navbar/>
            <div className="flex-1 flex items-center justify-center bg-base-100">
                <div className="text-center">
                    {children}
                </div>
            </div>

        </>
    );
}

const router = createBrowserRouter([
    {path: "/", element: <Layout><HomePage/></Layout>},
    {path: "/trip", element: <Layout><Trip/></Layout>},
    {path: "*", element: <ErrorPage/>},
    {path: "/login", element: <Layout><Login /></Layout>},
    {path: "/register", element: <Layout><Register /></Layout>},
]);

function App() {
    return (
        <RouterProvider router={router}></RouterProvider>
    );
}

export default App
