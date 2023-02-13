package gui;

import businessentities.CustomerData;
import businesslogic.CustomerManager;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author fontys
 */
public class CustomerController {

    private final Supplier<SceneManager> sceneManagerSupplier;
    private final CustomerManager customerManager;
    @FXML
    private TextField firstName, surName, birthDate;
    
    public CustomerController( Supplier<SceneManager> sceneManagerSupplier, CustomerManager customerManager ) {  
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.customerManager = customerManager;
    }

    @FXML
    private void switchToSecondary() {
        Consumer<SecondaryController> consumer =
                (c) -> c.setPreviousView("Customer");
        sceneManagerSupplier.get().changeScene( "secondary", consumer );
    }

    @FXML
    private void storeCustomer() {
        CustomerData c = customerManager.createCustomer(firstName.getText(), surName.getText(), LocalDate.parse(birthDate.getText() ) );

        customerManager.add( c );
    }
    
}
