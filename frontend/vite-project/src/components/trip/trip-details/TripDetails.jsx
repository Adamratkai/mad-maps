import TripActivity from "../trip-activity/TripActivity.jsx";

function TripDetails({tripDetail: {tripName, startDate, endDate}, activities}) {

    return (
        <div>
            {<div><h2>{tripName}</h2><p>{startDate}</p><p>{endDate}</p>
                <div className="recommendation-container">
                    <div className="flex gap-5 ">
                        {activities.map((activity) => (
                            <TripActivity key={activity.placeDTO.name} tripActivity={activity}/>))}</div>
                </div>
            </div>
            }
        </div>
    )

}

export default TripDetails;