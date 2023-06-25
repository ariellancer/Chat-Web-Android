const funcContact= require('../service/contacs');
const funcToken=require('../controllers/token');
const allUsers=require('../models/user');
const allChats=require('../models/chats');
const funcUsers=require('../service/user')
var admin = require("firebase-admin");

var serviceAccount = require("../exe3-1ed18-firebase-adminsdk-zdevy-44ca1dfd41.json");
const tokenService = require("../service/token");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
});
const creationOfContacts=async (req,res)=>{
    const token = req.headers.authorization.split(' ')[1];
    const username=funcToken.decoding(token);
    if(username === -1 ) {
        res.status(401).send();
    }else {
        const arrayChats = await funcContact.createContacts(username.username);
        const arrayChats1 = Array.from(arrayChats);
        const contacts = [];
        let i = 0
        for (; i < arrayChats1.length; i++) {
            var name;
            if (arrayChats1[i].users[0].username === username.username) {
                name = arrayChats1[i].users[1];
            }
            if (arrayChats1[i].users[1].username === username.username) {
                name = arrayChats1[i].users[0];
            }

            var mes;
            if (arrayChats1[i].messages.length === 0) {
                mes = null;
            } else {
                mes = {
                    id: arrayChats1[i].messages[arrayChats1[i].messages.length - 1].id,
                    created: arrayChats1[i].messages[arrayChats1[i].messages.length - 1].created,
                    content: arrayChats1[i].messages[arrayChats1[i].messages.length - 1].content
                }
            }
            const contact = {
                id: arrayChats1[i].id,
                user: name,
                lastMessage: mes
            }
            contacts.push(contact);
        }
        res.status(200).json(contacts);
    }
}
const addContact=async (req,res)=>{
    const token = req.headers.authorization.split(' ')[1];
    const valid=funcToken.decoding(token);
    if(valid === -1){
        res.status(401).send();
    }else {
        const requestBody = req.body.username;
        const me = await funcUsers.findUser(valid.username);
        const newFriend = await funcUsers.findUser(requestBody);
        if (newFriend === -1){
            res.status(409).send();
        }else {
            if(me.username !== newFriend.username) {
                const participants = [me, newFriend];
                const newChat = new allChats.chat({
                    id: await funcContact.updateId(allChats.chat),
                    users: participants,
                });
                await newChat.save();
                const myId = await allChats.chat.find({users: participants}).then(async found => {
                    return found.id;
                })
                const returnVal = {
                    id: newChat.id,
                    user: newFriend
                }
                let foundAndroidToken = await tokenService.getAndroidToken(requestBody);
                if (foundAndroidToken) {
                    let androidToken = foundAndroidToken.token;
                    const payload = {
                        notification: null,
                        data: {
                            username: requestBody,
                            content: "add friend",
                            id: newChat.id
                        },
                        token: androidToken
                    }
                    try {
                        await admin.messaging().send(payload);
                    } catch (error) {

                    }
                }
                res.status(200).json(returnVal);
            }else{
                res.status(409).send();
            }
        }
    }
}
module.exports = {creationOfContacts,addContact}