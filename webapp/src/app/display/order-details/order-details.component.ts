import {Component, OnInit} from '@angular/core';
import {OrderService} from '../../service/order.service';
import {Order} from '../../model/order';
import {ActivatedRoute, Router} from '@angular/router';
import {OrderItem} from '../../model/order-item';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
})

export class OrderDetailsComponent implements OnInit {
  order: Order = new Order();
  orderItems: OrderItem[] = [];
  orderId: number = 0;

  constructor(private route: ActivatedRoute, private router: Router, private orderService: OrderService) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.orderId = Number(this.route.snapshot.paramMap.get('id'));
      this.orderService.getById(String(this.orderId)).subscribe(order => {
        this.orderItems = order.orderItems;
        this.order = order;
      });
    });
  }

  goBack() {
    this.router.navigate(['orders']);
  }

  editOrder() {
    this.router.navigate(['orders/edit/', this.orderId]);
  }

}
