package br.com.estados.controller;

import br.com.estados.dto.EstadoDTO;
import br.com.estados.service.EstadoService;
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
@RequestMapping("/api/estados")
@Tag(name = "Estados", description = "This endpoint manages Estados")
public class EstadoController {
        @Autowired
        private EstadoService service;

        @PostMapping
        @Operation(summary = "Persists a new estado in database", tags = {"Estados"}, responses = {
                @ApiResponse(description = "Success!", responseCode = "200", content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = EstadoDTO.class))
                })
        })
        public EstadoDTO create(@RequestBody EstadoDTO dto){
        return service.create(dto);
    }

        @GetMapping("/{id}")
        @Operation(summary = "Find a Estado using the ID", tags = {"Estados"}, responses = {
                @ApiResponse(description = "Success!", responseCode = "200", content = {
                        @Content(mediaType = "application/json", schema =
                        @Schema(implementation = EstadoDTO.class)
                        )
                }),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content}),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = {@Content}),
                @ApiResponse(description = "Internal Error", responseCode = "500", content = {@Content})
        })
        public EstadoDTO findById(@PathVariable("id") int id){
        EstadoDTO dto = service.findById(id);
        //..adding HATEOAS link
        buildEntityLink(dto);
        return dto;
    }

        @GetMapping
        public ResponseEntity<PagedModel<EstadoDTO>> findAll(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        PagedResourcesAssembler<EstadoDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        Page<EstadoDTO> estados = service.findAll(pageable);

        for (EstadoDTO estado:estados){
            buildEntityLink(estado);
        }
        return new ResponseEntity(assembler.toModel(estados), HttpStatus.OK);
    }

        @GetMapping("/find")
        public ResponseEntity<PagedModel<EstadoDTO>> findByName(
            @RequestParam(value = "name", defaultValue = "") String name,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        PagedResourcesAssembler<EstadoDTO> assembler
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

        Page<EstadoDTO> estados = service.findByName(name, pageable);

        for (EstadoDTO estado:estados){
            buildEntityLink(estado);
        }
        return new ResponseEntity(assembler.toModel(estados), HttpStatus.OK);
    }

        @PutMapping
        public EstadoDTO update(@RequestBody EstadoDTO dto){
        return service.update(dto);
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        EstadoDTO dto = service.findById(id);
        service.delete(dto);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

        public void buildEntityLink(EstadoDTO estado){
        //..self link
        estado.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(
                                this.getClass()
                        ).findById(estado.getId())
                ).withSelfRel()
        );
    }

//    public void buildCollectionLink(CollectionModel<EstadoDTO> estados){
//        estados.add(
//                WebMvcLinkBuilder.linkTo(
//                        WebMvcLinkBuilder.methodOn(this.getClass()).findAll()
//                ).withRel(IanaLinkRelations.COLLECTION)
//        );
//    }



    }
