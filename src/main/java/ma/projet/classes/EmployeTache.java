package ma.projet.classes;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "employetache")
@IdClass(EmployeTachePK.class)
public class EmployeTache implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tache_id")
    private Tache tache;

    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;

    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    public EmployeTache() {
    }

    public EmployeTache(Date dateDebutReelle, Date dateFinReelle, Employe employe, Tache tache) {
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
        this.employe = employe;
        this.tache = tache;
    }

    public Date getDateDebutReelle() {
        return dateDebutReelle;
    }

    public void setDateDebutReelle(Date dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }

    public Date getDateFinReelle() {
        return dateFinReelle;
    }

    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    @Override
    public String toString() {
        return "EmployeTache{" +
                "dateDebutReelle=" + dateDebutReelle +
                ", dateFinReelle=" + dateFinReelle +
                ", employe=" + employe +
                ", tache=" + tache +
                '}';
    }
}

