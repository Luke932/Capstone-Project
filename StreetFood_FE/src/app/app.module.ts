import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { RegisterComponent } from './auth/register/register.component';
import { LoginComponent } from './auth/login/login.component';
import { RouterModule, Routes } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthGuard } from './auth/auth.guard.guard';
import { TokenInterceptor } from './auth/token.interceptor.interceptor';
import { HomeModificheComponent } from './components/home.modifiche/home.modifiche.component';
import { AccessoNegatoComponent } from './components/accesso-negato/accesso-negato.component';
import { HomeComponent } from './components/home/home.component';
import { ProdottiComponent } from './components/prodotti/prodotti.component';
import { ProfiloComponent } from './components/profilo/profilo.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { AuthService } from './auth/auth.service.service';
import { RoleService } from './auth/role.service';






const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'access-denied', component: AccessoNegatoComponent },
  {
    path: 'user',
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent },
      { path: 'prodotti', component: ProdottiComponent },
      { path: 'profilo', component: ProfiloComponent },
    ],
    canActivate: [AuthGuard],
    data: { expectedRole: 'USER' }
  },
  {
    path: 'admin',
    children: [
      { path: '', redirectTo: 'homemodifiche', pathMatch: 'full' },
      { path: 'homemodifiche', component: HomeModificheComponent },
      // Altre rotte amministratore...
    ],
    canActivate: [AuthGuard],
    data: { expectedRole: 'ADMIN' }
  },


];

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    HomeModificheComponent,
    HomeComponent,
    ProdottiComponent,
    ProfiloComponent,
    NavbarComponent,
    AccessoNegatoComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    FormsModule,
  ],
  providers: [
    AuthService,
    RoleService,
    {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true,
  },
AuthGuard
],
  bootstrap: [AppComponent]
})
export class AppModule { }
