import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ThemeInterface} from "../../interfaces/theme/theme.interface";

@Component({
  selector: 'app-subscription-card',
  templateUrl: './subscription-card.component.html',
  styleUrls: ['./subscription-card.component.css']
})
export class SubscriptionCardComponent implements OnInit {
  @Input() subscription!: ThemeInterface
  @Input() buttonText!: string;
  @Input() isSubscribed!: boolean;
  @Output() subscribeId = new EventEmitter<number>();

  constructor() { }

  ngOnInit(): void {}

  public onButtonClick(): void {
    this.subscribeId.emit(this.subscription.id);
  }
}
