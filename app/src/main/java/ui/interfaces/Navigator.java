package ui.interfaces;

import javafx.scene.Parent;
import javafx.scene.Scene;

public interface Navigator {
  /**
   * Set elements of a component to navigate to new scenes
   **/
   abstract void setNavigateEvent(final String elem, Scene root, Parent next);
}
