const chatsService = require('../service/chats');
const userService = require("../service/user");
const tokenController = require("../controllers/token");
const tokenService = require("../service/token");
var admin = require("firebase-admin");
const fileIo = require("../controllers/soketFile");
var serviceAccount = require("../exe3-ebaa4-firebase-adminsdk-msvuz-58c18ae0de.json");
const {use} = require("express/lib/router");
const {user} = require("../models/user");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
});

const getMessagesAndContactById = async (req, res) => {
    const token = req.headers.authorization.split(' ')[1];
    const username = tokenController.decoding(token);
    var response = null;
    if (username) {
        response = await chatsService.getMessagesAndContactById(req.params.id);
        res.status(200).json(response);
    } else {
        res.status(401).json(response);
    }
}
const deleteMessagesById = async (req, res) => {
    const token = req.headers.authorization.split(' ')[1];
    const username = tokenController.decoding(token);
    if (username !== -1) {
        let receiverUsername = await getUsernameToSend(req.params.id, username.username);
        if (receiverUsername) {
            let foundAndroidToken = await tokenService.getAndroidToken(receiverUsername);
            if (foundAndroidToken) {
                await sendToFirebase(foundAndroidToken, username, req.params.id);
            }
            let foundAndroidTokenSender = await tokenService.getAndroidToken(username.username);
            if (foundAndroidTokenSender) {
                fileIo.deleteFriend(parseInt(req.params.id));
            }
        }
        await chatsService.deleteMessagesById(req.params.id);
        res.status(200).send();
    } else {
        res.status(401).send();
    }
}

const addMessage = async (req, res) => {
    const token = req.headers.authorization.split(' ')[1];
    const username = await tokenController.decoding(token);
    if (username !== -1) {
        const userDetails = await userService.findUser(username.username);
        if (userDetails !== -1) {
            const response = await chatsService.createNewMessageById(req.params.id, userDetails, req.body.msg);
            if (response) {
                let receiverUsername = await getUsernameToSend(req.params.id, username.username);
                if (receiverUsername) {
                    let foundAndroidToken = await tokenService.getAndroidToken(receiverUsername);
                    if (foundAndroidToken) {
                        let androidToken = foundAndroidToken.token;
                        const payload = {
                            notification: {
                                title: receiverUsername,
                                body: response.content
                            },
                            data: {
                                content: response.content,
                                sender: response.sender.displayName,
                                id: req.params.id
                            },
                            token: androidToken
                        }
                        try {
                            await admin.messaging().send(payload);
                        } catch (error) {
                        }
                    }
                    let foundAndroidTokenSender = await tokenService.getAndroidToken(username.username);
                    if (foundAndroidTokenSender) {
                        fileIo.send(parseInt(req.params.id));
                    }
                }
                res.status(200).json(response);
            } else {
                res.status(404).json(response);
            }
        } else {
            res.status(404).json(userDetails);
        }
    } else {
        res.status(401).json(username);
    }
}
const getUsernameToSend = async (id, username) => {
    const foundUsername = await chatsService.getUsernameToSend(id)
    if (!foundUsername) {
        return null;
    }
    if (foundUsername.users[0].username === username) {
        return foundUsername.users[1].username;
    } else {
        return foundUsername.users[0].username;
    }
}
const getMessagesById = async (req, res) => {
    const token = req.headers.authorization.split(' ')[1];
    const username = tokenController.decoding(token);
    if (username != null) {
        const response = await chatsService.getMessagesById(req.params.id);
        res.status(200).json(response);
    } else {
        res.status(404).send();
    }
}
const sendToFirebase = async (foundAndroidToken, username, id) => {
    let androidToken = foundAndroidToken.token;
    const payload = {
        notification: {
            title: username.username,
            body: "d"
        },
        data: {
            username: username.username,
            content: "delete",
            id: id
        },
        token: androidToken
    }
    try {
        await admin.messaging().send(payload);
    } catch (error) {
    }
}
module.exports = {getMessagesAndContactById, deleteMessagesById, addMessage, getMessagesById, sendToFirebase}







