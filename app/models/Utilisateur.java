package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "telephone", nullable = false, unique = true)
    private Long telephone;
    @Column(name = "email")
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    public Utilisateur(String nom, Long telephone, String email, String password) {
        this.nom = nom;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
    }

    public Utilisateur(Long telephone, String password) {
        this.telephone = telephone;
        this.password = password;
    }

    public Utilisateur() {
    }

    /**
     * Tous les utilisateur
     *
     * @return
     */
    public List<Utilisateur> findList() {
        try {
            return JPA.em().createQuery("select utilisateur From Utilisateur utilisateur").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une utilisateur by Id
     *
     * @param id
     * @return
     */
    public Utilisateur findById(Long id) {
        try {
            return (Utilisateur) JPA.em().createQuery("select utilisateur From Utilisateur utilisateur WHERE utilisateur.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrouver une utilisateur by telephone
     *
     * @param telephone
     * @return
     */
    public Utilisateur findByTelephone(Long telephone) {
        try {
            return (Utilisateur) JPA.em().createQuery("select utilisateur From Utilisateur utilisateur WHERE utilisateur.telephone = :telephone").setParameter("telephone", telephone).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param telephone
     * @param password
     * @return
     */
    public Utilisateur findByTelephoneAndPassword(Long telephone, String password) {
        try {
            return (Utilisateur) JPA.em().createQuery("select utilisateur From Utilisateur utilisateur WHERE utilisateur.telephone = :telephone AND utilisateur.password = :password").setParameter("telephone", telephone).setParameter("password", password).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Créer une utilisateur
     *
     * @param utilisateur
     * @return
     */
    public String create(Utilisateur utilisateur) {
        Utilisateur utilisateurExiste = findByTelephone(utilisateur.getTelephone());

        if (utilisateurExiste != null) {
            return "Cette utilisateur existe";
        } else {
            String result = null;
            try {
                JPA.em().persist(utilisateur);
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }

    }

    /**
     * Modifier une utilisateur
     *
     * @param utilisateur
     * @return
     */
    public String update(Utilisateur utilisateur) {
        String result = null;

        Utilisateur utilisateurExiste = findById(utilisateur.getId());

        if (utilisateurExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            utilisateurExiste.setNom(utilisateur.getNom());
            utilisateurExiste.setEmail(utilisateur.getEmail());
            utilisateurExiste.setTelephone(utilisateur.getTelephone());
            utilisateurExiste.setPassword(utilisateur.getPassword());
            try {
                JPA.em().persist(utilisateurExiste);
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }
    }

    /**
     * Supprimer une utilisateur
     *
     * @param id
     * @return
     */
    public String delete(Long id) {
        String result = null;

        Utilisateur utilisateurExiste = findById(id);

        if (utilisateurExiste == null) {
            return "aucun enregistrement correspondant";
        } else {
            try {
                JPA.em().remove(utilisateurExiste);
                JPA.em().flush();
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
