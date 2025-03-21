import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { MenuStore } from '../menu.store';
import { Observable, Subscription, tap } from 'rxjs';
import { Menus, Orderdetails, OrderMade, Orders, OrderSuccess } from '../models';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestaurantService } from '../restaurant.service';
import { Router } from '@angular/router';
import { SuccessStore } from '../success.store';

@Component({
  selector: 'app-place-order',
  standalone: false,
  templateUrl: './place-order.component.html',
  styleUrl: './place-order.component.css'
})
export class PlaceOrderComponent implements OnInit, OnDestroy {

  // TODO: Task 3
  private menusStore = inject(MenuStore)
  protected cart!: Orders[]
  protected totalPrice$!: Observable<number>
  private fb = inject(FormBuilder)
  protected form!: FormGroup
  private sub!: Subscription
  private restSvc = inject(RestaurantService)
  private router = inject(Router)
  private successStore = inject(SuccessStore)

  ngOnInit(): void {
    this.sub = this.menusStore.getMenus.pipe(tap(result => this.cart = result)).subscribe()
    this.totalPrice$ = this.menusStore.getTotalPrice
    this.form = this.createForm()
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe()
  }
  submit() {
    const value = this.form.value
    let order: OrderMade = {
      username: value.username,
      password: value.password,
      items: []
    }
    this.cart.map(item => {
      const orderItem: Orderdetails = {
        id: item.id,
        price: item.price,
        quantity: item.quantity,
        total:item.total
      }
      const newItems = order.items
      newItems.push(orderItem)
      order = {
        ...order,
        items: newItems
      }
      
    })

    this.restSvc.checkout(order).then(result =>{
      this.successStore.save(result)
      this.router.navigate(['comfirm'])
    })
    .catch(error => {
      if(error.status == 401){
        alert(error.error.message)
      }
      if(error.status == 500){
        
        alert(error.error.message)
      }
      })
  }

  private createForm(): FormGroup {
    return this.fb.group({
      username: this.fb.control<string>('',Validators.required),
      password: this.fb.control<string>('',Validators.required)
    })
  }

  reset(){
    this.menusStore.reset()
    this.router.navigate([""])
  }

}
