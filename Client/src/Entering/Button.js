
import { useState } from "react";
import './Button.css'

function Button({ text, name,onClick}) {
    return (
        <>
            <button type="button" className={name} onClick={onClick}>
                {text}
            </button>
        </>
    );
}

export default Button;
