
const MAX_RATING = 5;

function TripActivity({tripActivity: {placeDTO: {name, rating, priceLevel, openingHours, photos}, visitTime}}) {

    return (<div className="dropdown">
            <div tabIndex={0} role="button" className="btn m-1">{name}</div>
            <ul tabIndex={0} className="dropdown-content menu bg-base-100 rounded-box z-1 w-52 p-2 shadow-sm">
                <li><a>{visitTime}</a></li>
                <li><a>Price: {"$".repeat(Math.max(1, Math.floor(priceLevel)))}</a></li>
                <li><a>{[...Array(MAX_RATING)].map((_, index) => (<div
                    key={index}
                    className="mask mask-star"
                    aria-label={`${index + 1} star`}
                    aria-current={index + 1 === Math.round(rating) ? "true" : undefined}
                ></div>))}
                    {rating}</a></li>
                <li><a>{openingHours}</a></li>
            </ul>
        </div>
    );
}

export default TripActivity;