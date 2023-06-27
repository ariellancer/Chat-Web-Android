import {useState} from "react";

function Messages(){
    const [messages ,setMessages] = useState([])
    return {messages ,setMessages};

}

export default Messages;
