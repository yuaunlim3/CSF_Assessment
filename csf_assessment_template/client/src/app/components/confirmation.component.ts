import { Component, inject, OnInit } from '@angular/core';
import { SuccessStore } from '../success.store';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { OrderSuccess } from '../models';
import { MenuStore } from '../menu.store';

@Component({
  selector: 'app-confirmation',
  standalone: false,
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.css'
})
export class ConfirmationComponent implements OnInit {

  // TODO: Task 5

  private successStore = inject(SuccessStore)
  private menuStore = inject(MenuStore)
  private router = inject(Router);
  protected result$!:Observable<OrderSuccess>

  ngOnInit(): void {
    this.result$ = this.successStore.loadData
  }

  back(){
    this.router.navigate([""])
    this.successStore.resetData()
    this.menuStore.reset()
  }

  
}
