package com.inventaire.Gestioninventaire.controller;


import com.inventaire.Gestioninventaire.model.Produit;
import com.inventaire.Gestioninventaire.model.ProduitDto;
import com.inventaire.Gestioninventaire.repository.ProduitRepo;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/produits")
public class Produitscontroller {
    @Autowired
    private ProduitRepo produitRepo;



    @GetMapping({"", "/"})
    public String getProduits (Model model) {
        var clients = produitRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("produits", clients);

        return "produits/index2";
    }

    @GetMapping({"/create"})
    public String createProduit(Model model) {
        ProduitDto produitDto = new ProduitDto();
        model.addAttribute("produitDto", produitDto);
        return "produits/create";
    }

    @PostMapping("/create")
    public String createProduit(
            @Valid @ModelAttribute("produitDto") ProduitDto produitDto,
            BindingResult result) {
        // Gestion des erreurs
        if (result.hasErrors()) {
            return "produits/create";
        }

        // Création et sauvegarde du produit
        Produit produit = new Produit();
        produit.setCatégorie(produitDto.getCatégorie());
        produit.setNom(produitDto.getNom());
        produit.setPrix(produitDto.getPrix());
        produit.setQuantité(produitDto.getQuantité());
        produitRepo.save(produit);

        // Redirection correcte
        return "redirect:/produits";
    }


    @GetMapping("/edit")
    public String editProduit(Model model, @RequestParam int id){
        Produit produit = produitRepo.findById(id).orElse(null);
        if (produit == null){
            return "redirect:/produits";
        }
        ProduitDto produitdto = new ProduitDto();
        produitdto.setCatégorie(produit.getCatégorie());
        produitdto.setNom(produit.getNom());
        produitdto.setPrix(produit.getPrix());
        produitdto.setQuantité(produit.getQuantité());
        model.addAttribute("produit", produit);
        model.addAttribute("produitDto", produitdto);
        return "produits/edit";
    }



    @PostMapping("/edit")
    public String editProduit(
            Model model, @RequestParam int id,
            @Valid @ModelAttribute ProduitDto produitdto,
            BindingResult result ) {
        Produit produit = produitRepo.findById(id).orElse(null);
        if (produit == null) {return "redirect:/produits";}
        model.addAttribute("produit", produit);
        if (result.hasErrors()){
            return "produits/edit";
        }
        //update produit
        produit.setCatégorie(produitdto.getCatégorie());
        produit.setNom(produitdto.getNom());
        produit.setPrix(produitdto.getPrix());
        produit.setQuantité(produitdto.getQuantité());

        try {
            produitRepo.save(produit);
        }catch(Exception e){

            return "produits/edit";
        }

        return "redirect:/produits";
    }

    @GetMapping("/delete")
    public String deleteProduit(@RequestParam int id){
        Produit produit = produitRepo.findById(id).orElse(null);
        if(produit !=null){
            produitRepo.delete(produit);
        }
        return "redirect:/produits";
    }

    @GetMapping("/produits")
    public String getProduits(HttpSession session) {
        if (session.getAttribute("client") == null) {
            return "redirect:/client/login"; // Rediriger vers la page de login si non connecté
        }
        // Logique pour afficher les produits
        return "produits"; // Page des produits
    }


}



