package codeeditor;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

/**
 *
 * @author Princy
 */
 
public class CodeAreaFactory {
    public CodeAreaFactory(){}
    private CodeArea applyCSS(CodeArea codeArea)
    {
        codeArea.getStylesheets().add(CodeAreaFactory.class.getResource("/resources/codeAreaStyle.css").toExternalForm());
        return codeArea;
    }
    public static SmartCodeArea getCodeArea(String fileName)
    {
        CodeAreaFactory factory=new CodeAreaFactory();
        String extention=fileName.substring(fileName.lastIndexOf(".")+1);
        TreeSet<String> keywords=Helper.getKeywordList(extention);
        TreeSet<String> reserveWords=Helper.getReserveWordSet(extention);
        SmartCodeArea codeArea;
        if(extention.equals("java"))
            codeArea = new SmartCodeArea(keywords,reserveWords);
        else
            codeArea = new SmartCodeArea(keywords);
        codeArea.getSyntaxHighLighter().highLightSyntax(codeArea);
        ObservableList<String> list=FXCollections.observableList(new ArrayList<String>());
        list.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                if(list.isEmpty())
                    return;
                codeArea.appendText(list.get(0)+" ");
                list.remove(0);
            }
        });
        codeArea.setList(list);
        factory.applyCSS(codeArea);
        codeArea.getStyleClass().add("codeArea");
        //codeArea.appendText("JaiShreeRam");
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        // for auto indentation 
        Pattern whiteSpace = Pattern.compile( "^\\s+" );
        codeArea.addEventFilter( KeyEvent.KEY_PRESSED, KE ->
        {
            if ( KE.getCode() == KeyCode.ENTER )
            {
                Matcher m = whiteSpace.matcher( codeArea.getParagraph( codeArea.getCurrentParagraph() ).getSegments().get( 0 ) );
                if ( m.find() ) Platform.runLater( () -> codeArea.insertText( codeArea.getCaretPosition(), m.group() ) );
            }
        });
        codeArea.addEventFilter(MouseEvent.MOUSE_RELEASED, event->{
            if(event.isPopupTrigger())
            {   
            ContextMenu mouseClickWindow=new ContextMenu();
            VBox vbox=(VBox) CodeEditor.anchorPane.getChildren().get(0);
            MenuBar menuBar=(MenuBar) vbox.getChildren().get(0);
            
            MenuItem menuItem1=new MenuItem("New");
            menuItem1.setOnAction(value->{
                menuBar.getMenus().get(0).getItems().get(0).fire();
            });
            mouseClickWindow.getItems().add(menuItem1);

            MenuItem menuItem2=new MenuItem("Open");
            menuItem2.setOnAction(value->{
                menuBar.getMenus().get(0).getItems().get(1).fire();
            });
            mouseClickWindow.getItems().add(menuItem2);

            MenuItem menuItem3=new MenuItem("Find");
            menuItem3.setOnAction(value->{
                menuBar.getMenus().get(1).getItems().get(6).fire();
            });            
            mouseClickWindow.getItems().add(menuItem3);
            
            MenuItem menuItem4=new MenuItem("Replace");
            menuItem4.setOnAction(value->{
                menuBar.getMenus().get(1).getItems().get(7).fire();
            });      
            mouseClickWindow.getItems().add(menuItem4);
            mouseClickWindow.show(CodeEditor.primaryStage,event.getSceneX(), event.getSceneY());
            }
        });
        
        /*codeArea.addEventFilter(KeyEvent.KEY_RELEASED, eventFilter->{
            if(eventFilter.getCode()==KeyCode.C)
            {
                codeArea.getSuggestionProvider().provideSuggestion(codeArea);                
            }
        });
   */     // set Syntax highlighting
        codeArea.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    codeArea.getSuggestionProvider().provideSuggestion(codeArea);                
                    codeArea.getSyntaxHighLighter().highLightSyntax(codeArea);            
     
            }
      });
        return codeArea;
    }
    
}