package controllers;

import models.Utilisateur;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import javax.inject.Inject;

public class HomeController extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result index() {
        if (session("telephone") == null) {
            return redirect(controllers.routes.HomeController.connexion());
        } else {
            return ok(index.render());
        }
    }

    public Result connexion() {
        return ok(connexion.render());
    }

    public Result deconnexion() {
        session().clear();
        return redirect(controllers.routes.HomeController.connexion());
    }

    public Result inscription() {
        return ok(inscription.render());
    }

    @Transactional
    public Result authentification() {
        Form<Utilisateur> form = formFactory.form(Utilisateur.class).bindFromRequest();
        if (form.hasErrors()) {
            flash("error", "Erreur de saisie");
            return redirect(controllers.routes.HomeController.connexion());
        } else {
            Utilisateur login = form.get();
            Utilisateur utilisateur = new Utilisateur().findByTelephoneAndPassword(login.getTelephone(), login.getPassword());

            if (utilisateur == null) {
                flash("error", " Email ou mot de passe incorrect. Veuillez saisir à nouveau");
                return redirect(controllers.routes.HomeController.connexion());
            } else {
                session("telephone", (utilisateur.getTelephone().toString()));
                return redirect(controllers.routes.HomeController.index());
            }
        }
    }

    @Transactional
    public Result errorClient() {
        return ok(error_client.render());
    }

    @Transactional
    public Result errorServer(String message) {
        return ok(error_server.render(message));
    }

}
