package com.inventaire.Gestioninventaire.controller;

import com.inventaire.Gestioninventaire.model.Client;
import com.inventaire.Gestioninventaire.repository.ClientRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {

    @Autowired
    private ClientRepo clientRepo;

    // Page de connexion
    @GetMapping("/loginClient")
    public String loginPage() {
        return "client/loginClient"; // Chemin du fichier loginClient.html
    }

    // Soumission du formulaire de connexion
    @PostMapping("/loginClient")
    public String login(@RequestParam String email, @RequestParam String motDePasse, HttpSession session, Model model) {
        System.out.println("Tentative de connexion pour l'email : " + email);
        Client client = clientRepo.findByEmail(email).orElse(null);

        if (client != null && client.getMotDePasse() != null && client.getMotDePasse().equals(motDePasse)){
            System.out.println("Client trouvé : " + client.getNom());
            if (client.getMotDePasse().equals(motDePasse)) {
                System.out.println("Mot de passe correct");
                session.setAttribute("client", client);
                return "redirect:/produits";
            } else {
                System.out.println("Mot de passe incorrect");
            }
        } else {
            System.out.println("Aucun client trouvé avec cet email");
        }

        model.addAttribute("error", "Email ou mot de passe incorrect");
        return "client/loginClient";
    }


    // Déconnexion
    @GetMapping("/client/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalider la session
        return "redirect:/loginClient"; // Redirection vers la page de connexion
    }
}
