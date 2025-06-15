package com.tecsup.demo.controller;

import com.tecsup.demo.model.Curso;
import com.tecsup.demo.model.Profesor;
import com.tecsup.demo.repository.CursoRepository;
import com.tecsup.demo.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public String listarProfesores(Model model) {
        model.addAttribute("titulo", "Listado de Profesores");
        model.addAttribute("lista", profesorRepository.findAll());
        return "profesor/listar";
    }

    @GetMapping("/nuevo")
    public String nuevoProfesor(Model model) {
        model.addAttribute("titulo", "Registrar Profesor");
        model.addAttribute("profesor", new Profesor());
        model.addAttribute("cursos", cursoRepository.findAll());
        return "profesor/formulario";
    }

    @PostMapping("/guardar")
    public String guardarProfesor(@ModelAttribute Profesor profesor, @RequestParam(required = false) List<Long> cursoIds) {
        Set<Curso> cursos = new HashSet<>();
        if (cursoIds != null) {
            cursoIds.forEach(id -> cursoRepository.findById(id).ifPresent(cursos::add));
        }
        profesor.setCursos(cursos);
        profesorRepository.save(profesor);
        return "redirect:/profesores";
    }

    @GetMapping("/editar/{id}")
    public String editarProfesor(@PathVariable Long id, Model model) {
        Profesor profesor = profesorRepository.findById(id).orElse(null);
        if (profesor != null) {
            model.addAttribute("titulo", "Editar Profesor");
            model.addAttribute("profesor", profesor);
            model.addAttribute("cursos", cursoRepository.findAll());
            return "profesor/formulario";
        }
        return "redirect:/profesores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProfesor(@PathVariable Long id) {
        profesorRepository.deleteById(id);
        return "redirect:/profesores";
    }
}
