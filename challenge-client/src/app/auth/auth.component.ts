import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
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

  onSubmit(form: NgForm) {
    if (!form.valid) {
      return;
    }
    const username = form.value.username;
    const email = form.value.email;
    const password = form.value.password
    if (this.isLoginMode) {
      this.authService.login(email, password).subscribe(resData => {
        this.router.navigate(['/home']);
      },
      error => {
        this.error = error;
      })
    } else {
      this.authService.signup(username, email, password).subscribe(resData => {
        this.router.navigate(['/home']);
      },
        error => {
          this.error = error;
        });
    }
    form.reset();
  }

}
