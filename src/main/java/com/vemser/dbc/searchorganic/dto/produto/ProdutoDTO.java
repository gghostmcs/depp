package com.vemser.dbc.searchorganic.dto.produto;

import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Data
public class ProdutoDTO {
    private Integer idProduto;

    @NotNull(message = "Produto deve pertencer a um fornecedor")
    @Schema(description = "id da empresa", required = true, example = "0")
    private Integer idEmpresa;

    @NotBlank(message = "O nome nao deve ser vazio")
    @Schema(description = "nome do produto", required = true, example = "Maçã")
    private String nome;

    @NotNull(message = "Digite algo sobre o produto")
    @Schema(description = "descrição produto", required = true, example = "Maça Gala")
    private String descricao;

    @NotNull(message = "O preço não pode ser vazio")
    @Schema(description = "preço do produto", required = true, example = "3.5")
    private BigDecimal preco;

    @NotNull(message = "A quantidade deve ser informada")
    @Schema(description = "quantidade disponivel do produto", required = true, example = "500")
    private BigDecimal quantidade;

    @NotNull(message = "Informe a categoria do produto")
    @Schema(description = "Categoria do produto", required = true, example = "fruta")
    private TipoCategoria categoria;

    @Schema(description = "taxa aplicada no produto", required = true, example = "3.3")
    private double taxa;

    @NotNull(message = "Aquantidade deve ter uma unidade de medida")
    @Schema(description = "unidade de medida para o produto", required = true, example = "KG")
    private UnidadeMedida unidadeMedida;

    @Schema(description = "link da imagem do produto")
    private String urlImagem;
}
