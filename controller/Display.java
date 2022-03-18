package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import model.camada_fisica.Quadro;
import model.camada_fisica.Rede;

public abstract class Display {

  private static void addSinal(HBox display, int current, int prev) {
    Platform.runLater(() -> {
      ObservableList led = display.getChildren();
      VBox vBox = new VBox();
      if (current == 0)
        vBox.setAlignment(Pos.BOTTOM_LEFT);
      else
        vBox.setAlignment(Pos.TOP_LEFT);
      if (current != prev && !led.isEmpty())
        led.add(0, new Line(0, 0, 0,display.getHeight() - display.getPadding().getTop() - display.getPadding().getBottom() - 1));

      vBox.getChildren().add(new Line(0, 0, 50, 0));
      led.add(0, vBox);
    });
  }


  public static void show(int[] bits, HBox display, int time, Quadro quadro) {

  Platform.runLater(() -> display.getChildren().clear());
   Thread objThread =  new Thread(() -> {
      for (int i = bits.length - 1; i >= 0; i--) {
        if (i == bits.length - 1)
          addSinal(display, bits[i], 'n');
        else
          addSinal(display, bits[i], bits[i + 1]);
        try {Thread.sleep(Rede.time);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
     quadro.fazer();
    });

    objThread.start();
  }
  
}
