import { createContext, useContext, useState } from "react";

export const MarkersContext = createContext(); // ✅ Define the context once

export const MarkersProvider = ({ children }) => { // ✅ Correct provider name
    const [markers, setMarkers] = useState([]);

    return (
        <MarkersContext.Provider value={{ markers, setMarkers }}>
            {children}
        </MarkersContext.Provider>
    );
};

export const useMarkers = () => useContext(MarkersContext);