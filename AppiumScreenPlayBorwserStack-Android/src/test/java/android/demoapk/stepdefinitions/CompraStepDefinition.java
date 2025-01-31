package android.demoapk.stepdefinitions;

import android.demoapk.driver.AndroidDriverr;
import android.demoapk.setup.Configuracion;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;

import static android.demoapk.questions.MensajeCompra.mensajeCompra;
import static android.demoapk.tasks.IniciarSesion.iniciarSesion;
import static android.demoapk.tasks.ElegirProducto.elegirProducto;
import static android.demoapk.tasks.DiligenciarInfoCompra.diligenciarInfoCompra;
import static android.demoapk.tasks.FinalizarCompra.finalizarCompra;
import static android.demoapk.util.Constants.*;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;

public class CompraStepDefinition extends Configuracion {

    public static Logger LOGGER = Logger.getLogger(CompraStepDefinition.class);

    @Given("el usuario inicia sesion en la aplicacion")
    public void elUsuarioIniciaSesionEnLaAplicacion() {
        try {
            setUplog4j();
            actor.can(BrowseTheWeb.with(AndroidDriverr.configureDriver().start()));
            LOGGER.info("INICIO AUTOMATIZACION");
            actor.attemptsTo(
                    iniciarSesion()
                            .withTheUser(USER)
                            .andThePassword(PASSWORD)
            );
        } catch (Exception e){
            LOGGER.info("Fallo en el inicio de sesion");
            LOGGER.warn(e.getMessage());
            Assertions.fail(e.getMessage());
        }
    }

    @When("elige un producto y lo compra")
    public void eligeUnProductoYLoCompra() {
        try {
            actor.attemptsTo(
                    elegirProducto(),
                    diligenciarInfoCompra(),
                    finalizarCompra()
            );
        } catch (Exception e){
            LOGGER.info("Fallo durante proceso compra");
            LOGGER.warn(e.getMessage());
            Assertions.fail(e.getMessage());
        }
    }

    @Then("debe visualizar un mensaje con informacion de la compra")
    public void debeVisualizarUnMensajeConInformacionDeLaCompra() {
        try {
            actor.should(
                    seeThat(mensajeCompra(), equalTo(MENSAJE_ORDEN_COMPLETA))
            );
            LOGGER.info("CUMPLE");
        } catch (Exception e){
            LOGGER.info("fallo en la comparacion de resultados");
            LOGGER.warn(e.getMessage());
            Assertions.fail(e.getMessage());
        }
    }



}
