import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title: String;

  git: string;

  constructor() {
    this.title = 'XXI';
    this.git = 'https://github.com/kissloryshy';
  }
}
