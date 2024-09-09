package com.devsuperior.demo.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.demo.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "SELECT obj FROM Product obj JOIN FETCH obj.categories")
	List<Product> searchAll();

	//Consulta JPQL não aceita paginação, para que consigamos, tem que fazer o seguinte macete:
	//usar 'countQuery' para a JPA entender que é uma paginação, e dentro dele, usar 'COUNT' para ficar claro que se trata de contar as páginas ...
	//e usar JOIN ao invés de join fetch.
	@Query(value = "SELECT obj FROM Product obj JOIN FETCH obj.categories",
			countQuery = "SELECT COUNT(obj) FROM Product obj JOIN obj.categories")
	Page<Product> searchAll(Pageable pageable);

}
