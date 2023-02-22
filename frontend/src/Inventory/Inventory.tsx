import {useQuery} from "react-query";
import "./Inventory.css"
import {Item} from "./types";
import ItemBox from "./ItemBox";
import {QueryKeys} from "../queryKeys";

function Inventory(): JSX.Element {

    const {refetch, isLoading, error, data} = useQuery<Item[], Error>([QueryKeys.ITEMS], () =>
        fetch('/inventory').then(res => {
                return res.json()
            }
        ),
        {enabled: false}
    )

    const renderItems = (items: Item[]) => {
        return items.map((item) => <ItemBox item={item} key={item.id}/>)
    }

    return <>
        <button onClick={() => refetch()} style={{marginBottom: "1rem"}}>Load item state</button>
        { isLoading ? <div>"Loading..."</div> :
            error ? <div>"Error occurred: " + error.message</div> :
                <div className={"items"}>
                    {renderItems(data ?? [])}
                </div>
        }
    </>

}

export default Inventory