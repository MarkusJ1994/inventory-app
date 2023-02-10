import InventoryLayout from "./Inventory/InventoryLayout";
import React, {useState} from "react";

export interface ApiResponseCtxtState {
    apiResponseList: string[]
    setApiResponseList: (state: string[]) => void
}

//TODO: Wanted a sidebar with request status, but procrastinating this
export const ApiResponseCtxt = React.createContext<ApiResponseCtxtState>({
        apiResponseList: [], setApiResponseList: () => {
        }
    }
);

function App(): JSX.Element {

    const [apiResponseList, setApiResponseList] = useState<string[]>([]);
    const value = {apiResponseList, setApiResponseList}

    return (
        <ApiResponseCtxt.Provider value={value}>
            <InventoryLayout/>
        </ApiResponseCtxt.Provider>
    )
}

export default App