package ma.projet.classes;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tache")
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateFin;

    @Column(nullable = false)
    private double prix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_id")
    private Projet projet;

    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmployeTache> employeTaches;

    public Tache() {
    }

    public Tache(String nom, Date dateDebut, Date dateFin, double prix) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
    }

    public Tache(int id, String nom, Date dateDebut, Date dateFin, double prix) {
        this.id = id;
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public List<EmployeTache> getEmployeTaches() {
        return employeTaches;
    }

    public void setEmployeTaches(List<EmployeTache> employeTaches) {
        this.employeTaches = employeTaches;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Tache{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", prix=" + prix +
                '}';
    }
}




