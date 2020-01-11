/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeeditor;

import java.util.StringTokenizer;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 *
 * @author Princy
 */
public class SyntaxHighlighting {
    public static TextArea highLightText(String data)
    {
        TextArea textArea=new TextArea();
        StringTokenizer token=new StringTokenizer(data);
        while(token.hasMoreTokens())
        {
           String s=token.nextToken();
           Text text=new Text(s);
           text.setFill(Color.RED);
           
        }
        return textArea;
    }
}
