package com.tecsup.demo.controller;

import com.tecsup.demo.model.Curso;
import com.tecsup.demo.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public String listarCursos(Model model) {
        model.addAttribute("titulo", "Listado de Cursos");
        model.addAttribute("lista", cursoRepository.findAll());
        return "curso/listar";
    }

    @GetMapping("/nuevo")
    public String nuevoCurso(Model model) {
        model.addAttribute("titulo", "Registrar Curso");
        model.addAttribute("curso", new Curso());
        return "curso/formulario";
    }

    @PostMapping("/guardar")
    public String guardarCurso(@ModelAttribute Curso curso) {
        cursoRepository.save(curso);
        return "redirect:/cursos";
    }

    @GetMapping("/editar/{id}")
    public String editarCurso(@PathVariable Long id, Model model) {
        Curso curso = cursoRepository.findById(id).orElse(null);
        if (curso != null) {
            model.addAttribute("titulo", "Editar Curso");
            model.addAttribute("curso", curso);
            return "curso/formulario";
        }
        return "redirect:/cursos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable Long id) {
        cursoRepository.deleteById(id);
        return "redirect:/cursos";
    }
}
