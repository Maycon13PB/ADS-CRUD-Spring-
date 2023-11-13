package br.com.estados.repository;

import br.com.estados.model.EstadoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<EstadoModel, Integer> {

    public Page<EstadoModel> findAll(Pageable pageable);

    public Page<EstadoModel> findByNameStartsWithIgnoreCase(String name, Pageable pageable);
}
