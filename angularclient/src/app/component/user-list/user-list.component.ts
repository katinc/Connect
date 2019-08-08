import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../Service/user-service.service';
import {Observable} from "rxjs";


@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  Allusers: Array<any> = [];

  constructor(private userService: UserServiceService) { }

  ngOnInit() {
    this.userService.findAll().subscribe( result => {
      console.log(result);
      this.Allusers = result.data;
    })
    // this.users = this.userService.findAll();
  };
  }


