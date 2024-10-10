package view;

import resource.LoadResource;
import javax.swing.*;
import java.awt.*;


public class RightPanel extends JPanel {
    public static JComponent[] arrayEncodeText,arrayEncodeFile,arrayHashFile,arrayHashText,arraySignature;
    public static JFileChooser sourceFileChooser,destFileChooser;
    public static JComboBox algorithmChooser,hashAlgorithmChooser,keySize,keyMatrixSize,keyMatrixRange;
    private String[]  listAlgorithmText = new String[]{"DES", "AES","TripleDES","Blowfish","IDEA","Camellia","RSA","Hill-en","Hill-vn","Vigenere-en","Vigenere-vn"};
    private String[]  listAlgorithmFile = new String[]{"DES", "AES","TripleDES","Blowfish","IDEA","Camellia","RSA"};
    private String[] listHashAlgorithm = new String[]{"MD5","SHA-1","SHA-224","SHA-256","SHA-512","SHA-384","SHA-512/224","SHA-512/256"};
    private String[] matrixSize = new String[]{"2x2","3x3"};
    public JTextField[] keyMatrix2x2;
    public JTextField[] keyMatrix3x3;
    public static JLabel
            titleAlgorithm,titleCurrentKey,titleKey
            ,titleInputText,titleOutputText,createButton,
            inputCopyButton,outputCopyButton,
            sourceFileButton,destFileButton,
            sourceFileTitle,destFileTitle,hashCodeTitle,
            sourceFileSignButton,checkSignButton,sourceFileSignTitle,
            hashCodeSignTitle,publicKeyTitle,privateKeyTitle,keyMatrixTitle;
    public static JTextArea
            currentKeyArea,inputTextArea,outputTextArea,
            sourcePathArea,destPathArea,hashCodeArea,
            sourceSignPathArea,hashSignArea,publicKeyArea,privateKeyArea;;
    public static JScrollPane
            scrollKey;
    public JScrollPane scrollInput,scrollSignHash,scrollPublicKey,
            scrollPrivateKey,scrollOutput,scrollSrcPath,
            scrollDestPath,scrollHash,scrollSrcSignPath;
    public static DefaultComboBoxModel<String> keyModel,keyMatrixRangeModel,algorithmTextModel,algorithmFileModel;
    public JPanel keyMatrixPanel3x3,keyMatrixPanel2x2,keyMatrixSelected;

    public RightPanel() {
        try {
            setBounds(413, 64, 1033, 822);
            setLayout(null);
            setBackground(Color.white);
            setBorder (BorderFactory.createLineBorder (Color.BLACK, 5));

            keyModel = new DefaultComboBoxModel<>();
            keyMatrixRangeModel = new DefaultComboBoxModel<>();

            keyMatrixRange = new JComboBox();
            keyMatrixRange.setBounds(387,82,140,60);
            keyMatrixRange.setFont(new Font("Arial", Font.PLAIN, 40));
            keyMatrixRange.setOpaque(false);
            DefaultListCellRenderer listRendererMatrixRange;
            listRendererMatrixRange = new DefaultListCellRenderer();
            listRendererMatrixRange.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
            keyMatrixRange.setBackground(new Color(204, 204, 255));
            keyMatrixRange.setBorder(BorderFactory.createLineBorder (Color.BLACK, 2));
            keyMatrixRange.setFocusable(false);
            keyMatrixRange.setModel(keyMatrixRangeModel);
            keyMatrixRange.setRenderer(listRendererMatrixRange);

            keyMatrixSize = new JComboBox(matrixSize);
            keyMatrixSize.setBounds(242,82,140,60);
            keyMatrixSize.setFont(new Font("Arial", Font.PLAIN, 40));
            keyMatrixSize.setOpaque(false);
            DefaultListCellRenderer listRendererMatrixSize;
            listRendererMatrixSize = new DefaultListCellRenderer();
            listRendererMatrixSize.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
            keyMatrixSize.setBackground(new Color(204, 204, 255));
            keyMatrixSize.setBorder(BorderFactory.createLineBorder (Color.BLACK, 2));
            keyMatrixSize.setFocusable(false);
            keyMatrixSize.setRenderer(listRendererMatrixSize);

            keyMatrixTitle = new JLabel("Key matrix");
            keyMatrixTitle.setBounds(692,248,243,43);
            keyMatrixTitle.setFont(new Font("Arial", Font.PLAIN, 40));

            keyMatrixPanel2x2 = new JPanel();
            keyMatrixPanel2x2.setLayout(null);
            keyMatrixPanel2x2.setBounds(695,22,220,212);
            keyMatrixPanel2x2.setBackground(new Color(204, 204, 255));
            keyMatrixPanel2x2.setBorder(BorderFactory.createLineBorder (Color.red, 2));
            keyMatrixPanel2x2.setVisible(false);


            keyMatrixPanel3x3 = new JPanel();
            keyMatrixPanel3x3.setLayout(null);
            keyMatrixPanel3x3.setBounds(689,22,240,212);
            keyMatrixPanel3x3.setBackground(new Color(204, 204, 255));
            keyMatrixPanel3x3.setBorder(BorderFactory.createLineBorder (Color.red, 2));
            keyMatrixPanel3x3.setVisible(false);

            keyMatrix2x2 = new JTextField[4];
            for(int i =0; i<keyMatrix2x2.length;i++){
                keyMatrix2x2[i] = new JTextField();
                keyMatrixPanel2x2.add(keyMatrix2x2[i]);
                keyMatrix2x2[i].setFont(new Font("Arial", Font.PLAIN, 25));
            }
            keyMatrix2x2[0].setBounds(16,12,90,90);
            keyMatrix2x2[1].setBounds(112,12,90,90);
            keyMatrix2x2[2].setBounds(16,106,90,90);
            keyMatrix2x2[3].setBounds(112,106,90,90);



            keyMatrix3x3 = new JTextField[9];
            for(int i =0; i<keyMatrix3x3.length;i++){
                keyMatrix3x3[i] = new JTextField();
                keyMatrixPanel3x3.add(keyMatrix3x3[i]);
                keyMatrix3x3[i].setFont(new Font("Arial", Font.PLAIN, 25));

            }
            keyMatrix3x3[0].setBounds(16,12,64,57);
            keyMatrix3x3[1].setBounds(88,12,64,57);
            keyMatrix3x3[2].setBounds(160,12,64,57);
            keyMatrix3x3[3].setBounds(16,77,64,57);
            keyMatrix3x3[4].setBounds(88,77,64,57);
            keyMatrix3x3[5].setBounds(160,77,64,57);
            keyMatrix3x3[6].setBounds(16,142,64,57);
            keyMatrix3x3[7].setBounds(88,142,64,57);
            keyMatrix3x3[8].setBounds(160,142,64,57);

            publicKeyTitle = new JLabel("Public key");
            publicKeyTitle.setBounds(175,275,192,48);
            publicKeyTitle.setFont(new Font("Arial", Font.PLAIN, 40));

            privateKeyTitle = new JLabel("Private key");
            privateKeyTitle.setBounds(693,275,209,48);
            privateKeyTitle.setFont(new Font("Arial", Font.PLAIN, 40));

            publicKeyArea = new JTextArea();
            publicKeyArea.setWrapStyleWord(true);
            publicKeyArea.setLineWrap(true);
            publicKeyArea.setFont(new Font("Arial", Font.PLAIN, 25));
            scrollPublicKey = new JScrollPane(publicKeyArea);
            scrollPublicKey.setBounds(82, 193, 424, 58);
            scrollPublicKey.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPublicKey.setBorder(BorderFactory.createLineBorder (Color.BLACK, 3));

            privateKeyArea = new JTextArea();
            privateKeyArea.setWrapStyleWord(true);
            privateKeyArea.setLineWrap(true);
            privateKeyArea.setFont(new Font("Arial", Font.PLAIN, 25));
            scrollPrivateKey = new JScrollPane(privateKeyArea);
            scrollPrivateKey.setBounds(563, 193, 424, 58);
            scrollPrivateKey.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPrivateKey.setBorder(BorderFactory.createLineBorder (Color.BLACK, 3));



            sourceSignPathArea = new JTextArea();
            sourceSignPathArea.setWrapStyleWord(true);
            sourceSignPathArea.setLineWrap(true);
            sourceSignPathArea.setFont(new Font("Arial", Font.PLAIN, 25));
            scrollSrcSignPath = new JScrollPane(sourceSignPathArea);
            scrollSrcSignPath.setBounds(195, 269, 635, 121);
            scrollSrcSignPath.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollSrcSignPath.setBorder(BorderFactory.createLineBorder (Color.BLACK, 3));

            hashSignArea = new JTextArea();
            hashSignArea.setWrapStyleWord(true);
            hashSignArea.setLineWrap(true);
            hashSignArea.setFont(new Font("Arial", Font.PLAIN, 25));
            scrollSignHash = new JScrollPane(hashSignArea);
            scrollSignHash.setBounds(199, 422, 635, 121);
            scrollSignHash.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollSignHash.setBorder(BorderFactory.createLineBorder (Color.BLACK, 3));


            sourceFileSignTitle = new JLabel("Source File");
            sourceFileSignTitle.setFont(new Font("Arial", Font.PLAIN, 40));
            sourceFileSignTitle.setBounds(369,191,240,43);

            hashCodeSignTitle = new JLabel("Origin Hash");
            hashCodeSignTitle.setFont(new Font("Arial", Font.PLAIN, 40));
            hashCodeSignTitle.setBounds(385,575,234,43);

            sourceFileSignButton = new JLabel(LoadResource.fileCheckSign);
            sourceFileSignButton.setBounds(64,279,100,100);

            checkSignButton = new JLabel(LoadResource.signatureBt);
            checkSignButton.setBounds(865,336,140,140);

            hashAlgorithmChooser = new JComboBox(listHashAlgorithm);
            hashAlgorithmChooser.setBounds(212, 15, 286, 42);
            hashAlgorithmChooser.setFont(new Font("Arial", Font.PLAIN, 40));
            hashAlgorithmChooser.setOpaque(false);
            DefaultListCellRenderer listRendererHash;
            listRendererHash = new DefaultListCellRenderer();
            listRendererHash.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
            hashAlgorithmChooser.setBackground(new Color(204, 204, 255));
            hashAlgorithmChooser.setBorder(BorderFactory.createLineBorder (Color.BLACK, 2));
            hashAlgorithmChooser.setFocusable(false);
            hashAlgorithmChooser.setRenderer(listRendererHash);

            hashCodeArea = new JTextArea();
            hashCodeArea.setWrapStyleWord(true);
            hashCodeArea.setLineWrap(true);
            hashCodeArea.setFont(new Font("Arial", Font.PLAIN, 25));
            scrollHash = new JScrollPane(hashCodeArea);
            scrollHash.setBounds(213, 562, 666, 110);
            scrollHash.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollHash.setBorder(BorderFactory.createLineBorder (Color.BLACK, 3));

            hashCodeTitle = new JLabel("Hash code");
            hashCodeTitle.setBounds(421,716,234,43);
            hashCodeTitle.setFont(new Font("Arial", Font.PLAIN, 40));

            sourceFileChooser = new JFileChooser();

            destFileChooser = new JFileChooser();

            sourceFileTitle = new JLabel("Source File");
            sourceFileTitle.setBounds(323,342,240,43);
            sourceFileTitle.setFont(new Font("Arial", Font.PLAIN, 40));


            destFileTitle = new JLabel("Dest File");
            destFileTitle.setBounds(329,734,234,43);
            destFileTitle.setFont(new Font("Arial", Font.PLAIN, 40));

            sourcePathArea = new JTextArea();
            sourcePathArea.setWrapStyleWord(true);
            sourcePathArea.setLineWrap(true);
            sourcePathArea.setFont(new Font("Arial", Font.PLAIN, 25));
            scrollSrcPath = new JScrollPane(sourcePathArea);
            scrollSrcPath.setBounds(156, 409, 537, 121);
            scrollSrcPath.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollSrcPath.setBorder(BorderFactory.createLineBorder (Color.BLACK, 3));

            destPathArea = new JTextArea();
            destPathArea.setWrapStyleWord(true);
            destPathArea.setLineWrap(true);
            destPathArea.setFont(new Font("Arial", Font.PLAIN, 25));
            scrollDestPath = new JScrollPane(destPathArea);
            scrollDestPath.setBounds(156, 589, 537, 121);
            scrollDestPath.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollDestPath.setBorder(BorderFactory.createLineBorder (Color.BLACK, 3));


            sourceFileButton = new JLabel(LoadResource.sourceFileBt);
            sourceFileButton.setBounds(32,423,100,100);

            destFileButton = new JLabel(LoadResource.destFileBt);
            destFileButton.setBounds(32,599,100,100);

            inputCopyButton = new JLabel(LoadResource.copyButton);
            inputCopyButton.setBounds(387,347,50,50);

            outputCopyButton = new JLabel(LoadResource.copyButton);
            outputCopyButton.setBounds(978,347,50,50);

            outputTextArea = new JTextArea();
            outputTextArea.setWrapStyleWord(true);
            outputTextArea.setLineWrap(true);
            outputTextArea.setFont(new Font("Arial", Font.PLAIN, 25));
            scrollOutput = new JScrollPane(outputTextArea);
            scrollOutput.setBounds(604, 409, 424, 407);
            scrollOutput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollOutput.setBorder(BorderFactory.createLineBorder (Color.BLACK, 3));


            inputTextArea = new JTextArea();
            inputTextArea.setWrapStyleWord(true);
            inputTextArea.setLineWrap(true);
            inputTextArea.setFont(new Font("Arial", Font.PLAIN, 25));
            scrollInput = new JScrollPane(inputTextArea);
            scrollInput.setBounds(10, 409, 424, 407);
            scrollInput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollInput.setBorder(BorderFactory.createLineBorder (Color.BLACK, 3));


            currentKeyArea = new JTextArea();
            currentKeyArea.setWrapStyleWord(true);
            currentKeyArea.setLineWrap(true);
            currentKeyArea.setFont(new Font("Arial", Font.PLAIN, 25));
            scrollKey = new JScrollPane(currentKeyArea);
            scrollKey.setBounds(642, 69, 383, 64);
            scrollKey.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollKey.setBorder(BorderFactory.createLineBorder (Color.BLACK, 3));




            keySize = new JComboBox();
            keySize.setFont(new Font("Arial", Font.PLAIN, 40));
            keySize.setBounds(240, 81, 140, 60);
            keySize.setOpaque(false);
            DefaultListCellRenderer listKeySize;
            listKeySize = new DefaultListCellRenderer();
            listKeySize.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
            keySize.setBackground(new Color(204, 204, 255));
            keySize.setFocusable(false);
            keySize.setRenderer(listKeySize);
            keySize.setModel(keyModel);
            keySize.setBackground(new Color(204, 204, 255));
            keySize.setFocusable(false);
            keySize.setBorder(BorderFactory.createLineBorder (Color.BLACK, 2));


            createButton = new JLabel(LoadResource.createButton);
            createButton.setBounds(383,81,144,60);


            algorithmFileModel = new DefaultComboBoxModel<>(listAlgorithmFile);
            algorithmTextModel = new DefaultComboBoxModel<>(listAlgorithmText);

            algorithmChooser = new JComboBox();
            algorithmChooser.setBounds(240, 15, 286, 42);
            algorithmChooser.setFont(new Font("Arial", Font.PLAIN, 40));
            algorithmChooser.setOpaque(false);
            DefaultListCellRenderer listRenderer;
            listRenderer = new DefaultListCellRenderer();
            listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
            algorithmChooser.setBackground(new Color(204, 204, 255));
            algorithmChooser.setBorder(BorderFactory.createLineBorder (Color.BLACK, 2));
            algorithmChooser.setFocusable(false);
            algorithmChooser.setRenderer(listRenderer);



            titleAlgorithm = new JLabel("Algorithm");
            titleAlgorithm.setBounds(10,15,183,48);
            titleAlgorithm.setFont(new Font("Arial", Font.PLAIN, 40));

            titleCurrentKey = new JLabel("Current key");
            titleCurrentKey.setBounds(713,15,220,48);
            titleCurrentKey.setFont(new Font("Arial", Font.PLAIN, 40));

            titleKey = new JLabel("Key");
            titleKey.setBounds(10,98,70,48);
            titleKey.setFont(new Font("Arial", Font.PLAIN, 40));

            titleInputText = new JLabel("Input text:");
            titleInputText.setBounds(10,347,205,48);
            titleInputText.setFont(new Font("Arial", Font.PLAIN, 40));


            titleOutputText = new JLabel("Output text:");
            titleOutputText.setBounds(600,347,234,48);
            titleOutputText.setFont(new Font("Arial", Font.PLAIN, 40));


            add(keyMatrixRange);
            add(keyMatrixSize);
            add(keyMatrixTitle);
            add(keyMatrixPanel2x2);
            add(keyMatrixPanel3x3);
            add(publicKeyTitle);
            add(privateKeyTitle);
            add(scrollPublicKey);
            add(scrollPrivateKey);
            add(scrollSrcSignPath);
            add(scrollSignHash);
            add(sourceFileSignTitle);
            add(hashCodeSignTitle);
            add(checkSignButton);
            add(sourceFileSignButton);
            add(hashAlgorithmChooser);
            add(hashCodeTitle);
            add(scrollHash);
            add(sourceFileChooser);
            add(destFileChooser);
            add(sourceFileTitle);
            add(destFileTitle);
            add(scrollDestPath);
            add(scrollSrcPath);
            add(sourceFileButton);
            add(destFileButton);
            add(inputCopyButton);
            add(outputCopyButton);
            add(scrollOutput);
            add(scrollInput);
            add(scrollKey);
            add(keySize);
            add(createButton);
            add(titleAlgorithm);
            add(titleKey);
            add(titleCurrentKey);
            add(titleInputText);
            add(titleOutputText);
            add(algorithmChooser);


            arrayEncodeText = new JComponent[]{outputCopyButton,scrollOutput,titleOutputText,inputCopyButton,scrollInput,titleInputText,keySize,titleKey,algorithmChooser,scrollKey,titleCurrentKey,createButton};
            arrayHashText = new JComponent[]{outputCopyButton,scrollOutput,titleOutputText,inputCopyButton,scrollInput,titleInputText,hashAlgorithmChooser};
            arrayEncodeFile = new JComponent[]{scrollSrcPath,scrollDestPath,sourceFileTitle,destFileTitle,sourceFileButton,destFileButton,keySize,titleKey,algorithmChooser,scrollKey,titleCurrentKey,createButton};
            arrayHashFile = new JComponent[]{hashCodeTitle,scrollHash,scrollSrcPath,sourceFileButton,hashAlgorithmChooser,sourceFileTitle};
            arraySignature = new JComponent[]{sourceFileSignTitle,hashCodeSignTitle,checkSignButton,sourceFileSignButton,hashAlgorithmChooser,scrollSrcSignPath,scrollSignHash};

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void encodeText(){
        for(JComponent c : arrayEncodeFile){
            c.setVisible(false);
        }
        for(JComponent c : arrayHashFile){
            c.setVisible(false);
        }
        for(JComponent c : arrayHashText){
            c.setVisible(false);
        }
        for(JComponent c : arraySignature){
            c.setVisible(false);
        }
        for(JComponent c : arrayEncodeText){
            c.setVisible(true);
        }

        algorithmChooser.setModel(algorithmTextModel);
        createButton.setBounds(383,81,144,60);
        if(keyMatrixSelected!=null)keyMatrixSelected.setVisible(false);
        noneRsaView();
        noneHillView();
    }
    public void encodeFile(){
        for(JComponent c : arrayEncodeText){
            c.setVisible(false);
        }
        for(JComponent c : arrayHashFile){
            c.setVisible(false);
        }
        for(JComponent c : arrayHashText){
            c.setVisible(false);
        }
        for(JComponent c : arraySignature){
            c.setVisible(false);
        }
        for(JComponent c : arrayEncodeFile){
            c.setVisible(true);
        }
        algorithmChooser.setModel(algorithmFileModel);
        scrollSrcPath.setBounds(156, 409, 537, 121);
        sourceFileButton.setBounds(32,423,100,100);
        sourceFileTitle.setBounds(323,342,240,43);
        createButton.setBounds(383,81,144,60);
        if(keyMatrixSelected!=null)keyMatrixSelected.setVisible(false);
        noneRsaView();
        noneHillView();
    }
    public void hashFile(){
        for(JComponent c : arrayEncodeText){
            c.setVisible(false);
        }
        for(JComponent c : arrayEncodeFile){
            c.setVisible(false);
        }
        for(JComponent c : arrayHashText){
            c.setVisible(false);
        }
        for(JComponent c : arraySignature){
            c.setVisible(false);
        }
        for(JComponent c : arrayHashFile){
            c.setVisible(true);
        }
        scrollSrcPath.setBounds(212,159,667,110);
        sourceFileButton.setBounds(95,164,100,100);
        sourceFileTitle.setBounds(419,313,240,43);
        noneRsaView();
        noneHillView();
    }
    public void hashText(){
        for(JComponent c : arrayEncodeText){
            c.setVisible(false);
        }
        for(JComponent c : arrayEncodeFile){
            c.setVisible(false);
        }
        for(JComponent c : arrayHashFile){
            c.setVisible(false);
        }
        for(JComponent c : arraySignature){
            c.setVisible(false);
        }
        for(JComponent c : arrayHashText){
            c.setVisible(true);
        }
        noneRsaView();
        noneHillView();
    }

    public void signature() {
        for(JComponent c : arrayEncodeText){
            c.setVisible(false);
        }
        for(JComponent c : arrayEncodeFile){
            c.setVisible(false);
        }
        for(JComponent c : arrayHashFile){
            c.setVisible(false);
        }
        for(JComponent c : arrayHashText){
            c.setVisible(false);
        }
        for(JComponent c : arraySignature){
            c.setVisible(true);
        }
        noneRsaView();
        noneHillView();
    }

    public void rsaView() {

        titleCurrentKey.setVisible(false);
        scrollKey.setVisible(false);
        scrollPublicKey.setVisible(true);
        scrollPrivateKey.setVisible(true);
        privateKeyTitle.setVisible(true);
        publicKeyTitle.setVisible(true);
        createButton.setBounds(383,81,144,60);
        noneHillView();
    }
    public void noneRsaView(){
        scrollPublicKey.setVisible(false);
        scrollPrivateKey.setVisible(false);
        publicKeyTitle.setVisible(false);
        privateKeyTitle.setVisible(false);


    }
    public void hillView() {
        noneRsaView();
        createButton.setBounds(314,174,144,60);
        scrollKey.setVisible(false);
        keyMatrixTitle.setVisible(true);
        keyMatrixRange.setVisible(true);
        keyMatrixSize.setVisible(true);
        keySize.setVisible(false);


    }
    public void noneHillView(){
    if(keyMatrixSelected!=null)keyMatrixSelected.setVisible(false);
    keyMatrixTitle.setVisible(false);
    keyMatrixRange.setVisible(false);
    keyMatrixSize.setVisible(false);

    }
}
