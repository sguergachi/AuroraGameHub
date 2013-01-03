package aurora.engine.V1.Logic;

import chrriis.dj.nativeswing.NSOption;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ABrowser extends JWebBrowser {

    private static final long serialVersionUID = 1L;

    private ABrowser browser;

    private String navigateTo;

    public ABrowser(NSOption... options) {

        super(options);
//        this.setBarsVisible(false);
        NativeInterface.open();
        NativeInterface.runEventPump();
    }

    public ABrowser() {
//        this.setBarsVisible(false);
        NativeInterface.open();
        NativeInterface.runEventPump();
    }

    public ABrowser(Dimension size) {

        this.setBarsVisible(false);
        this.setSize(size);
//        NativeInterface.open();
//        NativeInterface.runEventPump();
    }

    public ABrowser(int Widht, int Height) {

        this.setBarsVisible(false);
        this.setSize(Widht, Height);



    }

    public JPanel getPanelBrowser() {

        JPanel panel = new JPanel();
        panel.add(this);

        return panel;
    }

    public void goTo(String URL) {

        browser = this;
        navigateTo = URL;
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                browser.navigate(navigateTo);
            }
        });
        NativeInterface.runEventPump();
    }
}