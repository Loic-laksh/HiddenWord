import json
import urllib.request
import unicodedata
import re
from pathlib import Path


# =========================
# DOSSIER DU SCRIPT
# =========================

# Récupère automatiquement le dossier dans lequel se trouve genere.py
DOSSIER = Path(__file__).resolve().parent


# =========================
# FICHIERS
# =========================

# mots.json doit être dans le même dossier que genere.py
FICHIER_MOTS_SECRETS = DOSSIER / "mots.json"

# Le nouveau fichier sera créé dans le même dossier
FICHIER_SORTIE = DOSSIER / "motAcceptable.json"


# =========================
# SOURCES INTERNET
# =========================

# Dictionnaire français contenant des mots de 5 lettres
URL_FRANCAIS = (
    "https://raw.githubusercontent.com/"
    "cestpasphoto/wordle_solver_french/main/dict_fr_5.json"
)

# Liste de mots anglais courants
URL_ANGLAIS = (
    "https://raw.githubusercontent.com/"
    "first20hours/google-10000-english/master/"
    "google-10000-english-no-swears.txt"
)


# =========================
# NORMALISATION
# =========================

def normaliser(mot):

    # minuscules + suppression espaces
    mot = mot.lower().strip()

    # Cas particuliers
    mot = mot.replace("œ", "oe")
    mot = mot.replace("æ", "ae")

    # Supprime les accents
    mot = unicodedata.normalize("NFD", mot)

    mot = "".join(
        lettre
        for lettre in mot
        if unicodedata.category(lettre) != "Mn"
    )

    return mot


# =========================
# VÉRIFICATION MOT
# =========================

def mot_valide(mot):

    # Le mot doit contenir exactement
    # 5 lettres entre a et z
    return re.fullmatch(r"[a-z]{5}", mot) is not None


# =========================
# MOTS FRANÇAIS
# =========================

print("Téléchargement des mots français...")


with urllib.request.urlopen(URL_FRANCAIS) as reponse:

    contenu = reponse.read().decode("utf-8")

    donnees = json.loads(contenu)


mots_francais = set()


for mot in donnees["words"].keys():

    mot = normaliser(mot)

    if mot_valide(mot):

        mots_francais.add(mot)


print(
    len(mots_francais),
    "mots français trouvés"
)


# =========================
# MOTS ANGLAIS
# =========================

print("Téléchargement des mots anglais...")


with urllib.request.urlopen(URL_ANGLAIS) as reponse:

    texte = reponse.read().decode("utf-8")


mots_anglais = set()


for mot in texte.splitlines():

    mot = normaliser(mot)

    if mot_valide(mot):

        mots_anglais.add(mot)


print(
    len(mots_anglais),
    "mots anglais de 5 lettres trouvés"
)


# =========================
# VÉRIFICATION MOTS.JSON
# =========================

if not FICHIER_MOTS_SECRETS.exists():

    print()
    print("ERREUR : mots.json introuvable")
    print("Chemin recherché :")
    print(FICHIER_MOTS_SECRETS)

    exit()


# =========================
# RÉCUPÉRATION MOTS.JSON
# =========================

print()
print("Lecture de mots.json...")


with open(
    FICHIER_MOTS_SECRETS,
    "r",
    encoding="utf-8"
) as fichier:

    mots_secrets = json.load(fichier)


mots_du_json = set()


for mot in mots_secrets:

    mot = normaliser(mot)

    if mot_valide(mot):

        mots_du_json.add(mot)


print(
    len(mots_du_json),
    "mots récupérés depuis mots.json"
)


# =========================
# FUSION DES LISTES
# =========================

mots_acceptables = (
    mots_francais
    | mots_anglais
    | mots_du_json
)


# Transformation du set en liste
# + tri alphabétique
mots_acceptables = sorted(
    mots_acceptables
)


# =========================
# CRÉATION DU JSON
# =========================

with open(
    FICHIER_SORTIE,
    "w",
    encoding="utf-8"
) as fichier:

    json.dump(
        mots_acceptables,
        fichier,
        ensure_ascii=False,
        indent=2
    )


# =========================
# VÉRIFICATIONS
# =========================

ensemble_acceptables = set(mots_acceptables)


# Vérifie que tous les mots
# de mots.json sont présents
manquants = (
    mots_du_json
    - ensemble_acceptables
)


# Vérifie que tous les mots
# font bien 5 lettres
tous_5_lettres = all(
    len(mot) == 5
    for mot in mots_acceptables
)


# Vérifie uniquement a-z
uniquement_lettres = all(
    mot_valide(mot)
    for mot in mots_acceptables
)


# =========================
# RÉSULTAT
# =========================

print()
print("==============================")
print("TERMINÉ")
print("==============================")

print(
    "Nombre de mots français :",
    len(mots_francais)
)

print(
    "Nombre de mots anglais :",
    len(mots_anglais)
)

print(
    "Nombre de mots dans mots.json :",
    len(mots_du_json)
)

print(
    "Nombre TOTAL de mots acceptables :",
    len(mots_acceptables)
)

print(
    "Mots de mots.json manquants :",
    len(manquants)
)

print(
    "Tous les mots font 5 lettres :",
    tous_5_lettres
)

print(
    "Tous les mots contiennent seulement a-z :",
    uniquement_lettres
)

print()
print(
    "Fichier créé ici :"
)

print(
    FICHIER_SORTIE
)