import {useQuery} from "react-query";
import "./Inventory.css"
import {Item} from "./types";
import ItemBox from "./ItemBox";
import {QueryKeys} from "../queryKeys";

function Inventory(): JSX.Element {

    const {refetch, isLoading, error, data} = useQuery<Item[], Error>([QueryKeys.ITEMS], () =>
        fetch('/api/inventory/fold').then(res => {
                return res.json()
            }
        ),
    )

    return <>
        <button onClick={() => refetch()} style={{marginBottom: "1rem"}}>Load current item state</button>
        { isLoading ? <div>"Loading..."</div> :
            error ? <div>"Error occurred: " + error.message</div> :
                <div className={"items"}>
                    { (data ?? []).map((item) => <ItemBox item={item} key={item.id}/>) }
                </div>
        }
    </>

}

export default Inventory