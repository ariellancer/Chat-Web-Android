import './Contact.css'
import Image from "../Entering/Image";
import {useState} from "react";

function Contact({id,username,displayName,dateOfLastMessage,timeOfLastMessage,lastMessage,img,setCurrent,user,updateMessages,currentRef}){

    async function set(){
        const curr={
            name: displayName,
            image: img,
            id:id
        };
        currentRef.value=id;
        setCurrent(curr);
        updateMessages({user,id});
    }

    return(
        <div className="chat" onClick={set}>
            <Image img={img} id_name="contact"/>
            <div className="contactName">{displayName}</div>
            <div className="lastMessage">{lastMessage}</div>
            <div className="time">{dateOfLastMessage} {timeOfLastMessage}</div>
        </div>
    )
}

export default Contact;