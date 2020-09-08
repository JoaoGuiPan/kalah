import { Component, Input, OnInit, Inject } from '@angular/core';
import { Match } from 'src/app/model/match.model';
import { House } from 'src/app/model/house.model';
import { MatchService } from 'src/app/core/providers/match.service';
import { Router } from '@angular/router';
import { CONSTANTS } from 'src/app/common/constants';
import { LOCAL_STORAGE, StorageService } from 'ngx-webstorage-service';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})
export class BoardComponent implements OnInit {

  @Input()
  match: Match | null;

  constructor(private matchService: MatchService, private router: Router,
              @Inject(LOCAL_STORAGE) private storageService: StorageService) { }

  async ngOnInit() {
    if (this.match.currentTurnPlayer === CONSTANTS.COMPUTER_PLAYER) {
      await this.computerPlayMove();
    }
  }

  get northPlayerScore(): number {
    if (this.match) {
      return this.score(this.match.northPlayer);
    }
    return 0;
  }

  get southPlayerScore(): number {
    if (this.match) {
      return this.score(this.match.southPlayer);
    }
    return 0;
  }

  get northPlayerBoard(): House[] {
    if (this.match) {
      return this.playerBoard(this.match.northPlayer, true);
    }
    return [];
  }

  get southPlayerBoard(): House[] {
    if (this.match) {
      return this.playerBoard(this.match.southPlayer);
    }
    return [];
  }

  async playMove(house: House) {
    if (house.playerName === this.match.currentTurnPlayer) {
      this.match = await this.matchService.executeTurn(this.match.id, house.index).toPromise();

      if (this.match.currentTurnPlayer === CONSTANTS.COMPUTER_PLAYER) {
        setTimeout(async () => await this.computerPlayMove(), 1000);
      }

      this.checkGameOver();
    }
  }

  private async computerPlayMove() {
    this.match = await this.matchService.executeTurn(this.match.id).toPromise();

    if (this.match.currentTurnPlayer === CONSTANTS.COMPUTER_PLAYER) {
      setTimeout(async () => await this.computerPlayMove(), 1000);
    }

    this.checkGameOver();
  }

  private score(player: string): number {
    return this.match.board.find(h => h.playerStash && h.playerName === player).numberOfSeeds;
  }

  private playerBoard(player: string, reverse = false): House[] {
    const playerBoard = this.match.board.filter(h => !h.playerStash && h.playerName === player);

    if (reverse) {
      return playerBoard.sort((a, b) => b.index - a.index);
    }

    return playerBoard.sort((a, b) => a.index - b.index);
  }

  private checkGameOver() {
    if (this.match.winner) {
      alert('CONGRATULATIONS ' + this.match.winner + '!!!! Final score: ' + this.northPlayerScore + ' - ' + this.southPlayerScore);
      this.storageService.clear();
      this.router.navigateByUrl('');
    }
  }

}
