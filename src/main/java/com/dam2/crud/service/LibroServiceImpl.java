package com.dam2.crud.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.dam2.crud.bean.Libro;
import com.dam2.crud.repository.ILibroRepository;

@Service
public class LibroServiceImpl implements ILibroService {
	
	@Autowired
	ILibroRepository libroRepo;
	
	@GetMapping("libros")
	public List<Libro> getLibros() {
		return libroRepo.findAll();
	}

	@PostMapping("/insertar")
	public void inserta(Libro libro) {
		libroRepo.save(libro);
	}

	@DeleteMapping("/borrado/{id}")
	public void borrar(@PathVariable int id) {
		libroRepo.deleteById(id);
	}

	@PostMapping("/modificar/{id}")
	public void modifica(Libro libro) {
		libroRepo.save(libro);
	}

	public Libro getLibro(@PathVariable int id) {
		return libroRepo.findById(id).get();
	}

	@PutMapping
	public boolean compruebaUsuario(String usuario, String password) {
		boolean check = false;

		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			String conex = "jdbc:mysql://localhost:3306/biblioteca_online";
			Connection conexion = DriverManager.getConnection(conex, "root", "");
			Statement s = conexion.createStatement();
			String sqlQuery = "SELECT count(*) FROM usuarios WHERE usuario ='" + usuario + "' " + "AND password='"
					+ password + "'";
			s.execute(sqlQuery);
			ResultSet rs = s.getResultSet();
			rs.next();
			if (rs.getInt(1) > 0)
				check = true;

		} catch (SQLException ex) {
			System.out.print(ex.getMessage());
		}
		return check;
	}

}
