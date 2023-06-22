import {useState} from 'react'
import './First_page.css';
import Field from "../Entering/Field";
import '../Entering/Button.css';
import Text from "../Entering/Text";
import {useRef} from "react";
import Button from "../Entering/Button";
import { useNavigate } from 'react-router-dom';
function First_page({setUser}) {
    const navigate = useNavigate();
    const [errorInLogin,setErrorInLogin] = useState(false);
    const Username = useRef(null);
    const Password = useRef(null);
    async function IsRegistered(){
        const name=Username.current.value;
        const pass=Password.current.value;
            setErrorInLogin(false);
            const forToken = {
                username: name,
                password: pass
            };
            const res = await fetch('http://localhost:5000/api/Tokens', {
                'method': 'post',
                'headers':{
                    'Content-Type': 'application/json',
                },
                'body': JSON.stringify(forToken)
            })
        if(res.status === 200) {
            const token = await res.text();
           // let token = json.substring(1, json.length - 1);
            setUser({username: forToken.username, token: token});
            navigate('/Chat');
        }else if(res.status === 404) {
                setErrorInLogin(true);
        }
    }
  return (
<>
    <title>Login</title>
      <center>
        <div className="block_first">
            <Field id="Username_loged" placeholder="Username" type="text" icon="&#128528;" rel={Username} />
            <Field id="Password_loged" placeholder="Password" type="password" icon="&#128065;" rel={Password} keyDown={IsRegistered}/>
            <Text text1="Not registered?" move="/Register" text2= "to register"/>
            <Button text="Login" name="btn btn-primary log" onClick={IsRegistered} on/>
            {errorInLogin && <p style={{color:'red'}}>{'This username or password are incorrect'}</p>}

        </div>
      </center>
</>
  );
}

export default First_page;
