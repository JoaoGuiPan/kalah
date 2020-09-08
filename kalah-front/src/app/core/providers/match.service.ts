import { Injectable } from '@angular/core';
import { BaseUrls } from 'src/app/model/base-urls.model';
import { HttpClient } from '@angular/common/http';
import { Match } from 'src/app/model/match.model';

const matchUrls = new BaseUrls('matches');

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  constructor(private http: HttpClient) { }

  startGame(southPlayer: string, northPlayer?: string) {
    return this.http.post<Match>(matchUrls.root, { southPlayer, northPlayer });
  }

  getMatchById(id: number | string) {
    return this.http.get<Match>(matchUrls.byId(id));
  }

  executeTurn(matchId: number, houseMoved?: number) {
    return this.http.put<Match>(matchUrls.byId(matchId), { houseMoved });
  }
}
