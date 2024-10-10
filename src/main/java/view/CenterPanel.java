package view;

import resource.LoadResource;
import javax.swing.*;
import java.awt.*;
public class CenterPanel extends JPanel {
    public static JComponent[] arrayHash,arrayEncode,arraySignature;
    public static JLabel encryptButton,decryptButton,hashButton,signatureButton;
    public CenterPanel(){
        try {
            setLayout(null);
            setBounds(445, 462, 150, 300);
            setOpaque(true);
            setBackground(Color.white);

            signatureButton = new JLabel(LoadResource.signatureBt);
            signatureButton.setBounds(20,20,100,100);

            hashButton = new JLabel(LoadResource.hashButton);
            hashButton.setBounds(20,20,100,100);

            encryptButton = new JLabel();
            encryptButton.setOpaque(false);
            encryptButton.setIcon(LoadResource.encodeButton);
            encryptButton.setBounds(25,33,100,100);

            decryptButton = new JLabel();
            encryptButton.setOpaque(false);
            decryptButton.setIcon(LoadResource.decodeButton);
            decryptButton.setBounds(25,166,100,100);

            setBorder (BorderFactory.createLineBorder (Color.BLACK, 2));

            arrayHash = new JComponent[]{hashButton};
            arrayEncode = new JComponent[]{encryptButton,decryptButton};
            arraySignature = new JComponent[]{signatureButton};

            add(signatureButton);
            add(hashButton);
            add(decryptButton);
            add(encryptButton);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void encodeFile(){
        for(Component c : arrayHash){
            c.setVisible(false);
        }
        for(Component c : arrayEncode){
            c.setVisible(true);
        }
        signatureButton.setVisible(false);
        setBounds(837, 411, 150, 300);
    }
    public void hashFile(){

        for(Component c : arrayEncode){
            c.setVisible(false);
        }
        for(Component c : arrayHash){
            c.setVisible(true);
        }
        signatureButton.setVisible(false);
        hashButton.setIcon(LoadResource.hashButton);
        setBounds(446, 389, 140, 140);

    }
    public void hashText(){
        for(Component c : arrayEncode){
            c.setVisible(false);
        }
        for(Component c : arrayHash){
            c.setVisible(true);
        }
        signatureButton.setVisible(false);
        hashButton.setIcon(LoadResource.hashTextBt);
        setBounds(446, 389, 140, 140);
    }
    public void encodeText(){
        for(Component c : arrayHash){
            c.setVisible(false);
        }
        for(Component c : arrayEncode){
            c.setVisible(true);
        }
        signatureButton.setVisible(false);
        setBounds(445, 462, 150, 300);
    }

    public void signature() {
        for(Component c : arrayHash){
            c.setVisible(false);
        }
        for(Component c : arrayEncode){
            c.setVisible(false);
        }
        signatureButton.setVisible(true);
        setBounds(865, 336, 140, 140);
    }
}
