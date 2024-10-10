package view;
import resource.LoadResource;
import javax.swing.*;
import java.awt.*;




public class LeftPanel extends JPanel {
    public static JLabel logo;
    public static JLabel encodeTextButton,encodeFileButton,signatureButton,hashTextButton,hashFileButton,exitButton;
    public LeftPanel(){
       try {
           //left Panel create
           setLayout(null);
           setBounds(0, 64, 392, 822);
           setBackground(Color.white);
           setBorder (BorderFactory.createLineBorder (Color.BLACK, 5));

           //opacity  left panel

           //left panel component

           logo = new JLabel(LoadResource.logo);
           logo.setBounds(118,39,150,150);

           encodeTextButton = new JLabel(LoadResource.encodeTextButton);
           encodeTextButton.setBounds(5,248,383,74);

           encodeFileButton = new JLabel(LoadResource.encodeFileButton);
           encodeFileButton.setBounds(5,328,383,74);

           signatureButton = new JLabel(LoadResource.signatureButton);
           signatureButton.setBounds(5,408,383,74);

           hashTextButton = new JLabel(LoadResource.hashTextButton);
           hashTextButton.setBounds(5,488,383,74);

           hashFileButton = new JLabel(LoadResource.hashFileButton);
           hashFileButton.setBounds(5,568,383,74);

           exitButton = new JLabel(LoadResource.exitButton);
           exitButton.setBounds(143,683,100,100);





            add(encodeTextButton);
            add(encodeFileButton);
            add(signatureButton);
            add(hashTextButton);
            add(hashFileButton);
            add(exitButton);
            add(logo);

       }catch (Exception e){
           e.printStackTrace();
       }

    }
public static void encodeText(){
    hashFileButton.setIcon(LoadResource.hashFileButton);
    hashTextButton.setIcon(LoadResource.hashTextButton);
    encodeTextButton.setIcon(LoadResource.encodeTextButtonActive);
    encodeFileButton.setIcon(LoadResource.encodeFileButton);
    signatureButton.setIcon(LoadResource.signatureButton);
    }
public static void encodeFile(){
    hashFileButton.setIcon(LoadResource.hashFileButton);
    hashTextButton.setIcon(LoadResource.hashTextButton);
    encodeTextButton.setIcon(LoadResource.encodeTextButton);
    encodeFileButton.setIcon(LoadResource.encodeFileButtonActive);
    signatureButton.setIcon(LoadResource.signatureButton);

}
public static void hashFile(){

    hashFileButton.setIcon(LoadResource.hashFileButtonActive);
    hashTextButton.setIcon(LoadResource.hashTextButton);
    encodeTextButton.setIcon(LoadResource.encodeTextButton);
    encodeFileButton.setIcon(LoadResource.encodeFileButton);
    signatureButton.setIcon(LoadResource.signatureButton);
}
public static void hashText(){
    hashFileButton.setIcon(LoadResource.hashFileButton);
    hashTextButton.setIcon(LoadResource.hashTextButtonActive);
    encodeTextButton.setIcon(LoadResource.encodeTextButton);
    encodeFileButton.setIcon(LoadResource.encodeFileButton);
    signatureButton.setIcon(LoadResource.signatureButton);
}

    public static void signature() {
        hashFileButton.setIcon(LoadResource.hashFileButton);
        hashTextButton.setIcon(LoadResource.hashTextButton);
        encodeTextButton.setIcon(LoadResource.encodeTextButton);
        encodeFileButton.setIcon(LoadResource.encodeFileButton);
        signatureButton.setIcon(LoadResource.signatureButtonActive);
    }

    public static void homePage() {
        hashFileButton.setIcon(LoadResource.hashFileButton);
        hashTextButton.setIcon(LoadResource.hashTextButton);
        encodeTextButton.setIcon(LoadResource.encodeTextButton);
        encodeFileButton.setIcon(LoadResource.encodeFileButton);
        signatureButton.setIcon(LoadResource.signatureButton);
    }
}
