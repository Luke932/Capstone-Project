import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../components/home/home.component';
import { ProdottiComponent } from '../components/prodotti/prodotti.component';
import { ProfiloComponent } from '../components/profilo/profilo.component';
import { UsernavbarComponent } from '../components/usernavbar/usernavbar.component';
import { AppComponent } from '../app.component';
import { BrowserModule } from '@angular/platform-browser';


const routes: Routes = [
  {
    path: '',
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent },
      { path: 'prodotti', component: ProdottiComponent },
      { path: 'profilo', component: ProfiloComponent },
    ]
  }
];

@NgModule({
  declarations:[
    UsernavbarComponent,
  HomeComponent,
  ProdottiComponent,
  ProfiloComponent
],
  imports: [
    BrowserModule,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule],
  bootstrap: [AppComponent]
})
export class UserModule { }
