import { NgModule } from '@angular/core';
import { MatchService } from './providers/match.service';
import { LoaderService } from './providers/loader.service';

@NgModule({
  declarations: [],
  imports: [],
  providers: [
    LoaderService,
    MatchService,
  ]
})
export class CoreModule { }
