package com.web_service_restful.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.web_service_restful.webservice.entities.Curso;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
}