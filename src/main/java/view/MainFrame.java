package view;

import resource.LoadResource;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private  JLabel backgroundLb;
    public static LeftPanel leftPanel;
    public static RightPanel rightPanel;
    public static CenterPanel centerPanel;
    public static Color transparentGray = new Color(0xD9D9D9);
    public static int instance,hashFileIs,encodeTextIs,signatureIs,encodeFileIs,hashTextIs,modeInstance,hashIs,encodeIs,rsaIs,hillIs,vigenereIs;

    static {
        transparentGray = new Color(transparentGray.getRed(), transparentGray.getGreen(), transparentGray.getBlue(), 128);
        encodeTextIs = 1;
        encodeFileIs = 2;
        signatureIs = 3;
        hashTextIs = 4;
        hashFileIs = 5;
        hashIs = 6;
        encodeIs = 7;
        rsaIs = 8;
        hillIs = 9;
        vigenereIs = 10;
        instance = 0;
        modeInstance=0;
    }
    public MainFrame(){
        try {
            setSize(1446, 886);
            setResizable(false);
            setLayout(null);
            setIconImage(new ImageIcon(MainFrame.class.getResource("/component/logo.png")).getImage());
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            backgroundLb = new JLabel(LoadResource.mainBackground);
            setContentPane(backgroundLb);
            //left Panel create
            leftPanel = new LeftPanel();
            //center Panel create
            centerPanel = new CenterPanel();
            //right Panel create
            rightPanel = new RightPanel();
            rightPanel.add(centerPanel);

            //add component to frame
            rightPanel.setVisible(false);
            add(leftPanel);
            add(rightPanel);
            //visible frame
            setTitle("Secure app");
            setLocationRelativeTo(null);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void encodeFile(){
        instance = encodeFileIs;
        modeInstance = encodeIs;
        centerPanel.encodeFile();
        LeftPanel.encodeFile();
        rightPanel.encodeFile();
    }
    public static void hashFile(){
        instance = hashFileIs;
        modeInstance = hashIs;
        centerPanel.hashFile();
        LeftPanel.hashFile();
        rightPanel.hashFile();
    }
    public static void hashText(){
        instance = hashTextIs;
        modeInstance = hashIs;
        centerPanel.hashText();
        LeftPanel.hashText();
        rightPanel.hashText();
    }
    public static void encodeText(){
        instance = encodeTextIs;
        modeInstance = encodeIs;
        centerPanel.encodeText();
        LeftPanel.encodeText();
        rightPanel.encodeText();
    }

    public static void signature() {
        instance = signatureIs;
        modeInstance = MainFrame.signatureIs;
        centerPanel.signature();
        LeftPanel.signature();
        rightPanel.signature();
    }

    public static void homePage() {
      LeftPanel.homePage();
    }

    public static void rsaView() {
        rightPanel.rsaView();
        modeInstance = rsaIs;

    }

    public static void hillView() {
        rightPanel.hillView();
        modeInstance = hillIs;

    }
}
