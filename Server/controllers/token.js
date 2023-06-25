const tokenService = require('../service/token');
const jwt = require('jsonwebtoken');
const secretKey = 'LOL';
const ifRegister = async(req,res)=>{
    let foundUser = await tokenService.ifRegister(req.body.username,req.body.password);
    if(foundUser){
        const payload = { username:req.body.username, role: 'admin' };
        const token = jwt.sign(payload, secretKey);
        res.status(200).json(token);
    }
    else{
        res.status(404).send("Incorrect username and/or password");
    }
}
const decoding = (token)=>{
    if (token.charAt(0) === '"' && token.charAt(token.length - 1) === '"') {
        token = token.slice(1, -1);
    }
    try {
        return jwt.verify(token, secretKey);
    }catch (error){
        return -1;
    }

}
const addAndroidToken =async (req,res)=>{
    await tokenService.addAndroidToken(req.body.username,req.body.token);
    res.status(200).send("add");
}
const deleteAndroidToken =async (req,res)=>{
    await tokenService.deleteAndroidToken(req.body.username);
    res.status(200).send("delete");
}
module.exports = {ifRegister,decoding,addAndroidToken,deleteAndroidToken}