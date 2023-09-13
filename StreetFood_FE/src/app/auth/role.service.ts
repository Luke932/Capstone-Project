import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private userRole$ = new BehaviorSubject<string>('');

  setUserRole(role: string) {
    this.userRole$.next(role);
  }

 // ...
getUserRole$(): Observable<string> {
  const storedRole = localStorage.getItem('userRole');
  if (storedRole) {
    this.userRole$.next(storedRole);
  }
  return this.userRole$.asObservable();
}

}
