import './Logout.css'
// import {Link} from 'react-router-dom';
import logout from './logout1.jpg'
import {Link} from "react-router-dom";
function Logout() {
    return (
        <Link to="/">
                <img src={logout} alt="Log out" id="log-out"></img>
            </Link>

);
}
export default Logout;