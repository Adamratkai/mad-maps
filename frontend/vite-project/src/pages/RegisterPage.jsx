import React, {useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router";

const RegisterPage = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [error, setError] = useState(null);
    const [showPopup, setShowPopup] = useState(false);
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();

        if (password !== confirmPassword) {
            setError("Passwords do not match");
            return;
        }

        try {
            await axios.post("/api/user/register", {username, password});

            setError(null);
            setTimeout(() => {
                setShowPopup(false);
                navigate("/");
            }, 2000);
        } catch (err) {
            setError("Failed to register. Try again.");
            setShowPopup(false);
        }
    };

    return (
        <div>
            <form onSubmit={handleRegister}>
                <fieldset className="fieldset w-xs bg-base-200 border border-base-300 p-4 rounded-box">
                    <legend className="fieldset-legend">Register</legend>
                    <label className="fieldset-label">Username</label>
                    <input
                        type="text"
                        placeholder="Username"
                        className="input"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                    <label className="fieldset-label">Password</label>
                    <input
                        type="password"
                        placeholder="Password"
                        className="input"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <label className="fieldset-label">Password</label>
                    <input
                        type="password"
                        placeholder="Confirm Password"
                        className="input"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        required
                    />
                    <button type="submit" className="btn btn-neutral mt-4">Register</button>
                </fieldset>
            </form>
            {error && <p style={{color: "red"}}>{error}</p>}
            {showPopup && (
                <dialog id="my_modal_1" className="modal modal-open">
                    <div className="modal-box">
                        <p>Registration successful! Redirecting...</p>
                    </div>
                </dialog>
            )}
        </div>
    );
};

export default RegisterPage;
