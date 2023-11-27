package br.com.estados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CidadeDTO extends RepresentationModel {

    private int id;
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name="id_estado")
    private EstadoModel estado;
}