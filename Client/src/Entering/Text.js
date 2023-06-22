import './Text.css'
import Field from "./Field";
import {Link} from "react-router-dom";
function Text({text1,move,text2}){
    return(

        <div className="small-text">
            {text1} <Link to={move}>click here</Link> {text2}
        </div>
    );
}
export default Text