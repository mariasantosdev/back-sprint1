package br.com.rchlo.domain;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static br.com.rchlo.domain.ListaDeProdutos.lista;
import static java.util.stream.Collectors.toMap;

public class TesteProdutos {
    public static void main(String[] args) {
        TesteProdutos testeProdutos = new TesteProdutos();

//        List<Produto> produtos = testeProdutos.buscaTodasCamisetas();
//        produtos.forEach(System.out::println);

//        List<Produto> produtos= testeProdutos.buscaCamisetasBrancas();
//        produtos.forEach(System.out::println);

//        Optional<Produto> produtos = testeProdutos.buscaProdutoComMaiorPreco();
//        System.out.println(produtos);

//        Optional<Produto> produtos = testeProdutos.buscaProdutoComMenorPreco();
//        System.out.println(produtos);

//        boolean existeCamisetaCinza = testeProdutos.buscaSeExisteCamisetaCinza();
//        System.out.println(existeCamisetaCinza);

//        Map<Cor, Integer> corIntegerMap = testeProdutos.buscarTodasCoresComQuantidadeDeProduto();
//        System.out.println(corIntegerMap);

//        Map<Tamanho, List<Produto>> tamanhoListMap = testeProdutos.buscaTodosTamanhosDeProduto();
//        for (Map.Entry<Tamanho, List<Produto>> tamanhoListEntry : tamanhoListMap.entrySet()) {
//            System.out.println(tamanhoListEntry.getKey());
//            System.out.println(tamanhoListEntry.getValue().stream().map(p -> p.getNome()).collect(Collectors.toList()));
//        }
}

    List<Produto> listaDeProdutos = lista();

    public List<Produto> buscaTodasCamisetas() {
        return listaDeProdutos.stream().filter(p -> p.getNome().contains("Camiseta")).collect(Collectors.toList());
    }

    public List<Produto> buscaCamisetasBrancas() {
        return listaDeProdutos.stream().filter(p -> p.getNome().contains("Camiseta"))
                .filter(produto -> produto.getCor().equals(Cor.BRANCA))
                .collect(Collectors.toList());
    }

    public Optional<Produto> buscaProdutoComMaiorPreco() {
        return listaDeProdutos.stream().max(Comparator.comparing(Produto::getPreco));
    }

    public Optional<Produto> buscaProdutoComMenorPreco() {
        return listaDeProdutos.stream().filter(p -> p.getPrecoDesconto() != null)
                .min(Comparator.comparing(Produto::getPrecoDesconto));
    }

    public boolean buscaSeExisteCamisetaCinza() {
        return listaDeProdutos.stream().filter(p -> p.getNome().contains("Camiseta"))
                .anyMatch(p -> p.getCor().equals(Cor.CINZA));
    }

    public int contaProdutosDaCor(Cor cor){
        return (int) listaDeProdutos.stream().filter(p -> p.getCor().equals(cor)).count();
    }

    public Map<Cor,Integer> buscarTodasCoresComQuantidadeDeProduto() {
        return Arrays.stream(Cor.values()).collect(toMap(Function.identity(),
                this::contaProdutosDaCor
        ));
    }

    public List<Produto> buscaProdutosDoTamanho(Tamanho tamanho){
        return listaDeProdutos.stream().filter(p -> p.getTamanhosDisponiveis()
                .contains(tamanho)).collect(Collectors.toList());
    }

    public Map<Tamanho, List<Produto>> buscaTodosTamanhosDeProduto() {
        return Arrays.stream(Tamanho.values()).collect(toMap(Function.identity(),
                this::buscaProdutosDoTamanho
        ));
    }
}




