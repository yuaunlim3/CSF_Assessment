import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { provideHttpClient } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import{RouterModule, Routes} from '@angular/router'

import { AppComponent } from './app.component';
import { MenuComponent } from './components/menu.component';
import { PlaceOrderComponent } from './components/place-order.component';
import { ConfirmationComponent } from './components/confirmation.component';
import { RestaurantService } from './restaurant.service';
import { MenuStore } from './menu.store';
import { SuccessStore } from './success.store';

const routes: Routes = [
  {path:'', component:MenuComponent},
  {path:'placeOrder',component:PlaceOrderComponent},
  {path:'comfirm',component:ConfirmationComponent},
  { path: '**', redirectTo: '', pathMatch: 'full' }
]
@NgModule({
  declarations: [
    AppComponent, MenuComponent, PlaceOrderComponent, ConfirmationComponent
  ],
  imports: [
    BrowserModule, ReactiveFormsModule, RouterModule.forRoot(routes)
  ],
  providers: [provideHttpClient(),RestaurantService,MenuStore,SuccessStore],
  bootstrap: [AppComponent]
})
export class AppModule { }
