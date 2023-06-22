
const moduleChat = require('../models/chats');

const getMessagesAndContactById = async (id) => {
    try {
        return await moduleChat.chat.findOne({ id });
    } catch (error) {
        throw error;
    }
};

const deleteMessagesById = async (id) => {
    try {
        return await moduleChat.chat.findOneAndDelete({ id });
    } catch (error) {
        throw error;
    }
};


const createNewMessageById=async(id,userDetails, content)=>{
     return await moduleChat.chat.findOne({id:id}).then(async foundChat=>{
        if(!foundChat){
            return null;
        }else{
            // const Message=new moduleChat(Message);
            const newMessage={
                sender: userDetails,
                content:content
            };
            await foundChat.messages.push(newMessage);
            await foundChat.save();
            // await foundChat.save((saveErr, savedChat) => {
            //     if (saveErr) {
            //         console.error('Error saving chat:', saveErr);
            //
            //     }
            // });
            return newMessage;
        }
    })
}

const getMessagesById = async (id) => {
    await moduleChat.chat.findOne({ id: id }).then(async foundChat=>{
        if (!foundChat) {
            throw new Error('Chat not found');
        }else{
            return foundChat.messages;
        }
    })
};


module.exports={createNewMessageById,getMessagesAndContactById,getMessagesById, deleteMessagesById};
