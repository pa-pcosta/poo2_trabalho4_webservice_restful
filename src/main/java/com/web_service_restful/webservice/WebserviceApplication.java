package com.web_service_restful.webservice;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.web_service_restful.webservice.entities.Aluno;
import com.web_service_restful.webservice.repository.AlunoRepository;

@SpringBootApplication
public class WebserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebserviceApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public CommandLineRunner printTestes(AlunoRepository alunoRepository) {
        return args -> {

            Aluno novoAluno = new Aluno();
            novoAluno.setNome("Pedro A. P. Costa");
            novoAluno.setSexo("Masculino");
            novoAluno.setDt_nasc(LocalDate.of(2000, 1, 19));

            System.out.println("\n************************ A) LISTA DOS ALUNOS CADASTRADOS ************************");
            System.out.println("\n");
            for (Aluno aluno : alunoRepository.findAll()) {
                System.out.println(aluno);
            }
            System.out.println("\n*********************************************************************************\n");

            System.out.println("\n************************ B) BUSCAR ALUNO POR {ID} ************************\n");
            for (int i = 1; i <= 6; i++) {
                var alunoOpt = alunoRepository.findById(i);
                System.out.print("buscarPorId(" + i + "): \n");
                if (alunoOpt.isPresent()) {
                    System.out.println(alunoOpt.get());
                } else {
                    System.out.println("Aluno com id" + i + "não encontrado.");
                }
                System.out.print("\n");
            }
            System.out.println("\n*********************************************************************************\n");
        
            System.out.println("\n************************ C) INSERIR UM NOVO ALUNO ************************\n");

            novoAluno = alunoRepository.save(novoAluno);
            System.out.println("status : 201 CREATED\n" +
            "message : Aluno inserido com sucesso!" +
            "\nbody: {" +
            "\n    \"idaluno\" : " + novoAluno.getIdaluno() +
            "\n    \"nome\" : " + novoAluno.getNome() +
            "\n    \"sexo\" : " + novoAluno.getSexo() +
            "\n    \"dt_nasc\" : " + novoAluno.getDt_nasc() +
            "\n}"
            );
            System.out.println("\n*********************************************************************************\n");

            System.out.println("\n************************ D) ALTERAR OS DADOS DO ALUNO PELO {ID} ************************\n");
            Aluno alunoPUT = novoAluno;  
            alunoPUT.setNome("PEDRO AUGUSTO PEREIRA COSTA");

            ResponseEntity responsePUT = alunoRepository.findById(alunoPUT.getIdaluno())
                .map(aluno -> {
                    aluno.setNome(alunoPUT.getNome());
                    aluno.setSexo(alunoPUT.getSexo());
                    aluno.setDt_nasc(alunoPUT.getDt_nasc());
                    return ResponseEntity.ok(alunoRepository.save(aluno));
                }).orElse(ResponseEntity.notFound().build());

            System.out.println("statusCode : " + responsePUT.getStatusCode());
            if (responsePUT.hasBody()) {
                System.out.println("body: " + responsePUT.getBody());
            }
            else {
                System.out.println("Resposta sem corpo");
            }
            System.out.println("\n*********************************************************************************\n");

            System.out.println("\n************************ E) EXCLUIR O ALUNO PELO {ID} ************************\n");

            alunoRepository.delete(novoAluno);

            System.out.println("status : 200 OK\n" +
            "message : Aluno excluido com sucesso!" +
            "\nbody: {" +
            "\n    \"idaluno\" : " + novoAluno.getIdaluno() +
            "\n    \"nome\" : " + novoAluno.getNome() +
            "\n    \"sexo\" : " + novoAluno.getSexo() +
            "\n    \"dt_nasc\" : " + novoAluno.getDt_nasc() +
            "\n}"
            );
            System.out.println("\n*********************************************************************************\n");
        };
    }
}
