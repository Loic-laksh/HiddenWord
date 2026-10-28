package com.loic.hiddenword.Service;
import java.util.Map;
import com.loic.hiddenword.donnees.Joueur;
import com.loic.hiddenword.Repo.WordRepository;
import com.loic.hiddenword.donnees.Word;


public class Game {

/* Les attributs */
	
	public static final String[] TENTATIVELISTE={"OK     ", "PRESENT", "ABSENT "};
	public int compteTour;
	private String[] listeDesTentatives;
	private int tentative;
	private int nbTour;
	private int point;
	private String joueur;
	private Word motSecret ;
	private Joueur Player;
	private Joueur Player2;
	private boolean multi = false;
	private WordRepository wordRepository;



/* Les constructeurs */
	public Game(WordRepository wordRepository) {
		this.wordRepository = wordRepository;
	}
    public void LancementPartie(String joueur, int nbTour)  throws Exception {
		/*
        	Permet d’initialiser la variable d’instance grâce au nom (String) du joueur et à son nombre de tours (int).
    	*/
		this.listeDesTentatives = new String[6];
		this.tentative = 0;
		this.compteTour=0;
		this.nbTour = nbTour;
		this.Player = new Joueur(joueur);
		this.motSecret = wordRepository.getWord();
		this.multi=false;

    }

    public void LancementPartie(Game player)  throws Exception {
		/*
        	Permet d’initialiser la variable d’instance grâce à un constructeur par copie.
    	*/
		this.listeDesTentatives = new String[6];
		this.tentative = 0;
		this.compteTour=0;
		this.nbTour =  player.getNbTour();
		this.motSecret = wordRepository.getWord();
		this.Player = new Joueur(player.getJoueur());
		this.multi=false;

    }

    public void LancementMultijoueurPartie(String joueur, String joueur2, int nbTour)  throws Exception {
		/*
        	Permet d’initialiser la variable d’instance grâce au nom (String) du joueur et à son nombre de tours (int).
    	*/
		this.listeDesTentatives = new String[6];
		this.tentative = 0;
		this.compteTour=0;
		this.nbTour = nbTour*2;
		this.Player = new Joueur(joueur);
		this.Player2 = new Joueur(joueur2);
		this.motSecret = wordRepository.getWord();
		this.multi=true;

    }

    public void LancementMultijoueurPartie(Game player)  throws Exception {
		/*
        	Permet d’initialiser la variable d’instance grâce à un constructeur par copie.
    	*/
		this.listeDesTentatives = new String[6];
		this.tentative = 0;
		this.compteTour=0;
		this.nbTour =  player.getNbTour();
		this.motSecret = wordRepository.getWord();
		this.Player = new Joueur();
		this.Player2 = new Joueur();
		this.multi=true;

    }

/* Get */

	public String getMotListe (int i) {
		/*
            Retourne le mot d’indice i dans la liste de ses tentatives.
        */
		return this.listeDesTentatives[i];
	}

	public String getMotSecret () {
		/*
            Retourne le mot secret de type String.
        */
		return this.motSecret.getMot();
	}

	public int getTentative () {
		/*
            Retourne le nombre de tentatives effectuées.
        */
		return this.tentative;
	}

	public int getNbTour () {
		/*
            Retourne le nombre de tours effectués.
        */
		return this.nbTour;
	}

	public int getPointJoueur1 (){
		/*
            Retourne le nombre de points du joueur1.
        */
		return this.Player.getScore();
	}
	public int getPointJoueur2 (){
		/*
            Retourne le nombre de points du joueur 2.
        */
		return this.Player2.getScore();
	}
	public int getPoint (){
		/*
            Retourne le nombre de points.
        */
		return this.point;
	}
	public String getJoueur (){
		/*
            Retourne le nom du joueur.
        */
		return this.joueur;
	}





/* Set */

	public void setMotListe (String mot, int i) {
		/*
            Modifie le mot de la liste à l’indice i avec le mot passé en paramètre.
        */
		this.listeDesTentatives[i] = mot;
	}

	public void setTentative (int tentative) {
		/*
            Modifie le nombre de tentatives avec le nombre de tentatives passé en paramètre.
        */
		this.tentative = tentative;
	}

	public void setNbTour (int nbTour) {
		/*
            Modifie le nombre de tours avec le nombre de tours passé en paramètre.
        */
		this.nbTour = nbTour;
	}

	public void setPoint (int point){
		/*
            Modifie le nombre de points avec le nombre de points passé en paramètre.
        */
		this.point = point;
	}

		public void setPointJoueur1 (int point){
		/*
            Modifie le nombre de points du joueur 1 avec le nombre de points passé en paramètre.
        */
		this.Player.setScore(point);
	}
		public void setPointJoueur2 (int point){
		/*
            Modifie le nombre de points du joueur 2 avec le nombre de points passé en paramètre.
        */
		this.Player2.setScore(point);
	}
	public void setJoueur (String nom){
		/*
            Modifie le nom du joueur avec le celui passé en paramètre.
        */
		this.joueur = nom;
	}

/* Méthodes */
	public void resetScore(){
		Player.setScore( 0);
		Player2.setScore( 0);
	}
	public void reset() throws Exception{
		listeDesTentatives=new String[6];
		this.motSecret = wordRepository.getWord();
		this.tentative=0;
		compteTour+=1;
	}

	public boolean motExiste(String mot) {
    	return this.motSecret.motExistant(mot);
	}

	public boolean saisieTentative(String motSaisie, int nbTour) throws Exception {


		if (this.motSecret.motValide(motSaisie)) {

			if (((tentative % 6) == 0) && compteTour != nbTour && tentative != 0) {
				reset();
			}

			else if (compteTour == nbTour) {
				return false;
			}
			listeDesTentatives[tentative] = motSaisie;
			tentative = tentative + 1;
			vainqueur(motSaisie);
			return true;
		}

		return false;
	}

	public boolean vainqueur (String mot){
		/*
            Permet de vérifier si le mot passé en paramètre est le même que le mot secret.
			Si c’est le cas, les points sont attribués en fonction du nombre de tentatives et la méthode renvoie TRUE sinon, elle renvoie FALSE.
        */
		if(this.motSecret.getMot().equals(mot)){
			return true;
		}
		return false;
	}

	public String[] essai (String motSaisie, int nbTour) throws Exception{
		/*
        Va créer un tableau contenant les réponses associées à la tentative, puis le renvoyer. Exemple : {"OK", "ABSENT", "OK", "PRÉSENT", "ABSENT"}.
        */
		String[] reponse = new String[5];
		
		if (saisieTentative( motSaisie, nbTour)){
			Map<Character, Integer> repetition= this.motSecret.occurrenceLettre();
				for (int j=0; j < listeDesTentatives[this.tentative - 1].length(); j++){
					if (this.motSecret.contientLettre (listeDesTentatives[this.tentative - 1].charAt(j))){
						if (this.motSecret.memeLettre (listeDesTentatives[this.tentative - 1].charAt(j), j)){
							reponse[j] = TENTATIVELISTE[0];
							int valeur=repetition.get(listeDesTentatives[this.tentative - 1].charAt(j));
							if(valeur>=1){
								valeur-=1;
								repetition.put(listeDesTentatives[this.tentative - 1].charAt(j), valeur);}
						}
					}
			}
				for (int j = 0; j < listeDesTentatives[this.tentative - 1].length(); j++) {
					if (reponse[j] == null) {
						if (repetition.containsKey(listeDesTentatives[this.tentative - 1].charAt(j)) && repetition.get(listeDesTentatives[this.tentative - 1].charAt(j)) > 0) {
							reponse[j] = TENTATIVELISTE[1];
							int valeur = repetition.get(listeDesTentatives[this.tentative - 1].charAt(j));
							valeur -= 1;
							repetition.put(listeDesTentatives[this.tentative - 1].charAt(j), valeur);
						}
						else {
							reponse[j] = TENTATIVELISTE[2];
						}
					}
				}
		}
		if(vainqueur(motSaisie)==true){
			this.point = 7 - tentative;
			if(multi==true){
			if(compteTour%2==0 || nbTour==0){
				this.Player.ajouteScore(point);
			}
			else{
				this.Player2.ajouteScore(point);
			}}

			else{
				this.Player.ajouteScore(point);
			}
			reset();
		}
		return reponse;


	}


	public String emplacementValide (String [] reponse) {
		/*
        Prend en paramètre un tableau de la forme {"OK", "ABSENT", "OK", "PRÉSENT", "ABSENT"}
		et renvoie une chaîne de caractères (String) de la forme : OK      ABSENT   OK      PRESENT ABSENT
        */
		String resultats = "";
        for (int j = 0; j < reponse.length; j++) {
            resultats += reponse[j];
            if (j < reponse.length - 1) {
                resultats += "  ";
            }
        }
    	resultats += "";
		return resultats;
	}

	public String getLigne (String resultats, int i){
		/*
		Prend en paramètre une chaîne de caractères (String) de la forme : OK      ABSENT   OK      PRESENT ABSENT
		ainsi qu’un indice i correspondant à un mot dans la liste des tentatives.
		Retoutne une une chaîne de caractères (String) de la forme : [ L ] [ O ] [ G ] [ E ] [ R ]  =>  OK      ABSENT   OK      PRESENT ABSENT
        */
		String motSaisie =  getMotListe(i);
		return "[ " + Character.toUpperCase(motSaisie.charAt(0)) + " ] [ " + Character.toUpperCase(motSaisie.charAt(1)) + " ] [ " + Character.toUpperCase(motSaisie.charAt(2)) + " ] [ " + Character.toUpperCase(motSaisie.charAt(3)) + " ] [ " + Character.toUpperCase(motSaisie.charAt(4)) + " ]  => " + resultats;
	}
	


/* toString */

	public String toString() {
		String resultat = "Game [ Mot Secret : " + this.motSecret.getMot() + ", Joueur : " + this.joueur + ", Tentative : " + this.tentative + ", Liste des tentatives :[";
		for (int i = 0; i < listeDesTentatives.length; i++) {
			resultat += listeDesTentatives[i];
			if (i < listeDesTentatives.length - 1) {
				resultat += ", ";
			}
		}
		resultat += "]]";
		return resultat;
	}
}
