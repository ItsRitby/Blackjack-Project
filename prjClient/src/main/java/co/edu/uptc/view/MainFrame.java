package co.edu.uptc.view;

import co.edu.uptc.controller.ClientController;
import co.edu.uptc.view.popups.MessageDialog;
import co.edu.uptc.view.reusable.Constants;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class MainFrame extends JFrame {
    private ClientController controller;
    private MainPanel mainPanel;
    private int bet = 0;
    private String action = null;

    public MainFrame(ClientController controller) {
        super(Constants.FRAME_TITLE);
        this.controller = controller;
        initComponents(controller);
        setSize(Constants.WINDOW_DIMENSION);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setIcon();
    }

    private void setIcon() {
        URL iconURL = getClass().getResource(Constants.FRAME_ICON_PATH);
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            this.setIconImage(icon.getImage());
        }
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public final void initComponents(ClientController controller) {
        mainPanel = new MainPanel(this, controller);
        setContentPane(mainPanel);
    }

    public void showMessage(String message) {
        new MessageDialog(message).showPopUp();
    }

    public int promptBet() {
        if (bet == 0) {
            bet = 10;
        }
        return bet;
    }

    public String promptAction() {
        if (action == null) {
            action = "STAND";
        }
        return action;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getBet() {
        return bet;
    }

    public String getAction() {
        return action;
    }

    public void addCrupierCards(String cards) {
        mainPanel.addCrupierCards(cards);
    }

    public void addPlayerCards(String cards) {
        mainPanel.addPlayerCards(cards);
    }

    public void clearCrupierCards() {
        mainPanel.clearCrupierCards();
    }

    public void clearPlayerCards() {
        mainPanel.clearPlayerCards();
    }
}
