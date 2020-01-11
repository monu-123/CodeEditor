package codeeditor;

import java.util.TreeSet;
import javafx.collections.ObservableList;
import org.fxmisc.richtext.CodeArea;

/**
 *
 * @author Princy
 */
public class SmartCodeArea extends CodeArea{
    private SuggestionProvider suggestionProvider=null;
    private SyntaxHighLighter syntaxHighlighter=null;
    private ObservableList<String> list=null;
    public SmartCodeArea(){super();}
    public SmartCodeArea(TreeSet<String> keywords,TreeSet<String> reserveWords)
    {
        super();
        this.suggestionProvider=new SuggestionProvider(reserveWords,keywords);
        this.syntaxHighlighter=new SyntaxHighLighter(keywords);
    }
    public SmartCodeArea(TreeSet<String> keywords)
    {
        this.suggestionProvider=new SuggestionProvider(keywords);
        this.syntaxHighlighter=new SyntaxHighLighter(keywords);
    }
    public SuggestionProvider getSuggestionProvider()
    {
        return this.suggestionProvider;
    }
    public SyntaxHighLighter getSyntaxHighLighter()
    {
        return this.syntaxHighlighter;
    }
    public void setList(ObservableList<String> list)
    {
        this.list=list;
    }
    public ObservableList getList()
    {
        return this.list;
    }
    
}
