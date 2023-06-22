import './Second_page.css'
import Field from "../Entering/Field";
import Field_img from "../Entering/Field_img";
import Text from "../Entering/Text";
import Button from "../Entering/Button";
import Image from "../Entering/Image";
import prof from "../Entering/dow.jpg";
import {useRef, useState} from "react";
import { useNavigate } from 'react-router-dom';

function Second_page() {
    const [profileImage, setProfileImage] = useState();
    const navigate = useNavigate();
    const [isValid, setIsValid] = useState(true);
    const [isEqual, setIsEqual] = useState(true);
    const [isExists, setIsExists] = useState(false);
    const [errorInRegister, setErrorInRegister] = useState(true);
    const name = useRef(null);
    const pass = useRef(null);
    const verify = useRef(null);
    const display = useRef(null);
    async function check_empty() {
        const username = name.current.value;
        const password = pass.current.value;
        const verifyPassword = verify.current.value;
        const displayName =display.current.value;
        const fieldImage = profileImage;
        if (username && password && verifyPassword && displayName && isEqual && isEqual && fieldImage) {
                const newUser = {
                    username: username,
                    password: password,
                    displayName: displayName,
                    profilePic: fieldImage
                };
                 const res = await fetch('http://localhost:5000/api/Users', {
                        'method': 'post',
                        'headers':{
                            'Content-Type': 'application/json',
                        },
                        'body': JSON.stringify(newUser)
                    })
                 if(res.status === 200){
                     setErrorInRegister(true);
                     setIsExists(false);
                     navigate('/');
                 }else if(res.status === 409) {
                    setErrorInRegister(true);
                    setIsExists(true);
                    }
        } else {
            setErrorInRegister(false);
        }
    }

    return (
        <>
            <title>Register</title>
            <center>
                <div className="block_sec">
                    <Field id="Username" placeholder="Username" type="text" icon="&#128528;" isValid={isValid}
                           setIsValid={setIsValid} isEqual={isEqual} setIsEqual={setIsEqual} rel={name}/>
                    <Field id="Password" placeholder="Password" type="password" icon="&#128065;" isValid={isValid}
                           setIsValid={setIsValid} isEqual={isEqual} setIsEqual={setIsEqual} rel={pass}/>
                    <Field id="Verify Password" placeholder="Verify Password" type="password" icon="&#128064;"
                           isValid={isValid} setIsValid={setIsValid} isEqual={isEqual} setIsEqual={setIsEqual} rel={verify}/>
                    <Field id="Display name" placeholder="Display name" type="text" icon="&#128527;" isValid={isValid}
                           setIsValid={setIsValid} isEqual={isEqual} setIsEqual={setIsEqual} rel={display}/>
                    <Field_img setImage={setProfileImage}/>
                    <div className="profile_image">
                        <Image img={prof} id_name="Register"/>
                    </div>
                    <Text text1="Registered?" move="/" text2="to login"/>
                    <Button text="Register" name="btn btn-primary reg" onClick={check_empty} />
                    {!errorInRegister && <p>Please fill in all fields</p>}
                    {isExists && <p>Username exists, try another Username</p>}
                </div>
            </center>
        </>
    );
}

export default Second_page;
