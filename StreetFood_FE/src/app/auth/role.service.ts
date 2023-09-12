import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private userRole$ = new BehaviorSubject<string>('');

  setUserRole(role: string) {
    this.userRole$.next(role);
  }

  getUserRole$() {
    return this.userRole$.asObservable();
  }
}
