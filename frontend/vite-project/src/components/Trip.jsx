import GoogleMapComponent from './GoogleMapComponent'
import TripMenu from "./tripmenu/TripMenu.jsx";

function Trip() {

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

export default Trip
