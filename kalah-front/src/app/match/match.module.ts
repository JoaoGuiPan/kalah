import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppCommonModule } from '../common/app-common.module';
import { MatchComponent } from './match/match.component';
import { BoardComponent } from './match/board/board.component';

const routes: Routes = [
  {
    path: '',
    component: MatchComponent,
  },
];

@NgModule({
  declarations: [MatchComponent, BoardComponent],
  imports: [
    AppCommonModule,
    RouterModule.forChild(routes),
  ]
})
export class MatchModule { }
