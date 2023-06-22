import './Chatbox-input.css'

function ChatboxInput({inputRef, handleClick, currentContact}) {
    const handleKeyDown = (e) => {
        if (e.keyCode === 13) {
            handleClick();
            inputRef.current.value = "";
        }
    }

    const handleButtonClick = () => {
        handleClick();
        inputRef.current.value = "";
    }
    return (
        <>
        {
            currentContact ? <div className="chatbox-input">
                <input type="text" placeholder="Type your message here..." ref={inputRef} onKeyDown={handleKeyDown}/>
                <button type="button" onClick={handleButtonClick}>Send</button>
            </div> : <div className="chatbox-input">
                <input type="text" placeholder="Type your message here..." ref={inputRef}/>
                <button type="button">Send</button>
            </div>
        }
        </>

        // <>
        //     { currentContact != null ? <div className="chatbox-input">
        //         <input type="text" placeholder="Type your message here..." ref={inputRef} onKeyDown={handleKeyDown}/>
        //         <button type="button" onClick={handleButtonClick}>Send</button>
        //     </div> : <></>}
        // </>
    )
}

export default ChatboxInput;