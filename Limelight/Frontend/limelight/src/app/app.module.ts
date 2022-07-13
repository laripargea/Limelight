import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'
import { HttpClientModule } from '@angular/common/http'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { FavouritesComponent } from './components/favourites/favourites.component';
import { RecommendationsComponent } from './components/recommendations/recommendations.component';
import { VirtualStageComponent } from './components/virtual-stage/virtual-stage.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgbdSortableHeader } from './services/sortable.directive';
import { RouterModule } from '@angular/router';
import { DecimalPipe } from '@angular/common';
import { ToastsContainerComponent } from './components/toasts-container/toasts-container.component';
import { PagenotfoundComponent } from './components/pagenotfound/pagenotfound.component';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    HomeComponent,
    FavouritesComponent,
    RecommendationsComponent,
    VirtualStageComponent,
    NgbdSortableHeader,
    ToastsContainerComponent,
    PagenotfoundComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    RouterModule
  ],
  providers: [DecimalPipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
