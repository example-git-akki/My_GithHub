package Pojo;


import io.cucumber.java.Before;
import stepDefinitions.simpleToolRentalStepDefinition;


public class Hooks {

    @Before("@getOrders")
    public void beforeScenarioGetOrders() {
        simpleToolRentalStepDefinition m = new simpleToolRentalStepDefinition();
        if (simpleToolRentalStepDefinition.accessToken == null) {
            m.userExtractsTheACCESSTOKENGeneratedAfterPOSTHTTPREQUEST();
        }
    }
}

