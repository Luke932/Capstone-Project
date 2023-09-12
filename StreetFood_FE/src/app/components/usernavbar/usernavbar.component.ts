import { Component, OnInit } from '@angular/core';
import { RoleService } from 'src/app/auth/role.service';

@Component({
  selector: 'app-usernavbar',
  templateUrl: './usernavbar.component.html',
  styleUrls: ['./usernavbar.component.scss']
})
export class UsernavbarComponent implements OnInit {

  constructor(private roleService: RoleService) {}


  userRole = this.roleService.getUserRole();
  ngOnInit(): void {
  }

}
