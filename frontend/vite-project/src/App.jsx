import './App.css'
import Navbar from "./components/Navbar.jsx";
import {createBrowserRouter, RouterProvider} from "react-router";
import HomePage from "./components/HomePage.jsx";
import ErrorPage from "./components/ErrorPage.jsx";
import Trip from "./components/Trip.jsx";


function Layout({children}) {
    return (
        <>
            <Navbar/>
            {children}
        </>
    );
}

const router = createBrowserRouter([
    {path: "/", element: <Layout><HomePage/></Layout>},
    {path: "/trip", element: <Layout><Trip/></Layout>},
    {path: "*", element: <ErrorPage/>}
]);

function App() {
    return (
        <RouterProvider router={router}></RouterProvider>
    );
}

export default App
