import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Menus, OrderMade, OrderSuccess } from "./models";
import { firstValueFrom, Observable, Subject } from "rxjs";

Injectable()
export class RestaurantService {

  private http = inject(HttpClient)

  protected orderSuccess !: Subject<OrderSuccess>

  // TODO: Task 2.2
  // You change the method's signature but not the name
  getMenuItems():Observable<Menus[]> {
    return this.http.get<Menus[]>('/api/menu')
  }

  // TODO: Task 3.2

  checkout(order: OrderMade):Promise<OrderSuccess>{
    return firstValueFrom(this.http.post<OrderSuccess>('/api/food_order',order))
  }
}
