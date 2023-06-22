const userService = require('../service/user');
const tokenController = require("../controllers/token");
 const addUser = async (req,res)=>{
     const response = await userService.addUser(req.body.username,req.body.password,req.body.displayName,req.body.profilePic);
     if(response === 1){
         res.status(200).send();
     }else{
         res.status(409).send();
     }
 }
 const findUser = async (req,res)=>{
     const token = req.headers.authorization.split(" ")[1];
     const username=tokenController.decoding(token);
     if (username===-1){
         res.status(401).send();
     }else{
        const response = await userService.findUser(req.params.username);
        if (response ===-1){
            res.status(401).send();
        }else{
            res.status(200).json(response);
        }
     }
 }
module.exports = {addUser,findUser}