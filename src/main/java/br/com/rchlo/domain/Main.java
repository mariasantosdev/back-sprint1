package br.com.rchlo.domain;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Main {

    public static void main(String[] args) {
        List<Produto> produtos = ListaDeProdutos.lista();

        //buscando todas as camisetas
        List<Produto> camisetas = produtos
                .stream()
                .filter(p -> p.getNome().contains("Camiseta"))
                .collect(toList());
        camisetas.forEach(System.out::println);

        //buscando todas as camisetas brancas
        List<Produto> brancas = camisetas.stream()
                .filter(c -> Cor.BRANCA.equals(c))
                .collect(toList());
        brancas.forEach(System.out::println);

        // buscando se existe camisetas da cor cinza
        boolean existeCinza = camisetas.stream().filter(c -> Cor.CINZA.equals(c.getCor())).findFirst().isPresent();
        System.out.println("Temos camisetas nas cores cinzas? " + existeCinza);

        //buscando o produto com menor preço que contenha desconto
        BigDecimal menorPrecoComDesconto = produtos.stream().map(Produto::getPrecoDesconto).filter(p -> p != null).min(BigDecimal::compareTo).get();
        System.out.println("O menor preço de um produto com desconto é: " + menorPrecoComDesconto);

        //buscando o produto com menor preço filtrando antes pelos que possuem desconto
        BigDecimal min = produtos.stream().filter(Produto::temDesconto).map(Produto::getPrecoDesconto).min(BigDecimal::compareTo).get();
        System.out.println("Menor preço do produto que possui desconto: " + min);

        //produto com maior preço
        Produto produto = produtos.stream().max(Comparator.comparing(Produto::getPreco)).get();
        System.out.println(produto);

        //buscando todos os produtos disponiveis para cada tamanho
        Map<Tamanho, List<Produto>> produtosDisponiveisPorTamanho = new HashMap<>();
        Stream.of(Tamanho.values()).forEach(tamanho -> {
            List<Produto> produtosPorTamanho = produtos.stream().filter(p -> p.getTamanhosDisponiveis().contains(tamanho)).collect(toList());
            produtosDisponiveisPorTamanho.put(tamanho, produtosPorTamanho);
        });
        System.out.println(produtosDisponiveisPorTamanho);

        Map<Tamanho, List<Produto>> produtosPorTamanho = Arrays.stream(Tamanho.values())
                .collect(groupingBy(tamanho -> tamanho, flatMapping(tamanho -> produtos.stream().filter(p -> p.getTamanhosDisponiveis().contains(tamanho)), toList())));
        System.out.println(produtosPorTamanho);

        //contando quantos produtos cada cor possui
        Map<Cor, Long> quantidadeProdutosPorCor = Arrays.stream(Cor.values())
                .collect(groupingBy(cor -> cor, mapping(cor -> produtos.stream().filter(p -> p.getCor().equals(cor)), counting())));
        System.out.println(quantidadeProdutosPorCor);

        Map<Cor, Long> qtdProdutosPorCor = new HashMap<>();
        Stream.of(Cor.values()).forEach(cor -> {
            long count = produtos.stream().filter(p -> p.getCor().equals(cor)).count();
            qtdProdutosPorCor.put(cor, count);
        });
        System.out.println(qtdProdutosPorCor);
    }
}
