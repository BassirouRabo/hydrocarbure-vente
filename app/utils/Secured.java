package utils;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("telephone");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect("/connexion");
    }
}
