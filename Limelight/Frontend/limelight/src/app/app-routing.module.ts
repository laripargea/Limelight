import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { FavouritesComponent } from './components/favourites/favourites.component';
import { RecommendationsComponent } from './components/recommendations/recommendations.component';
import { VirtualStageComponent } from './components/virtual-stage/virtual-stage.component';
import { PagenotfoundComponent } from './components/pagenotfound/pagenotfound.component';
import { AuthGuard } from './auth.guard'

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'registration', component: RegistrationComponent},
  {path: 'favourites', component: FavouritesComponent, canActivate: [AuthGuard]},
  {path: 'recommendations', component: RecommendationsComponent, canActivate: [AuthGuard]},
  {path: 'virtualStage', component: VirtualStageComponent, canActivate: [AuthGuard]},
  {path: '**', component: PagenotfoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
