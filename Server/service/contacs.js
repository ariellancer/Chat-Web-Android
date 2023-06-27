const moduleChat = require('../models/chats');
//const funcToken=require('../controllers/token');
const createContacts = async (username)=>{
    // const releventChats1=await moduleChat.chat.find({ 'users[0].username': username }).exec();
    // const releventChats2=await moduleChat.chat.find({ 'users[1].username': username }).exec();
    return await moduleChat.chat.find({ 'users.username': username }).exec();
}
async function updateId(chat){
    let maxId = await chat.findOne().sort({id:-1}).select('id').lean();
    maxId = maxId ? maxId.id : 0;
    return maxId+1;
}
module.exports={createContacts,updateId};