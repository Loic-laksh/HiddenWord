package com.loic.hiddenword.Repo;
import org.springframework.stereotype.Repository;

import com.loic.hiddenword.donnees.Word;
import words.WordSet;
import words.JsonWordSet;
@Repository
public class JsonWordRepository extends WordRepository {

/* L'attribut */

    private WordSet listeMots;

/* Le constructeur */

    public JsonWordRepository() throws Exception {
        /*
            Permet d’initialiser la liste de mots grâce au fichier JSON
        */
        this.listeMots = new JsonWordSet("mots.json");

    }

/* La méthode */

    public Word getWord() throws Exception {
        /*
            Retourne un mot aléatoire provenant du fichier JSON
        */
        return new Word(this.listeMots.random());
    }
}
