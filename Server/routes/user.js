const userController = require('../controllers/user');

const express=require('express');

const router=express.Router();
router.route('/').post(userController.addUser);
router.route('/:username').get(userController.findUser);
module.exports = router;