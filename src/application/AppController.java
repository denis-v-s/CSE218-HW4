package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import linkedList.MasterLink;
import linkedList.MasterLinkedList;
import linkedList.BabyLink;
import linkedList.ListIterator;

public class AppController implements Initializable {
  @FXML private TextArea txtLoaded;
  @FXML private TextArea txtProcessed;
  @FXML private TextField txtN;
  @FXML private TextField txtParentWord;
  
  private Stage stage;
  private Path dir = Paths.get("").toAbsolutePath().resolve("data/");
  private MasterLinkedList<String> masterList;
  private ListIterator<String> masterIter;
  private ListIterator<String> childIter;
  private String inputFile = dir.resolve("blowingInTheWind.txt").toString();
  
  public AppController(Stage stage) {
    this.stage = stage;
    processFileContent(inputFile);
  }
  
  public void setInputFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(dir.toFile());
    File selection = fileChooser.showOpenDialog(stage);
    if (selection != null) {
      inputFile = selection.toString();
      processFileContent(inputFile);
      displayContent();
    }
  }
  
  public void processFileContent(String inputFile) {
    masterList = new MasterLinkedList<>();
    masterIter = masterList.getIterator();
    
    try (FileReader fr = new FileReader(inputFile); BufferedReader reader = new BufferedReader(fr)) {
      String line;
      MasterLink<String> node = null;
      
      while ((line = reader.readLine()) != null) {
        for(String word : line.split(" ")) {
          // add a word that follows
          if (node != null) {
            childIter = node.babyList.getIterator();
            childIter.insertAfter(new BabyLink<String>(word));
          }
          
          node = (MasterLink<String>) masterIter.getLinkReference(word);
          // if this is a new word, then add it to the master list
          if (node == null) {
            node = (MasterLink<String>) masterIter.insertAfter(new MasterLink<String>(word));
          }
        }
      }
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  
  public void btnProcess() {
    if (!txtParentWord.getText().isEmpty() && !txtN.getText().isEmpty()) {
      int n = Integer.parseInt(txtN.getText());
      
      txtProcessed.setText(getRandomWords(txtParentWord.getText(), n));      
    }
  }
  
  public void btnLoadFile() {
    displayContent();
  }
  
  //word: parent node from the master linked list
  // n: number of random links to return as a concatenated string
  public String getRandomWords(String word, int n) {
    MasterLink<String> node = (MasterLink<String>) masterIter.getLinkReference(word);
    String randomWord;
    
    if (node == null) {
      return "no such word in master linked list";
    }
    
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < n; i++) {
      randomWord = masterIter.getRandomLinkValue(node);
      result.append(randomWord + " ");
      node = (MasterLink<String>) masterIter.getLinkReference(randomWord);
    }
    
    return result.toString();
  }
  
  public void displayContent() {
    StringBuilder content = new StringBuilder();
    
    masterIter.reset();
    while (masterIter.getCurrent() != null) {
      // show the parent node
      MasterLink<String> node = (MasterLink<String>) masterIter.getCurrent();
      content.append(node.getData() + " ");
      
      // show the child nodes
      childIter = node.babyList.getIterator();
      childIter.reset();
      while (childIter.getCurrent() != null) {
        content.append(childIter.getCurrent().getData() + " ");
        childIter.nextLink();
      }
      
      masterIter.nextLink();
      content.append("\r\n");
    }
    
    txtLoaded.clear();
    txtLoaded.setText(content.toString());
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    displayContent();
    //txtLoaded.setText("Press the 'Load File' button");
  }
}
