import {Link} from "react-router-dom";

function Navbar() {
    return (
        <nav className="navbar bg-base-100 shadow-sm">
            <div className="navbar-start">
                <Link to="/" className="btn btn-ghost text-xl">
                    Home
                </Link>
                <Link to="/trip" className="btn btn-ghost text-xl">
                    Trip
                </Link>
            </div>
            <div className="navbar-end dropdown dropdown-end">
                <div tabIndex={0} role="button" className="btn btn-ghost btn-circle avatar">
                    <div className="w-10 rounded-full">
                        <img
                            alt="Tailwind CSS Navbar component"
                            src="https://img.daisyui.com/images/stock/photo-1534528741775-53994a69daeb.webp" />
                    </div>
                </div>
                <ul
                    tabIndex={0}
                    className="menu menu-md dropdown-content bg-base-100 rounded-box z-1 mt-3 w-52 p-2 shadow">

                    <li><Link to="/login">Login</Link></li>
                    <li><Link to="/register">Register</Link></li>
                </ul>
            </div>
        </nav>
    );
}

export default Navbar;