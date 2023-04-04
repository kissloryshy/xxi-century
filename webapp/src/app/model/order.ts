import {OrderItem} from './order-item';
import DateTimeFormat = Intl.DateTimeFormat;

export class Order {
  orderId: number;
  client: string;
  date: DateTimeFormat;
  address: string;
  orderItems: OrderItem[];
}
