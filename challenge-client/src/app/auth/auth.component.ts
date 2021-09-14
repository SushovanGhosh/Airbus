import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { from } from 'rxjs';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  @ViewChild('authForm') authForm: NgForm;
  isLoginMode = true;
  error: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  onSwitchMode() {
    this.isLoginMode = !this.isLoginMode;
    this.error = ''; 
    this.authForm.reset();
  }

  // validateForm(form: NgForm) : boolean{  
  //   const username:string = form.value.username;
  //   const email: string = form.value.email;
  //   const password:string = form.value.password;
  //   const confirmPassword: string = form.value.confirmPassword;
  //   if (!this.isLoginMode && username.trim() === '') {
  //     this.error = "Please enter a username";
  //     return false;
  //   }
  //   if (email.trim() === '') {
  //     this.error = "Please enter an email";
  //     return false;
  //   }
  //   if (password.trim() === '') {
  //     console.log("hi")
  //     this.error = "Please enter a password";
  //     return false;
  //   }
  //   if(!this.isLoginMode && confirmPassword.trim() === ''){
  //     this.error = "Please re-write the password";
  //     return false;
  //   }
  //   if(!this.isLoginMode && password === confirmPassword){
  //     this.error = "Passwords doesn't match";
  //     return false;
  //   }
  //   return true;
  // }

  onSubmit(form: NgForm) {
    // if(this.validateForm(form)){
    
    const username = form.value.username;
    const email = form.value.email;
    const password = form.value.password
    const confirmPassword = form.value.confirmPassword;
    if(!this.isLoginMode && password != confirmPassword){
      this.error = "Passwords don't match"
      return;
    }
    if (this.isLoginMode && form.valid) {
      this.authService.login(email, password).subscribe(resData => {
        this.router.navigate(['/home']);
      },
      error => {
        this.error = error;
      })
    } else if(!this.isLoginMode && form.valid) {
      this.authService.signup(username, email, password).subscribe(resData => {
        this.router.navigate(['/home']);
      },
        error => {
          this.error = error;
        });
      }
  }

  
}
