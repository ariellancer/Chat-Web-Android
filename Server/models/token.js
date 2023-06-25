const mongoose = require('mongoose')
const Schema = mongoose.Schema;

const UserPass = new Schema({
    username: {
        type: String,
        required: true
    },
    password: {
        type: String ,
        required: true
    }
})
const Token = new Schema({
    username: {
        type: String,
        required: true
    },
    token: {
        type: String,
        required: true
    }
})
const tokenForAndroid = mongoose.model('androidTokens', Token);
module.exports = {UserPass,tokenForAndroid};

