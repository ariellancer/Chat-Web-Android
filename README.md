# Getting Started with LOL-chat

## Overview
LOL-chat is a chat application created by Lancer, Omer, and Lidor, the three founders of the chat.

The application allows users to register, log in, and engage in real-time conversations with other users.The application supports user communication using web and android.

This readme provides an overview of the project, instructions on how to run the application, information about the database used, and the requirements to set up and run the application.

## Features

- User Registration: Users can create an account by providing their details such as username, image, and password.

  **Note**: Our unique field that seperates between different users in their username, creation of a user with existing username is not possible.
- User Authentication: Registered users can log in to the application using their credentials.
- Real-time Chat: Once logged in, users can engage in real-time conversations with other users through the chat interface.
- Chat page properties: Adding different users as new contacts (the users must be in the database), deleting contacts from your list and sending messages to all your contacts.
- Uses Io and Firebase in order to update the chat dynamically without refresh.
- Seamless Navigation: The application provides smooth navigation between different pages, allowing users to switch between registration and chat screens effortlessly.

## Screenshots

# Web app

### The First Page:
![image](https://github.com/lidormoryosef/ex2/assets/107669637/f8d01c3a-1db7-459e-87fa-8fbfc9e38ace)

### The Second Page:
![image](https://github.com/lidormoryosef/ex2/assets/107669637/24a4505a-766a-4bfb-8bd2-09cb0131c609)

### The Third Page:
![image](https://github.com/lidormoryosef/ex2/assets/107669637/afe52f42-8ad0-48a2-bd66-e40e75917a1f)

## How to Run 

To run the LOL-chat application, Web version, follow these steps:

1. Clone the project repository to your local machine.

2. Open the terminal or command prompt and navigate to the servers directory.

3. Install the required dependencies by running the following command: npm install.
   
4. Start the application by running the following command: node app.js

5. Once the server is running, open any web browser and navigate to [http://localhost:5000](http://localhost:5000).

6. You will be directed to the first page, where you can either register as a new user or log in if you already have an account.

7. After registration or login, you will be taken to the chat interface where you can start interacting with other users in real-time.

# Android app

<img src="https://github.com/ariellancer/Ex3/assets/107669637/16e19f12-0719-4fc5-8308-a45a00672ce6" width="300">
<img src="[https://github.com/ariellancer/Ex3/assets/107669637/16e19f12-0719-4fc5-8308-a45a00672ce6](https://github.com/ariellancer/Ex3/assets/107669637/cc60d316-2e53-48b9-882a-556e9bdfb52d)" width="300">


## How to Run 

To run the LOL-chat application, android version, follow these steps:

1. Clone the project repository to your local machine.

2. Open the terminal or command prompt and navigate to the servers directory.

3. Install the required dependencies by running the following command: npm install.

4. Start the application by running the following command: node app.js

5. Once the server is running, open the android directory using Android Studio and run the program, either on an emulator or an Android phone.

6. Change the IP in the settings (inside the login page) to match your current IP. If you are using an emulator, the ip should be defaulted to work with the emulator - 10.0.2.2

## Database

LOL-chat utilizes MongoDB as its database to store user and chat information.
MongoDB is a popular NoSQL database that provides scalability and flexibility for managing data.

### Using MongoDB

To use MongoDB with LOL-chat, follow these steps:

1. Install MongoDB by visiting the [MongoDB website](https://www.mongodb.com/) and downloading the latest version suitable for your operating system.

2. Follow the installation instructions provided by MongoDB to set up and configure the database.

3. Start MongoDB by running the appropriate command for your operating system.

4. LOL-chat will automatically connect to the running MongoDB instance and use it to store and retrieve data.

## Requirements

To run LOL-chat, ensure you have the following requirements fulfilled:

1. Node.js: Make sure you have Node.js installed on your system. You can download and install Node.js from the official website: [https://nodejs.org](https://nodejs.org)

2. MongoDB: Install MongoDB to set up the database for LOL-chat. Follow the steps mentioned in the "Using MongoDB" section above to install and
start using and enjoying.


