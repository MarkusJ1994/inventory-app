import {Item} from "./types";
import TextField from "../Form/TextField";
import {useState} from "react";
import {useMutation, useQueryClient} from "react-query";
import {QueryKeys} from "../queryKeys";

interface ItemProps {
    item: Item
    editMode?: boolean
    isAddMode?: boolean
    onAddSuccess?: () => void
}

function ItemBox({
                     item, editMode = false, isAddMode = false, onAddSuccess = () => {
    }
                 }: ItemProps): JSX.Element {

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
                    items.splice(idxToRemove, 1)
                    return items
                }
                return items
            })
        }
    })

    const add = useMutation<Item, Error, Item>({
        mutationFn: (itemToAdd) => {
            return fetch('/inventory', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(itemToAdd)
            }).then(res => {
                onAddSuccess()
                return res.json()
            })
        },
        onSuccess: (addedItem) => {
            queryClient.setQueryData<Item[] | undefined>([QueryKeys.ITEMS], (items) => {
                if (items != undefined) {
                    items.push(addedItem)
                    return items
                }
                return items
            })
        }
    })

    const [itemState, setItemState] = useState(item)

    const [isEditMode, setIsEditMode] = useState(editMode)

    const onEdit = () => {
        setIsEditMode(!isEditMode)
    }

    const onSave = () => {
        update.mutate(itemState)
    }

    const onRemove = () => {
        if (itemState.id != null) {
            remove.mutate(itemState.id)
        }
    }

    const onAdd = () => {
        add.mutate(itemState)
    }

    return <div className={"item"}>
        {
            update.isLoading ? <div>Saving...</div> : <>

                {(isAddMode ? <div>
                    <button onClick={onAdd} type={"submit"}>{"Add"}</button>
                </div> : <div>
                    {
                        isEditMode ?
                            <button onClick={onSave} type={"submit"}>{"Save"}</button>
                            : <>
                                <button style={{marginRight: "0.5rem"}} onClick={onEdit}
                                        type={"button"}>{"Edit"}</button>
                                <button onClick={onRemove}>Remove</button>
                            </>
                    }
                </div>)}

                <form className={"item-form"}>
                    <TextField label={"Name"} name={"name"} disabled={!isEditMode} value={itemState.name}
                               onChange={(val) =>
                                   setItemState({
                                       ...itemState, name: val
                                   })}/>
                    <TextField label={"Category"} name={"category"} disabled={!isEditMode} value={itemState.category}
                               onChange={(val) => setItemState({
                                   ...itemState, category: val
                               })}/>
                </form>
            </>
        }

    </div>
}

export default ItemBox