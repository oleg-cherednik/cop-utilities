/*
 * RangesDialog
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

import java.util.ResourceBundle;
import java.util.Locale;
import java.awt.*;
import javax.swing.*;

/**
 * This dialog emulates some UI with data retrival from resource bundle
 *
 * @author Eugene Matyushkin
 * @version 1.0
 */
public class RangesDialog extends JDialog {
	private static final long serialVersionUID = 2450959065124210678L;

	/**
     * Constructs dialog, centered with respect to owner.
     *
     * @param owner dialog owner
     * @param locale locale for resource lookup
     */
    public RangesDialog(Frame owner, Locale locale){
        super(owner, true);
        ResourceBundle loc_data = ResourceBundle.getBundle("resources.loc_data", locale);
        setTitle(loc_data.getString("title"));
        String enabled = loc_data.getString("enabled");
        String disabled = loc_data.getString("disabled");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JLabel lblBig = new JLabel(loc_data.getString("bigAlphabet"));
        JLabel lblSmall = new JLabel(loc_data.getString("smallAlphabet"));
        JLabel lblDigits = new JLabel(loc_data.getString("digits"));
        JComboBox cbxBig = new JComboBox(new Object[]{enabled, disabled});
        JComboBox cbxSmall = new JComboBox(new Object[]{enabled, disabled});
        JComboBox cbxDigits = new JComboBox(new Object[]{enabled, disabled});
        JPanel cp = new JPanel();
        cp.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        GridBagLayout gbl = new GridBagLayout();
        cp.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.insets.right = 5;
        gbl.setConstraints(lblBig, gbc);
        gbl.setConstraints(lblSmall, gbc);
        gbl.setConstraints(lblDigits, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets.right = 0;
        gbl.setConstraints(cbxBig, gbc);
        gbl.setConstraints(cbxSmall, gbc);
        gbl.setConstraints(cbxDigits, gbc);
        cp.add(lblBig);
        cp.add(cbxBig);
        cp.add(lblSmall);
        cp.add(cbxSmall);
        cp.add(lblDigits);
        cp.add(cbxDigits);
        setContentPane(cp);
        pack();
        setLocationRelativeTo(owner);
    }

}
