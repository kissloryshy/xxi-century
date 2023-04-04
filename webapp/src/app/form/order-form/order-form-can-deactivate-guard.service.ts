import {Injectable} from '@angular/core';
import {CanDeactivate} from '@angular/router';
import {OrderFormComponent} from './order-form.component';

@Injectable({
  providedIn: 'root'
})
export class OrderFormCanDeactivateGuardService implements CanDeactivate<OrderFormComponent> {
  canDeactivate(component: OrderFormComponent): boolean {
    if (component.orderForm.dirty && !component.orderForm.submitted) {
      return confirm('Покинуть страницу?');
    }
    return true;
  }
}
