package br.com.estados.repository;

import br.com.estados.model.CidadeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<CidadeModel, Integer> {

    public Page<CidadeModel> findAll(Pageable pageable);

    public Page<CidadeModel> findByNameStartsWithIgnoreCase(String name, Pageable pageable);
}
