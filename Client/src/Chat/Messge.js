import './Message.css'
import TimeOfMessage from "./TimeOfMessage";

function Message({message, time,sender}){
    return(
        <div className={sender}>
            {message}
            <TimeOfMessage time={time} sender={sender}/>
        </div>
    )
}
export default Message