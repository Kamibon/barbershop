export interface Barber {
    id:number
    name: string
    surname: string
    phoneNumber: string
}

export const barbers: Barber[] = [
    {
        id: 1,
        name: "Mario",
        surname: "Rossi",
        phoneNumber: "3331234567"
    },
    {
        id: 2,
        name: "Luigi",
        surname: "Verdi",
        phoneNumber: "3339876543"
    },
    {
        id: 3,
        name: "Paolo",
        surname: "Bianchi",
        phoneNumber: "3205554444"
    }
]
