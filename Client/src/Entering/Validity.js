function isValidPassword(password) {
    const regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\d]{8,}$/;
    return regex.test(password);
}

function validity(id, setIsValid, event, setIsEqual) {
    if (id === "Password") {
        if(!isValidPassword(event.target.value)) {
            setIsValid(false);
        }else {
            setIsValid(true);
        }
    } else if (id === "Verify Password") {
        if (event.target.value !== document.getElementById("Password").value) {
            setIsEqual(false);
        } else {
            setIsEqual(true);
        }
    }
};
export default validity;