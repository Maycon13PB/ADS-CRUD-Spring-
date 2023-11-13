package br.com.estados.controller;

import br.com.estados.dto.CidadeDTO;
import br.com.estados.dto.CidadeDTO;
import br.com.estados.service.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/cidades")
@Tag(name = "Cidades", description = "This endpoint manages Cidades")
public class CidadeController {
        @Autowired
        private CidadeService service;

        @PostMapping
        @Operation(summary = "Persists a new cidade in database", tags = {"Cidades"}, responses = {
                @ApiResponse(description = "Success!", responseCode = "200", content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = CidadeDTO.class))
                })
        })
        public CidadeDTO create(@RequestBody CidadeDTO dto){
        return service.create(dto);
    }

        @GetMapping("/{id}")
        @Operation(summary = "Find a Cidade using the ID", tags = {"Cidades"}, responses = {
                @ApiResponse(description = "Success!", responseCode = "200", content = {
                        @Content(mediaType = "application/json", schema =
                        @Schema(implementation = CidadeDTO.class)
                        )
                }),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                @ApiResponse(description = "Internal Error", responseCode = "500", content = {@Content})
        })
        public CidadeDTO findById(@PathVariable("id") int id){
        CidadeDTO dto = service.findById(id);
        //..adding HATEOAS link
        buildEntityLink(dto);
        return dto;
    }

        @GetMapping
        public ResponseEntity<PagedModel<CidadeDTO>> findAll(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        PagedResourcesAssembler<CidadeDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        Page<CidadeDTO> cidades = service.findAll(pageable);

        for (CidadeDTO cidade:cidades){
            buildEntityLink(cidade);
        }
        return new ResponseEntity(assembler.toModel(cidades), HttpStatus.OK);
    }

        @GetMapping("/find")
        public ResponseEntity<PagedModel<CidadeDTO>> findByName(
            @RequestParam(value = "name", defaultValue = "") String name,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        PagedResourcesAssembler<CidadeDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        Page<CidadeDTO> cidades = service.findByName(name, pageable);

        for (CidadeDTO cidade:cidades){
            buildEntityLink(cidade);
        }
        return new ResponseEntity(assembler.toModel(cidades), HttpStatus.OK);
    }

        @PutMapping
        public CidadeDTO update(@RequestBody CidadeDTO dto){
        return service.update(dto);
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        CidadeDTO dto = service.findById(id);
        service.delete(dto);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

        public void buildEntityLink(CidadeDTO cidade){
        //..self link
        cidade.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                this.getClass()
                        ).findById(cidade.getId())
                ).withSelfRel()
        );
    }

//    public void buildCollectionLink(CollectionModel<CidadeDTO> cidades){
//        cidades.add(
//                WebMvcLinkBuilder.linkTo(
//                        WebMvcLinkBuilder.methodOn(this.getClass()).findAll()
//                ).withRel(IanaLinkRelations.COLLECTION)
//        );
//    }



    }
