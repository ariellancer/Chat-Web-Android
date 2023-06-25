const chatsService = require('../service/chats');
const userService = require("../service/user");
const tokenController = require("../controllers/token");
const tokenService = require("../service/token");
var admin = require("firebase-admin");

var serviceAccount = require("../exe3-1ed18-firebase-adminsdk-zdevy-44ca1dfd41.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
});

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
        let receiverUsername = getUsernameToSend(req.params.id,username.username);
        if(receiverUsername) {
            let foundAndroidToken = await tokenService.getAndroidToken(receiverUsername);
            if (foundAndroidToken){
                let androidToken = foundAndroidToken.token;
                const payload = {
                    notification:null,
                    data: {
                        username:username.username,
                        content:"delete",
                        id: req.params.id
                    },
                    token:androidToken
                }
                try{
                    await admin.messaging().send(payload);
                }catch (error){

                }
            }
        }
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
                let receiverUsername = getUsernameToSend(req.params.id,username.username);
                if(receiverUsername) {
                    let foundAndroidToken = await tokenService.getAndroidToken(receiverUsername);
                    if (foundAndroidToken){
                        let androidToken = foundAndroidToken.token;
                        const payload = {
                            notification:{
                                title: receiverUsername,
                                body: response.content
                            },
                            data: {
                                content: response.content,
                                sender: response.sender.displayName,
                                id: req.params.id
                            },
                            token:androidToken
                        }
                        try{
                            await admin.messaging().send(payload);
                        }catch (error){

                        }
                    }
                }
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
const getUsernameToSend = async (id,username)=>{
    const foundUsername = await chatsService.getUsernameToSend(id)
    if (!foundUsername){
        return null;
    }
    if(foundUsername.users[0].username === username){
        return foundUsername.users[0].username;
    }else{
        return foundUsername.users[1].username;
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







