import { useState, useEffect } from "react";
import axios from "axios";
import AddTrip from "./AddTrip.jsx";

export default function TripList() {
    const [trips, setTrips] = useState([]);

    useEffect(() => {
        fetchTrips();
    }, []);

    const fetchTrips = async () => {
        try {
            const response = await axios.get("/api/trips/");
            setTrips(response.data);
        } catch (error) {
            console.error("Error fetching trips:", error);
        }
    };
    return (
        <div className="card flex flex-col bg-base-200  items-center  min-h-2/3 min-w-1/2 p-5 ">
            <h2 className="justify-start">Trip Menu</h2>
            {trips.length > 0 && trips.map((trip) => (
                <div key={trip.id} id={trip.id}>
                    <div><strong>{trip.name}</strong></div>
                    <div>Start: {trip.startDate}</div>
                    <div>End: {trip.endDate}</div>
                </div>

            ))}

            <AddTrip className="justify-end" trips={trips} setTrips={setTrips} />
        </div>
    );
}
