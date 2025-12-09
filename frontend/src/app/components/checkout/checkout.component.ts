import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { OrderService } from '../../services/order.service';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent {
  name: string = '';
  address: string = '';

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private authService: AuthService,
    private router: Router
  ) { }

  placeOrder() {
    if (!this.name || !this.address) {
      alert('Please fill in all fields.');
      return;
    }

    const user = this.authService.currentUser();
    if (!user) {
      alert('You must be logged in to place an order.');
      this.router.navigate(['/login']);
      return;
    }

    // Prepare payload
    // CartService works with signals usually in this project structure
    const cartItems = this.cartService.getItems();
    const gameIds = cartItems.map((item: any) => item.id);

    const payload = {
      username: user.username,
      address: this.address,
      gameIds: gameIds
    };

    this.orderService.placeOrder(payload).subscribe({
      next: (res) => {
        alert('Order placed successfully!');
        this.cartService.clearCart();
        this.router.navigate(['/profile']);
      },
      error: (err) => {
        console.error(err);
        alert('Failed to place order.');
      }
    });
  }
}
