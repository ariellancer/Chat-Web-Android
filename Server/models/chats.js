const mongoose = require('mongoose')
const Schema = mongoose.Schema;
const moduleUser = require('../models/user');


const Message=new Schema({
    id: {
        type:Number,
    },
    created: {
        type: Date,
        default: Date.now,
    },
    sender:{
        type:moduleUser.details,
        required:true,
    },
    content: {
        type: String,
        required: true
    },
});


const Chat=new Schema({
    id:{
        type:Number,
        required:true
    },
    users:{
        type:[moduleUser.details]
    },
    messages:{
        type:[Message],
        required:[],
    },

});

const chat = mongoose.model('chats', Chat);
module.exports = {chat,Message};