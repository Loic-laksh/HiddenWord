package com.loic.hiddenword;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.loic.hiddenword.Service.Game;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class GameController {
    
    @Autowired
    private Game partie ;

    
    @PostMapping("/api/renseignement")
    public  Map<String, String> Lancement(@RequestBody Map<String, String> json) throws Exception {
        Map<String, String> reponse = new HashMap<>();
        String nomJoueur = json.get("Nom");
        int nbTours = Integer.parseInt(json.get("NombreDeTour"));
        reponse.put("nom du Joueur", nomJoueur);
        reponse.put("Nombre de tours", String.valueOf(nbTours));
        partie.LancementPartie(nomJoueur, nbTours);

        return reponse;
    }

    @PostMapping("/api/renseignementmulti")
    public  Map<String, String> LancementMultijoueurPartie(@RequestBody Map<String, String> json) throws Exception {
        Map<String, String> reponse = new HashMap<>();
        String nomJoueur1 = json.get("nomJoueur1");
        String nomJoueur2 = json.get("nomJoueur2");
        int nbTours = Integer.parseInt(json.get("NombreDeTour"));
        reponse.put("nom du Joueur 1", nomJoueur1);
        reponse.put("nom du Joueur 2", nomJoueur2);
        reponse.put("Nombre de tours", String.valueOf(nbTours));
        partie.LancementMultijoueurPartie(nomJoueur1, nomJoueur2, nbTours);

        return reponse;
    }

        @PostMapping("/api/verifier")
    public  String[] essai (@RequestBody Map<String, String> json) throws Exception {
        String essai = json.get("essai");
        int nbTours = partie.getNbTour();
        String[] reponse =partie.essai(essai, nbTours);

        return reponse;
    }
            @PostMapping("/api/vainqueur")
    public  boolean vainqeur (@RequestBody Map<String, String> json) throws Exception {
        String essai = json.get("essai");
        boolean reponse =partie.vainqueur(essai);

        return reponse;
    }

        @GetMapping("/api/score")
    public int Envoiscore() {
        return partie.getPoint();
    }
            @GetMapping("/api/scorej1")
    public int Envoiscorej1() {
        return partie.getPointJoueur1();
    }

            @GetMapping("/api/scorej2")
    public int Envoiscorej2() {
        return partie.getPointJoueur2();
    }

            @GetMapping("/api/reset")
    public void reset() {
        partie.resetScore();
    }
}

