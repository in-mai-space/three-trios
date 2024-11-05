package controller;

import view.gui.GameGUIView;

public interface GameController extends Feature {
  void setView(GameGUIView view);
}
