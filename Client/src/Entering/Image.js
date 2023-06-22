import './Image.css'

function Image({img,id_name}){
    return(
            <img src={img} alt="Image" id={id_name}/>
    );
}
export default Image;