import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Game } from './game.service';

@Injectable({
    providedIn: 'root'
})
export class CartService {
    private cartItems = new BehaviorSubject<Game[]>(this.getCartFromStorage());
    cartItems$ = this.cartItems.asObservable();

    addToCart(game: Game) {
        const currentItems = this.cartItems.value;
        const newItems = [...currentItems, game];
        this.cartItems.next(newItems);
        this.saveCartToStorage(newItems);
    }

    removeFromCart(game: Game) {
        const currentItems = this.cartItems.value;
        const index = currentItems.findIndex(item => item.id === game.id);
        if (index > -1) {
            currentItems.splice(index, 1);
            const newItems = [...currentItems];
            this.cartItems.next(newItems);
            this.saveCartToStorage(newItems);
        }
    }

    clearCart() {
        this.cartItems.next([]);
        this.saveCartToStorage([]);
    }

    private saveCartToStorage(items: Game[]) {
        localStorage.setItem('cart', JSON.stringify(items));
    }

    private getCartFromStorage(): Game[] {
        const cartStr = localStorage.getItem('cart');
        return cartStr ? JSON.parse(cartStr) : [];
    }

    getTotal(): number {
        return this.cartItems.value.reduce((acc, item) => acc + item.price, 0);
    }

    getItems(): Game[] {
        return this.cartItems.value;
    }
}
