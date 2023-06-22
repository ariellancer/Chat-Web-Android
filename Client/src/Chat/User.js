
import './User.css'
import Image from "../Entering/Image";
import {useRef, useState} from "react";
import Button from "../Entering/Button";
import x from "./modiin.jpg"
import lidi from "./lidor_shnichel.jpg";
import ListOfFriends from "./ListOfFriends";
// export function ListOfFriends(){
//     const [listOfFriends,setFriends]=useState([{name:"aaa",dateOfLastMessage:"3",timeOfLastMessage:"3",img:lidi}]);
//     return {listOfFriends,setFriends};
// }

function User({name,img,contact_ref,errorMsg,adding}){
    const fixing = () => {
        contact_ref.current.className="form-control";
    }
    const enter = (e)=>{
        if(e.keyCode===13){
            adding();
        }
    }
    return(

        <>
         <div className="chat_user">
             <Image img={img} id_name="user"/>
            <div className="list-header">{name}
                <a className="add-emoji" data-bs-toggle="modal" data-bs-target="#exampleModal">
                <button className="btn_add" type="button" data-bs-target="#exampleModal"><span role="img" aria-label="telescope">&#128101;</span> </button>

                </a>
            </div>

         </div>
            <div className="modal fade" id="exampleModal" tabIndex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h1 className="modal-title fs-5" id="exampleModalLabel">Adding a contact</h1>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <center>
                            <div className="mb-3">
                                <input type="text" className="form-control" id="exampleFormControlInput1"
                                       width="80%" ref={contact_ref} placeholder="Enter a friend" onKeyDown={enter}/>
                                <span ref={errorMsg} className="help-block"></span>
                            </div>
                        </center>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary"
                                    data-bs-dismiss="modal" onClick={fixing}>Close
                            </button>
                            <Button text="Add" name="btn btn-primary" onClick={adding}></Button>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}
export default User;