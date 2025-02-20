import { useState, useEffect } from "react";
import axios from "axios";
import NewTrip from "./NewTrip.jsx";

export default function TripMenu() {
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
        <>
            <h2>Trip Menu</h2>
            {trips.length > 0 && trips.map((trip) => (
                <div key={trip.id} id={trip.id}>
                    <div><strong>{trip.name}</strong></div>
                    <div>Start: {trip.startDate}</div>
                    <div>End: {trip.endDate}</div>
                </div>

            ))}

            <NewTrip trips={trips} setTrips={setTrips} />



        </>
    );
}
