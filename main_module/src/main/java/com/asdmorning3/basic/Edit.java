package com.asdmorning3.basic;

import com.asdmorning3.test.InterfaceLanguages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Objects;

public class Edit {

    VocableDictionary vcb;
    Vocable vocable;
    JFrame frame;
    JPanel pane;
    JButton btnSubmit;

    JTextField txtFld1;
    JLabel lblLang1;
    JLabel lblWord1;
    public JComboBox<Vocable.Language> comboBoxLang1;
    InterfaceLanguages.Languages lang;

    public Edit(VocableDictionary v, Vocable vc, InterfaceLanguages.Languages int_lang)
    {

        vcb = v;
        vocable = vc;
        pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        btnSubmit = new JButton();

        txtFld1 = new JTextField(vc.getWord());
        lblLang1 = new JLabel();
        lang = int_lang;
        frame = new JFrame();
        lblWord1 = new JLabel();
        getIntLang();
        comboBoxLang1 = new JComboBox<>(Vocable.Language.class.getEnumConstants());
        comboBoxLang1.setSelectedItem(vocable.getLanguage());

        frame.setSize(300, 300);

        getIntLang();
        c.gridx = 0;
        c.gridy++;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = .05;
        c.insets = new Insets(10, 15, 0, 20);
        pane.add(lblLang1, c);
        c.gridx = 0;
        c.gridy++;
        c.insets = new Insets(10, 15, 10, 20);
        comboBoxLang1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    if(!((Vocable.Language)comboBoxLang1.getSelectedItem()).equals(vocable.getLanguage()))
                        txtFld1.setText((vocable = vocable.getTranslation((Vocable.Language)comboBoxLang1.getSelectedItem())).getWord());
                }
                catch (Exception ex)
                {
                    System.out.println(ex.toString());
                }
            }
        });
        pane.add(comboBoxLang1, c);

        c.gridx = 0;
        c.gridy++;
        c.insets = new Insets(10, 15, 0, 20);
        pane.add(lblWord1, c);

        c.gridx = 0;
        c.gridy++;
        c.insets = new Insets(10, 15, 0, 20);
        pane.add(txtFld1, c);

        c.gridx = 1;
        c.gridy++;
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {

                }
                catch(NullPointerException ex)
                {
                    System.out.println("one of the objects is null");
                }
             }
        });
        pane.add(btnSubmit, c);


        frame.add(pane, BorderLayout.CENTER);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                /*try {
                    JFileChooser tosave = new JFileChooser();
                    if(tosave.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
                    {
                        vcb.save(tosave.getSelectedFile().getPath());
                    }

                }
                catch(NullPointerException | IOException ex)
                {
                    System.out.println("one of the objects is null");
                }*/
                super.windowClosing(e);
            }
        });
        frame.setVisible(true);
    }

     //testing
    public static void main(String args[])
    {
        VocableDictionary d = new VocableDictionary();
        d.addVocable(new Vocable("hallo", Vocable.Language.GER),
                new Vocable("hello", Vocable.Language.ENG));
        Edit g = new Edit(d, (Vocable) d.getVocableList().toArray()[0], InterfaceLanguages.Languages.EN);
    }

    private void getIntLang()
    {

        lblLang1.setText(InterfaceLanguages.getString(lang, "language"));
        lblWord1.setText(InterfaceLanguages.getString(lang, "word"));
        btnSubmit.setText(InterfaceLanguages.getString(lang, "save"));
        frame.setTitle(InterfaceLanguages.getString(lang, "vocab-trainer"));
    }
}
