import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {Product} from '../../model/product';
import {ProductService} from '../../service/product.service';
import {FormBuilder, NgForm, Validators} from '@angular/forms';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css']
})

export class ProductFormComponent implements OnInit {
  @ViewChild('productForm', {static: false}) public productForm: NgForm;
  product: Product = new Product();
  panelTitle: string;
  private id: number;

  constructor(
    private route: ActivatedRoute, 
    private router: Router, 
    private productService: ProductService){}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.id = Number(this.route.snapshot.paramMap.get('id'));
      if (this.id !== 0) {
        this.panelTitle = 'Изменение товара';
        this.productService.getById(this.id).subscribe(product => {
          this.product = product;
        });
      } else {
        this.panelTitle = 'Добавление товара';
        this.productForm.resetForm();
      }
    });
  }

  onSubmit() {
    if (this.id !== 0) {
      this.productService.update(this.id, this.product).subscribe(value => {
        this.product = Object.assign(new Product(), value)
      });
      
      this.gotoProductList();
    } else {
      this.productService.add(this.product).subscribe(result => this.gotoProductList());
    }
  }

  private gotoProductList() {
    this.router.navigate(['/products']);
  }
  
}
