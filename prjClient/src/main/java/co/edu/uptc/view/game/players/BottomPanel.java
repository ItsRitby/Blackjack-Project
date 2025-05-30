package co.edu.uptc.view.game.players;

import co.edu.uptc.controller.ClientController;
import co.edu.uptc.view.MainFrame;
import co.edu.uptc.view.game.GamePanel;

import java.awt.FlowLayout;

import javax.swing.JPanel;

public class BottomPanel extends JPanel {

    private MainFrame frame;
    private GamePanel gamePanel;
    private final ActionsPanel actionsPanel;
    private final TokenButtonsPanel tokenButtonsPanel;

    public BottomPanel(GamePanel gamePanel, MainFrame frame, ClientController controller) {
        this.frame = frame;
        this.gamePanel = gamePanel;
        this.actionsPanel = new ActionsPanel(frame, controller);
        this.tokenButtonsPanel = new TokenButtonsPanel(this, frame, controller);
        this.setOpaque(false);
        initcomponents();
        addComponents();
    }

    private void initcomponents() {
        this.setLayout(new FlowLayout());

    }

    private void addComponents() {
        this.add(actionsPanel);
        this.add(tokenButtonsPanel);
    }

    public ActionsPanel getActionsPanel() {
        return actionsPanel;
    }
}
