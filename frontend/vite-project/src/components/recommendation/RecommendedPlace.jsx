import React from "react";

const RecommendedPlace = ({ name, price, rating, handlePlaceClick }) => {
    return (
        <div className="recommended-place-item" onClick={handlePlaceClick}>
            <h2>{name}</h2>
            <p><strong>Price:</strong> {price}</p>
            <p><strong>Rating:</strong> {rating}</p>
        </div>
    );
};

export default RecommendedPlace;