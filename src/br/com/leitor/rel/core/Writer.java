package br.com.leitor.rel.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.leitor.rel.model.FileXLS;

public class Writer {
	
    private HSSFWorkbook workbook;
    private HSSFSheet principalSheet;
    private OutputStream outputFile;
	
	public void generateXLS(FileXLS fileXLS){
		
		try{
			outputFile = new FileOutputStream(fileXLS.getPath());
	        workbook = new HSSFWorkbook();
	        principalSheet = workbook.createSheet(fileXLS.getSheetName());
			
	        String data[] = null;
	        for (int linha = 0; linha < fileXLS.getListContent().size(); linha++) {
	            data = fileXLS.getListContent().get(linha);
	            HSSFRow row = principalSheet.createRow(linha);
	            for (int col = 0; col < data.length; col++) {
	                String s = data[col];
	                HSSFCell cell = row.createCell(col);
	                cell.setCellValue(s);
	            }
	        }
	        
	        workbook.write(outputFile);
	        outputFile.flush();
//	        fileXLS.setListContent(new ArrayList<String[]>());
	        outputFile.close();
        }catch(IOException io){
        	io.printStackTrace();
        }
    }
}
