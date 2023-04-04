import {CanDeactivate} from '@angular/router';
import {ProductFormComponent} from './product-form.component';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class ProductFormCanDeactivateGuardService implements CanDeactivate<ProductFormComponent> {
  canDeactivate(component: ProductFormComponent): boolean {
    if (component.productForm.dirty && !component.productForm.submitted) {
      return confirm('Покинуть страницу?');
    }
    return true;
  }
}
