import { Component, OnInit } from '@angular/core';
import {homeTextsConstants} from "../../const/HOME_TEXTS";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}

  protected readonly homeTextsConstants = homeTextsConstants;
}
