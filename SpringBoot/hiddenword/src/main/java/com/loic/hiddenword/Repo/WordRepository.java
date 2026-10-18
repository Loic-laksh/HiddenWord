package com.loic.hiddenword.Repo;
import com.loic.hiddenword.données.Word;
public abstract class WordRepository {
		/* La méthode abstraite */
    public abstract Word getWord() throws Exception;

}
