import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-register',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './register.component.html',
    styleUrl: '../login/login.component.css' // Reuse login styles
})
export class RegisterComponent {
    user = {
        username: '',
        email: '',
        password: ''
    };
    errorMessage = '';

    constructor(private authService: AuthService, private router: Router) { }

    onSubmit() {
        this.authService.register(this.user).subscribe({
            next: () => {
                alert('Registration successful! Please login.');
                this.router.navigate(['/login']);
            },
            error: (err) => {
                this.errorMessage = err.error?.message || 'Registration failed.';
                console.error('Registration error', err);
            }
        });
    }
}
