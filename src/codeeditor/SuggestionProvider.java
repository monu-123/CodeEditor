package codeeditor;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.PopupAlignment;

/**
 *
 * @author Princy
 */
public class SuggestionProvider {
    private int lastPos=0;
    private ContextMenu suggestionWindow=null;
    private Trie trie=null;
    private TreeSet<String> className=null;
    public SuggestionProvider(TreeSet<String> keywords)
    {
        trie=new Trie(keywords);
    }
    public SuggestionProvider(TreeSet<String> reserveWord,TreeSet<String> keywords)
    {
        trie=new Trie(reserveWord,keywords);
    }
    public Trie getTrie()
    {
        return trie;
    }
    
    public void provideSuggestion(CodeArea codeArea)
    {
       int currentPos=codeArea.getCaretPosition();
       String text=codeArea.getText().substring(0,codeArea.getCaretPosition());
       lastPos=Math.max(text.lastIndexOf(" ")+1,Math.max(text.lastIndexOf(";")+1,Math.max(1+text.lastIndexOf("\t"),1+text.lastIndexOf("\n"))));
       codeArea.setWrapText(true);
       //System.out.println(lastPos+" "+currentPos+" "+codeArea.getCaretColumn());
       String prefix=codeArea.getText().substring(lastPos, currentPos);
       
       if(prefix.length()==0)
       {
           if(codeArea.getPopupWindow()!=null)
               codeArea.getPopupWindow().hide();
           return;
       }
       //codeArea.getStyleClass().add("contextMenu");
       Pattern pattern=Pattern.compile("[^a-zA-Z]");
       Matcher m=pattern.matcher(prefix);
       TreeSet<String> suggestions=null;
       if(m.find())
       {
           if(codeArea.getPopupWindow()!=null)
                codeArea.getPopupWindow().hide();
           return;
       }
       if(text.charAt(currentPos-1)=='.')
       {
            if(className.contains(prefix));
            {
                
            }
       }
       suggestions=trie.getAllPrefixMatchWord(prefix);
       if(suggestionWindow==null)
       {
           suggestionWindow = new ContextMenu();
           codeArea.setPopupWindow(suggestionWindow);
           //suggestionWindow.prefHeightProperty().bind(codeArea.heightProperty());
       }
       else
           suggestionWindow=(ContextMenu) codeArea.getPopupWindow();
       suggestionWindow.setMaxWidth(300);
       //suggestionWindow.setStyle(" -fx-background-color: #006699;  -fx-text-fill: white; ");
       suggestionWindow.setHideOnEscape(true);
       if(suggestions.size()==0)
       {
            if(codeArea.getPopupWindow()!=null)
                suggestionWindow.hide();
            return;
       }
       suggestionWindow.getItems().clear();
       int count=0;
       for(String suggestion : suggestions)
       {
           if(count==10)
               break;
           MenuItem menuItem=new MenuItem(suggestion);
           
           if(count>0)
               suggestionWindow.getItems().add(new SeparatorMenuItem());

           suggestionWindow.getItems().add(menuItem);
           count++;
           menuItem.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent putSuggestion) {
                   String suggest=menuItem.getText();
                   codeArea.replaceText(lastPos, currentPos, suggest);
               }
           });
       }
       suggestionWindow.requestFocus();
       
       suggestionWindow.setAutoHide(true);
       codeArea.setPopupAlignment(PopupAlignment.CARET_BOTTOM);
       suggestionWindow.show(CodeEditor.primaryStage);       
    }
}



