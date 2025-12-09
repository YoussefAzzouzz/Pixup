import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Game {
    id: number;
    title: string;
    description: string;
    price: number;
    imageUrl: string;
    platform: string;
    genre: string;
}

@Injectable({
    providedIn: 'root'
})
export class GameService {
    private apiUrl = 'http://localhost:8080/api/games';

    constructor(private http: HttpClient) { }

    getGames(): Observable<Game[]> {
        return this.http.get<Game[]>(this.apiUrl);
    }

    getGameById(id: number): Observable<Game> {
        return this.http.get<Game>(`${this.apiUrl}/${id}`);
    }
}
