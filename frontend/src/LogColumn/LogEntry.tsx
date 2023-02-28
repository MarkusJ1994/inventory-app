import {EventLog} from "./types";
import {useQuery} from "react-query";
import {Item} from "../Inventory/types";
import {QueryKeys} from "../queryKeys";

interface LogEntryProps {
    idx: number
    log: EventLog
}

function LogEntry({idx, log}: LogEntryProps): JSX.Element {

    const query = useQuery<Item[], Error>([QueryKeys.ITEMS], () =>
            fetch('/api/inventory/fold/' + log.id).then(res => {
                    return res.json()
                }
            ),
        {enabled: false}
    )

    return <div style={{background: "lightgrey", marginTop: "1rem", padding: "0.5rem"}}>
        <button onClick={() => query.refetch()}>Replay</button>
        <pre key={idx}>{JSON.stringify(log, undefined, 2)}</pre>
    </div>
}

export default LogEntry