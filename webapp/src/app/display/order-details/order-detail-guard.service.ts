import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from '@angular/router';
import {OrderService} from '../../service/order.service';
import {Injectable} from "@angular/core";

@Injectable()

export class OrderDetailGuardService {
  constructor(private orderService: OrderService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const order = this.orderService.getById(route.paramMap.get('orderId')).subscribe(value => {
      if (value.orderId) {
        return value;
      } else {
        return null;
      }
    });

    if (order !== null) {
      return true;
    } else {
      this.router.navigate(['not-found']);
      return false;
    }
  }
}
