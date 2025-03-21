// You may use this file to create any models
export interface Menus{
    id:string
    name:string
    description:string
    price:number

}

export interface MenusStore{
    items:Orders[]
}

export interface Orders{
    id:string
    name:string
    description:string
    price:number
    quantity:number
    total:number
}

export interface OrderMade{
    username:string
    password:string
    items:Orderdetails[]
}

export interface Orderdetails{
    id:string
    price:number
    quantity:number
    total:number
}

export interface OrderSuccess{
    orderId:string
    paymentId:string
    total:number
    timestamp:string
}


