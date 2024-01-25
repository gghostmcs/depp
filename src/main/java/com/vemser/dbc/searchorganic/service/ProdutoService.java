package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.ProdutoCarrinho;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;


    public ProdutoDTO adicionarProduto(ProdutoCreateDTO produto) throws Exception {
        Produto produtoEntity = objectMapper.convertValue(produto, Produto.class);
        produtoEntity = produtoRepository.adicionar(produtoEntity);
        ProdutoDTO produtoDto = objectMapper.convertValue(produtoEntity, ProdutoDTO.class);

        return produtoDto;

    }
    public ProdutoDTO atualizarProduto(Integer id, ProdutoCreateDTO produtos) throws BancoDeDadosException {
        Optional<Integer> optionalId = Optional.ofNullable(id);

        if (optionalId.isPresent()) {
            Integer idProduto = optionalId.get();

            Produto produtoEntity = objectMapper.convertValue(produtos, Produto.class);
            boolean editadoComSucesso = produtoRepository.editar(id, produtoEntity);

            if (editadoComSucesso) {
                Produto produtoAtualizado = produtoRepository.buscarProdutoPorId(id);
                ProdutoDTO produtoDto = objectMapper.convertValue(produtoAtualizado, ProdutoDTO.class);
                return produtoDto;

        } else {
            // Lida com a situação em que o id é nulo
            throw new IllegalArgumentException("O ID fornecido é nulo");
        }
    }
        return null;
    }




    public List<ProdutoDTO> list() throws BancoDeDadosException {

        List<Produto> produtos = produtoRepository.listar();
        return objectMapper.convertValue(produtos, new TypeReference<List<ProdutoDTO>>() {
        });
    }

    public void deleterProduto(Integer idEndereco) throws BancoDeDadosException {
        produtoRepository.remover(idEndereco);
    }


    public Produto buscarProdutoPorId(Integer id) throws BancoDeDadosException {
        Produto produto = produtoRepository.buscarProdutoPorId(id);
        return produto;
    }

    public Produto listarProdutosPorCategoria(Integer categoria) throws BancoDeDadosException {
        List<Produto> produtos = produtoRepository.listarProdutosPorCategoria(categoria);
        return null;
    }


    public List<Produto> listarProdutosLoja(Integer idLoja) throws BancoDeDadosException {
        return produtoRepository.listarProdutosLoja(idLoja);
    }

    public String getMensagemProdutoEmail(ArrayList<ProdutoCarrinho> produtos) {
        StringBuilder mensagemFinal = new StringBuilder();
        for(ProdutoCarrinho produtoCarrinho : produtos){
            String mensagemProduto = String.format("""
                Nome: %s, Quantidade:  %s, Valor por cada quantidade: R$ %s  <br>
                """, produtoCarrinho.getProduto().getNome(),
                    produtoCarrinho.getQuantidade(),
                    produtoCarrinho.getProduto().getPreco());
            mensagemFinal.append(mensagemProduto);
        }
        return mensagemFinal.toString();
    }


        public List<Produto> buscarProdutos() {
        try {
            return produtoRepository.listar();
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao buscar produtos: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void atualizarSilenciosamente(Produto produto) {
        try {
            produtoRepository.editar(produto.getIdProduto(), produto);

        } catch (SQLException e) {
            System.out.println("Erro " + e.getMessage());
            e.printStackTrace();
        }
    }

}



