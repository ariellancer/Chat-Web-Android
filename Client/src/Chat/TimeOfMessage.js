import './TimeOfMessage.css';

function TimeOfMessage({time,sender}){
        if(sender=='me') {
            return (
                <div>
                    <div className="me_time">{time}</div>
                </div>
            )
        }else {
            return (
                <div>
                    <div className="friend_time">{time}</div>
                </div>
            )
        }


}

export default TimeOfMessage