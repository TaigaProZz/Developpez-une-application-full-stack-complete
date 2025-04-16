import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-plain-button',
  templateUrl: './plain-button.component.html',
  styleUrls: ['./plain-button.component.css']
})
export class PlainButtonComponent implements OnInit {

  @Input() textButton: string = 'Button';
  @Input() disabled!: boolean;

  constructor() { }

  ngOnInit(): void {
  }

}
