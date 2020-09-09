import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppCommonModule } from '../common/app-common.module';
import { MatchComponent } from './match/match.component';
import { BoardComponent } from './match/board/board.component';
import { HouseComponent } from './match/board/house/house.component';
import { MatchService } from '../core/providers/match.service';
import { GameOverDialogComponent } from './match/board/game-over-dialog/game-over-dialog.component';

const routes: Routes = [
  {
    path: '',
    component: MatchComponent,
  },
  {
    path: ':id',
    component: MatchComponent,
    resolve: { match: MatchService }
  },
];

@NgModule({
  declarations: [
    MatchComponent,
    BoardComponent,
    HouseComponent,
    GameOverDialogComponent,
  ],
  imports: [
    AppCommonModule,
    RouterModule.forChild(routes),
  ]
})
export class MatchModule { }
