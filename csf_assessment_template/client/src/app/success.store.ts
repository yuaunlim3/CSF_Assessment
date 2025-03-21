import { Injectable } from "@angular/core";
import {ComponentStore} from "@ngrx/component-store"
import { Menus, MenusStore, Orders, OrderSuccess } from "./models";

const INIT:OrderSuccess = {
    orderId:'',
    paymentId:'',
    total:0,
    timestamp:''
}
@Injectable()
export class SuccessStore extends ComponentStore<OrderSuccess>{

    constructor(){
        super(INIT)
    }

    readonly save = this.updater<OrderSuccess>(
        (current: OrderSuccess, newOrder:OrderSuccess) =>{
            return newOrder
        }
    )

    readonly loadData = this.select(
        (orderSuccess : OrderSuccess) => orderSuccess
    )

    readonly resetData = this.updater(
        () => {
            return{
                orderId:'',
                paymentId:'',
                total:0,
                timestamp:''
            } as OrderSuccess
        }
      )
}