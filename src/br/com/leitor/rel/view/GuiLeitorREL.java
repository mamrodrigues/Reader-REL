package br.com.leitor.rel.view;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.com.leitor.rel.core.Formatter;
import br.com.leitor.rel.core.Reader;
import br.com.leitor.rel.core.Writer;
import br.com.leitor.rel.model.FileDAT;
import br.com.leitor.rel.model.FileREL;
import br.com.leitor.rel.model.FileXLS;
import br.com.leitor.rel.util.ValidaFormUtil;

public class GuiLeitorREL {
	JPanel aba;
	
	JLabel diretorioRELLabel = new JLabel("Selecione a pasta de arquivos .REL");;
	JTextField diretorioREL = new JTextField("", 30);
	JLabel diretorioDATLabel  = new JLabel("Selecione o arquivo .DAT");
	JTextField diretorioDAT = new JTextField("", 30);
	JLabel destinoSalvarLabel = new JLabel("Selecione a pasta de Destino");
	JTextField destinoSalvar = new JTextField("", 30);
	
	JButton procurarREL = new JButton("Procurar");
	JButton procurarDAT = new JButton("Procurar");
	JButton procurarDestinoSalvar = new JButton("Procurar");
	
	JButton limparaba1 = new JButton("Limpar");
	JButton limparaba2 = new JButton("Limpar");
	JButton limparaba3 = new JButton("Limpar");
	
	JButton gerarArquivos = new JButton("Gerar");
	JButton xls;
	JLabel inf;
	JFrame frame;
	JLabel status;
	JFileChooser fchooser;
	JTextArea tarea;
	JButton copiar;

	public void interfaceGui(Container pane) {
		
		// Definição de ABAS
		JTabbedPane tabs = new JTabbedPane();
		final String TAB1 = "Adicionar .REL";
		final String TAB2 = "Adicionar .DAT";
		final String TAB3 = "Gerar Arquivo de Entrada";

		// ABA1
		JPanel panel1 = new JPanel();
		panel1.add(diretorioRELLabel);
		panel1.add(diretorioREL);
		panel1.add(procurarREL);
		panel1.add(limparaba1);

		// ABA2
		JPanel panel2 = new JPanel();
		panel2.add(diretorioDATLabel);
		panel2.add(diretorioDAT);
		panel2.add(procurarDAT);
		panel2.add(limparaba2);

		// ABA3
		JPanel panel3 = new JPanel();
		panel3.add(destinoSalvarLabel);
		panel3.add(destinoSalvar);
		panel3.add(procurarDestinoSalvar);
		panel3.add(limparaba3);
		panel3.add(gerarArquivos);

		// Organiza��o das abas
		tabs.add(panel1, TAB1);
		tabs.add(panel2, TAB2);
		tabs.add(panel3, TAB3);

		pane.add(tabs, BorderLayout.CENTER);
		
		procurarREL.addActionListener(new FormActionListener(FormActionListener.PROCURAR_REL,fchooser,frame, diretorioREL));
		procurarDAT.addActionListener(new FormActionListener(FormActionListener.PROCURAR_DAT,fchooser,frame, diretorioDAT));
		procurarDestinoSalvar.addActionListener(new FormActionListener(FormActionListener.PROCURAR_DESTINO,fchooser,frame, destinoSalvar));
		
		//Limpar
		limparaba1.addActionListener(new FormActionListener(FormActionListener.LIMPAR,fchooser,frame, diretorioREL));
		limparaba2.addActionListener(new FormActionListener(FormActionListener.LIMPAR,fchooser,frame, diretorioDAT));
		limparaba3.addActionListener(new FormActionListener(FormActionListener.LIMPAR,fchooser,frame, destinoSalvar));
		
		//Gerar
		gerarArquivos.addActionListener(new GuiLeitorREL.gerarArquivosAL());
	}

	public void itemStateChanged(ItemEvent evt) {
		CardLayout cl = (CardLayout) (aba.getLayout());
		cl.show(aba, (String) evt.getItem());
	}

	private static void executarSistema() {
		JFrame frame = new JFrame(":: GERADOR DO ARQUIVO DE ENTRADA ::");
		frame.setDefaultCloseOperation(3);
		frame.setLocation(420, 250);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GuiLeitorREL exe = new GuiLeitorREL();
		exe.interfaceGui(frame.getContentPane());

		frame.pack();
		frame.setVisible(true);
	}
	
	public class gerarArquivosAL implements ActionListener {
		
		@SuppressWarnings("static-access")
		public void actionPerformed(ActionEvent e){
			ValidaFormUtil formUtil =  new ValidaFormUtil(frame);
			
			if(formUtil.validaCampos(diretorioREL.getText(), diretorioDAT.getText(),destinoSalvar.getText())){
				List<FileREL> listFileREL = new Reader().getListFileREL(diretorioREL.getText());
				FileDAT fileDAT = new Reader().getFileDAT(diretorioDAT.getText());
				String path = destinoSalvar.getText();
				int index = 1;
				
				for (FileREL fileREL : listFileREL) {
					if(formUtil.validaFileREL(fileREL)){
						String pathCaso = path.concat("/CASO-0"+index);
						File pastaCaso = new File(pathCaso);
						pastaCaso.mkdir();
//						GeradorXLS geradorXLS = new GeradorXLS();
//						geradorXLS.gerarArquivoXLS(fileREL,fileDAT,pathCaso);
						
						FileXLS fileXLS = new FileXLS();
						fileXLS.setPath(destinoSalvar.getText()+"/SAIDA-DESEJADA.xls");
						fileXLS.setSheetName("SAIDA");
						
						Formatter formatter = new Formatter(fileREL, fileDAT);
				        fileXLS.setListContent(formatter.FormatterXLS(new FileREL(), new FileDAT()));
				        
				        Writer writer = new Writer();
				        writer.generateXLS(fileXLS);	
				        
						index++;
					}	
				}
				JOptionPane.showMessageDialog(frame, "Arquivos gerados com sucesso na pasta: "+destinoSalvar.getText());
			}
		}

	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				executarSistema();
			}
		});

	}
}
