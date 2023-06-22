
import './Field.css';
import validity from "./Validity.js";
import {useRef, useState} from "react";

function Field({id, placeholder, type, icon,isValid,setIsValid,isEqual, setIsEqual,rel,keyDown}) {
    // const [message, setMessage] = useState("");

    function handleChange(event) {
        validity(id, setIsValid, event,setIsEqual);
    }

    const handleKeyDown = (e) => {
        if (e.keyCode === 13) {
            keyDown();
        }
    }

    if(id==="Password_loged"){
        return (
            <>
                <div className="input-group flex-nowrap">
                    <div className="input-group-text">{icon}</div>
                    <input
                        id={id}
                        type={type}
                        className="form-control"
                        placeholder={placeholder}
                        onChange={handleChange}
                        onKeyDown={handleKeyDown}
                        required
                        ref={rel}
                    />

                </div>

                {!isValid && id === "Password" && <p className="text-danger">Password must be at least 8 characters long and contain
                    at least one uppercase letter, one lowercase letter, and one number.</p> }
                {!isEqual && id === "Verify Password" && <p className="text-danger">The passwords are not identical, try again.</p>}

            </>
        );
    }

    return (
        <>
            <div className="input-group flex-nowrap">
                <div className="input-group-text">{icon}</div>
                <input
                    id={id}
                    type={type}
                    className="form-control"
                    placeholder={placeholder}
                    onChange={handleChange}
                    required
                    ref={rel}
                />

            </div>

            {!isValid && id === "Password" && <p className="text-danger">Password must be at least 8 characters long and contain
                at least one uppercase letter, one lowercase letter, and one number.</p> }
            {!isEqual && id === "Verify Password" && <p className="text-danger">The passwords are not identical, try again.</p>}

        </>
    );
}

export default Field;
