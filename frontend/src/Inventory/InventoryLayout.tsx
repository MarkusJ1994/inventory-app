import Inventory from "./Inventory";

function InventoryLayout() {

    return <div className={"inventory-layout"}>
        <div className={"button-row"}>
            <button>Add</button>
        </div>
        <div><Inventory/></div>
    </div>
}

export default InventoryLayout