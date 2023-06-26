let io;

const setIo = (updateIO)=>{
    io=updateIO;
}
const send = (id)=>{
    console.log(id);
    io.emit('newMessage',id);
}
module.exports = {setIo,send}