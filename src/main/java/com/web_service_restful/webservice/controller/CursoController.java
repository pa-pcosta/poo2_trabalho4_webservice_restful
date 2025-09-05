package com.web_service_restful.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.web_service_restful.webservice.entities.Curso;
import com.web_service_restful.webservice.repository.CursoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    // Listar todos os cursos
    @GetMapping
    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    // Buscar curso por idcurso
    @GetMapping("/{idcurso}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Integer idcurso) {
        Optional<Curso> curso = cursoRepository.findById(idcurso);
        if (curso.isPresent()) {
            return ResponseEntity.ok(curso.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Inserir novo curso
    @PostMapping
    public Curso inserir(@RequestBody Curso curso) {
        return cursoRepository.save(curso);
    }

    // Atualizar curso pelo idcurso
    @PutMapping("/{idcurso}")
    public ResponseEntity<Curso> atualizar(@PathVariable Integer idcurso, @RequestBody Curso dadosAtualizados) {
        return cursoRepository.findById(idcurso)
                .map(curso -> {
                    curso.setNomecurso(dadosAtualizados.getNomecurso());
                    return ResponseEntity.ok(cursoRepository.save(curso));
                }).orElse(ResponseEntity.notFound().build());
    }

    // Excluir curso pelo idcurso
    @DeleteMapping("/{idcurso}")
    public ResponseEntity<Curso> excluir(@PathVariable Integer idcurso) {
        return cursoRepository.findById(idcurso)
                .map(curso -> {
                    cursoRepository.delete(curso);
                    return ResponseEntity.ok(curso);
                }).orElse(ResponseEntity.notFound().build());
    }
}