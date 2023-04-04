import {Component, OnInit} from '@angular/core';
import {Product} from '../../model/product';
import {ProductService} from '../../service/product.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
})
export class ProductListComponent implements OnInit {
  products: Product[];

  constructor(
    // private route: ActivatedRoute, 
    private router: Router, 
    private productService: ProductService) {
      // this.products = this.route.snapshot.data['productList'];
  }

  ngOnInit() {
    this.productService.getAll().subscribe(
      products => {
        this.products = products;
      }
    )
  }

  public delete(productId: number): void {
    this.productService.delete(productId).subscribe(
      () => this.productService.getAll().subscribe((data: Product[]) => this.products = data));
  }

  public update(productId: number) {
    this.router.navigate(['products/edit', productId]);
  }
  
}
