import './Chat_header.css'
import Button from "../Entering/Button";
import Image from "../Entering/Image";


function Chat_header({image, name, currentContact,erase,user}) {

    const deleting = () => {
        let id=currentContact.id;
        erase ({user,id});
    }


    return (
        <div className="chatbox-header">
            { currentContact ?
                <>
                    <div className="image-container"><Image img={image} id_name="current_chat"/></div>
                    <div className="name_header">{name}</div>
                    <Button text="delete" name="delete" onClick={deleting}></Button>
                </>
                : <></> }
        </div>

    )
}

export default Chat_header