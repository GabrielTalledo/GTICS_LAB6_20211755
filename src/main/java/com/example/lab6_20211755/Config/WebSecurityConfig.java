package com.example.lab6_20211755.Config;

import com.example.lab6_20211755.Repositories.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.sql.DataSource;

@SuppressWarnings("removal")
@Configuration
public class WebSecurityConfig {


    final UsuarioRepository usuarioRepository;

    public WebSecurityConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/submitLoginForm")
                .successHandler((request, response, authentication) -> {

                    DefaultSavedRequest defaultSavedRequest =
                            (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");

                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", usuarioRepository.findByCorreo(authentication.getName()));

                    //si vengo por url -> defaultSR existe
                    if (defaultSavedRequest != null) {
                        String targetURl = defaultSavedRequest.getRequestURL();
                        new DefaultRedirectStrategy().sendRedirect(request, response, targetURl);
                    } else { //estoy viniendo del botón de login
                        String rol = "";
                        for (GrantedAuthority role : authentication.getAuthorities()) {
                            rol = role.getAuthority();
                            break;
                        }

                        response.sendRedirect("/mesa/list");
                    }
                });

        http.authorizeHttpRequests()
                .requestMatchers("/mesa/new", "/mesa/edit","/mesa/delete").hasAnyAuthority("ADMIN")
                .requestMatchers("/reserva", "/reserva/list").hasAnyAuthority("GERENTE","CLIENTE")
                .requestMatchers("/reserva/new", "/reserva/delete").hasAnyAuthority("CLIENTE")
                .anyRequest().permitAll();

        http.logout()
                .logoutSuccessUrl("/mesa/list")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);



        return http.build();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        //para loguearse sqlAuth -> username | password | enable
        String sqlAuth = "SELECT correo, password, estado FROM usuarios WHERE correo = ?";

        //para autenticación -> username, nombre del rol
        String sqlAuto = "SELECT u.correo, r.nombre FROM usuarios u INNER JOIN rol r ON u.idRol = r.idRol WHERE u.correo = ?";

        users.setUsersByUsernameQuery(sqlAuth);
        users.setAuthoritiesByUsernameQuery(sqlAuto);

        return users;
    }
}
