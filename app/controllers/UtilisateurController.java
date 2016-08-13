package controllers;

import com.google.inject.Inject;
import models.Utilisateur;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.Secured;
import views.html.utilisateur;
import views.html.utilisateurs;

import java.util.ArrayList;
import java.util.List;

@Security.Authenticated(Secured.class)
public class UtilisateurController extends Controller {
    @Inject
    FormFactory formFactory;


    @Transactional
    public Result reads() {
        List<Utilisateur> utilisateurList = new Utilisateur().findList();

        if (utilisateurList == null) {
            return ok(utilisateurs.render(new ArrayList<>()));
        } else {
            return ok(utilisateurs.render(utilisateurList));
        }
    }

    @Transactional
    public Result read(Long id) {
        Utilisateur UtilisateurExiste = new Utilisateur().findById(id);
        if (UtilisateurExiste == null) {
            return redirect(routes.UtilisateurController.reads());
        } else {
            return ok(utilisateur.render(UtilisateurExiste));
        }
    }

    @Transactional
    public Result create() {
        Form<Utilisateur> form = formFactory.form(Utilisateur.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Utilisateur utilisateur = form.get();
            String result = utilisateur.create(utilisateur);
            if (result != null) {
                flash("error", "Ce utilisateur existe déjà. Veuillez saisir un nouveau");
            } else {
                flash("success", "L'utilisateur été ajouté");
            }
        }
        return redirect(routes.UtilisateurController.reads());
    }

    @Transactional
    public Result update(Long id) {
        Form<Utilisateur> form = formFactory.form(Utilisateur.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Veuillez vérifier les données saisies");
        } else {
            Utilisateur utilisateur = form.get();
            utilisateur.setId(id);
            String result = utilisateur.update(utilisateur);
            if (result != null) {
                flash("error", "Cet utilisateur n'existe pas");
            } else {
                flash("success", "Le résultat a été modifié");
            }
        }
        return redirect(routes.UtilisateurController.read(id));
    }

    @Transactional
    public Result delete(Long id) {
        String result = new Utilisateur().delete(id);
        if (result != null) {
            flash("error", "Erreur de suppression, veuillez réessayer");
        } else {
            flash("success", "L'utilisateur a été supprimé");
        }
        return redirect(routes.UtilisateurController.read(id));
    }
}
