import { Component, inject, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Menus, Orders } from '../models';
import { RestaurantService } from '../restaurant.service';
import { MenuStore } from '../menu.store';

@Component({
  selector: 'app-menu',
  standalone: false,
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit{
  // TODO: Task 2

  protected menusItem$!:Observable<Menus[]>
  private restSvc = inject(RestaurantService)
  protected menuStore = inject(MenuStore)
  protected total$!: Observable<number>
  protected totalPrice$!: Observable<number>
  protected cart$!:Observable<Orders[]>
  


  ngOnInit(): void {
    this.menusItem$ = this.restSvc.getMenuItems()
    this.total$ = this.menuStore.getTotalItem
    this.totalPrice$ = this.menuStore.getTotalPrice
    this.cart$ = this.menuStore.getMenus
  }
  remove(item:Menus){
    this.menuStore.removeItem(item)
  }

  add(item:Menus){
    console.info(item)
    this.menuStore.addItem(item)
  }


}
