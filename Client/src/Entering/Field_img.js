import './Field_img.css';
import Image from "./Image";
import prof from "./modiin.jpg"
function Field_img({setImage}) {
    const profile_img= (e) =>{
        const file = e.target.files[0];
        if(file){
            const reader = new FileReader();
            reader.onload=(event)=>{
                setImage(event.target.result);
                document.getElementById("Register").src = event.target.result;

            };
            reader.readAsDataURL(file);
        }

    }
    return (
        <>
            <div className="input-group ">
                <input type="file" className="form-control" id="inputGroupFile01" accept="image/*"
                       onChange={profile_img}
                       required/>
            </div>
            <br/>
        </>
    );
}

export default Field_img