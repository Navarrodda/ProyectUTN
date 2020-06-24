package utn.project.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import utn.project.domain.State;
import utn.project.service.StateService;

import java.util.List;

class StateControllerTest {

    @Mock
    StateService stateServiceMock;


    @Test
    void getMoreCity() throws Exception {
        List<State> resultado = stateServiceMock.getMoreCity();
        // Comprobar que se devuelve un resultado
        if(!resultado.isEmpty())
        {
            System.out.println(resultado.get(0).getName());
        }
        else{
            System.out.println("Null");
        }

    }
}