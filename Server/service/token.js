
const modelUser = require('../models/user');

const ifRegister = async (username, password) => {
    try {
        return await modelUser.user.findOne({ username, password });
    } catch (error) {
        return -1 ; // or return an appropriate response
    }
};

module.exports = { ifRegister };
