package app_cad_pro.app_cad_pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app_cad_pro.app_cad_pro.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
