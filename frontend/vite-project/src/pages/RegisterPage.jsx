import React, {useContext, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router";
import {AuthContext} from "./AuthProvider.jsx";

const RegisterPage = () => {
    const {register, error} = useContext(AuthContext);
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [showPopup, setShowPopup] = useState(false);
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        register(username, email, password);
        if (!error) {
            setShowPopup(true);
            setTimeout(() => {
                setShowPopup(false);
                navigate("/");
            }, 2000);
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
                    <label className="fieldset-label">email</label>
                    <input
                        type="text"
                        placeholder="email"
                        className="input"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
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
