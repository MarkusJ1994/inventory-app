import {useQuery} from "react-query";
import {QueryKeys} from "../queryKeys";

interface EventLog {
    timestamp: string
    user: string
    reason: string | null
    command: string
    data: any
}

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
            return query.data.map((log, idx) => <pre key={idx}>{JSON.stringify(log, undefined, 2)}</pre>)
        }
    }

    return <div className={"col"}>
        <button onClick={onRefresh}>Refresh</button>
        {renderLogs()}
    </div>
}

export default LogColumnLayout