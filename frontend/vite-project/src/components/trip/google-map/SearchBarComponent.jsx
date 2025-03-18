import { StandaloneSearchBox } from "@react-google-maps/api";
import { useCallback, useRef, useState } from "react";

const SearchBarComponent = ({ onPlacesChanged }) => {
    const inputRef = useRef(null);
    const [searchBox, setSearchBox] = useState(null);
    
    const onSearchBoxLoad = useCallback((ref) => setSearchBox(ref), []);
  
    return (
      <StandaloneSearchBox onLoad={onSearchBoxLoad} onPlacesChanged={() => onPlacesChanged(searchBox)}>
        <input
          ref={inputRef}
          type="text"
          placeholder="Search for a place..."
          style={{
            width: "100%",
            padding: "10px",
            fontSize: "16px",
            border: "1px solid #ccc",
            borderRadius: "5px",
          }}
        />
      </StandaloneSearchBox>
    );
  };

  export default SearchBarComponent