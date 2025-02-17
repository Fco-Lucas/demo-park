import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { Router, RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  imports: [FormsModule, RouterLink, RouterOutlet],
})
export class RegisterComponent {
  userData = { name: '', email: '', password: '' };

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.register(this.userData).subscribe({
      next: (response) => {
        console.log('Registro bem-sucedido:', response);
        this.router.navigate(['/login']); // Redireciona para o login após o registro
      },
      error: (error) => {
        console.error('Erro no registro:', error);
      },
    });
  }
}