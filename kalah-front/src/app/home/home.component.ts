import { Component, OnInit, Inject } from '@angular/core';
import { MatchService } from '../core/providers/match.service';
import { Router } from '@angular/router';
import {LOCAL_STORAGE, StorageService} from 'ngx-webstorage-service';

const EXISTING_MATCH = 'EXISTING_MATCH';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  currentMatch: string | null;

  constructor(@Inject(LOCAL_STORAGE) private storageService: StorageService,
              private matchService: MatchService, private router: Router) {
     this.currentMatch = this.storageService.get(EXISTING_MATCH);
  }

  ngOnInit(): void {
  }

  resumeMatch() {
    this.router.navigateByUrl(`match/${this.currentMatch}`);
  }

  async startSinglePlayer() {
    const match = await this.matchService.startGame('Player 1').toPromise();
    this.storageService.set(EXISTING_MATCH, match.id);
    this.router.navigateByUrl(`match/${match.id}`);
  }

  async startLocalMultiplayer() {
    const match = await this.matchService.startGame('Player 1', 'Player 2').toPromise();
    this.storageService.set(EXISTING_MATCH, match.id);
    this.router.navigateByUrl(`match/${match.id}`);
  }

}
