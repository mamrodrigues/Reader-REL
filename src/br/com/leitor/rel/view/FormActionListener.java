package br.com.leitor.rel.view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FormActionListener extends GuiLeitorREL implements ActionListener{
	
	public final static String PROCURAR_REL = "procurarREL";
	public final static String PROCURAR_DAT = "procurarDAT";
	public final static String PROCURAR_DESTINO = "procurarDestino";
	
	public final static String LIMPAR = "limpar";
	public final static String GERAR_ARQUIVOS = "gerarArquivos";
	
	String action;
	JFileChooser fchooser;
	JFrame frame;
	JTextField field;
	
	public FormActionListener(String action, JFileChooser jFileChooser, JFrame jFrame, JTextField jField) {
		this.action = action;
		fchooser = jFileChooser;
		frame = jFrame;
		field = jField;
	}
	
	public void actionPerformedREL(ActionEvent e) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos .REL","rel");
		fchooser = new JFileChooser();
		fchooser.setFileFilter(filter);
		fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fchooser.enableInputMethods(true);
		fchooser.showSaveDialog(frame);

		field.setText(fchooser.getSelectedFile().getAbsolutePath());
	}
	
	public void actionPerformedDAT(ActionEvent e) {

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos .DAT","dat");
		fchooser = new JFileChooser();
		fchooser.setFileFilter(filter);
		fchooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fchooser.enableInputMethods(true);
		fchooser.showSaveDialog(frame);

		field.setText(fchooser.getSelectedFile().getAbsolutePath());
	}
	
	public void actionPerformedDestino(ActionEvent e) {

		fchooser = new JFileChooser();
		fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fchooser.enableInputMethods(true);
		fchooser.showSaveDialog(frame);

		field.setText(fchooser.getSelectedFile().getAbsolutePath());
	}
	
	private void actionPerformedLimpar(ActionEvent e) {
		field.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (action) {
		case PROCURAR_REL:
			this.actionPerformedREL(e);
			break;
		case PROCURAR_DAT:
			this.actionPerformedDAT(e);
			break;
		case PROCURAR_DESTINO:
			this.actionPerformedDestino(e);
			break;
		case LIMPAR:
			this.actionPerformedLimpar(e);
			break;
		}
	}

}
