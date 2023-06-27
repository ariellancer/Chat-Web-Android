import {useRef} from "react";
function Search({doSearch,searchBox}) {
    const search = function (){
        doSearch(searchBox.current.value);
    }
    return (
        <div className="li">
            <div className="form-outline">
                <input ref={searchBox} onKeyUp={search} id="search-input" placeholder="search chat..." id="form1" className="form-control"/>
            </div>
        </div>
    );
}

export default Search;

