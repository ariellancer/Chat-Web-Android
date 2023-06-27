const bodyParser = require('body-parser');
const express = require('express');
const fileIo = require("./controllers/soketFile");
let app = express();
const http=require('http');
const server=http.createServer(app);
const {Server}=require("socket.io");
const io=new Server(server,{
        cors: {
            origin: 'http://localhost:3000'
        }
    }
    );
fileIo.setIo(io);
app.use(bodyParser.urlencoded({extended:true}))
app.use(express.json({ limit: '1000mb' }));

const cors = require('cors');
app.use(cors());

// const customEnv = require('custom-env');
// customEnv.env(process.env.NODE_ENV, './config');

const mongoose = require('mongoose');

mongoose.connect('mongodb://127.0.0.1:27017', {
    useNewUrlParser: true,
    useUnifiedTopology: true
})
    .then(() => {
        // Your code here
    })
    .catch((error) => {
        console.error('Error connecting to MongoDB:', error);
    });

const User = require('./routes/user');
const token = require('./routes/token');
const Contacts = require('./routes/contacts');
app.use(express.static('public'))

io.on('connection',(socket)=>{
    socket.on('newMessage',(id)=>{
        socket.broadcast.emit('newMessage',id);
    })
    socket.on('newContact',()=>{
        socket.broadcast.emit('newContact');
    })
    socket.on('deleteContact',(id)=>{

        socket.broadcast.emit('deleteContact',id);
    })
    socket.on('disconnect',()=>{

    })
})

app.use('/api/Users', User);
app.use('/api/Tokens', token);
app.use('/api/Chats', Contacts);
server.listen(5000);
