package com.example.lab6_20211755.Repositories;

import com.example.lab6_20211755.Entities.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {

}
