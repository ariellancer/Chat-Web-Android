
const modelUser = require('../models/user');
const modelToken = require('../models/token');

const ifRegister = async (username, password) => {
    try {
        return await modelUser.user.findOne({ username, password });
    } catch (error) {
        return -1 ; // or return an appropriate response
    }
};
const addAndroidToken = async (username,token) =>{
    try {
         await modelToken.tokenForAndroid.findOneAndDelete({username});

    }catch (error){

    }finally {
        const newToken = new modelToken.tokenForAndroid({
            username: username,
            token: token
        })
        await newToken.save();
    }
}
const deleteAndroidToken = async (username) =>{
    try {
        await modelToken.tokenForAndroid.findOneAndDelete({username});

    }catch (error){

    }
}
const getAndroidToken = async (username) =>{
    try {
        return await modelToken.tokenForAndroid.findOne({username});
    }catch (error){
        return null;
    }
}
module.exports = { ifRegister,addAndroidToken,getAndroidToken,deleteAndroidToken };
