interface TextFieldProps {
    name: string
    label: string
    value?: string
    disabled?: boolean
    onChange: (value: string) => void
}

function TextField({label, name, value, disabled, onChange}: TextFieldProps): JSX.Element {

    return (
        <div className={"item-form-field"}>
            <label htmlFor={name}>{label}: </label>
            <input className={"item-form-input"} style={{borderBottom: disabled ? "0px" : "1px solid"}} type={"text"}
                   id={name}
                   disabled={disabled} value={value}
                   onChange={(evt) => {
                       onChange(evt.currentTarget.value)
                   }}/><br/>
        </div>
    )
}

export default TextField