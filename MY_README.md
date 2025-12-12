# Projet — Génie Logiciel Avancé (M1 Informatique)
## TP — Amélioration d’un Kata

## Informations générales
- **Nom :** Glory MBOUDO MOUNDZALLET
- **Groupe :** TP G3
- **UE :** Génie Logiciel Avancé
- **Année :** 2025–2026

### Remarque importante
Chaque branche correspond à une partie du TP.  
La **version finale à évaluer** se trouve dans la branche **`METHODE_AFFICHAGE_TDD`** et est identifiée par le tag **`v1.0-final`**.

---

## Dépôt GitHub
- **Lien du dépôt (branche version finale) :**  
  https://github.com/RandomUserLt/MyRepository/tree/METHODE_AFFICHAGE_TDD
- **Lien du dépôt (projet global) :**  
  https://github.com/RandomUserLt/MyRepository/
- **Version finale :** tag `v1.0-final`
- **Branche support de la version finale :** `METHODE_AFFICHAGE_TDD`

> La branche `main` contient une version antérieure du projet  
> (tests de couverture avant refactoring).  
> **La version évaluée est celle du tag `v1.0-final`.**

---

## Fonctionnalités implémentées

### Partie 1 — Rédaction des tests unitaires
- Écriture de tests unitaires couvrant le code existant
- Obtention d’un taux de couverture maximal avec JaCoCo
- Aucun changement du code source à ce stade

### Partie 2 — Mutation de code (PIT)
- Analyse de la robustesse des tests via PIT
- Ajout de tests ciblés pour tuer certaines mutations

### Partie 3 — Refactoring
- Refactorisation complète du code source
- Réduction de la complexité cognitive
- Mutualisation du code dupliqué
- **Correction du bug de l’aventurier au passage au niveau 2**
- Respect strict du comportement validé par les tests

### Partie 4 — Ajout de fonctionnalités
- **Introduction du nouveau rôle : Gobelin (GOBLIN)**
- Amélioration de la gestion des objets :
  - Création d’un type `GameObject`  
    (nom choisi pour éviter un conflit avec le mot-clé Java `Object`)
  - Ajout d’une description, d’un poids et d’une valeur
  - Gestion d’un **poids maximal transportable**
  - Implémentation de la méthode `sell`  
    (le joueur vend l’objet à la “boutique” du jeu et récupère une somme)

### Partie 5 — Nouvelle méthode d’affichage
- Ajout d’un **affichage Markdown**
- Implémentation réalisée en **TDD**
  - Commit 1 : tests
  - Commit 2 : code

---

## Organisation des branches
- `main` : version de référence initiale (tests de couverture avant refactoring)
- `refactoring` : travaux de refactorisation
- `feature/GOBLIN` : introduction de la classe Gobelin
- `feature/AMELIORATION_OBJETS` : amélioration du type objet
- `METHODE_AFFICHAGE_TDD` : implémentation TDD de l’affichage Markdown  
  (**version finale**)

---

## Annexes
- `DiagrammeUML.png`
- `Diagramme_des_Paquets.png`
- `RenduAffichageMarkdown.pdf`
- `Architecture.pdf`

---

## Exécution / Tests
- Exécution des tests et du programme :
```bash
./gradlew test
./gradlew run
