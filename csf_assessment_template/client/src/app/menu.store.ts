import { Injectable } from "@angular/core";
import {ComponentStore} from "@ngrx/component-store"
import { Menus, MenusStore, Orders } from "./models";

const INIT:MenusStore = {
    items:[]
}
@Injectable()
export class MenuStore extends ComponentStore<MenusStore>{

    constructor(){
        super(INIT)
    }

    readonly addItem = this.updater<Menus>(
        (cart:MenusStore, newItem:Menus) => {
            const updatedCart: Orders[] = cart.items.map(menu => {
                if (menu.name == newItem.name) {
                    return {
                        ...menu,
                        quantity: menu.quantity + 1,
                        total: menu.total + newItem.price
                    }
                }

                return menu

            })

            if (!cart.items.some(item => item.name == newItem.name)) {
                const menuItem:Orders = {
                    ...newItem,
                    quantity:1,
                    total:newItem.price
                }
                updatedCart.push(menuItem)
            }

            return {
                ...cart,
                items: updatedCart
            } as MenusStore
        }
    )


    readonly getTotalItem = this.select<number>(
        (cart:MenusStore) =>{
            let count = 0;
            if(cart.items.length > 0){
                cart.items.map(item => count = count + item.quantity)
            }

            return count;
        }
    )

    readonly removeItem = this.updater<Menus>(
        (cart:MenusStore, item:Menus) => {
            console.info(item)
            const updatedCart: Orders[] = cart.items.map(menu => {
                if (menu.name == item.name) {
                    return {
                        ...menu,
                        quantity: menu.quantity - 1,
                        total: menu.total - item.price
                    }
                }

                return menu

            })
            return {
                ...cart,
                items: updatedCart
            } as MenusStore
        }
    )


    readonly getTotalPrice = this.select<number>(
        (cart:MenusStore) =>{
            let count = 0;
            if(cart.items.length > 0){
                cart.items.map(item => count = count + item.price)
            }

            return count;

        }
    )

    readonly getMenus = this.select<Orders[]>(
        (cart:MenusStore) =>cart.items.filter(item => item.quantity > 0)
    )

    readonly reset = this.updater(
        () => {
            console.info("reset")
          return {
            items: []
          } as MenusStore
        }
      )
    
}