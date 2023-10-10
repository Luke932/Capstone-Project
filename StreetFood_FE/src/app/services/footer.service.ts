// footer.service.ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FooterService {
  private showFooter = true;

  constructor() { }

  getShowFooter(): boolean {
    return this.showFooter;
  }

  setShowFooter(value: boolean): void {
    this.showFooter = value;
  }
}
