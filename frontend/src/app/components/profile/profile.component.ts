import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { OrderService } from '../../services/order.service';
import { UserService } from '../../services/user.service';

@Component({
    selector: 'app-profile',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './profile.component.html',
    styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
    user: any = null;
    selectedFile: File | null = null;
    message = '';
    isEditing = false;

    // Clone for editing
    editUser: any = {};
    orders: any[] = [];

    constructor(
        private authService: AuthService,
        private orderService: OrderService,
        private userService: UserService
    ) { }

    ngOnInit() {
        this.user = this.authService.currentUser();
        this.refreshProfile();
        this.fetchOrders();
    }

    fetchOrders() {
        if (this.user) {
            this.orderService.getUserOrders(this.user.username).subscribe({
                next: (data) => {
                    this.orders = data;
                },
                error: (err) => console.error(err)
            });
        }
    }

    refreshProfile() {
        if (this.user) {
            // Fetch latest data
            this.userService.getProfile(this.user.username).subscribe({
                next: (data: any) => {
                    this.user = data;
                    // Update auth service state if needed, or just local
                    // For picture updates to reflect
                },
                error: (err) => console.error(err)
            });
        }
    }

    toggleEdit() {
        this.isEditing = !this.isEditing;
        if (this.isEditing) {
            this.editUser = { ...this.user };
        }
    }

    saveProfile() {
        this.userService.updateProfile(this.user.username, this.editUser).subscribe({
            next: (res: any) => {
                this.user = res;
                this.isEditing = false;
                this.message = 'Profile updated successfully!';
                setTimeout(() => this.message = '', 3000);
            },
            error: (err) => {
                this.message = 'Error updating profile.';
                console.error(err);
            }
        });
    }

    onFileSelected(event: any) {
        this.selectedFile = event.target.files[0];
    }

    uploadPicture() {
        if (!this.selectedFile) return;

        this.userService.uploadProfilePicture(this.user.username, this.selectedFile).subscribe({
            next: (res: any) => {
                this.user = res;
                this.message = 'Profile picture updated!';
                this.selectedFile = null;
                setTimeout(() => this.message = '', 3000);
            },
            error: (err) => {
                this.message = 'Error uploading picture.';
                console.error(err);
            }
        });
    }
}
