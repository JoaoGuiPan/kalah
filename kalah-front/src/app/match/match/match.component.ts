import { Component, OnInit } from '@angular/core';
import { MatchService } from 'src/app/core/providers/match.service';
import { ActivatedRoute } from '@angular/router';
import { Match } from 'src/app/model/match.model';

@Component({
  selector: 'app-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.scss']
})
export class MatchComponent implements OnInit {

  match: Match | null;

  constructor(private matchService: MatchService, private route: ActivatedRoute) { }

  async ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    this.match = await this.matchService.getMatchById(id).toPromise();
  }

}
