import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class Display extends JFrame implements MouseListener{
    /* TODO: This class should do following things:
            - Display game GUIs for each players, differently!
            - This might be converting "MainFrame" class
            - Be aware of that this is module to utilize displaying for server-client association easier.
    */
    private JLayeredPane base_pane = new JLayeredPane();
    private Bell bell = new Bell();
    private Card card_1, card_2;
    private Deck deck_1, deck_2;
    ArrayList <Long> ringBellLog = new ArrayList<Long>();

    public Display() {
        int width, height, card_width, card_height;
        /* 4:3 resolution */
        width = 800;
        height = 600;
        card_width = width/8;
        card_height = height/6;

        this.setPreferredSize(new Dimension(width, height));
        this.setResizable(false);        // No frame resize occurs, except for future feature: Configure settings
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Halli-Galli");

        /*Add base_pane to Frame*/
        this.add(this.base_pane, BorderLayout.CENTER);

        /* Set Boundary */
        this.base_pane.setBounds(0, 0, width, height);

        /*Create "Bell" bell Instance*/
        bell.setOpaque(true);
        bell.setBounds(0, 0, width, height);

        /*Deck Class this.card_1*/
        this.deck_1 = new Deck();
        deck_1.createDeck();
        System.out.println("Succeed building deck_1 . . .");
        this.card_1 = this.deck_1.getTopCard();
        card_1.setBounds(width/2 -card_width/2, height - card_height, card_width, card_height);

        /*Card Class this.card_2: this.card_1ing image*/
        this.deck_2 = new Deck();
        this.deck_2.createDeck();
        System.out.println("Succeed building deck_2 . . .");
        this.card_2 = this.deck_2.getTopCard();
        this.card_2.setBounds(width/2 -card_width/2, 0, card_width, card_height);

        base_pane.add(bell, new Integer(0));
        base_pane.add(this.card_1, new Integer(1), 0);
        base_pane.add(this.card_2, new Integer(1), 1);
        this.pack();
        this.setVisible(true);

        /* Mouse click eventListener used for this.card_1ing*/
        this.card_1.addMouseListener(this);
        this.bell.getLabel().addMouseListener(this);

        this.card_1 = this.deck_1.getTopCard();
    }
    public Card getCard(int num){
        switch(num){
            case 1:
                return this.card_1;
            case 2:
                return this.card_2;
            default:
                System.out.println("Invalid Card Access");
                return null;
        }
    }
    public Deck getDeck(int num){
        switch(num){
            case 1:
                return this.deck_1;
            case 2:
                return this.deck_2;
            default:
                System.out.println("Invalid Deck Access");
                return null;
        }
    }
    public void flipAndUpdate(int num){
        /* flip */
        switch(num){
            case 1:
                this.card_1.getDeck().nextTopCard();
                this.card_1.setCard(this.card_1.getDeck().getTopCard());
                break;
            case 2:
                this.card_2.getDeck().nextTopCard();
                this.card_2.setCard(this.card_2.getDeck().getTopCard());
                break;
            default:
                System.out.println("Invalid Access");
                break;
        }
        /* update */
        base_pane.revalidate();
        base_pane.repaint();
    }
    private int mouseIsEntered = 0;
    @Override
    public void mouseClicked(MouseEvent e){
        if(this.mouseIsEntered == 1 && e.getSource()==card_1) {
            System.out.println("CLICKED CARD");
            this.flipAndUpdate(1);
        }
        else if(this.mouseIsEntered == 1 && e.getSource() == this.bell.getLabel()){
            ringBellLog.add(System.currentTimeMillis());
            for(int i=0; i<ringBellLog.size(); i++){
                System.out.print(ringBellLog.get(i)+" ");
            }
            System.out.println();
        }
    }
    @Override
    public void mouseEntered(MouseEvent e){
        this.mouseIsEntered = 1;
    }
    @Override
    public void mouseExited(MouseEvent e){
        this.mouseIsEntered = 0;
    }
    @Override
    public void mousePressed(MouseEvent e){

    }
    @Override
    public void mouseReleased(MouseEvent e){

    }

}