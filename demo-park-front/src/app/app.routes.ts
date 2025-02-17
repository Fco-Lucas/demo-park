import { Routes } from '@angular/router';
import { authRoutes } from './pages/auth/auth.routes';

export const routes: Routes = [
  ...authRoutes,
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Rota padrão redireciona para o login
  { path: '**', redirectTo: '/login' }, // Rota curinga redireciona para o login
];