package com.loic.hiddenword;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.loic.hiddenword.Service.Game;
import com.loic.hiddenword.Service.Gerergame;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = {
    "http://127.0.0.1:5500",
    "http://localhost:5500",
    "https://loic-laksh.github.io"
})

@RestController
public class GameController {
    
    @Autowired
    private Gerergame gerergame;

    
    @PostMapping("/api/renseignement")
    public  Map<String, String> Lancement(@RequestBody Map<String, String> json) throws Exception {
        Map<String, String> reponse = new HashMap<>();
        String nomJoueur = json.get("Nom");
        int nbTours = Integer.parseInt(json.get("NombreDeTour"));
        int idpartie = gerergame.creerPartie(nomJoueur, nbTours);
        reponse.put("nom du Joueur", nomJoueur);
        reponse.put("Nombre de tours", String.valueOf(nbTours));
        reponse.put("idpartie", String.valueOf(idpartie));
    
        return reponse;
    }

    @PostMapping("/api/renseignementmulti")
    public  Map<String, String> LancementMultijoueurPartie(@RequestBody Map<String, String> json) throws Exception {
        Map<String, String> reponse = new HashMap<>();
        String nomJoueur1 = json.get("nomJoueur1");
        String nomJoueur2 = json.get("nomJoueur2");
        int nbTours = Integer.parseInt(json.get("NombreDeTour"));
        int idpartie =gerergame.creerPartieMulti(nomJoueur1, nomJoueur2, nbTours);
        reponse.put("nom du Joueur 1", nomJoueur1);
        reponse.put("nom du Joueur 2", nomJoueur2);
        reponse.put("Nombre de tours", String.valueOf(nbTours));
        reponse.put("idpartie", String.valueOf(idpartie));


        return reponse;
    }

    @PostMapping("/api/verifier")
    public  String[] essai (@RequestBody Map<String, String> json) throws Exception {
        String essai = json.get("essai");
        int idpartie = Integer.parseInt(json.get("idpartie"));
        Game partie = gerergame.getPartie(idpartie);
        int nbTours = partie.getNbTour();
        String[] reponse =partie.essai(essai, nbTours);

        return reponse;
    }
            @PostMapping("/api/vainqueur")
    public  boolean vainqeur (@RequestBody Map<String, String> json) throws Exception {
        String essai = json.get("essai");
        int idpartie = Integer.parseInt(json.get("idpartie"));
        Game partie = gerergame.getPartie(idpartie);
        boolean reponse =partie.vainqueur(essai);

        return reponse;
    }

        @GetMapping("/api/score")
    public int Envoiscore(@RequestParam int idpartie) {
        Game partie = gerergame.getPartie(idpartie);
        return partie.getPoint();
    }
            @GetMapping("/api/scorej1")
    public int Envoiscorej1(@RequestParam int idpartie) {
        Game partie = gerergame.getPartie(idpartie);
        return partie.getPointJoueur1();
    }

            @GetMapping("/api/scorej2")
    public int Envoiscorej2(@RequestParam int idpartie) {
        Game partie = gerergame.getPartie(idpartie);
        return partie.getPointJoueur2();
    }

            @GetMapping("/api/reset")
    public void reset(@RequestParam int idpartie) {
        Game partie = gerergame.getPartie(idpartie);
        partie.resetScore();
    }
                @GetMapping("/api/motsecret")
    public String motsecret(@RequestParam int idpartie) {
        Game partie = gerergame.getPartie(idpartie);
        return partie.getMotSecret();
    }
            @PostMapping("/api/existe")
    public boolean existe(@RequestBody Map<String, String> body) {
        int idpartie = Integer.parseInt(body.get("idpartie"));
        Game partie = gerergame.getPartie(idpartie);
        return partie.motExiste(body.get("essai"));
    }

}

