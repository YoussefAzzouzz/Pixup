import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

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
    private apiUrl = `${environment.apiUrl}/games`;

    constructor(private http: HttpClient) { }

    getGames(): Observable<Game[]> {
        return this.http.get<Game[]>(this.apiUrl);
    }

    getGameById(id: number): Observable<Game> {
        return this.http.get<Game>(`${this.apiUrl}/${id}`);
    }
}
