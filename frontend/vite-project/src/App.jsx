import './App.css'
import Navbar from "./components/Navbar.jsx";
import {createBrowserRouter, RouterProvider} from "react-router";
import HomePage from "./pages/HomePage.jsx";
import ErrorPage from "./pages/ErrorPage.jsx";
import TripPage from "./pages/TripPage.jsx";
import LoginPage from "./pages/LoginPage.jsx";
import RegisterPage from "./pages/RegisterPage.jsx";


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
    {path: "/trip", element: <Layout><TripPage/></Layout>},
    {path: "*", element: <ErrorPage/>},
    {path: "/login", element: <Layout><LoginPage/></Layout>},
    {path: "/register", element: <Layout><RegisterPage/></Layout>},
]);

function App() {
    return (
        <RouterProvider router={router}></RouterProvider>
    );
}

export default App
