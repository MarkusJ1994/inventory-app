import Inventory from "./Inventory";
import {Item} from "./types";
import ItemBox from "./ItemBox";
import {useState} from "react";
import LogColumnLayout from "../LogColumn/LogColumnLayout";
import ItemAggregationLayout from "./ItemAggregationLayout";

function InventoryLayout() {

    let [addItem, setAddItem] = useState<Item | undefined>(undefined);

    const onAddClicked = () => {
        setAddItem({
            id: null, category: "", name: ""
        })
    }

    return <div className={"inventory-layout row"}>
        <div className={"col"} style={{maxWidth: "70%", marginRight: "1rem", flexGrow: "1"}}>
            <div className={"button-row"}>
                <button onClick={onAddClicked} disabled={addItem != undefined}>Add</button>
            </div>
            {
                addItem != undefined ?
                    <div className={"items"} style={{marginBottom: "1rem"}}>
                        <ItemBox item={addItem} editMode={true} isAddMode={true}
                                 onAddSuccess={() => setAddItem(undefined)}/>
                    </div>
                    : null
            }
            <div><Inventory/></div>
        </div>
        <div className={"col log-column"}><ItemAggregationLayout/></div>
        <div className={"col log-column"}><LogColumnLayout/></div>
    </div>
}

export default InventoryLayout