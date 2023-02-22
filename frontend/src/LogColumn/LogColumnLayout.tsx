import {useQuery} from "react-query";
import {QueryKeys} from "../queryKeys";
import {EventLog} from "./types";
import LogEntry from "./LogEntry";

function LogColumnLayout(): JSX.Element {

    const query = useQuery<EventLog[], Error>([QueryKeys.EVENT_LOGS], () =>
        fetch('/events').then(res => {
                return res.json()
            }
        )
    )

    const onRefresh = () => {
        query.refetch().then()
    }

    const renderLogs = () => {
        if (query.data != undefined) {
            return query.data.map((log, idx) => <LogEntry idx={idx} log={log}/>)
        }
    }

    return <div className={"col"}>
        <button onClick={onRefresh}>Refresh</button>
        {renderLogs()}
    </div>
}

export default LogColumnLayout