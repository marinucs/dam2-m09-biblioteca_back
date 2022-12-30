package com.dam2.crud.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dam2.crud.bean.Usuario;
import com.dam2.crud.service.LibroServiceImpl;
import com.dam2.crud.bean.Libro;

@Controller
@RequestMapping("")
public class Controlador {
	Usuario usuario;

	@Autowired
	LibroServiceImpl libroService;
	
	/*
	@GetMapping("/")
	public String iniciar(Model model) {
		model.addAttribute("titulo", "FORMULARIO DE ACCESO");
		return "login";
	}
	
	@PostMapping("/")
	public String login(Usuario usuario, Model model) {
		if (libroService.compruebaUsuario(usuario.getNombre(), usuario.getPassword())) {
			List<Libro> libros = libroService.getLibros();
			model.addAttribute("usuario", usuario);
			this.usuario = usuario;
			model.addAttribute("libros", libros);
			model.addAttribute("libro", null);
			model.addAttribute("boton", "Inserta libro");
			model.addAttribute("action", "/insertar");
			return "consulta";
		} else {
			model.addAttribute("titulo", "FORMULARIO DE ACCESO");
			return "login";
		}
	}
	*/
	
	@PostMapping("/libros/insertar")
	public ResponseEntity<?> inserta(@RequestBody Libro libroDetail) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		libroService.inserta(libroDetail);
		map.put("status", 1);
		map.put("message", "Libro insertado correctamente");
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}
	
	@PutMapping("libros/{id}/modificar")
	public ResponseEntity<?> modificaLibro(@PathVariable int id, @RequestBody Libro libroDetail ) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			Libro libro = libroService.getLibro(id);
			libro.setTitulo(libroDetail.getTitulo());
			libro.setAutor(libroDetail.getAutor());
			libro.setEditorial(libroDetail.getEditorial());
			libro.setFecha(libroDetail.getFecha());
			libro.setTematica(libroDetail.getTematica());
			libroService.modifica(libro);
			map.put("status", 1);
			map.put("data", libroService.getLibro(id));
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception ex) {
			map.clear();
			map.put("status", 0);
			map.put("message", "Libro no encontrado");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("libros/{id}/borrar")
	public ResponseEntity<?> borrar(@PathVariable int id) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			libroService.borrar(id);
			map.put("status", 1);
			map.put("message", "Libro eliminado correctamente");
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception ex) {
			map.clear();
			map.put("status", 0);
			map.put("message", "Libro no encontrado");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/libros/{id}")
	public ResponseEntity<?> getLibro(@PathVariable int id) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			Libro libro = libroService.getLibro(id);
			map.put("status", 1);
			map.put("data", libro);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception ex) {
			map.clear();
			map.put("status", 0);
			map.put("message", "Data is not found");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/libros")
	public ResponseEntity<?> getLibros() {
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Libro> libros = libroService.getLibros();
		if (!libros.isEmpty()) {
			map.put("status", 1);
			map.put("data", libros);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.clear();
			map.put("status", 0);
			map.put("message", "No hay libros");
			return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<Boolean> compruebaUsuario(String usuario, String password) {
		boolean status = libroService.compruebaUsuario(usuario, password);
		if (status) {
			return ResponseEntity.ok(true);
		} else {
			return ResponseEntity.ok(false);
		}
	}

}
