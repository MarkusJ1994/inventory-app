import {useQuery} from "react-query";
import "./Inventory.css"
import {Item} from "./types";
import ItemBox from "./ItemBox";
import {QueryKeys} from "../queryKeys";

function Inventory(): JSX.Element {
    const {isLoading, error, data} = useQuery<Item[], Error>([QueryKeys.ITEMS], () =>
        fetch('/inventory').then(res =>
            res.json()
        )
    )

    const renderItems = (items: Item[]) => {
        return items.map(item => <ItemBox item={item}/>)
    }

    if (isLoading) return <div>"Loading..."</div>

    if (error) return <div>"Error occurred: " + error.message</div>

    return <div className={"items"}>
        {renderItems(data != null ? data : [])}
    </div>

}

export default Inventory