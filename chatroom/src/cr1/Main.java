package cr1;

import javax.swing.UIManager;

public class Main {
    private static Cr1 s;
    public static void main(String[] args) throws Throwable {
        UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        s = new Cr1();
    }
}
