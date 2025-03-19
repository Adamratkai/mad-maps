
import TripActivity from "../trip-activity/TripActivity.jsx";

function TripDetails({tripDetail: {tripName, startDate, endDate, tripActivities}}) {

    return (
        <div>
            {<div><h2>{tripName}</h2><p>{startDate}</p><p>{endDate}</p>
                <div className="recommendation-container">
                    <div className="flex gap-5 ">
                        {tripActivities.map((activity) => (
                            <TripActivity key={activity.name} tripActivity={activity}/>))}</div></div></div>}</div>)

}

export default TripDetails;