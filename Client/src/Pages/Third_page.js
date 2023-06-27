import './Third_page.css'
import '../Background/chatbox.css'
import Logout from "../Chat/Logout";
import Message from "../Chat/Messge";
import Messages from "../Chat/Messages";
import Contact from "../Chat/Contact"
import Chat_header from "../Chat/Chat_header";
import ListOfFriends from "../Chat/ListOfFriends";
import ChatboxInput from "../Chat/Chatbox-input";
import User from "../Chat/User";
import Search from "../Chat/Search";
import {useEffect, useRef, useState} from "react";

import React from 'react';
import Friends from "../Chat/Friends";
async function getDataUser(user) {
    const res = await fetch('http://localhost:5000/api/Users/' + user.username, {
        'method': 'get',
        'headers': {
            'Content-Type': 'application/json',
            'authorization': 'bearer ' + user.token,
        },
    });
    const temp = await res.text();
    return JSON.parse(temp);
}


function Third_page({user,socket}) {
    const[messagesOfContact,setMessagesOfContact] = useState([])
    const [currentContact, setCurrentContact] = useState(null);
    const currentRef=useRef(null);
    const contact_ref = useRef(null);
    const errorMsg = useRef(null);
    const [listOfFriends, setFriends] = useState([]);
    const [searchQuery,setSearchQuery] = useState(listOfFriends);
    const scroll_contacts = useRef(null);
    const inputRef = useRef(null);
    const messagesRef = useRef(null);
    const searchBox = useRef(null)
    var counter = 0;
    const [userData, setUserData] = useState(null);

    const doSearch =  (friendTOSearch) =>{
        setSearchQuery(listOfFriends.filter((friend) => friend.user.displayName.includes(friendTOSearch)));
    }
    useEffect(() => {
        const fetchData = async () => {
            const data = await getDataUser(user);
            setUserData(data);
            await getUpdatedContact({user});
        };

        if (counter === 0) {
            fetchData();
            counter++;
        }
        socket.on('newMessage',async (id)=>{
            await getUpdatedContact({user});
            if(currentRef.value === id){
                await updateMessages({user,id});
            }
        })
        socket.on('deleteContact',async (id) => {
            await getUpdatedContact({user});
            if (currentRef.value === id) {
                setCurrentContact(null);
                setMessagesOfContact([]);
            }

        });
        socket.on('newContact', async ()=>{
            await getUpdatedContact({user});
        })
    }, []);
    const messagesList = [];
    let time;
    let date;
    const getUpdatedContact=async ({user})=>{
        const responseChats = await fetch('http://localhost:5000/api/Chats', {
            'method': 'get',
            'headers': {
                'Content-Type': 'application/json',
                'authorization': 'bearer ' + user.token,
            },
        });
        const temp1 = await responseChats.text();
        setFriends([])
        setFriends(prevContact => [...prevContact, ...JSON.parse(temp1)])
        setSearchQuery([])
        setSearchQuery(prevContact => [...prevContact, ...JSON.parse(temp1)])
    };

    const handleClick = async () => {
        searchBox.current.value="";
        const newMessage = {
            msg:inputRef.current.value
        }
        const res = await fetch('http://localhost:5000/api/Chats/' + currentContact.id + '/Messages', {
            'method': 'post',
            'headers':{
                'Content-Type': 'application/json',
                'authorization': 'bearer ' + user.token,
            },
            'body': JSON.stringify(newMessage)
        })
        let id=currentContact.id;
        await updateMessages({user, id})
        await getUpdatedContact({user})
        socket.emit('newMessage',id);

    };
    const updateMessages=async ({user,id})=>{
        const res = await fetch('http://localhost:5000/api/Chats/' + id, {
            'method': 'get',
            'headers': {
                'Content-Type': 'application/json',
                'authorization': 'bearer ' + user.token,
            },
        });
        let message = await res.json();
        setMessagesOfContact([])
        if(message)
            setMessagesOfContact(prevMessages => [...prevMessages, message.messages]);
    };
        for (let i = 0; i < messagesOfContact.length; i++) {
            const message = messagesOfContact[i];
            if (message) {
                for (let j = 0; j < message.length; j++) {
                    const str = message[j].created;
                    const delimiter = "T";
                    const subString = str.split(delimiter);
                    const substring1 = subString[1].split(":");
                    let sender;
                    if (message[j].sender.username === user.username) {
                        sender = "me";
                    } else {
                        sender = "friend";
                    }
                    const adapter = {
                        message: message[j].content,
                        time: substring1[0] + ":" + substring1[1],
                        sender: sender,
                    };
                    messagesList.push(<Message {...adapter} key={`${i}-${j}`}/>);
                }
            }
        }

    async function deleting({user,id}){
        const res = await fetch('http://localhost:5000/api/Chats/'+ id, {
            'method': 'delete',
            'headers': {
                'Accept':'*/*',
                'authorization': 'bearer ' + user.token,
            },
        });
        let message = await res.text();
        setCurrentContact(null);
        setMessagesOfContact([]);
        await getUpdatedContact({user});
        socket.emit('deleteContact',id);
    }
    async function adding() {
        const foundUser = listOfFriends.find(friend => friend.user.username=== contact_ref.current.value);
        if(!foundUser) {
            const addFriend = {
                username: contact_ref.current.value,
            };
            contact_ref.current.value = "";
            const res = await fetch('http://localhost:5000/api/Chats', {
                'method': 'post',
                'headers': {
                    'Content-Type': 'application/json',
                    'authorization': `Bearer ${user.token}`,
                }, 'body': JSON.stringify(addFriend)
            });
            if (res.status === 200) {
                const temp = await res.text();
                const parsedData = JSON.parse(temp);
                setFriends([...listOfFriends, parsedData]);
                setSearchQuery([...listOfFriends, parsedData]);
                contact_ref.current.className = "form-control";
                errorMsg.current.innerText = "";
                socket.emit('newContact');
            } else {
                contact_ref.current.value = "";
                contact_ref.current.className = "form-control is-invalid";
                errorMsg.current.innerText = "invalid username,try again";
            }
        }else{
            contact_ref.current.value = "";
            contact_ref.current.className = "form-control is-invalid";
            errorMsg.current.innerText = "invalid username,try again";
        }
}




    useEffect(() => {
        scroll_contacts.current.scrollTop = scroll_contacts.current.scrollHeight;
    }, [listOfFriends]);


    useEffect(() => {
        messagesRef.current.scrollTop = messagesRef.current.scrollHeight;
    }, [messagesOfContact]);

    return (
        <>
            <title>Chat</title>
            <center>
                <div className="block_third">
                    <div className="chatbox">
                        <Chat_header {...currentContact} currentContact={currentContact} erase={deleting} user={user}/>
                        <div className="chatbox-messages" ref={messagesRef}>
                            {messagesList}
                        </div>
                        <ChatboxInput inputRef={inputRef} handleClick={handleClick} currentContact={currentContact}/>
                    </div>
                    <div className="list">
                        {userData && (
                            <User name={userData.displayName} img={userData.profilePic} contact_ref={contact_ref}
                                  errorMsg={errorMsg} adding={adding}/>
                        )}
                        <Search searchBox ={searchBox} doSearch={doSearch}/>
                        <Friends listOfFriends={searchQuery} setCurrentContact={setCurrentContact} user={user} currentRef={currentRef} scroll_contacts={scroll_contacts} updateMessages={updateMessages}/>
                    </div>
                </div>
            </center>
            <Logout/>
        </>
    );
}

export default Third_page


