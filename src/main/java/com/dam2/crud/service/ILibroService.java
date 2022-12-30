package com.dam2.crud.service;

import java.util.List;

import com.dam2.crud.bean.Libro;

public interface ILibroService {
	
	public void inserta(Libro libro); //save
	public void borrar(int id); //deleteById
	public void modifica(Libro libro); //save
	public Libro getLibro(int id); //findById
	public List<Libro> getLibros(); //findAll
	public boolean compruebaUsuario(String usuario, String password); //No

}