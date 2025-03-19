import {useState} from 'react';
import axios from "axios";


function AddTrip({onTripAdd}) {
    const [newTrip, setNewTrip] = useState({ name: "", startDate: "", endDate: "" });

    function formatDateISO(date) {
        return date.toISOString().split("T")[0];
    }


    async function addTrip(e) {
        e.preventDefault();
        try {
            const response = await axios.post("/api/trips/", newTrip);
            onTripAdd(response.data);
            setNewTrip({ name: "", startDate: "", endDate: "" });
        } catch (error) {
            console.error("Error adding trip:", error);
        }
    }

    function handleChange(e, fieldName) {
        setNewTrip((prevState) => {
            return {...prevState, [fieldName]: e.target.value};
        });
    }

  return (
      <form className="flex flex-col" onSubmit={addTrip}>
          <input
              type="text"
              value={newTrip.name}
              onChange={(e) => handleChange(e, "name") }
              placeholder="Trip Name"
              required
          />
          <input
              type="date"
              min={formatDateISO(new Date())}
              value={newTrip.startDate}
              onChange={(e) => handleChange(e, "startDate") }
              required
          />
          <input
              type="date"
              value={newTrip.endDate}
              onChange={(e) => handleChange(e, "endDate") }
              required
          />
          <button type="submit">Add Trip</button>
      </form>  )
}

export default AddTrip;