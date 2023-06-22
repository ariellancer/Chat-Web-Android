
import {useState} from "react";
function ListOfFriends(){
    const [listOfFriends ,setFriends] = useState([]);
    return {listOfFriends ,setFriends};
}

export default ListOfFriends;