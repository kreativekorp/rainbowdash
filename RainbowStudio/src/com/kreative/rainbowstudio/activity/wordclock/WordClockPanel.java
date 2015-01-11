package com.kreative.rainbowstudio.activity.wordclock;

import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WordClockPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JComboBox languageField;
	
	public WordClockPanel() {
		languageField = new JComboBox(WordClockLanguage.LANGUAGES);
		languageField.setMaximumRowCount(WordClockLanguage.LANGUAGES.length);
		languageField.setSelectedItem(WordClockLanguage.ENGLISH);
		languageField.setEditable(false);
		
		JPanel inner = new JPanel(new BorderLayout(12,8));
		inner.add(new JLabel("Language:"), BorderLayout.LINE_START);
		inner.add(languageField, BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		add(inner, BorderLayout.PAGE_START);
	}
	
	public WordClockLanguage getLanguage() {
		Object o = languageField.getSelectedItem();
		if (o instanceof WordClockLanguage) {
			return (WordClockLanguage)o;
		} else {
			return WordClockLanguage.ENGLISH;
		}
	}
}
