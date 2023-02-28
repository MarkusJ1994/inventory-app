import {AddItem, Item} from "./types";
import TextField from "../Form/TextField";
import {useEffect, useState} from "react";
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

    const queryClient = useQueryClient()

    const update = useMutation<void, Error, Item>({
        mutationFn: (changedItem) => {
            return fetch('/api/inventory/' + changedItem.id, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(changedItem)
            }).then(() => {
                setIsEditMode(false)
            })
        },
    })

    const remove = useMutation<void, Error, string>( {
        mutationFn: (removedId) => {
            return fetch('/api/inventory/' + removedId, {
                method: 'DELETE',
            }).then(() => {})
        },
    })

    const add = useMutation<void, Error, AddItem>( {
        mutationFn: (itemToAdd) => {
            return fetch('/api/inventory', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(itemToAdd)
            }).then(() => {
                onAddSuccess()
            })
        },
        onSuccess: () => {
            console.log("invalidate query")
            queryClient.invalidateQueries([QueryKeys.ITEMS])
        }
    })

    const [itemState, setItemState] = useState(item)

    useEffect(() => {
        setItemState(item)
    }, [item]);

    const [isEditMode, setIsEditMode] = useState(editMode)

    const onEdit = () => {
        setIsEditMode(!isEditMode)
    }

    const onSave = () => {
        update.mutate(itemState)
    }

    const onRemove = () => {
        if (itemState.id !== null) {
            remove.mutate(itemState.id)
        }
    }

    const onAdd = () => {
        add.mutate({
            name: itemState.name,
            category: itemState.category
        })
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