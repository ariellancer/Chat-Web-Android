const chatsService = require('../service/chats');
const userService = require("../service/user");
const tokenController = require("../controllers/token");
const userModel = require("../models/user");

const getMessagesAndContactById = async (req,res)=>{
    const token = req.headers.authorization.split(' ')[1];
    const username = tokenController.decoding(token);
    var response=null;
    if(username){
        response = await chatsService.getMessagesAndContactById(req.params.id);
        res.status(200).json(response);
    }
    else{
        res.status(401).json(response);
    }
}
const deleteMessagesById = async (req,res)=>{
    const token = req.headers.authorization.split(' ')[1];
    const username = tokenController.decoding(token);
    if(username!==-1){
        await chatsService.deleteMessagesById(req.params.id);
        res.status(200).send();
    }
    else{
        res.status(401).send();
    }
}

const addMessage = async (req,res)=>{
    const token=req.headers.authorization.split(' ')[1];
    const username= await tokenController.decoding(token);
    if(username !== -1) {
        const userDetails = await userService.findUser(username.username);
        if(userDetails !== -1) {
            const response = await chatsService.createNewMessageById(req.params.id, userDetails, req.body.msg);
            if (response) {
                res.status(200).json(response);
            } else {
                res.status(404).json(response);
            }
        }else{
            res.status(404).json(userDetails);
        }
    }else{
        res.status(401).json(username);
    }
}

const getMessagesById = async (req,res) => {
    const token=req.headers.authorization.split(' ')[1];
    const username=tokenController.decoding(token);
    if(username!=null){
        const response = await chatsService.getMessagesById(req.params.id);
        res.status(200).json(response);
    }else{
        res.status(404).send();
    }
}
module.exports = {getMessagesAndContactById,deleteMessagesById,addMessage,getMessagesById}







