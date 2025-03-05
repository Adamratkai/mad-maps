import './App.css'
import GoogleMapComponent from './components/GoogleMapComponent'
import TripMenu from "./components/tripmenu/TripMenu.jsx";
import Recommendation from "./components/recommendation/Recommendation.jsx";
import {useState} from "react";

function App() {

    const [location, setLocation] = useState(null)

    function handleLocationChange(location) {
        setLocation(location)
    }

    return (
        <div className="App">
            <div className="GoogleMap">
                <GoogleMapComponent handleLocationChange={handleLocationChange}/>
            </div>
            <div className="TripMenu">
                <TripMenu/>
            </div>
            <div className="RecommendedPlaces">
                {location && <Recommendation location={location}/>}
            </div>
        </div>
    )
}

export default App
