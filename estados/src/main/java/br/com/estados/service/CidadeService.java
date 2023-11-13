package br.com.estados.service;

import br.com.estados.dto.CidadeDTO;
import br.com.estados.dto.CidadeDTO;
import br.com.estados.exception.ResourceNotFoundException;
import br.com.estados.mapper.CustomModelMapper;
import br.com.estados.model.CidadeModel;
import br.com.estados.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    public CidadeDTO create(CidadeDTO dto){
        CidadeModel model = CustomModelMapper.parseObject(dto, CidadeModel.class);
        return CustomModelMapper.parseObject(repository.save(model), CidadeDTO.class);
    }

    public CidadeDTO findById(int id){
        CidadeModel model = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(null));
        return CustomModelMapper.parseObject(model, CidadeDTO.class);
    }

    public Page<CidadeDTO> findAll(Pageable pageable){
        var page = repository.findAll(pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, CidadeDTO.class));
    }

    public CidadeDTO update(CidadeDTO dto){
        CidadeModel model = repository.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException(null));
        model = CustomModelMapper.parseObject(dto, CidadeModel.class);
        return CustomModelMapper.parseObject(repository.save(model), CidadeDTO.class);
    }

    public void delete(CidadeDTO dto){
        CidadeModel model = repository.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException(null)
        );
        repository.delete(model);
    }

    public Page<CidadeDTO> findByName(String name, Pageable pageable){
        var page = repository.findByNameStartsWithIgnoreCase(name, pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, CidadeDTO.class));
    }

}
