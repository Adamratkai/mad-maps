import { useState } from "react";

const MAX_RATING = 5;

function TripActivity({ tripActivity: { placeDTO: { name, rating, priceLevel, openingHours }, visitTime } }) {
    const [isOpen, setIsOpen] = useState(false);

    return (
        <div className="border rounded-lg shadow-sm p-2 bg-base-200">
            <button
                className="w-full text-left font-semibold flex justify-between items-center p-2"
                onClick={() => setIsOpen(!isOpen)}
            >
                {name}
                <span className={`transition-transform ${isOpen ? "rotate-180" : ""}`}>▼</span>
            </button>
            {isOpen && (
                <div className="p-2 bg-base-100 rounded-md mt-2 shadow-inner">
                    <p className="text-sm text-gray-600">Visit Time: {visitTime}</p>
                    <p className="text-sm">Price: {"$".repeat(Math.max(1, Math.floor(priceLevel)))}</p>
                    <div className="flex items-center gap-1">
                        {[...Array(MAX_RATING)].map((_, index) => (
                            <div key={index} className={`mask mask-star ${index < Math.round(rating) ? "bg-yellow-400" : "bg-gray-300"}`}></div>
                        ))}
                        <span className="ml-2">{rating}</span>
                    </div>
                    <p className="text-sm">{openingHours}</p>
                </div>
            )}
        </div>
    );
}

export default TripActivity;