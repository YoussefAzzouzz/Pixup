import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
    import { environment } from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = `${environment.apiUrl}/auth`;

    // Signal to track if user is logged in
    public currentUser = signal<any>(this.getUserFromStorage());

    constructor(private http: HttpClient, private router: Router) { }

    register(user: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/register`, user);
    }

    login(credentials: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/login`, credentials).pipe(
            tap((user: any) => {
                this.setUser(user);
                this.router.navigate(['/']);
            })
        );
    }

    logout() {
        localStorage.removeItem('user');
        this.currentUser.set(null);
        this.router.navigate(['/login']);
    }

    private setUser(user: any) {
        localStorage.setItem('user', JSON.stringify(user));
        this.currentUser.set(user);
    }

    private getUserFromStorage() {
        const userStr = localStorage.getItem('user');
        return userStr ? JSON.parse(userStr) : null;
    }

    isLoggedIn() {
        return this.currentUser() !== null;
    }
}
