import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-house',
  templateUrl: './house.component.html',
  styleUrls: ['./house.component.scss']
})
export class HouseComponent implements OnInit {

  seeds = [];

  @Input()
  playerStash = false;

  @Input()
  scoreAbove = false;

  @Input()
  set numberOfSeeds(numberOfSeeds: number) {
    this.seeds = [];
    for (let i = 0; i < numberOfSeeds; i++) {
      this.seeds.push(0);
    }
  }

  constructor() { }

  ngOnInit(): void {
  }

}
