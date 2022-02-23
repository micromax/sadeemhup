package com.github.sadeemhup.system.core.controller;


import com.github.sadeemhup.system.core.CG_Controller;
import io.vertx.ext.web.RoutingContext;

import java.io.IOException;

public class Error extends CG_Controller {



    public void index() throws IOException {
        super.index();


        Render(this.load.View("templates/lost.ftl", this.getRxtx()));
    }
    public void misconf(){

        Render(this.load.View("templates/mis.ftl", this.getRxtx()));
    }
    public Error(RoutingContext rxtx) {
        super(rxtx);

        Render(this.load.View("templates/lost.ftl", rxtx));
    }

}
