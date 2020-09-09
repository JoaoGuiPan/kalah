import { NgModule } from '@angular/core';
import { Routes, RouterModule, Router, NavigationStart, NavigationEnd, NavigationError } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoaderService } from './core/providers/loader.service';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'match',
    loadChildren: () => import('./match/match.module').then(m => m.MatchModule),
  },
  {
    path: '**',
    redirectTo: '',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
  constructor(router: Router, loaderService: LoaderService) {
    router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        loaderService.show();
      } else if (event instanceof NavigationEnd) {
        loaderService.hide();
      } else if (event instanceof NavigationError) {
        loaderService.hide();
      }
    });
  }
}
