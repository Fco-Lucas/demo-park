import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { Router, RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  imports: [FormsModule, RouterLink, RouterOutlet],
})
export class LoginComponent {
  credentials = { email: '', password: '' };

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        console.log('Login bem-sucedido:', response);
        this.router.navigate(['/']); // Redireciona para a página inicial após o login
      },
      error: (error) => {
        console.error('Erro no login:', error);
      },
    });
  }
}