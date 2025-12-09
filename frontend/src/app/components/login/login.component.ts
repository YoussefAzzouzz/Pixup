import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './login.component.html',
    styleUrl: './login.component.css'
})
export class LoginComponent {
    credentials = {
        username: '',
        password: ''
    };
    errorMessage = '';

    constructor(private authService: AuthService) { }

    onSubmit() {
        this.authService.login(this.credentials).subscribe({
            next: () => {
                // Redirect handled in service
            },
            error: (err) => {
                this.errorMessage = 'Login failed. Please check your credentials.';
                console.error('Login error', err);
            }
        });
    }
}
