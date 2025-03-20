
import TripActivity from "../trip-activity/TripActivity.jsx";

function TripDetails({tripDetail: {name, startDate, endDate}, activities}) {

    return (
        <div>
            {<div><h2>{name}</h2><p>{startDate}</p><p>{endDate}</p>
                <div className="recommendation-container">
                    <div className="flex gap-5 ">
                        {activities.map((activity) => (
                            <TripActivity key={activity.name} tripActivity={activity}/>))}</div></div></div>}</div>)

}

export default TripDetails;