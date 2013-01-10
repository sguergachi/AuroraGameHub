package aurora.engine.V1.Logic;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ABrowser extends JFrame{

    private static final long serialVersionUID = 1L;

    private ABrowser browser;

    private String navigateTo;


    public ABrowser() {
//        this.setBarsVisible(false);
//        NativeInterface.open();
//        NativeInterface.runEventPump();
    }

    public ABrowser(Dimension size) {

//        this.setBarsVisible(false);
        this.setSize(size);
//        NativeInterface.open();
//        NativeInterface.runEventPump();
    }

    public ABrowser(int Widht, int Height) {

//        this.setBarsVisible(false);
        this.setSize(Widht, Height);



    }

    public JPanel getPanelBrowser() {

        JPanel panel = new JPanel();
        panel.add(this);

        return panel;
    }

    public void goTo(String URL) {

//        browser = this;
//        navigateTo = URL;
//        NativeInterface.open();
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                browser.navigate(navigateTo);
//            }
//        });
//        NativeInterface.runEventPump();
    }
}