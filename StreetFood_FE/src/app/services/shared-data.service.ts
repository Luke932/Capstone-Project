import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {
  private reloadProfilePage = new EventEmitter<void>();

  get reloadProfilePage$() {
    return this.reloadProfilePage.asObservable();
  }

  triggerProfileReload() {
    this.reloadProfilePage.emit();
  }
}
