package com.dam2.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam2.crud.bean.Libro;

public interface ILibroRepository extends JpaRepository<Libro, Integer> {
	
}