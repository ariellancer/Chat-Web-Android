import Contact from "./Contact";
import React from "react";
import './Friends.css'
function Friends({listOfFriends,setCurrentContact,user,updateMessages,currentRef,scroll_contacts}){
    const contactsList = listOfFriends ? listOfFriends.map((friend, key) => {
        if (!friend) {
            return null;
        } else {
            let adapter;
            if (friend.lastMessage != null) {
                const str = friend.lastMessage.created;
                const delimiter = "T";
                const subString = str.split(delimiter);
                const substring1 = subString[1].split(":");
                adapter = {
                    id: friend.id,
                    username: friend.user.username,
                    displayName: friend.user.displayName,
                    dateOfLastMessage: subString[0] + ",",
                    timeOfLastMessage: substring1[0] + ":" + substring1[1],
                    lastMessage: friend.lastMessage.content,
                    img: friend.user.profilePic
                }
            } else {
                adapter = {
                    id: friend.id,
                    username: friend.user.username,
                    displayName: friend.user.displayName,
                    dateOfLastMessage: "",
                    timeOfLastMessage: "",
                    lastMessage: null,
                    img: friend.user.profilePic
                }
            }
            return <Contact {...adapter} key={key} setCurrent={setCurrentContact} user = {user} updateMessages ={updateMessages}
                            currentRef={currentRef}/>
        }
    }) : null;
    return(
        <div className="scroll_contacts" ref={scroll_contacts}>
            {contactsList}
        </div>
    );
}

export default Friends;