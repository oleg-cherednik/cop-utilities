/*
 * Main
 *
 * Copyright (c) 2005-2006 Eugene Matyushkin (E-mail: skipy@mail.ru)
 *
 * License agreement:
 *
 * 1. This code is published AS IS. Author is not responsible for any damage that can be
 *    caused by any application that uses this code.
 * 2. Author does not give a garantee, that this code is error free.
 * 3. This code can be used in NON-COMMERCIAL applications AS IS without any special
 *    permission from author.
 * 4. This code can be modified without any special permission from author IF AND ONLY IF
 *    this license agreement will remain unchanged.
 * 5. SPECIAL PERMISSION for this code usage in COMMERCIAL application SHOULD be obtained
 *    from author.
 */
package ru.skipy.juga_ru.tests.rlo;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * This is a main application frame
 *
 * @author Eugene Matyushkin
 * @version 1.0
 */
public class Main extends JFrame {
	private static final long serialVersionUID = 5775299263866090279L;
	/**
     * English button caption
     */
    private static final String EN = "English";
    /**
     * English locale
     */
    private static final Locale LOCALE_EN = Locale.ENGLISH;
    /**
     * Russion button caption
     */
    private static final String RU = "\u0420\u0443\u0441\u0441\u043a\u0438\u0439";
    /**
     * Russian locale
     */
    private static final Locale LOCALE_RU = new Locale("RU");
    /**
     * French button caption
     */
    private static final String FR = "Fran\u00e7ais";
    /**
     * French locale
     */
    private static final Locale LOCALE_FR = new Locale("FR");


    /**
     * Constructs main application window
     */
    public Main() {
        super("RLO test");
        JButton btnEn = new JButton(EN);
        btnEn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDialog(LOCALE_EN);
            }
        });
        JButton btnRu = new JButton(RU);
        btnRu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDialog(LOCALE_RU);
            }
        });
        JButton btnFr = new JButton(FR);
        btnFr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDialog(LOCALE_FR);
            }
        });
        getContentPane().setLayout(new GridLayout(3, 1));
        getContentPane().add(btnEn);
        getContentPane().add(btnRu);
        getContentPane().add(btnFr);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * Opens dialog using locale specified
     *
     * @param locale locale to pass into dialog
     *
     * @see RangesDialog
     */
    private void openDialog(Locale locale) {
        RangesDialog rd = new RangesDialog(this, locale);
        rd.setVisible(true);
    }


    /**
     * Runs application
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Main m = new Main();
        m.setVisible(true);
    }

}
