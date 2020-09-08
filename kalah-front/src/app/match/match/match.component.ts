import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Match } from 'src/app/model/match.model';

@Component({
  selector: 'app-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.scss']
})
export class MatchComponent implements OnInit {

  match: Match | null;

  constructor(route: ActivatedRoute) {
    route.data.subscribe(value => this.match = value.match);
  }

  ngOnInit() {
  }

}
