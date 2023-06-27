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
    },
    token: {
        type: String,
    }
})
const tokenForAndroid = mongoose.model('androidTokens', Token);
module.exports = {UserPass,tokenForAndroid};

