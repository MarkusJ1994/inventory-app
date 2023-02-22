import {useQuery} from "react-query";
import {QueryKeys} from "../queryKeys";
import {Aggregation, Item} from "./types";

function ItemAggregationLayout(): JSX.Element {

    const query = useQuery<Aggregation<Item[], Item>[], Error>([QueryKeys.ITEM_AGGREGATION], () =>
        fetch('/inventory/aggregate/steps').then(res => {
                return res.json()
            }
        )
    )

    const onRefresh = () => {
        query.refetch().then()
    }

    const renderSteps = () => {
        if (query.data != undefined) {
            return query.data.map((log, idx) => <pre style={{background: "lightgrey"}} key={idx}>{JSON.stringify(log, undefined, 2)}</pre>)
        }
    }

    return <div className={"col"}>
        <button onClick={onRefresh}>Refresh</button>
        {renderSteps()}
    </div>
}

export default ItemAggregationLayout