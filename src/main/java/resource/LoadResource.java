package resource;

import javax.swing.*;

public class LoadResource {
   public static ImageIcon logo,mainBackground,encodeTextButton,encodeFileButton,
           signatureButton,hashTextButton,hashFileButton,exitButton,encodeButton,
           decodeButton,hashButton,createButton,copyButton,encodeTextButtonActive,
           encodeFileButtonActive,sourceFileBt,destFileBt,hashFileButtonActive,
           hashTextBt, hashTextButtonActive,signatureButtonActive,signatureBt,
           fileCheckSign,encodeTextHover,encodeFileHover,hashTextHover,hashFileHover,
           signatureHover,viewKeyButton,exitBt;


    static{
      try {
            logo = new ImageIcon(LoadResource.class.getResource("/component/logo.png"));
            mainBackground = new ImageIcon(LoadResource.class.getResource("/component/background.png"));
            encodeTextButton = new ImageIcon(LoadResource.class.getResource("/component/encodeText.png"));
            encodeFileButton = new ImageIcon(LoadResource.class.getResource("/component/encodeFile.png"));
            signatureButton = new ImageIcon(LoadResource.class.getResource("/component/signature.png"));
            hashTextButton = new ImageIcon(LoadResource.class.getResource("/component/hashText.png"));
            hashFileButton = new ImageIcon(LoadResource.class.getResource("/component/hashFile.png"));
            exitButton = new ImageIcon(LoadResource.class.getResource("/component/exit.png"));
            encodeButton = new ImageIcon(LoadResource.class.getResource("/component/encryption.png"));
            decodeButton = new ImageIcon(LoadResource.class.getResource("/component/decryption.png"));
            createButton = new ImageIcon(LoadResource.class.getResource("/component/create.png"));
            copyButton = new ImageIcon(LoadResource.class.getResource("/component/copy.png"));
            encodeTextButtonActive = new ImageIcon(LoadResource.class.getResource("/component/encodeTextActive.png"));
            sourceFileBt = new ImageIcon(LoadResource.class.getResource("/component/sourceFile.png"));
            destFileBt = new ImageIcon(LoadResource.class.getResource("/component/destFile.png"));
            encodeFileButtonActive = new ImageIcon(LoadResource.class.getResource("/component/encodeFileActive.png"));
            hashFileButtonActive = new ImageIcon(LoadResource.class.getResource("/component/hashFileActive.png"));
            hashButton = new ImageIcon(LoadResource.class.getResource("/component/hashButton.png"));
            hashTextBt = new ImageIcon(LoadResource.class.getResource("/component/hashTextButton.png"));
            hashTextButtonActive = new ImageIcon(LoadResource.class.getResource("/component/hashTextActive.png"));
            signatureButtonActive = new ImageIcon(LoadResource.class.getResource("/component/signatureActive.png"));
            signatureBt = new ImageIcon(LoadResource.class.getResource("/component/signatureBt.png"));
            fileCheckSign = new ImageIcon(LoadResource.class.getResource("/component/fileCheckSign.png"));
            encodeFileHover = new ImageIcon(LoadResource.class.getResource("/component/encodeFileHover.png"));
            encodeTextHover = new ImageIcon(LoadResource.class.getResource("/component/encodeTextHover.png"));
            hashFileHover = new ImageIcon(LoadResource.class.getResource("/component/hashFileHover.png"));
            hashTextHover = new ImageIcon(LoadResource.class.getResource("/component/hashTextHover.png"));
            signatureHover = new ImageIcon(LoadResource.class.getResource("/component/signatureHover.png"));
            viewKeyButton = new ImageIcon(LoadResource.class.getResource("/component/viewKeyButton.png"));
            exitBt = new ImageIcon(LoadResource.class.getResource("/component/exitBt.png"));
      }catch (Exception e){
          e.printStackTrace();
      }
  }
}
