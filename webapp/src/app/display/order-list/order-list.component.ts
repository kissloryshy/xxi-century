import {Component, OnInit} from '@angular/core';
import {Order} from '../../model/order';
import {OrderService} from '../../service/order.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {
  orders: Order[] = [];

  constructor(private route: ActivatedRoute, private router: Router, private orderService: OrderService) {}

  ngOnInit() {
    this.orderService.getAll().subscribe(data => {
      this.orders = data;
    });
  }

  public delete(orderId: number): void {
    this.orderService.delete(orderId).subscribe(result => this.ngOnInit());
  }

  public update(orderId: number): void {
    this.router.navigate(['orders/edit', orderId]);
  }

  public orderDetails(orderId: number) {
    console.log(orderId);
    this.router.navigate(['orders', orderId]);
  }
  
}
