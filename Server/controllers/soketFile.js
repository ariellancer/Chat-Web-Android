let io;

const setIo = (updateIO)=>{
    io=updateIO;
}
const send = (id)=>{
    io.broadcast.emit('newMessage',id);
}
module.exports = {setIo,send}