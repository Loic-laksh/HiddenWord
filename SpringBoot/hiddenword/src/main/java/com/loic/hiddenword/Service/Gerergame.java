package com.loic.hiddenword.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loic.hiddenword.Repo.WordRepository;

@Service
public class Gerergame {

    private Map<Integer, Game> parties = new HashMap<>();
    private Random random = new Random();
    private WordRepository wordRepository;


    @Autowired
    public Gerergame(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public int creerPartie(String joueur, int nbTour) throws Exception {
        int idpartie = 100000 + random.nextInt(900000);

        while (this.parties.containsKey(idpartie)) {
            idpartie = 100000 + random.nextInt(900000);
        }

        Game partie = new Game(wordRepository);
        partie.LancementPartie(joueur, nbTour);
        this.parties.put(idpartie, partie);
        System.out.println("ID GENERE : " + idpartie);
        return idpartie;
    }

    public int creerPartieMulti(String joueur1, String joueur2, int nbTour) throws Exception {

        int idpartie = 100000 + random.nextInt(900000);

        while (this.parties.containsKey(idpartie)) {
            idpartie = 100000 + random.nextInt(900000);
        }

        Game partie = new Game(wordRepository);
        partie.LancementMultijoueurPartie(joueur1, joueur2, nbTour);
        this.parties.put(idpartie, partie);
        return idpartie;
    }

    public Game getPartie(int idpartie) {

        return this.parties.get(idpartie);
    }
}