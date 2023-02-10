import {Item} from "./types";
import TextField from "../Form/TextField";
import {useState} from "react";
import {useMutation, useQueryClient} from "react-query";
import {QueryKeys} from "../queryKeys";

interface ItemProps {
    item: Item
}

function ItemBox({item}: ItemProps): JSX.Element {

    let queryClient = useQueryClient();

    const update = useMutation<Item, Error, Item>({
        mutationFn: (changedItem) => {
            return fetch('/inventory/' + changedItem.id, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(changedItem)
            }).then(res => {
                setIsEditMode(false)
                return res.json()
            })
        },
    })

    const remove = useMutation<string, Error, string>({
        mutationFn: (removedId) => {
            return fetch('/inventory/' + removedId, {
                method: 'DELETE',
            }).then(() => removedId)
        },
        onSuccess: (removedId) => {
            queryClient.setQueryData<Item[] | undefined>([QueryKeys.ITEMS], (items) => {
                if (items != undefined) {
                    const idxToRemove = items.findIndex(item => item.id === removedId);
                    return items.splice(idxToRemove, 1)
                }
                return items
            })
        }
    })

    const [itemState, setItemState] = useState(item)

    const [isEditMode, setIsEditMode] = useState(false)

    const onEdit = () => {
        setIsEditMode(!isEditMode)
    }

    const onSave = () => {
        update.mutate(itemState)
    }

    const onRemove = () => {
        remove.mutate(itemState.id)
    }

    return <div className={"item"}>
        {
            update.isLoading ? <div>Saving...</div> : <>
                <div>
                    {
                        isEditMode ?
                            <button onClick={onSave}>{"Save"}</button>
                            : <>
                                <button style={{marginRight: "0.5rem"}} onClick={onEdit}>{"Edit"}</button>
                                <button onClick={onRemove}>Remove</button>
                            </>
                    }
                </div>
                <form className={"item-form"}>
                    <TextField label={"Name"} name={"name"} disabled={!isEditMode} value={item.name}
                               onChange={(val) => setItemState({
                                   ...itemState, name: val
                               })}/>
                    <TextField label={"Category"} name={"category"} disabled={!isEditMode} value={item.category}
                               onChange={(val) => setItemState({
                                   ...itemState, category: val
                               })}/>
                </form>
            </>
        }

    </div>
}

export default ItemBox