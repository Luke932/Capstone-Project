import { Component, OnInit } from '@angular/core';
import { RoleService } from './auth/role.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
isLoading = false;
userRole!: string;

constructor (private roleService: RoleService) {}


ngOnInit(): void {
  const userRole = this.roleService.getUserRole();
  }

  title = 'StreetFood_FE';
}
