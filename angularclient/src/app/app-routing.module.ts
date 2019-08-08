import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UserFormComponent} from "./component/user-form/user-form.component";
import {UserListComponent} from "./component/user-list/user-list.component";


const routes: Routes = [
  { path: 'users', component: UserListComponent },
  { path: 'adduser', component: UserFormComponent },
  {
    path: '',
    redirectTo: 'users',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
