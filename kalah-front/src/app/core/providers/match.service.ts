import { Injectable } from '@angular/core';
import { BaseUrls } from 'src/app/model/base-urls.model';
import { HttpClient } from '@angular/common/http';
import { Match } from 'src/app/model/match.model';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

const matchUrls = new BaseUrls('matches');

@Injectable({
  providedIn: 'root'
})
export class MatchService implements Resolve<Match> {

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

  deactivate(matchId: number) {
    return this.http.get(matchUrls.byId(`${matchId}/deactivate`));
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Match | Observable<Match> | Promise<Match> {
    return this.getMatchById(route.params.id);
  }
}
