package com.web_service_restful.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.web_service_restful.webservice.entities.Aluno;
import com.web_service_restful.webservice.repository.AlunoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    // a) Listar todos os alunos
    @GetMapping
    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    // b) Buscar aluno pelo idaluno
    @GetMapping("/{idaluno}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Integer idaluno) {
        Optional<Aluno> aluno = alunoRepository.findById(idaluno);
        if (aluno.isPresent()) {
            return ResponseEntity.ok(aluno.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // c) Inserir um novo aluno
    @PostMapping
    public Aluno inserir(@RequestBody Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    // d) Atualizar aluno pelo idaluno
    @PutMapping("/{idaluno}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Integer idaluno, @RequestBody Aluno dadosAtualizados) {
        return alunoRepository.findById(idaluno)
                .map(aluno -> {
                    aluno.setNome(dadosAtualizados.getNome());
                    aluno.setSexo(dadosAtualizados.getSexo());
                    aluno.setDt_nasc(dadosAtualizados.getDt_nasc());
                    return ResponseEntity.ok(alunoRepository.save(aluno));
                }).orElse(ResponseEntity.notFound().build());
    }

    // e) Excluir aluno pelo idaluno
   @DeleteMapping("/{idaluno}")
    public ResponseEntity<Aluno> excluir(@PathVariable Integer idaluno) {
        return alunoRepository.findById(idaluno)
                .map(aluno -> {
                    alunoRepository.delete(aluno);
                    return ResponseEntity.ok(aluno);
                }).orElse(ResponseEntity.notFound().build());
    }
}
