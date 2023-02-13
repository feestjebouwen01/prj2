package gui;


import javafx.application.Application;

import businesslogic.BusinessLogicAPI;
import java.io.IOException;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * The main class for the GUI
 * 
 * @author m.bonajo@fontys.nl
 */
public class GUIApp extends Application {
    
    private final BusinessLogicAPI businessLogicAPI;
    private SceneManager sceneManager;
    private static final String INITIAL_VIEW = "customerView";
    
    private final Callback<Class<?>, Object> controllerFactory;

    /**
     * Create a new GUI for the Airline Information System
     * @param businessLogicAPI provider to interact with the business logic
     */
    public GUIApp(BusinessLogicAPI businessLogicAPI) {
        this.businessLogicAPI = businessLogicAPI;
        this.controllerFactory = (Class<?> c)
                -> {
            
            switch (c.getName()) {
                case "fontys.frontend.CustomerController":
                    return new CustomerController(this::getSceneManager, businessLogicAPI.getCustomerManager());
                case "fontys.frontend.SecondaryController":
                    return new SecondaryController(this::getSceneManager);
                case "fontys.frontend.ErrorController":
                    return new ErrorController(this::getSceneManager);
                default:
                    return null;
            }
        };
        
    }
    
    public GUIApp show() {
        return init(true);
    }

    GUIApp init(boolean startJavaFXToolkit) {
        
        if (startJavaFXToolkit) {

            Platform.startup(() -> {
            });
            
            initializeSceneManager();

            Platform.runLater(() -> {
                Stage stage = new Stage();
                try {
                    start(stage);
                } catch (IOException ex) {
                    throw new IllegalStateException(ex);
                }
            });
            
        } else {
            initializeSceneManager();
        }

        return this;
    }
    
    private void initializeSceneManager(){
        sceneManager = new SceneManager(controllerFactory,INITIAL_VIEW);
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        sceneManager.displayOn(stage, 640, 480);
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

}
