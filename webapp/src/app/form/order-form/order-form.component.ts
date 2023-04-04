import {Component, OnInit, ViewChild} from '@angular/core';
import {Order} from '../../model/order';
import {ActivatedRoute, Router} from '@angular/router';
import {OrderService} from '../../service/order.service';
import {ProductService} from '../../service/product.service';
import {Product} from '../../model/product';
import {OrderItem} from '../../model/order-item';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-order-form',
  templateUrl: './order-form.component.html',
})

export class OrderFormComponent implements OnInit {
  @ViewChild('orderForm', {static: false}) public orderForm: NgForm;
  order: Order = new Order();
  products: Product[];
  countProduct: number[] = [];
  searchTerm: string;
  id: number = 0;
  panelTitle: string;

  orderItemsMap: Map<Product, number> = new Map<Product, number>();

  constructor(private route: ActivatedRoute, private router: Router, private orderService: OrderService, private productService: ProductService) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.id = +params.get('id');
      if (this.id !== 0) {
        this.panelTitle = 'Изменение заказа';
        this.orderService.getById(this.id.toString()).subscribe(order => {
          this.order = order;
        });
      } else {
        this.panelTitle = 'Добавление заказа';
        this.orderForm.reset();
      }
    });
    this.productService.getAll().subscribe(data => {
      this.products = data;
    });
  }

  public onSubmit() {
    this.order.orderItems = [];
    let index = 0;
    for (const item of this.orderItemsMap) {
      this.order.orderItems[index] = new OrderItem();
      this.order.orderItems[index].product = new Product();
      this.order.orderItems[index].product.productId = item[0].productId;
      this.order.orderItems[index++].count = item[1];
    }

    if (this.id !== 0) {
      this.orderService.update(this.id, this.order).subscribe(value => {
        this.order = Object.assign(new Order(), value);
        this.goToOrderList();
      });
    } else {
      this.orderService.add(this.order).subscribe(() => this.goToOrderList());
    }
  }

  private goToOrderList() {
    this.router.navigate(['/orders']);
  }

  public addProductToOrder(product: Product, count: number) {
    this.orderItemsMap.set(product, count);
  }

  public deleteProductWithOrder(product: Product) {
    this.orderItemsMap.delete(product);
  }

  public getSum(): number {
    let total = 0;
    for (const product of this.orderItemsMap.keys()) {
      const count = this.orderItemsMap.get(product);
      for (let index = 0; index < count; index++) {
        total += product.price;
      }
    }
    return total;
  }

  public getCount(): number {
    let cnt = 0;
    for (const count of this.orderItemsMap.values()) {
      cnt += count;
    }
    return cnt;
  }
}
