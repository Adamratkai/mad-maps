import React, {useState} from 'react'
import axios from "axios";

function NewTrip({trips, setTrips}) {
    const [newTrip, setNewTrip] = useState({ name: "", startDate: "", endDate: "" });

    const formatDateISO = (date) => {
        const isoString = date.toISOString();
        const formattedDate = isoString.split("T")[0];
        return formattedDate;
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewTrip((prevTrip) => ({
            ...prevTrip,
            [name]: value,
        }));
    };

    const addTrip = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post("/api/trips/", newTrip);
            setTrips([...trips, response.data]);
            setNewTrip({ name: "", startDate: "", endDate: "" });
        } catch (error) {
            console.error("Error adding trip:", error);
        }
    };
  return (
      <form onSubmit={addTrip}>
          <input
              type="text"
              name="name"
              value={newTrip.name}
              onChange={handleInputChange}
              placeholder="Trip Name"
              required
          />
          <input
              type="date"
              name="startDate"
              min={formatDateISO(new Date())}
              value={newTrip.startDate}
              onChange={handleInputChange}
              required
          />
          <input
              type="date"
              name="endDate"
              value={newTrip.endDate}
              onChange={handleInputChange}
              required
          />
          <button type="submit">Add Trip</button>
      </form>  )
}

export default NewTrip