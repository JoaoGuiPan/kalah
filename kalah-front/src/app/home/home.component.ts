import { Component, OnInit } from '@angular/core';
import { MatchService } from '../core/providers/match.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private matchService: MatchService, private router: Router) { }

  ngOnInit(): void {
  }

  async startSinglePlayer() {
    // TODO NAME
    const match = await this.matchService.startGame('Player 1').toPromise();
    this.router.navigateByUrl(`match/${match.id}`);
  }

  async startLocalMultiplayer() {
    // TODO NAMES
    const match = await this.matchService.startGame('Player 1', 'Player 2').toPromise();
    this.router.navigateByUrl(`match/${match.id}`);
  }

}
