import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Game } from '../../services/game.service';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-game-card',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './game-card.component.html',
  styleUrl: './game-card.component.css'
})
export class GameCardComponent {
  @Input() game!: Game;

  constructor(private cartService: CartService) { }

  addToCart() {
    this.cartService.addToCart(this.game);
    alert('Added to cart!');
  }
}
