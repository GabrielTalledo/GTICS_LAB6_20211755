package com.example.lab6_20211755.Repositories;

import com.example.lab6_20211755.Entities.Mesa;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM mesas WHERE disponibles > 0")
    List<Mesa> obtenerMesasDisponibles();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE mesas SET disponibles = disponibles - 1 WHERE idMesa = ?1")
    void disminuirCantidadDisponible(int idDispositivo);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE mesas SET disponibles = disponibles +1 WHERE idMesa = ?1")
    void aumentarCantidadDisponible(int idDispositivo);
}
