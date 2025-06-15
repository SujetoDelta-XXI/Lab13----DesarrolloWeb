package com.tecsup.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToMany(mappedBy = "cursos")
    @JsonIgnoreProperties("cursos") // ignora solo la propiedad "cursos" de cada profesor
    private Set<Profesor> profesores;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Set<Profesor> getProfesores() { return profesores; }
    public void setProfesores(Set<Profesor> profesores) { this.profesores = profesores; }
}
