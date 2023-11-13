package br.com.estados.service;

import br.com.estados.dto.EstadoDTO;
import br.com.estados.exception.ResourceNotFoundException;
import br.com.estados.mapper.CustomModelMapper;
import br.com.estados.model.EstadoModel;
import br.com.estados.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository repository;

    public EstadoDTO create(EstadoDTO dto){
        EstadoModel model = CustomModelMapper.parseObject(dto, EstadoModel.class);
        return CustomModelMapper.parseObject(repository.save(model), EstadoDTO.class);
    }

    public EstadoDTO findById(int id){
        EstadoModel model = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(null));
        return CustomModelMapper.parseObject(model, EstadoDTO.class);
    }

    public Page<EstadoDTO> findAll(Pageable pageable){
        var page = repository.findAll(pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, EstadoDTO.class));
    }

    public EstadoDTO update(EstadoDTO dto){
        EstadoModel model = repository.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException(null));
        model = CustomModelMapper.parseObject(dto, EstadoModel.class);
        return CustomModelMapper.parseObject(repository.save(model), EstadoDTO.class);
    }

    public void delete(EstadoDTO dto){
        EstadoModel model = repository.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException(null)
        );
        repository.delete(model);
    }

    public Page<EstadoDTO> findByName(String name, Pageable pageable){
        var page = repository.findByNameStartsWithIgnoreCase(name, pageable);
        return page.map(p -> CustomModelMapper.parseObject(p, EstadoDTO.class));
    }

}
