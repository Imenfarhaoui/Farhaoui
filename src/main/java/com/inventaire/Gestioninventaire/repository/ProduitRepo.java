package com.inventaire.Gestioninventaire.repository;


import com.inventaire.Gestioninventaire.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

  public interface ProduitRepo extends JpaRepository<Produit, Integer> {


  }


