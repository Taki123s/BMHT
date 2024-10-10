package controller;
import model.*;
import resource.LoadResource;
import view.*;

import javax.crypto.IllegalBlockSizeException;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static view.RightPanel.keyMatrixRangeModel;
import static view.RightPanel.keyModel;


public class Controller {
    MainFrame mainFrame;
    String key, destFilePath,publicKey,privateKey;
    String inputData, outputData;
    int keySize;
    IEncryptionAlgorithm algorithm;
    File fileSelected,destFileSelected;
    RSA rsaAlgorithm;
    HillCipher hillCipher;
    Vigenere vigenere;
    public Controller() {
        mainFrame = new MainFrame();
        Control();

    }

    public void Control() {
        ActionListener keyMatrixSizeChooser = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dataSelect = RightPanel.keyMatrixSize.getSelectedIndex();
                if(dataSelect==0){
                    MainFrame.rightPanel.keyMatrixSelected = MainFrame.rightPanel.keyMatrixPanel2x2;
                    MainFrame.rightPanel.keyMatrixPanel3x3.setVisible(false);
                }else{
                    MainFrame.rightPanel.keyMatrixSelected = MainFrame.rightPanel.keyMatrixPanel3x3;
                    MainFrame.rightPanel.keyMatrixPanel2x2.setVisible(false);
                }
                if(mainFrame.modeInstance == MainFrame.hillIs){
                    MainFrame.rightPanel.keyMatrixSelected.setVisible(true);
                }
            }
        };
        ActionListener chooseAlgorithmEvent  = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dataSelect = RightPanel.algorithmChooser.getSelectedIndex();

                    switch (dataSelect) {
                        case 0: {
                            algorithm = new DES();
                            MainFrame.modeInstance = MainFrame.encodeIs;
                            break;
                        }
                        case 1: {
                            algorithm = new AES();
                            MainFrame.modeInstance = MainFrame.encodeIs;
                            break;
                        }
                        case 2:{
                            algorithm = new TripleDES();
                            MainFrame.modeInstance = MainFrame.encodeIs;
                            break;
                        }
                        case 3:{
                            algorithm = new Blowfish();
                            MainFrame.modeInstance = MainFrame.encodeIs;
                            break;
                        }
                        case 4:{
                            algorithm = new IDEA();
                            MainFrame.modeInstance = MainFrame.encodeIs;
                            break;
                        }
                        case 5:{
                            algorithm = new Camellia();
                            MainFrame.modeInstance = MainFrame.encodeIs;
                            break;
                        }
                        case 6:{
                            rsaAlgorithm = new RSA();
                            MainFrame.rsaView();
                            key = null;
                            keyModel = new DefaultComboBoxModel<>(rsaAlgorithm.getValidKeySize());
                            RightPanel.keySize.setModel(keyModel);

                            break;
                        }
                        case 7:{
                            hillCipher = new HillCipher();
                            hillCipher.setAlphabets(HillCipher.ENGLISH_ALPHABET);
                            MainFrame.hillView();
                            key = null;
                            keyModel.removeAllElements();
                            for(int i=4;i<hillCipher.getAlphabets().length();i++){
                                keyMatrixRangeModel.addElement(String.valueOf(i));
                            }
                            RightPanel.keyMatrixSize.setSelectedIndex(0);
                            ActionEvent eventMatrixSize = new ActionEvent(RightPanel.keyMatrixSize, ActionEvent.ACTION_PERFORMED, "");
                            keyMatrixSizeChooser.actionPerformed(eventMatrixSize);
                            break;
                        }
                        case 8:{
                            hillCipher = new HillCipher();
                            hillCipher.setAlphabets(HillCipher.VIETNAMESE_ALPHABET);
                            MainFrame.hillView();
                            key = null;
                            keyModel.removeAllElements();
                            for(int i=4;i<hillCipher.getAlphabets().length();i++){
                                keyMatrixRangeModel.addElement(String.valueOf(i));
                            }
                            RightPanel.keyMatrixSize.setSelectedIndex(0);
                            ActionEvent eventMatrixSize = new ActionEvent(RightPanel.keyMatrixSize, ActionEvent.ACTION_PERFORMED, "");
                            keyMatrixSizeChooser.actionPerformed(eventMatrixSize);
                            break;
                        }
                        case 9:{
                            vigenere = new Vigenere();
                            vigenere.setAlphabets(HillCipher.ENGLISH_ALPHABET);
                            MainFrame.modeInstance = MainFrame.vigenereIs;
                            break;
                        } case 10:{
                            vigenere = new Vigenere();
                            vigenere.setAlphabets(HillCipher.VIETNAMESE_ALPHABET);
                            MainFrame.modeInstance = MainFrame.vigenereIs;
                            break;
                        }
                        default:
                            System.out.println("lua chon ko hop le");
                    }

                   if(MainFrame.modeInstance==MainFrame.encodeIs||MainFrame.modeInstance==MainFrame.vigenereIs){
                       key = null;
                       RightPanel.currentKeyArea.setText("");
                       RightPanel.titleCurrentKey.setVisible(true);
                       RightPanel.keySize.setVisible(true);
                       RightPanel.scrollKey.setVisible(true);
                       RightPanel.createButton.setBounds(383,81,144,60);
                       MainFrame.rightPanel.noneRsaView();
                       MainFrame.rightPanel.noneHillView();
                       keyModel.removeAllElements();
                    if(MainFrame.modeInstance == MainFrame.encodeIs){
                        keyModel = new DefaultComboBoxModel<>(algorithm.getValidKeySize());

                    }else{
                        for(int i=1;i<vigenere.getAlphabets().length();i++){
                            keyModel.addElement(String.valueOf(i));
                        }
                    }
                       RightPanel.keySize.setModel(keyModel);
                   }

            }
        };

        RightPanel.keyMatrixSize.addActionListener(keyMatrixSizeChooser);


        RightPanel.algorithmChooser.addActionListener(chooseAlgorithmEvent);
        LeftPanel.logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.rightPanel.setVisible(false);
                MainFrame.homePage();
            }
        });

        LeftPanel.signatureButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.rightPanel.setVisible(false);
                MainFrame.signature();
                MainFrame.rightPanel.setVisible(true);

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                LeftPanel.signatureButton.setIcon(LoadResource.signatureHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(MainFrame.instance == MainFrame.signatureIs){
                    LeftPanel.signatureButton.setIcon(LoadResource.signatureButtonActive);
                }else{
                    LeftPanel.signatureButton.setIcon(LoadResource.signatureButton);
                }
            }
        });

        LeftPanel.encodeTextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    MainFrame.rightPanel.setVisible(false);
                    MainFrame.encodeText();
                    MainFrame.rightPanel.setVisible(true);
                    RightPanel.algorithmChooser.setSelectedIndex(0);
                    ActionEvent event = new ActionEvent(RightPanel.algorithmChooser, ActionEvent.ACTION_PERFORMED, "");
                    chooseAlgorithmEvent.actionPerformed(event);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                LeftPanel.encodeTextButton.setIcon(LoadResource.encodeTextHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(MainFrame.instance == MainFrame.encodeTextIs){
                    LeftPanel.encodeTextButton.setIcon(LoadResource.encodeTextButtonActive);
                }else{
                    LeftPanel.encodeTextButton.setIcon(LoadResource.encodeTextButton);
                }
            }
        });
        LeftPanel.encodeFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    MainFrame.rightPanel.setVisible(false);
                    MainFrame.encodeFile();
                    MainFrame.rightPanel.setVisible(true);
                    RightPanel.algorithmChooser.setSelectedIndex(0);
                    ActionEvent event = new ActionEvent(RightPanel.algorithmChooser, ActionEvent.ACTION_PERFORMED, "");
                    chooseAlgorithmEvent.actionPerformed(event);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                LeftPanel.encodeFileButton.setIcon(LoadResource.encodeFileHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(MainFrame.instance == MainFrame.encodeFileIs){
                    LeftPanel.encodeFileButton.setIcon(LoadResource.encodeFileButtonActive);
                }else{
                    LeftPanel.encodeFileButton.setIcon(LoadResource.encodeFileButton);
                }
            }
        });

        LeftPanel.hashTextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    MainFrame.hashText();
                    MainFrame.rightPanel.setVisible(true);

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                LeftPanel.hashTextButton.setIcon(LoadResource.hashTextHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(MainFrame.instance == MainFrame.hashTextIs){
                    LeftPanel.hashTextButton.setIcon(LoadResource.hashTextButtonActive);
                }else{
                    LeftPanel.hashTextButton.setIcon(LoadResource.hashTextButton);
                }
            }
        });
        LeftPanel.hashFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    MainFrame.hashFile();
                    MainFrame.rightPanel.setVisible(true);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                LeftPanel.hashFileButton.setIcon(LoadResource.hashFileHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(MainFrame.instance == MainFrame.hashFileIs){
                    LeftPanel.hashFileButton.setIcon(LoadResource.hashFileButtonActive);
                }else{
                    LeftPanel.hashFileButton.setIcon(LoadResource.hashFileButton);
                }
            }
        });



        RightPanel.inputCopyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection stringSelection = new StringSelection(RightPanel.inputTextArea.getText());
                clipboard.setContents(stringSelection, null);
                JOptionPane.showMessageDialog(mainFrame, "Coppy thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        RightPanel.outputCopyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection stringSelection = new StringSelection(RightPanel.outputTextArea.getText());
                clipboard.setContents(stringSelection, null);
                JOptionPane.showMessageDialog(mainFrame, "Coppy thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });



        RightPanel.createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String keySizeInput;
                    if(MainFrame.modeInstance == MainFrame.rsaIs) {
                        keySizeInput = RightPanel.keySize.getSelectedItem().toString();
                        keySize = Integer.parseInt(keySizeInput);
                        rsaAlgorithm.generateKey(keySize);
                        RightPanel.publicKeyArea.setText(rsaAlgorithm.exportPublicKey());
                        RightPanel.privateKeyArea.setText(rsaAlgorithm.exportPrivateKey());
                        JOptionPane.showMessageDialog(mainFrame, "Tạo key thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                    if (MainFrame.modeInstance == MainFrame.hillIs) {
                        int matrixSize = RightPanel.keyMatrixSize.getSelectedIndex() == 0 ? 2 : 3;
                        int range = Integer.parseInt(keyMatrixRangeModel.getSelectedItem().toString());
                        hillCipher.generateRandomMatrix(matrixSize, range);
                        int[] keyMatrix = hillCipher.getKeyMatrix();
                        if (matrixSize == 2) {
                            for (int i = 0; i < keyMatrix.length; i++) {
                                MainFrame.rightPanel.keyMatrix2x2[i].setText(keyMatrix[i] + "");
                            }
                        } else {

                            for (int i = 0; i < keyMatrix.length; i++) {
                                MainFrame.rightPanel.keyMatrix3x3[i].setText(keyMatrix[i] + "");
                            }
                        }
                        JOptionPane.showMessageDialog(mainFrame, "Tạo key thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                    }if(MainFrame.modeInstance == MainFrame.encodeIs||MainFrame.modeInstance==MainFrame.vigenereIs) {
                        keySizeInput = RightPanel.keySize.getSelectedItem().toString();
                        keySize = Integer.parseInt(keySizeInput);;
                        if(MainFrame.modeInstance == MainFrame.encodeIs){
                            algorithm.generateKey(keySize);
                            key = algorithm.exportKey();

                        }else{
                            vigenere.generateRandomKey(keySize);
                            key = vigenere.getKey();
                        }
                        RightPanel.currentKeyArea.setText(key);
                        JOptionPane.showMessageDialog(mainFrame, "Tạo key thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }


                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainFrame, ex.toString(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
//                } catch (NoSuchProviderException ex) {
//                    throw new RuntimeException(ex);
//                }
            }
        });
        LeftPanel.exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        CenterPanel.encryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                        try {
                            if(MainFrame.modeInstance == MainFrame.vigenereIs){
                                inputData = RightPanel.inputTextArea.getText().trim();
                                String key = RightPanel.currentKeyArea.getText();
                                vigenere.setKey(key);
                                outputData = vigenere.encrypt(inputData);
                                RightPanel.outputTextArea.setText(outputData);
                                JOptionPane.showMessageDialog(mainFrame, "Mã hóa văn bản thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                            }
                                if (MainFrame.modeInstance == MainFrame.encodeIs) {
                                    if (MainFrame.instance == MainFrame.encodeTextIs) {
                                        inputData = RightPanel.inputTextArea.getText().trim();
                                        key = RightPanel.currentKeyArea.getText();
                                        algorithm.importKey(key);
                                        outputData = algorithm.encryptText(inputData);
                                        RightPanel.outputTextArea.setText(outputData);
                                        JOptionPane.showMessageDialog(mainFrame, "Mã hóa văn bản thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                    }

                                    if (MainFrame.instance == MainFrame.encodeFileIs) {
                                        key = RightPanel.currentKeyArea.getText();
                                        algorithm.importKey(key);
                                        destFilePath = RightPanel.destPathArea.getText();
                                        boolean isSuccess;
                                        isSuccess = algorithm.encryptFile(fileSelected.getAbsolutePath(), destFilePath);
                                        if (isSuccess) {
                                            JOptionPane.showMessageDialog(mainFrame, "Mã hóa file thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                            new File(destFilePath).delete();
                                            JOptionPane.showMessageDialog(mainFrame, "Mã hóa file thất bại do file đích đã tồn tại hoặc file nguồn không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }

                                }if(MainFrame.modeInstance == MainFrame.rsaIs){

                                    publicKey = RightPanel.publicKeyArea.getText();
                                    privateKey = RightPanel.privateKeyArea.getText();
                                    rsaAlgorithm.importPublicKey(publicKey);
                                    if (MainFrame.instance == MainFrame.encodeTextIs) {
                                       try{
                                           inputData = RightPanel.inputTextArea.getText().trim();
                                           outputData = rsaAlgorithm.encryptText(inputData);
                                           RightPanel.outputTextArea.setText(outputData);
                                           JOptionPane.showMessageDialog(mainFrame, "Mã hóa văn bản thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                       }catch (NoSuchAlgorithmException exception){
                                           JOptionPane.showMessageDialog(mainFrame, "Public key không hợp lệ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                       }catch (IllegalBlockSizeException ilE){
                                           JOptionPane.showMessageDialog(mainFrame, "Dữ liệu đầu vào quá dài, độ dài dữ liệu phù hợp cho từng key size:\n 1024: 117 bytes\n 2048: 245 bytes\n 4096: 501 bytes", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                       }
                                    }
                                    if (MainFrame.instance == MainFrame.encodeFileIs) {
                                        destFilePath = RightPanel.destPathArea.getText();
                                        try{
                                            rsaAlgorithm.encryptFile(fileSelected.getAbsolutePath(), destFilePath);
                                            JOptionPane.showMessageDialog(mainFrame, "Mã hóa file thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                        }catch (IOException exception){
                                            new File(destFilePath).delete();
                                            JOptionPane.showMessageDialog(mainFrame, "Mã hóa file thất bại do file đích đã tồn tại hoặc file nguồn không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                        }catch (NoSuchAlgorithmException esl){
                                            JOptionPane.showMessageDialog(mainFrame, "Public key không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);

                                        }
                                    }
                                }if(MainFrame.modeInstance == MainFrame.hillIs){
                                    int matrixSize = RightPanel.keyMatrixSize.getSelectedIndex()==0?2:3;
                                    int[] matrix = new int[matrixSize*matrixSize];
                                    String msg = RightPanel.inputTextArea.getText();
                                    try {
                                        if (matrixSize == 2) {
                                            for (int i = 0; i < matrix.length; i++) {
                                                matrix[i] = Integer.parseInt(MainFrame.rightPanel.keyMatrix2x2[i].getText());
                                            }
                                        } else {
                                            for (int i = 0; i < matrix.length; i++) {
                                                matrix[i] = Integer.parseInt(MainFrame.rightPanel.keyMatrix3x3[i].getText());
                                            }
                                        }
                                        hillCipher.setKeyMatrix(matrix);
                                    String output = hillCipher.encrypt(msg);
                                    RightPanel.outputTextArea.setText(output);
                                    }catch (NumberFormatException Ie){
                                        JOptionPane.showMessageDialog(mainFrame, "Key không hợp lệ, các ô trong ma trận phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);

                                    }catch (StringIndexOutOfBoundsException Se){
                                        JOptionPane.showMessageDialog(mainFrame, "Các kí tự trong dữ liệu không phù hợp với bảng mã!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                    }catch (Error er){
                                        JOptionPane.showMessageDialog(mainFrame, er.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);

                                    }
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(mainFrame, "Key không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);

                        }


            }


        });
        RightPanel.sourceFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int returnVal = RightPanel.sourceFileChooser.showOpenDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    fileSelected = RightPanel.sourceFileChooser.getSelectedFile();
                    RightPanel.sourcePathArea.setText(fileSelected.getAbsolutePath());
                } else {
                    RightPanel.sourcePathArea.setText("");
                }
            }
        });
        RightPanel.destFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RightPanel.destFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = RightPanel.destFileChooser.showOpenDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    destFileSelected = RightPanel.destFileChooser.getSelectedFile();
                    if(fileSelected.exists()){
                        RightPanel.destPathArea.setText(destFileSelected.getAbsolutePath()+File.separator+System.currentTimeMillis()+fileSelected.getName());
                    }
                } else {
                    RightPanel.destPathArea.setText("");
                }
            }
        });
        RightPanel.sourceFileSignButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int returnVal = RightPanel.sourceFileChooser.showOpenDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    fileSelected = RightPanel.sourceFileChooser.getSelectedFile();
                    RightPanel.sourceSignPathArea.setText(fileSelected.getAbsolutePath());
                } else {
                    RightPanel.sourceSignPathArea.setText("");
                }
            }
        });
        CenterPanel.signatureButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String nameHashAl = RightPanel.hashAlgorithmChooser.getSelectedItem().toString();
                String originHash = RightPanel.hashSignArea.getText();
                destFilePath = RightPanel.sourcePathArea.getText();
                try {
                    outputData = HashAlgorithm.hashFile(fileSelected.getAbsolutePath(), nameHashAl);
                    if(originHash.trim().equalsIgnoreCase(outputData.trim())){
                        JOptionPane.showMessageDialog(mainFrame, "File hợp lệ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(mainFrame, "File không hợp lệ với mã hash gốc", "Lỗi", JOptionPane.ERROR_MESSAGE);

                    }
                  } catch (NoSuchAlgorithmException ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Thuật toán không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(mainFrame, "Lỗi xử lý", "Lỗi", JOptionPane.ERROR_MESSAGE);

                }

            }
        });
        CenterPanel.hashButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String nameHashAl = RightPanel.hashAlgorithmChooser.getSelectedItem().toString();

                    if (MainFrame.modeInstance == MainFrame.hashIs) {
                        if (MainFrame.instance == MainFrame.hashTextIs) {
                            inputData = RightPanel.inputTextArea.getText().trim();
                            outputData = HashAlgorithm.hashText(inputData, nameHashAl);
                            RightPanel.outputTextArea.setText(outputData);
                            JOptionPane.showMessageDialog(mainFrame, "Hash văn bản thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            destFilePath = RightPanel.sourcePathArea.getText();
                            outputData = HashAlgorithm.hashFile(fileSelected.getAbsolutePath(), nameHashAl);
                            RightPanel.hashCodeArea.setText(outputData);
                            JOptionPane.showMessageDialog(mainFrame, "Hash file thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        CenterPanel.decryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if(MainFrame.modeInstance == MainFrame.vigenereIs){
                        inputData = RightPanel.inputTextArea.getText().trim();
                        String key = RightPanel.currentKeyArea.getText();
                        vigenere.setKey(key);
                        outputData = vigenere.decrypt(inputData);
                        RightPanel.outputTextArea.setText(outputData);
                        JOptionPane.showMessageDialog(mainFrame, "Giải mã văn bản thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                    }
                        if (MainFrame.modeInstance == MainFrame.encodeIs) {
                            key = RightPanel.currentKeyArea.getText();
                            algorithm.importKey(key);
                            if (MainFrame.instance == MainFrame.encodeTextIs) {
                                inputData = RightPanel.inputTextArea.getText().trim();
                                outputData = algorithm.decryptText(inputData);
                                RightPanel.outputTextArea.setText(outputData);
                                JOptionPane.showMessageDialog(mainFrame, "Giải mã văn bản thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            }

                            if (MainFrame.instance == MainFrame.encodeFileIs) {
                                destFilePath = RightPanel.destPathArea.getText();
                                boolean isSuccess;
                                isSuccess = algorithm.decryptFile(fileSelected.getAbsolutePath(), destFilePath);
                                if (isSuccess) {
                                    JOptionPane.showMessageDialog(mainFrame, "Giải mã file thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    new File(destFilePath).delete();
                                    JOptionPane.showMessageDialog(mainFrame, "Giải mã file thất bại do file đích đã tồn tại hoặc file nguồn không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                }
                            }

                        }if(MainFrame.modeInstance == MainFrame.rsaIs){
                            publicKey = RightPanel.publicKeyArea.getText();
                            privateKey = RightPanel.privateKeyArea.getText();
                            rsaAlgorithm.importPublicKey(publicKey);
                            rsaAlgorithm.importPrivateKey(privateKey);

                            if (MainFrame.instance == MainFrame.encodeTextIs) {
                                try{
                                inputData = RightPanel.inputTextArea.getText().trim();
                                outputData = rsaAlgorithm.decryptText(inputData);
                                RightPanel.outputTextArea.setText(outputData);
                                JOptionPane.showMessageDialog(mainFrame, "Giải mã văn bản thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                }catch (NoSuchAlgorithmException exception){
                            JOptionPane.showMessageDialog(mainFrame, "Private key không hợp lệ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            }catch (IllegalBlockSizeException ilE){
                            JOptionPane.showMessageDialog(mainFrame, "Dữ liệu đầu vào quá dài, độ dài dữ liệu phù hợp cho từng key size:\n 1024: 117 bytes\n 2048: 245 bytes\n 4096: 501 bytes", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
                            }
                            if (MainFrame.instance == MainFrame.encodeFileIs) {
                                destFilePath = RightPanel.destPathArea.getText();
                                try {
                                    rsaAlgorithm.decryptFile(fileSelected.getAbsolutePath(), destFilePath);
                                    JOptionPane.showMessageDialog(mainFrame, "Giải mã file thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                } catch (IllegalBlockSizeException ilE) {
                                    JOptionPane.showMessageDialog(mainFrame, "Dữ liệu đầu vào quá dài, độ dài dữ liệu phù hợp cho từng key size:\n 1024: 117 bytes\n 2048: 245 bytes\n 4096: 501 bytes", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                }catch (Exception exception){
                                    new File(destFilePath).delete();
                                    JOptionPane.showMessageDialog(mainFrame, "Giải mã file thất bại do file đích đã tồn tại hoặc file nguồn không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    if(MainFrame.modeInstance == MainFrame.hillIs){
                        int matrixSize = RightPanel.keyMatrixSize.getSelectedIndex()==0?2:3;
                        int[] matrix = new int[matrixSize*matrixSize];
                        String msg = RightPanel.inputTextArea.getText();
                        try {
                            if (matrixSize == 2) {
                                for (int i = 0; i < matrix.length; i++) {
                                    matrix[i] = Integer.parseInt(MainFrame.rightPanel.keyMatrix2x2[i].getText());
                                }
                            } else {
                                for (int i = 0; i < matrix.length; i++) {
                                    matrix[i] = Integer.parseInt(MainFrame.rightPanel.keyMatrix3x3[i].getText());
                                }
                            }
                            hillCipher.setKeyMatrix(matrix);
                            String output = hillCipher.decrypt(msg);
                            RightPanel.outputTextArea.setText(output);
                        }catch (NumberFormatException Ie){
                            JOptionPane.showMessageDialog(mainFrame, "Key không hợp lệ, các ô trong ma trận phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mainFrame, "Key không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);

                }




        }

        });

    }
}
