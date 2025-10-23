package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestApplication {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private static final String[] MOIS = {
        "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
        "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
    };

    private static String formatDateLong(Date date) {
        if (date == null) return "";
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        return cal.get(java.util.Calendar.DAY_OF_MONTH) + " " +
               MOIS[cal.get(java.util.Calendar.MONTH)] + " " +
               cal.get(java.util.Calendar.YEAR);
    }

    public static void main(String[] args) {
        try {
            // Initialisation des services
            ProjetService projetService = new ProjetService();
            EmployeService employeService = new EmployeService();
            TacheService tacheService = new TacheService();
            EmployeTacheService employeTacheService = new EmployeTacheService();

            // Déclaration des variables
            Employe emp1 = null;
            Employe emp2 = null;
            Employe emp3 = null;
            Projet projet1 = null;
            Projet projet2 = null;

            System.out.println("=== Gestion de Stock - Projets et Tâches ===\n");

            // Vérifier si des données existent déjà
            List<Employe> employesExistants = employeService.findAll();

            if (employesExistants.isEmpty()) {
                // Création des employés
                System.out.println("--- Création des employés ---");
                emp1 = new Employe("Dupont", "Jean", "0612345678");
                emp2 = new Employe("Martin", "Sophie", "0623456789");
                emp3 = new Employe("Bernard", "Pierre", "0634567890");

                employeService.create(emp1);
                employeService.create(emp2);
                employeService.create(emp3);
                System.out.println("Employés créés avec succès!\n");
            } else {
                System.out.println("--- Données existantes détectées, utilisation des données en base ---\n");
            }

            // Récupération des employés avec leurs IDs
            List<Employe> employes = employeService.findAll();
            emp1 = employes.get(0);
            emp2 = employes.get(1);
            emp3 = employes.get(2);

            // Création des projets (uniquement si la base est vide)
            List<Projet> projetsExistants = projetService.findAll();

            if (projetsExistants.isEmpty()) {
                System.out.println("--- Création des projets ---");
                projet1 = new Projet("Gestion de stock", sdf.parse("14/01/2013"), sdf.parse("30/06/2013"));
                projet2 = new Projet("Développement Web", sdf.parse("01/03/2013"), sdf.parse("31/12/2013"));

                projetService.create(projet1);
                projetService.create(projet2);
                System.out.println("Projets créés avec succès!\n");
            }

            // Récupération des projets avec leurs IDs
            List<Projet> projets = projetService.findAll();
            projet1 = projets.get(0);
            projet2 = projets.get(1);

            // Création des tâches (uniquement si la base est vide)
            List<Tache> tachesExistantes = tacheService.findAll();
            Tache tache1, tache2, tache3, tache4;

            if (tachesExistantes.isEmpty()) {
                System.out.println("--- Création des tâches ---");
                tache1 = new Tache("Analyse", sdf.parse("10/01/2013"), sdf.parse("28/02/2013"), 1500.0);
                tache2 = new Tache("Conception", sdf.parse("10/03/2013"), sdf.parse("15/04/2013"), 2000.0);
                tache3 = new Tache("Développement", sdf.parse("10/04/2013"), sdf.parse("25/06/2013"), 3500.0);
                tache4 = new Tache("Test", sdf.parse("01/05/2013"), sdf.parse("30/06/2013"), 800.0);

                tacheService.create(tache1, projet1.getId());
                tacheService.create(tache2, projet1.getId());
                tacheService.create(tache3, projet1.getId());
                tacheService.create(tache4, projet2.getId());
                System.out.println("Tâches créées avec succès!\n");
            }

            // Récupération des tâches avec leurs IDs
            List<Tache> taches = tacheService.findAll();
            tache1 = taches.get(0);
            tache2 = taches.get(1);
            tache3 = taches.get(2);
            tache4 = taches.get(3);

            // Attribution des tâches aux employés (uniquement si la base est vide)
            List<EmployeTache> attributionsExistantes = employeTacheService.findAll();

            if (attributionsExistantes == null || attributionsExistantes.isEmpty()) {
                System.out.println("--- Attribution des tâches aux employés ---");
                EmployeTache et1 = new EmployeTache(sdf.parse("10/01/2013"), sdf.parse("28/02/2013"), emp1, tache1);
                EmployeTache et2 = new EmployeTache(sdf.parse("10/03/2013"), sdf.parse("15/04/2013"), emp2, tache2);
                EmployeTache et3 = new EmployeTache(sdf.parse("10/04/2013"), sdf.parse("25/06/2013"), emp1, tache3);
                EmployeTache et4 = new EmployeTache(sdf.parse("01/05/2013"), null, emp3, tache4);

                employeTacheService.create(et1);
                employeTacheService.create(et2);
                employeTacheService.create(et3);
                employeTacheService.create(et4);
                System.out.println("Tâches attribuées avec succès!\n");
            }

            // Test 1: Afficher la liste des tâches réalisées par un employé
            System.out.println("=== Test 1: Tâches réalisées par l'employé " + emp1.getNom() + " " + emp1.getPrenom() + " ===");
            List<Tache> tachesEmp1 = employeService.getTachesRealisees(emp1.getId());
            for (Tache t : tachesEmp1) {
                System.out.println(t.getId() + " - " + t.getNom() + " (Prix: " + t.getPrix() + " DH)");
            }
            System.out.println();

            // Test 2: Afficher la liste des projets gérés par un employé
            System.out.println("=== Test 2: Projets gérés par l'employé " + emp1.getNom() + " " + emp1.getPrenom() + " ===");
            List<Projet> projetsEmp1 = employeService.getProjetsGeres(emp1.getId());
            for (Projet p : projetsEmp1) {
                System.out.println(p.getId() + " - " + p.getNom());
            }
            System.out.println();

            // Test 3: Afficher la liste des tâches planifiées pour un projet
            System.out.println("=== Test 3: Tâches planifiées pour le projet " + projet1.getNom() + " ===");
            List<Tache> tachesPlanifiees = projetService.getTachesPlannifiees(projet1.getId());
            for (Tache t : tachesPlanifiees) {
                System.out.println(t.getId() + " - " + t.getNom() + " (Date début: " + sdf.format(t.getDateDebut()) +
                                   ", Date fin: " + sdf.format(t.getDateFin()) + ")");
            }
            System.out.println();

            // Test 4: Afficher la liste des tâches réalisées avec les dates réelles
            System.out.println("Projet : " + projet1.getId() +
                             "      Nom : " + projet1.getNom() +
                             "     Date début : " + formatDateLong(projet1.getDateDebut()));
            System.out.println("Liste des tâches:");
            System.out.println("Num Nom            Date Début Réelle   Date Fin Réelle");

            List<Object[]> tachesRealisees = projetService.getTachesRealisees(projet1.getId());
            for (Object[] result : tachesRealisees) {
                Tache t = (Tache) result[0];
                Date dateDebutReelle = (Date) result[1];
                Date dateFinReelle = (Date) result[2];
                System.out.printf("%-3d %-14s %-19s %s%n",
                                 t.getId(),
                                 t.getNom(),
                                 sdf.format(dateDebutReelle),
                                 sdf.format(dateFinReelle));
            }
            System.out.println();

            // Test 5: Afficher les tâches dont le prix est supérieur à 1000 DH
            System.out.println("=== Test 5: Tâches dont le prix est supérieur à 1000 DH ===");
            List<Tache> tachesPrixSup = tacheService.getTachesPrixSuperieur(1000.0);
            for (Tache t : tachesPrixSup) {
                System.out.println(t.getId() + " - " + t.getNom() + " (Prix: " + t.getPrix() + " DH)");
            }
            System.out.println();

            // Test 6: Afficher les tâches réalisées entre deux dates
            System.out.println("=== Test 6: Tâches réalisées entre le 01/02/2013 et le 30/04/2013 ===");
            Date date1 = sdf.parse("01/02/2013");
            Date date2 = sdf.parse("30/04/2013");
            List<Tache> tachesEntreDates = tacheService.getTachesRealisesEntreDates(date1, date2);
            for (Tache t : tachesEntreDates) {
                System.out.println(t.getId() + " - " + t.getNom() +
                                   " (Date début: " + sdf.format(t.getDateDebut()) +
                                   ", Date fin: " + sdf.format(t.getDateFin()) + ")");
            }

        } catch (ParseException e) {
            System.err.println("Erreur de format de date: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

