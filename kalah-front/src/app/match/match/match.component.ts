import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Match } from 'src/app/model/match.model';
import { MatchService } from 'src/app/core/providers/match.service';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';

@Component({
  selector: 'app-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.scss']
})
export class MatchComponent implements OnInit {

  match: Match | null;

  constructor(route: ActivatedRoute,
              @Inject(LOCAL_STORAGE) private storageService: StorageService,
              private router: Router, private matchService: MatchService) {
    route.data.subscribe(value => this.match = value.match);
  }

  ngOnInit() {
  }

  exit() {
    this.storageService.clear();
    this.router.navigateByUrl('');
  }

  forfeit() {
    this.matchService.deactivate(this.match.id).toPromise()
      .then(() => {
        this.exit();
      })
      .catch(err => console.error(err));
  }

}
