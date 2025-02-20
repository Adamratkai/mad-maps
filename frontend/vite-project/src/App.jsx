import './App.css'
import GoogleMapComponent from './components/GoogleMapComponent'
import TripMenu from "./components/tripmenu/TripMenu.jsx";

function App() {

    return (
        <div className="App">
            <div className="GoogleMap">
                <GoogleMapComponent/>
            </div>
            <div className="TripMenu">
                <TripMenu/>
            </div>
        </div>
    )
}

export default App
