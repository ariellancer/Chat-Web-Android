const tokenController = require('../controllers/token');

const express=require('express');

var router=express.Router();
router.route('/').post(tokenController.ifRegister);
router.route('/fireBaseToken').post(tokenController.addAndroidToken);
router.route('/fireBaseToken').delete(tokenController.deleteAndroidToken);
module.exports = router