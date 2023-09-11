import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../components/home/home.component';
import { HomeModificheComponent } from '../components/home.modifiche/home.modifiche.component';

const routes: Routes = [
  { path: 'homemodifiche', component: HomeModificheComponent },

  // Altre rotte amministratore...
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class AdminModule { }
