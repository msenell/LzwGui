package msenell.Algoritmalar.Odev4withGui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	ArrayList<String> dataDic = new ArrayList<String>();
	ArrayList<String> dataDicDcmp = new ArrayList<String>();
	ArrayList<String> Lines = new ArrayList<String>();
	ArrayList<String> rCodes = new ArrayList<String>();
	ArrayList<Integer> Codes = new ArrayList<Integer>();

	JPanel PnlCompress, PnlDCompress;
	JTextArea TxtOrginal, TxtCompress, TxtDic, TxtCompress2, TxtDeCompress, TxtDic2;
	JTextField TxtPath;
	JList<String> ListDict;
	Button BtnReload, BtnCompAll, BtnSelect, BtnRead, BtnEmp, BtnDeCompress, BtnDReload, BtnEmp2;
	Box BoxBtn, BoxLoad, BoxCmp, BoxDeCmp, BoxDBtn;
	
	String result="";
	
	public void Reset()
	{
		TxtOrginal.setText("");
		TxtCompress.setText("");
		TxtDic.setText("");
		result = "";
		dataDic.clear();
		Lines.clear();
		BtnSelect.setEnabled(true);
		BtnReload.setEnabled(false);
	}
	
	public void Select()
	{
		TxtPath.setText(SelectATxtFile());
		BtnRead.setEnabled(true);
		BtnSelect.setEnabled(false);
	}
	
	public void Read()
	{
		DeleteTxt("C://111220002_lzw.txt");
		TxtOrginal.setText("");
		TxtToArea(TxtPath.getText(), true);
		BtnCompAll.setEnabled(true);
		BtnRead.setEnabled(false);
	}
	
	public void Compress()
	{
		for(int i = 0; i < Lines.size(); i++)
			LzwALine(Lines.get(i));
		BtnRead.setEnabled(false);
		BtnCompAll.setEnabled(false);
		BtnReload.setEnabled(true);
		
		for(int i = 0; i < dataDic.size(); i++)
			TxtDic.append("" + (256+i) + " "+ dataDic.get(i) + "\n");
	}
	
	public void _deCompress()
	{
		DeleteTxt("C://111220002_orijinal.txt");
		TxtCompress2.setText("");
		TxtToArea("C://111220002_lzw.txt", false);
		
		for(int i = 0; i < rCodes.size(); i++)
			deCompress(rCodes.get(i));
		
		for(int i = 0; i < dataDic.size(); i++)
			TxtDic2.append("" + (256+i) + " "+ dataDic.get(i) + "\n");
		
	}
	
	
	public MainFrame() 
	{
		PnlCompress = new JPanel();
		PnlCompress.setBackground(Color.cyan);
		PnlDCompress = new JPanel();
		PnlDCompress.setBackground(Color.yellow);
		
		TxtOrginal = new JTextArea(15, 25);
		TxtOrginal.setVisible(true);
		TxtOrginal.setEditable(false);
		TxtOrginal.setAutoscrolls(true);
		
		TxtCompress = new JTextArea(15, 25);
		TxtCompress.setVisible(true);
		TxtCompress.setEditable(false);
		
		TxtDic = new JTextArea(15, 10);
		TxtDic.setVisible(true);
		TxtDic.setEditable(false);
		TxtDic.setAutoscrolls(false);
		
		TxtDic2 = new JTextArea(15, 10);
		TxtDic2.setVisible(true);
		TxtDic2.setEditable(false);
		TxtDic2.setAutoscrolls(false);
		
		TxtCompress2 = new JTextArea(15, 25);
		TxtCompress2.setVisible(true);
		TxtCompress2.setEditable(false);
		
		TxtDeCompress = new JTextArea(15, 25);
		TxtDeCompress.setVisible(true);
		TxtDeCompress.setEditable(false);
		
		
		TxtPath = new JTextField("Metin Belgesi Seçiniz:");
		TxtPath.setEnabled(false);
		
		BtnReload = new Button("Reload");
		BtnReload.setEnabled(false);
		
		BtnCompAll = new Button("      Compress");
		BtnCompAll.setEnabled(false);
		
		BtnSelect = new Button("Select");
		
		BtnRead = new Button("Read");
		BtnRead.setEnabled(false);
		
		BtnEmp = new Button();
		BtnEmp.setEnabled(false);
		BtnEmp.setBackground(Color.cyan);
		
		BtnEmp2 = new Button();
		BtnEmp2.setEnabled(false);
		BtnEmp2.setBackground(Color.yellow);
		
		BtnDeCompress = new Button("De-Compress");
		BtnDeCompress.setEnabled(true);
		
		BtnDReload = new Button("Reload");
		BtnDReload.setEnabled(false);
		
		BtnSelect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				Select();		
			}
		});
		
		BtnRead.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Read();
			}
		});
		BtnCompAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Compress();
			}
		});
		
		BtnReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Reset();
			}
		});
		
		BtnDeCompress.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_deCompress();
				
			}
		});
		
		BoxBtn = Box.createVerticalBox();
		BoxLoad = Box.createHorizontalBox();
		BoxCmp = Box.createHorizontalBox();
		BoxDBtn = Box.createVerticalBox();
		BoxDeCmp = Box.createHorizontalBox();
		
		BoxLoad.add(TxtPath);
		BoxLoad.add(BtnSelect);
		BoxLoad.add(BtnRead);
		
		
		BoxBtn.add(BtnReload, BorderLayout.CENTER);
		BoxBtn.add(BtnCompAll, BorderLayout.CENTER);
		
		BoxCmp.add(TxtOrginal);
		BoxCmp.add(BoxBtn);
		BoxCmp.add(TxtCompress);
		BoxCmp.add(BtnEmp);
		BoxCmp.add(TxtDic);
		
		BoxDBtn.add(BtnDeCompress);
		BoxDBtn.add(BtnDReload);
		
		BoxDeCmp.add(TxtCompress2);
		BoxDeCmp.add(BoxDBtn);
		BoxDeCmp.add(TxtDeCompress);
		BoxDeCmp.add(BtnEmp2);
		BoxDeCmp.add(TxtDic2);
		
		PnlCompress.setLayout(new BorderLayout());
		
		PnlCompress.add(BoxLoad, BorderLayout.NORTH);
		PnlCompress.add(BoxCmp);

		PnlDCompress.add(BoxDeCmp);
		
		this.setLayout(new FlowLayout());
		this.add(PnlCompress);
		this.add(PnlDCompress);
		this.setTitle("LZW Algorithm");
		this.setSize(800, 640);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		new MainFrame();
	}
	
	private void setLookAndFeel() //Dosya seçiçi görünümünü Windows'a uyarlar.
	{
		try {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch ( Exception e ) {
		System.err.println( "Could not use Look and Feel:" + e );
		}
	}
	
	public String SelectATxtFile()  //TxT Dosyasý Seçmemizi Saðlayan Method.
	{
		setLookAndFeel(); 
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Only TxT Files", "txt");
		JFileChooser tc = new JFileChooser("C:\\");
		tc.setFileFilter(filter);
		int result = tc.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) //Secim onaylandý ise
		{
			return tc.getSelectedFile().toString(); //Seçilen Dosyanýn Yolunu Döndürür.
		}
		else if(result == JFileChooser.CANCEL_OPTION) //Ýptal edildi ise
		{
			System.out.println("Islem iptal edildi!");
		}
		else 
		{
			System.out.println("Bir hata oluþtu!");
		}
		
		return null;
	}
	
	public void TxtToArea(String path, boolean mode)
	{
		File nonZippedFile = null;
		FileInputStream fiStr = null;
		InputStreamReader isRdr = null;
		BufferedReader bfRdr = null;
		String aLine = null; //Dosyadan okunan bir satýr.
		try 
		{
			nonZippedFile = new File(path);
			fiStr = new FileInputStream(nonZippedFile);
			isRdr = new InputStreamReader(fiStr);
			bfRdr = new BufferedReader(isRdr);
		} catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try 
		{
			while((aLine = bfRdr.readLine()) != null )
			{
				if(mode)
				{
					Lines.add(aLine);
					TxtOrginal.append(aLine + "\n");
				}
				else
				{
					rCodes.add(aLine);
					TxtCompress2.append(aLine + "\n");
				}
				
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void LzwALine(String aLine)
	{
		String part;
		result = "";
		int compCode = -1;
		boolean isThere = true;
		part ="" + aLine.charAt(0);
		for(int i = 0; i<aLine.length(); i++)
		{
			part = String.valueOf(aLine.charAt(i));
			if(part.length() == 1)
			{
				compCode = (int) (part.charAt(0));
			}
			while( (isThere) && ( (i+1) < aLine.length() ) )
			{
				i++;
				part = part + aLine.charAt(i);
				if(dataDic.indexOf(part) == -1)
				{
					isThere = false;
					dataDic.add(part);
					i--;
				}
				else
					compCode = 256 + dataDic.indexOf(part);
				
			}
			isThere = true;
			if(compCode != -1)
				result = result + String.valueOf(compCode) + " ";
			
		}
		TxtCompress.append(result + "\n");
		WriteToTxt(result, "C://111220002_lzw.txt");
	}
	
	public void WriteToTxt(String compLine, String path) //Ýþlem sonucunu dosyaya yazan fonksiyon
	{
		File cmpFile = new File(path);
		try {
			FileWriter fw = new FileWriter(cmpFile, true);
			BufferedWriter bfWrt = new BufferedWriter(fw);
			PrintWriter pwt = new PrintWriter(bfWrt, true);
			pwt.flush();
			pwt.println(compLine);
			pwt.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void DeleteTxt(String path)  //Dosya silen fonksiyon
	{
	    File f = new File(path); 
	 
	    if(!f.exists()){ // eðer dosya yoksa
	        System.out.println("Dosya bulunamadýðýndan silinemedi");
	    }else{
	        f.delete(); // eðer dosya varsa silme iþlemi gerçekleþtirir.
	    }
	}
	
	public void deCompress(String aLine)
	{
		int exCode, newCode;
		String result;
		String S, orgEx;
		char C;
		Codes.clear();
		getCode(aLine);
		exCode = Codes.get(0);
		if(exCode < 256)
			orgEx = "" + (char)exCode;
		else
			orgEx = dataDicDcmp.get(exCode-256);
		result = orgEx;
		C = orgEx.charAt(0);
		for(int i = 1; i<Codes.size(); i++)
		{
			newCode = Codes.get(i);
			if( (newCode>255) && ((newCode-256) > dataDicDcmp.size()) ) //Sözlükte yoksa
			{
				S = orgEx;
				S = S + C;
			}
			
			else
			{
				if(newCode < 256)
					S = "" + (char)newCode;
				else
					S = dataDicDcmp.get(newCode-256);
			}
			result = result + S;
			C = S.charAt(0);
			dataDicDcmp.add(orgEx+C);
			exCode = newCode;
			if(newCode < 256)
				orgEx = "" + (char)newCode;
			else
				orgEx = dataDicDcmp.get(newCode-256);
		}
		WriteToTxt(result, "C://111220002_orijinal.txt");
		TxtDeCompress.append(result + "\n");
		
	}
	
	public void getCode(String aLine)
	{
		String code = "";
		for(int i = 0; i<aLine.length(); i++)
		{
			if(aLine.charAt(i) != ' ')
			{
				code = code + aLine.charAt(i);
			}
			else
			{
				Codes.add(Integer.parseInt(code));
				code = "";
			}
		}
	}
}
