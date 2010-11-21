package pt.cnbc.wikimodels;

import net.sourceforge.jeuclid.awt.MathComponent;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.applet.*;
import java.awt.*;
import java.io.IOException;
import java.io.StringReader;

public class DrawingLines extends Applet {

    int width, height;
    MathComponent comp;

    public void init() {
        width = getSize().width;
        height = getSize().height;
        comp = new MathComponent();
        try {
            comp.setDocument(stringToDom(mathMLString));
            //add(comp);
            //validate(); 
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    static String mathMLString = "<?xml version=\"1.0\"?>\n" +
            "<!DOCTYPE math PUBLIC \"-//W3C//DTD MathML 2.0//EN\" \"http://www.w3.org/TR/MathML2/dtd/mathml2.dtd\">\n" +
            "<math mode=\"display\">\n" +
            "  <mrow>\n" +
            "    <mi>I</mi>\n" +
            "    <mrow>\n" +
            "      <mo>(</mo>\n" +
            "      <mi>x</mi>\n" +
            "      <mo>)</mo>\n" +
            "    </mrow>\n" +
            "    <mo>=</mo>\n" +
            "    <mi>F</mi>\n" +
            "    <mo stretchy=\"false\">(</mo>\n" +
            "    <mi>x</mi>\n" +
            "    <mo stretchy=\"false\">)</mo>\n" +
            "    <mo>+</mo>\n" +
            "    <mi>C</mi>\n" +
            "  </mrow>\n" +
            "</math>";

    public void paint(Graphics g) {
       /*g.setColor( Color.green );
       for ( int i = 0; i < 10; ++i ) {
          g.drawLine( width, height, i * width / 10, 0 );
       }*/
        comp.paint(g);
    }

    public static Document stringToDom(String xmlSource)
            throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlSource)));
    }
}