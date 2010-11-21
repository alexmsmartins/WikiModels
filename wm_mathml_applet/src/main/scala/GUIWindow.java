/*
 * Copyright (c) 1995-1997 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies. Please refer to the file "copyright.html"
 * for further important copyright and licensing information.
 *
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
import java.awt.*;

public class GUIWindow extends Frame {
    boolean inAnApplet = true;
    final String FILEDIALOGMENUITEM = "File dialog...";

    public GUIWindow() {
        Panel bottomPanel = new Panel();
        Panel centerPanel = new Panel();
        setLayout(new BorderLayout());

        //Set up the menu bar.
        MenuBar mb = new MenuBar();
        Menu m = new Menu("Menu");
        m.add(new MenuItem("Menu item 1"));
        m.add(new CheckboxMenuItem("Menu item 2"));
        m.add(new MenuItem("Menu item 3"));
        m.add(new MenuItem("-"));
        m.add(new MenuItem(FILEDIALOGMENUITEM));
        mb.add(m);
        setMenuBar(mb);

        //Add small things at the bottom of the window.
        bottomPanel.add(new TextField("TextField"));
        bottomPanel.add(new Button("Button"));
        bottomPanel.add(new Checkbox("Checkbox"));
        Choice c = new Choice();
        c.addItem("Choice Item 1");
        c.addItem("Choice Item 2");
        c.addItem("Choice Item 3");
        bottomPanel.add(c);
        add("South", bottomPanel);

        //Add big things to the center area of the window.
        centerPanel.setLayout(new GridLayout(1,2));
        //Put a canvas in the left column.
        centerPanel.add(new MyCanvas());
        //Put a label and a text area in the right column.
        Panel p = new Panel();
        p.setLayout(new BorderLayout());
        p.add("North", new Label("Label", Label.CENTER));
        p.add("Center", new TextArea("TextArea", 5, 20));
        centerPanel.add(p);
        add("Center", centerPanel);

        //Put a list on the right side of the window.
        List l = new List(3, false);
        for (int i = 1; i <= 10; i++) {
            l.addItem("List item " + i);
        }
        add("East", l);
    }

    public boolean action(Event event, Object arg) {
        //The only action event we pay attention to is when the
        //user requests we bring up a FileDialog.
        if (event.target instanceof MenuItem) {
            if (((String)arg).equals(FILEDIALOGMENUITEM)) {
                FileDialog fd = new FileDialog(this, "FileDialog");
                fd.show();
            }
        }
        return true;
    }

    public boolean handleEvent(Event event) {
        //If we're running as an application, closing the window
        //should quit the application.
        if (event.id == Event.WINDOW_DESTROY) {
            if (inAnApplet) {
                dispose();
            } else {
                System.exit(0);
            }
        }
        return super.handleEvent(event);
    }

    public static void main(String args[]) {
        GUIWindow window = new GUIWindow();
        window.inAnApplet = false;

        window.setTitle("The AWT Components");
        window.pack();
        window.show();
    }

}

//We can't just instantiate Canvas, since its default implementation
//gives us nothing interesting to look at or do.  So here's a Canvas
//subclass that draws something slightly interesting.
class MyCanvas extends Canvas {

    public void paint(Graphics g) {
        int w = size().width;
        int h = size().height;
        g.drawRect(0, 0, w - 1, h - 1);
        g.drawString("Canvas", (w - g.getFontMetrics().stringWidth("Canvas"))/2,
                      10);

        g.setFont(new Font("Helvetica", Font.PLAIN, 8));
        g.drawLine(10,10, 100,100);
        g.fillRect(9,9,3,3);
        g.drawString("(10,10)", 13, 10);
        g.fillRect(49,49,3,3);
        g.drawString("(50,50)", 53, 50);
        g.fillRect(99,99,3,3);
        g.drawString("(100,100)", 103, 100);
    }

    //If we don't specify this, the canvas might not show up at all
    //(depending on the layout manager).
    public Dimension minimumSize() {
        return new Dimension(150,130);
    }

    //If we don't specify this, the canvas might not show up at all
    //(depending on the layout manager).
    public Dimension preferredSize() {
        return minimumSize();
    }
}