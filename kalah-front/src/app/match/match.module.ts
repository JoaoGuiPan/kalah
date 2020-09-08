import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppCommonModule } from '../common/app-common.module';
import { MatchComponent } from './match/match.component';
import { BoardComponent } from './match/board/board.component';
import { HouseComponent } from './match/board/house/house.component';

const routes: Routes = [
  {
    path: ':id',
    component: MatchComponent,
  },
];

@NgModule({
  declarations: [MatchComponent, BoardComponent, HouseComponent],
  imports: [
    AppCommonModule,
    RouterModule.forChild(routes),
  ]
})
export class MatchModule { }
