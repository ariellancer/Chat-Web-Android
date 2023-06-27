import Second_page from "./Pages/Second_page";
import {useState} from "react";
import { BrowserRouter, Route, Switch,Routes ,Link} from 'react-router-dom';
import First_page from "./Pages/First_page";
import Third_page from "./Pages/Third_page";
import { useNavigate, Navigate } from 'react-router-dom';
import io from 'socket.io-client';
var socket=io();
function App(){
    const [user, setUser] = useState(null);
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<First_page setUser={setUser} />}/>
                <Route path="/Register" element={<Second_page />} />
                <Route path="/Chat" element={user ? <Third_page user={user} socket={socket} /> : <Navigate to="/" /> } />
            </Routes>
        </BrowserRouter>
    );
}
export default App;