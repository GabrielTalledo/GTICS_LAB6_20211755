package com.example.lab6_20211755.Repositories;

import com.example.lab6_20211755.Entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM reservas where idMesa = ?1")
    List<Reserva> encontrarReservaPorIdMesa(int idMesa);
}
