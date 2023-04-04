import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Order} from '../model/order';
import {catchError} from 'rxjs/operators';

@Injectable()

export class OrderService {
  private readonly orderUrl: string;
  headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  constructor(private http: HttpClient) {
    this.orderUrl = 'http://localhost:9000/orders';
  }

  private static handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error('Backend returned code ${error.status},', 'body was: ${error.error}');
    }
    return throwError('Something bad happened; please try again later.');
  }

  public getAll(): Observable<Order[]> {
    return this.http.get<Order[]>(this.orderUrl)
      .pipe(catchError(OrderService.handleError));
  }

  public getById(orderId: string): Observable<Order> {
    console.log(this.orderUrl + '/' + orderId);
    return this.http.get<Order>(`${this.orderUrl}/${orderId}`)
      .pipe(catchError(OrderService.handleError));
  }

  public delete(orderId: number) {
    return this.http.delete(`${this.orderUrl}/${orderId}`)
      .pipe(catchError(OrderService.handleError));
  }

  public update(orderId: number, order: Order): Observable<Object> {
    return this.http.put(`${this.orderUrl}/${orderId}`, order, {headers: this.headers})
      .pipe(catchError(OrderService.handleError));
  }

  public add(order: Order): Observable<Order> {
    return this.http.post<Order>(`${this.orderUrl}/`, order, {headers: this.headers})
      .pipe(catchError(OrderService.handleError));
  }

}
