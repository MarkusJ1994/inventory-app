export interface EventLog {
    id: string
    timestamp: string
    user: string
    reason: string | null
    command: string
    data: any
}