import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.Controller;
/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 10/01/2022
* Ultima alteracao.: 05/02/2020
* Nome.............: Principal.java
* Funcao...........: Inicializar programa
*************************************************************** */

public class Principal extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Controller objControlle = new Controller();
    Parent root = FXMLLoader.load(getClass().getResource("/view/sample.fxml"));
    primaryStage.setTitle("REDES I ");
    primaryStage.setScene(new Scene(root));
    primaryStage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    }); // ecerra todos os processos
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
