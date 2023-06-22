const moduleUser = require('../models/user');
const addUser = async (username, password, displayName, profilePic) => {
    try {
        const foundUser = await moduleUser.user.findOne({ username });
        if (foundUser) {
            return -1;
        } else {
            const NewUser = new moduleUser.user({
                username: username,
                password: password,
                displayName: displayName,
                profilePic: profilePic
            });
            await NewUser.save();
            return 1;
        }
    } catch (error) {
        throw error; // or return an appropriate response
    }
};

const findUser = async (username) => {
    try {
        const foundUser = await moduleUser.user.findOne({ username });
        if (foundUser) {
            return {
                username: foundUser.username,
                displayName: foundUser.displayName,
                profilePic: foundUser.profilePic
            };
        } else {
            return -1;
        }
    } catch (error) {
        return -2;
         // or return an appropriate response
    }
};

module.exports = { addUser, findUser };
