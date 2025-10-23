# Application de Gestion de Projets - Exercice 2

## Description
Cette application permet de gérer des projets, des employés, des tâches et leurs associations selon le diagramme UML fourni.

## Fonctionnalités implémentées

### 1. Couche Persistance
- Entités : Projet, Employe, Tache, EmployeTache
- Interface IDao générique
- Fichier de configuration : application.properties
- Classe utilitaire : HibernateUtil

### 2. Couche Service
- **ProjetService** : Gestion des projets
  - Afficher la liste des tâches planifiées pour un projet
  - Afficher la liste des tâches réalisées avec les dates réelles

- **EmployeService** : Gestion des employés
  - Afficher la liste des tâches réalisées par un employé
  - Afficher la liste des projets gérés par un employé

- **TacheService** : Gestion des tâches
  - Afficher les tâches dont le prix est supérieur à 1000 DH
  - Afficher les tâches réalisées entre deux dates

- **EmployeTacheService** : Gestion de l'association employé-tâche

### 3. Tests
Tous les tests sont implémentés dans la classe `TestApplication` qui démontre :
- Création de projets, employés et tâches
- Attribution de tâches aux employés
- Affichage des différentes listes selon les critères demandés

## Installation

### Prérequis
- Java 21
- MySQL Server
- Maven

## Affichage 

Le programme affichera :
- Projet : 4 - Gestion de stock 
- Liste des tâches par employé
- Liste des projets gérés
- Tâches planifiées vs réalisées
- Tâches avec prix > 1000 DH
- Tâches réalisées entre deux dates

![img.png](img.png)

![img_1.png](img_1.png)

## Auteur
Exercice d'évaluation - Gestion de projets

