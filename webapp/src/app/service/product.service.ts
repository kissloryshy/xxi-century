import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';

import {Product} from '../model/product';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable()

export class ProductService {
  private readonly productsUrl: string;
  headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  constructor(private http: HttpClient) {
    this.productsUrl = 'http://localhost:9000/products';
  }

  private static handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error('Backend returned code ${error.status},', 'body was: ${error.error}');
    }
    return throwError('Something bad happened; please try again later.');
  }

  public getAll(): Observable<Product[]> {
    return this.http.get<Product[]>(this.productsUrl)
      .pipe(catchError(ProductService.handleError));
  }

  public getById(productId: number): Observable<Product> {
    return this.http.get<Product>(`${this.productsUrl}/${productId}`)
      .pipe(catchError(ProductService.handleError));
  }

  public add(product: Product): Observable<Product> {
    return this.http.post<Product>(`${this.productsUrl}/`, product,
      {headers: this.headers})
      .pipe(catchError(ProductService.handleError));
  }

  public delete(productId: number) {
    console.log(`${this.productsUrl}/${productId}`);
    return this.http.delete(`${this.productsUrl}/${productId}`)
      .pipe(catchError(ProductService.handleError));
  }

  public update(productId: number, product: Product): Observable<Object> {
    return this.http.put(`${this.productsUrl}/${productId}`, product,
      {headers: this.headers})
      .pipe(catchError(ProductService.handleError));
  }

}
