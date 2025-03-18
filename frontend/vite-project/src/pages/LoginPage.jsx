import React, {useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import {useNavigate} from "react-router";


const LoginPage = () => {
    const [user, setUser] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const [showPopup, setShowPopup] = useState(false);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post("/api/user/login", {user, password});
            setError(null);
            setTimeout(() => {
                setShowPopup(false);
                navigate("/trip");
            }, 2000);
        } catch (err) {
            setError(err);
        }
    };

    return (
        <div>
            <form onSubmit={handleLogin}>
                <fieldset className="fieldset w-xs bg-base-200 border border-base-300 p-4 rounded-box">
                    <legend className="fieldset-legend">Login</legend>
                    <label className="fieldset-label">Username</label>
                    <input type="text"
                           required
                           className="input"
                           placeholder="Username"
                           value={user}
                           onChange={(e) => setUser(e.target.value)}
                    />
                    <label className="fieldset-label">Password</label>
                    <input type="password"
                           required
                           className="input"
                           placeholder="Password"
                           value={password}
                           onChange={(e) => setPassword(e.target.value)}
                    />

                    <Link className="link link-primary" to={"/register"}>Don't have an account? Register here</Link>
                    <submit className="btn btn-neutral mt-4">Login</submit>
                </fieldset>
            </form>
            {error && <p>{error}</p>}
            {showPopup && (
                <dialog id="my_modal_1" className="modal modal-open">
                    <div className="modal-box">
                        <p>Logged in successful! Redirecting...</p>
                    </div>
                </dialog>
            )}
        </div>
    );

}

export default LoginPage;