package com.inventaire.Gestioninventaire.repository;

import com.inventaire.Gestioninventaire.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmail(String email);
}
