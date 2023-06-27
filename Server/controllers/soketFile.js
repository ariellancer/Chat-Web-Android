let io;

const setIo = (updateIO)=>{
    io=updateIO;
}
const send = (id)=>{
    io.emit('newMessage',id);
}
const deleteFriend = (id)=>{
    io.emit('deleteContact',id);
}

const addContact = ()=>{
    io.emit('newContact');
}
module.exports = {setIo,send,deleteFriend,addContact}