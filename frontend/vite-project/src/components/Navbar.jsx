import {Link} from "react-router-dom";

function Navbar() {
    return (
        <nav className="navbar bg-base-100 shadow-sm">
            <div className="navbar-center">
                <Link to="/" className="btn btn-ghost text-xl">
                    Home
                </Link>
                <Link to="/trip" className="btn btn-ghost text-xl">
                    Trip
                </Link>
            </div>
        </nav>
    );
}

export default Navbar;