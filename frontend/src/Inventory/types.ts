export interface Item {
    id: string | null,
    name: string,
    category: string
}

export interface AddItem {
    name: string,
    category: string
}

export interface Event<T> {
    payload: T
    command: string
}

export interface Result {
    result: boolean
    info: string
}

export interface Aggregation<S, T> {
    event: Event<T>
    result: Result
    state: S
}