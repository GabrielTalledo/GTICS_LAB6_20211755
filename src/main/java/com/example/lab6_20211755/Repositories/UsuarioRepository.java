package com.example.lab6_20211755.Repositories;

import com.example.lab6_20211755.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByCorreo(String correo);

    @Query(nativeQuery = true, value = "SELECT * FROM usuarios WHERE idRol = 3")
    List<Usuario> findClientes();
}
