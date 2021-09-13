import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap } from 'rxjs/operators';
import { BehaviorSubject, throwError } from 'rxjs';
import { User } from './user.model';
import { Router } from '@angular/router';

interface AuthResponseData {
  accessToken: string;
  username: string;
  userId: number;
  tokenType: string;
  tokenExpiration: number;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  user = new BehaviorSubject<User>(null);
  private tokenExpirationTimer: any;
  ROOT_URL:string = 'http://Airbusappservice-env.eba-wdga66sv.us-east-1.elasticbeanstalk.com';
  LOCAL_URL:string = 'http://localhost:8081';
  constructor(private http: HttpClient, private router: Router) {}

  signup(username: string, email: string, password: string) {
    return this.http
      .post<AuthResponseData>(this.ROOT_URL+'/airbus/auth/signup', {
        username: username,
        email: email,
        password: password,
      })
      .pipe(
        catchError(this.handleError),
        tap((resData) => {
          this.handleAuthentication(
            resData.username,
            resData.userId,
            resData.accessToken,
            resData.tokenType,
            resData.tokenExpiration
          );
        })
      );
  }

  login(username: string, password: string) {
    return this.http
      .post<AuthResponseData>(this.ROOT_URL+'/airbus/auth/signin', {
        usernameOrEmail: username,
        password: password,
      })
      .pipe(
        catchError(this.handleError),
        tap((resData) => {
          this.handleAuthentication(
            resData.username,
            resData.userId,
            resData.accessToken,
            resData.tokenType,
            resData.tokenExpiration
          );
        })
      );
  }

  autoLogin() {
    const userData: {
      username: string;
      id: number;
      _token: string;
      tokenType: string;
      _tokenExpirationDate: string;
    } = JSON.parse(localStorage.getItem('userData'));
    if (!userData) {
      return;
    }
    const loadedUser = new User(
      userData.username,
      userData.id,
      userData._token,
      userData.tokenType,
      new Date(userData._tokenExpirationDate)
    );

    if (loadedUser.token) {
      this.user.next(loadedUser);
      const expirationDuration =
        new Date(userData._tokenExpirationDate).getTime() -
        new Date().getTime();
      this.autoLogout(expirationDuration);
    }
  }

  logout() {
    this.user.next(null);
    this.router.navigate(['/auth']);
    localStorage.removeItem('userData');
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }

  autoLogout(expirationDuration: number) {
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout();
    }, expirationDuration);
  }

  private handleAuthentication(
    username: string,
    userId: number,
    accessToken: string,
    tokenType: string,
    tokenExpiration: number
  ) {
    const expirationDate = new Date(new Date().getTime() + tokenExpiration);
    const user = new User(
      username,
      userId,
      accessToken,
      tokenType,
      expirationDate
    );
    this.user.next(user);
    this.autoLogout(tokenExpiration * 1000);
    localStorage.setItem('userData', JSON.stringify(user));
  }

  private handleError(errorRes: HttpErrorResponse) {
    let errorMessage = 'An unknown error occured!';
    if (!errorRes.error) {
      return throwError(errorMessage);
    }
    switch (errorRes.error.message) {
      case 'USERNAME_ALREADY_EXIST':
        errorMessage = 'The username already exists';
        break;
      case 'EMAIL_ALREADY_EXIST':
        errorMessage = 'The email id already exists';
        break;
      case 'Bad credentials':
        errorMessage = 'Invalid username/password';
        break;
    }
    if(errorRes.error.status === 401){
      errorMessage = 'Invalid username/password';
    }
    return throwError(errorMessage);
  }
}
