import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../components/home/home.component';
import { ProdottiComponent } from '../components/prodotti/prodotti.component';
import { ProfiloComponent } from '../components/profilo/profilo.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'prodotti', component: ProdottiComponent },
  { path: 'profilo', component: ProfiloComponent },
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class UserModule { }
