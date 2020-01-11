package codeeditor;
//import org.fxmisc.richtext.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import static codeeditor.CodeEditor.anchorPane;

public class EditorController implements Initializable {

    @FXML
    private AnchorPane anchor1;

    @FXML
    private Menu fileMenus;

    @FXML
    private MenuItem createNewFile;

    @FXML
    private MenuItem openFile;

    @FXML
    private SeparatorMenuItem openSeperator;

    @FXML
    private MenuItem saveFile;

    @FXML
    private MenuItem saveas;

    @FXML
    private SeparatorMenuItem saveSeperator;

    @FXML
    private MenuItem closeFile;

    @FXML
    private MenuItem exitFile;

    @FXML
    private Menu editMenus;

    @FXML
    private MenuItem undo;

    @FXML
    private MenuItem redo;

    @FXML
    private SeparatorMenuItem editSeperator1;

    @FXML
    private MenuItem cut;

    @FXML
    private MenuItem copy;

    @FXML
    private MenuItem paste;

    @FXML
    private Menu viewMenus;

    @FXML
    private MenuItem showToolBar;

    @FXML
    private MenuItem showLineNumber;

    @FXML
    private Menu toolMenus;

    @FXML
    private MenuItem addCompiler;

    @FXML
    private MenuItem openTerminal;

    @FXML
    private Menu helpMenus;

    @FXML
    private MenuItem about;

    @FXML
    private ToolBar toolBar;

    @FXML
    private TabPane tabPane;

    @FXML
    private HBox hbox;

    @FXML
    private MenuButton selectLanguageMenu;

    @FXML
    private MenuItem selectLanguage;

    @FXML
    private Button runButton;

    @FXML
    private VBox resultBox;

    private VoiceRecognition voiceRecognition;
    static int unsaveFileCount = 0;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Define Shortcuts key for MenuItem
        
        createNewFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        saveFile.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveas.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        exitFile.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        openTerminal.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN));
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        redo.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
    }

    @FXML
    public void searchWord(ActionEvent searchEvent) {
        Stage popup = new Stage();
        popup.setTitle("Find");
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(CodeEditor.primaryStage);
        GridPane vbox = new GridPane();
        vbox.setHgap(5);
        vbox.setVgap(5);
        //textField.setStyle("-fx-font-family: Quicksand; -fx-font-size: 18; -fx-padding: 1,1,1,1;-fx-border-color: grey; -fx-border-width: 2; -fx-border-radius: 1; -fx-border: gone; -fx-background-color: transparent; -fx-text-fill: grey;");
        TextField textField=new TextField();
        vbox.add(new Label("FindWhat :"),1,0,1,1);
        vbox.add(textField,2,0,1,1);
        CheckBox checkCase = new CheckBox();
        Label label = new Label("Check Case");
        Button find = new Button("Find");
        Button next = new Button("Next");
        vbox.getBoundsInParent();
        vbox.add(checkCase,1,2,1,1);
        vbox.add(label,2,2,1,1);
        vbox.add(find,1,3,1,1);
        vbox.add(next,2,3,1,1);
        find.setOnAction(action -> {
            Tab tab = tabPane.getSelectionModel().getSelectedItem();
            SmartCodeArea codeArea = (SmartCodeArea) tab.getContent();
            Pattern pattern = null;
            if (checkCase.isSelected()) {
                pattern = Pattern.compile(textField.getText());
            } else {
                pattern = Pattern.compile(textField.getText(), Pattern.CASE_INSENSITIVE);
            }
           Matcher m = pattern.matcher(codeArea.getText());
            next.setOnAction(value->{
                if(m.find())
                {
                    codeArea.selectRange(m.start(),m.end());
                }
            });
        });
        Scene findScene = new Scene(vbox, 400, 200);
        popup.setScene(findScene);
        popup.show();
    }

    @FXML
    public void searchReplace(ActionEvent searchEvent) {
        Stage popup = new Stage();
        popup.setTitle("Find");
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(CodeEditor.primaryStage);
        GridPane vbox = new GridPane();
        //vbox.setPadding(new Insets(3, 2, 2, 3));
        TextField textField = new TextField();
        TextField replacementField = new TextField();
        textField.setMaxWidth(300);
        replacementField.setMaxWidth(300);
        vbox.setHgap(5);
        vbox.setVgap(5);
        //textField.setStyle("-fx-font-family: Quicksand; -fx-font-size: 18; -fx-padding: 1,1,1,1;-fx-border-color: grey; -fx-border-width: 2; -fx-border-radius: 1; -fx-border: gone; -fx-background-color: transparent; -fx-text-fill: grey;");
        textField.getStyleClass().add("textField");
        replacementField.getStyleClass().add("textField");
        vbox.add(new Label("FindWhat :"),1,0,1,1);
   
        vbox.add(textField,2,0,1,1);
        vbox.add(new Label("Replace With: "),1,1,1,1);
        vbox.add(replacementField,2,1,1,1);
        CheckBox checkCase = new CheckBox();
        Label label = new Label("Check Case");
        Button find = new Button("Find");
        Button next = new Button("Next");
        Button replace=new Button("Replace");
        vbox.applyCss();
        vbox.getBoundsInParent();
        vbox.add(checkCase,1,2,1,1);
        vbox.add(label,2,2,1,1);
        vbox.add(find,1,3,1,1);
        vbox.add(next,2,3,1,1);
        vbox.add(replace,3,3,1,1);
      //  find.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        find.setOnAction(action -> {
            Tab tab = tabPane.getSelectionModel().getSelectedItem();
            SmartCodeArea codeArea = (SmartCodeArea) tab.getContent();
            Pattern pattern = null;
            if (checkCase.isSelected()) {
                pattern = Pattern.compile(textField.getText());
            } else {
                pattern = Pattern.compile(textField.getText(), Pattern.CASE_INSENSITIVE);
            }
           String replaceWith=replacementField.getText();
           Matcher m = pattern.matcher(codeArea.getText());
           
           next.setOnAction(value->{
                if(m.find())
                {
                    codeArea.selectRange(m.start(),m.end());
                    replace.setOnAction(replaceText->{
                        codeArea.replaceText(m.start(),m.end(), replaceWith);
                    });
                }
            });
        });
        Scene findScene = new Scene(vbox, 400, 200);
        popup.setScene(findScene);
        popup.show();
    }
    @FXML
    public void createNewfile(ActionEvent ar) {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All file ", "*.*");
        chooser.getExtensionFilters().add(extFilter);
        File file = chooser.showSaveDialog(CodeEditor.primaryStage);
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Tab tab = new Tab();
        tab.setStyle("-fx-fill:white;");
        tab.setOnCloseRequest(value->{
            saveFile(new ActionEvent());
        });
        String fileName = file.getAbsolutePath();
        tab.setUserData(fileName);
        tab.setStyle("-fx-background-color: #4D555C; -fx-text-base-color: white;");
        
        tab.setText(file.getName());
        SmartCodeArea codeArea = CodeAreaFactory.getCodeArea(fileName);
        chooser.getInitialDirectory();
        tab.setContent(codeArea);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().selectLast();
        tabPane.prefHeight(300);
        codeArea.setFocusTraversable(true);
        codeArea.requestFocus();
    }

    @FXML
    //open file 
    public void openOldFile(ActionEvent ar) {
        Stage stage = new Stage();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File selectedFile = chooser.showOpenDialog(stage);
        
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(selectedFile.getAbsolutePath().toString());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";

        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }
        } catch (IOException ex) {
            Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Tab tab = new Tab();
        tab.setStyle("-fx-background-color: #4D555C; -fx-tab-text-color: white;");
       
        tab.setOnCloseRequest(value->{
            saveFile(new ActionEvent());
        });
        tab.setUserData(selectedFile.getAbsolutePath().toString());
        tab.setText(selectedFile.getName());
        SmartCodeArea codeArea = CodeAreaFactory.getCodeArea(selectedFile.getAbsolutePath().toString());
        codeArea.appendText(stringBuilder.toString());
        codeArea.requestFocus();
        tab.setContent(codeArea);
        codeArea.setFocusTraversable(true);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().selectLast();
        TextField tf = new TextField();
    }

    @FXML
    //save file
    public void saveFile(ActionEvent ar) {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        String fileLocation = (String) tab.getUserData();
        fileLocation = fileLocation.replace("/", "\\");
        SmartCodeArea codeArea = (SmartCodeArea) tab.getContent();
        String fileData = codeArea.getText();
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(fileLocation);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(printWriter);
        try {
            bufferedWriter.write(fileData);
            bufferedWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String fileName = tab.getText();
        File file = new File(fileLocation);
        if (file != null) {
            saveTextToFile(fileData, file);
        }
    }

    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void exitFromProgram(ActionEvent closeProgramAction) {
        //if (voiceRecognition != null) {
            
        //}
        System.exit(1);
    }

    @FXML
    public void onSpeechRecognition(ActionEvent speechAction) {
        SmartCodeArea codeArea=(SmartCodeArea) tabPane.getSelectionModel().getSelectedItem().getContent();
        if(codeArea!=null)
        {
            if(voiceRecognition!=null)
                return;
            String text=tabPane.getSelectionModel().getSelectedItem().getText();
            String extention=text.substring(text.lastIndexOf(".")+1);
            TreeSet<String> keywords=new TreeSet<String>(Helper.getKeywordList(extention));
            voiceRecognition=new VoiceRecognition(keywords,codeArea.getList());
            voiceRecognition.start();
                /*try
                {
                     voiceRecognition.join();
                }
                catch (InterruptedException ex)
                {
                     Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
                }*/
        }
    }
    @FXML
    public void stopSpeechRecognition(ActionEvent event)
    {
        if(voiceRecognition!=null&&voiceRecognition.isAlive())
        {
            System.out.println("stop");
            voiceRecognition.stopSpeechThread();
        }
    }
    @FXML
    public void showTerminal(ActionEvent event) {
        TabPane ioTabPane = new TabPane();
        VBox resultBox = (VBox) CodeEditor.anchorPane.getChildren().get(1);
        ioTabPane.prefWidthProperty().bind(resultBox.widthProperty());
        // set input output Tab
        Tab inputTab = new Tab();
        inputTab.setText("Input");
        TextArea inputArea = new TextArea();
        inputTab.setContent(inputArea);
        Tab outputTab = new Tab();
        outputTab.setText("Output");
        outputTab.setClosable(false);
        inputTab.setClosable(false);
        TextArea outputArea = new TextArea();
        outputTab.setContent(outputArea);
        outputArea.setEditable(false);
        outputArea.setText("Output:\n");
        ioTabPane.getTabs().add(inputTab);
        ioTabPane.getTabs().add(outputTab);

        resultBox.getChildren().add(ioTabPane);
    }
    public void selectLanguage(ActionEvent selectLanguageEvent) {
        MenuItem languageMenu = (MenuItem) selectLanguageEvent.getSource();
        String language = languageMenu.getText();
        selectLanguageMenu.setText(language);
    }

    public void runProgram(ActionEvent runProgramEvent) {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        if(resultBox.getChildren().size()==0)
        {
            showTerminal(new ActionEvent());
        }
        TabPane resultTabPane = (TabPane) resultBox.getChildren().get(0);
        Tab inputTab = resultTabPane.getTabs().get(0);
        TextArea inputArea = (TextArea) inputTab.getContent();

        Tab outputTab = resultTabPane.getTabs().get(1);
        TextArea outputArea = (TextArea) outputTab.getContent();
        String fileLocation = (String) currentTab.getUserData();
        String extention = fileLocation.substring(fileLocation.lastIndexOf(".") + 1);
        Process p, p1;
        switch (extention) {
            case "cpp": {
                try {
                    p = Runtime.getRuntime().exec("g++ " + fileLocation.substring(0, fileLocation.lastIndexOf("\\")) + " -target 8 -source 8 " + fileLocation);
                    BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    BufferedReader outputError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    String line = null;
                    while ((line = output.readLine()) != null) {
                        outputArea.appendText(line + "\n");
                    }
                    while ((line = outputError.readLine()) != null) {
                        outputArea.appendText(line + "\n");
                    }
                    Button b = new Button();
                    b.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent eh) {
                            Process pRun;
                            try {
                                pRun = Runtime.getRuntime().exec("java -cp " + fileLocation.substring(0, fileLocation.lastIndexOf("\\")) + " " + currentTab.getText().substring(0, currentTab.getText().lastIndexOf(".")));
                                OutputStream out = pRun.getOutputStream();
                                out.write(inputArea.getText().getBytes());
                                out.close();
                                BufferedReader outputRun = new BufferedReader(new InputStreamReader(pRun.getInputStream()));
                                BufferedReader outputErrorRun = new BufferedReader(new InputStreamReader(pRun.getErrorStream()));
                                String line;
                                outputArea.clear();
                                while ((line = outputRun.readLine()) != null) {
                                    outputArea.appendText(line + "\n");
                                }
                                while ((line = outputErrorRun.readLine()) != null) {
                                    outputArea.appendText(line + "\n");
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    b.fireEvent(new ActionEvent());
                } catch (IOException ex) {
                    Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "c": {
                try {
                    p = Runtime.getRuntime().exec("gcc " + fileLocation);
                    //                             p=Runtime.getRuntime().exec("type input.text | "+
                    //                                   fileLocation.substring(0,fileLocation.lastIndexOf("."))+"exe > "+outputFileLocation);
                } catch (IOException ex) {
                    Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case "java": {
                try {
                    p = Runtime.getRuntime().exec("javac -d " + fileLocation.substring(0, fileLocation.lastIndexOf("\\")) + "  " + fileLocation);
                    BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    BufferedReader outputError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    String line = null;
                    boolean errorFlag = false;
                    while ((line = output.readLine()) != null) {
                        outputArea.appendText(line + "\n");
                    }
                    while ((line = outputError.readLine()) != null) {
                        errorFlag = true;
                        outputArea.appendText(line + "\n");
                    }
                    Button b = new Button();
                    b.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent eh) {
                            Process pRun;
                            try {
                                pRun = Runtime.getRuntime().exec("java -cp " + fileLocation.substring(0, fileLocation.lastIndexOf("\\")) + " " + currentTab.getText().substring(0, currentTab.getText().lastIndexOf(".")));
                                OutputStream out = pRun.getOutputStream();
                                out.write(inputArea.getText().getBytes());
                                out.close();
                                BufferedReader outputRun = new BufferedReader(new InputStreamReader(pRun.getInputStream()));
                                BufferedReader outputErrorRun = new BufferedReader(new InputStreamReader(pRun.getErrorStream()));
                                String line;
                                outputArea.clear();
                                while ((line = outputRun.readLine()) != null) {
                                    outputArea.appendText(line + "\n");
                                }
                                while ((line = outputErrorRun.readLine()) != null) {
                                    outputArea.appendText(line + "\n");
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    if (!errorFlag) {
                        b.fireEvent(new ActionEvent());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "py": {
                try {
                    p = Runtime.getRuntime().exec("python " + fileLocation);
                    OutputStream out = p.getOutputStream();
                    out.write(inputArea.getText().getBytes());
                    out.close();
                    BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    BufferedReader outputError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                    String line = null;
                    while ((line = output.readLine()) != null) {
                        outputArea.appendText(line + "\n");
                    }
                    while ((line = outputError.readLine()) != null) {
                        outputArea.appendText(line + "\n");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            default:
        }
        resultTabPane.getSelectionModel().select(outputTab);
    }
    @FXML
    public void undo(ActionEvent undoAction)
    {
       Tab selectedTab=tabPane.getSelectionModel().getSelectedItem();
       SmartCodeArea codeArea=(SmartCodeArea) selectedTab.getContent();
       codeArea.undo();
    }
    @FXML
    public void redo(ActionEvent redoAction)
    {
       Tab selectedTab=tabPane.getSelectionModel().getSelectedItem();
       SmartCodeArea codeArea=(SmartCodeArea) selectedTab.getContent();
       codeArea.redo();
    }
    @FXML
    public void cut(ActionEvent cutAction)
    {
       Tab selectedTab=tabPane.getSelectionModel().getSelectedItem();
       SmartCodeArea codeArea=(SmartCodeArea) selectedTab.getContent();
       String cutText=codeArea.getSelectedText();
       codeArea.replaceSelection("");
       codeArea.setUserData(cutText);
    }
    @FXML
    public void paste(ActionEvent pasteAction)
    {
       Tab selectedTab=tabPane.getSelectionModel().getSelectedItem();
       SmartCodeArea codeArea=(SmartCodeArea) selectedTab.getContent();
       String text=(String) codeArea.getUserData();
       codeArea.replaceSelection(text);
       //codeArea.replaceText(codeArea.getCaretPosition(),codeArea.getCaretPosition()+cutText.length(), cutText);
    }
    @FXML
    public void copy(ActionEvent copyAction)
    {
        System.out.println("cpoy");
       Tab selectedTab=tabPane.getSelectionModel().getSelectedItem();
       SmartCodeArea codeArea=(SmartCodeArea) selectedTab.getContent();
       String copyText=(String) codeArea.getSelectedText();
       codeArea.setUserData(copyText);
    }

    
    @FXML
    public void setJavaPath(ActionEvent setPath) {
        Stage popup=new Stage();
        popup.setTitle("Set Path");
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(CodeEditor.primaryStage);
        VBox vbox=new VBox();
        vbox.applyCss();
        Label label=new Label("Enter Path to jre\\lib directory:");
        vbox.getChildren().add(label);
        TextField textField=new TextField();
        vbox.getChildren().add(textField);
        Button saveButton=new Button("Save");
        vbox.getChildren().add(saveButton);
        saveButton.setOnAction(save->{
            String path=textField.getText();
            String pathFileLocation=getClass().getResource("/resources/paths.text").getFile();
            BufferedWriter writer=null;
            try {
                writer=new BufferedWriter(new FileWriter(pathFileLocation));
                writer.write(path+"dsds");
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            popup.close();
        });
        Scene scene=new Scene(vbox,300,200);
        popup.setScene(scene);
        popup.show();
    }
    public void closeSelectedFile(ActionEvent closeFile)
    {
         Tab selectedTab=tabPane.getSelectionModel().getSelectedItem();
         ;
    }
    public void aboutApplication(ActionEvent showAbout)
    {
        Stage stage=new Stage();
        stage.setTitle("About");
        stage.setResizable(false);
        stage.alwaysOnTopProperty();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(CodeEditor.primaryStage);
        VBox vbox=new VBox();
        
        VBox.setVgrow(anchorPane.getChildren().get(0), Priority.NEVER);
        //Label label1=new Label("CodeEditor Developed by Triloki Nath");
        //label1.setStyle("-fx-");
        //label1.setAlignment(Pos.CENTER);
        //vbox.getChildren().add(label1);
        //Label label2=new Label("Use for coding..");
        //label2.setAlignment(Pos.CENTER);
        //label2.setStyle("-fx-text-fill:");
        //vbox.getChildren().add(label2);
        Scene scene1=new Scene(vbox,300,200);
        stage.setAlwaysOnTop(true);
        stage.setScene(scene1);
        stage.show();
    }
    
}
