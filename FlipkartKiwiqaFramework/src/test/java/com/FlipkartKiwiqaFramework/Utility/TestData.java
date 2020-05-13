package com.FlipkartKiwiqaFramework.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestData {

	public static String uploadFilePath(String filename) {
		String dataFilePath = "Data/" + filename + "";
		File datafile = new File(dataFilePath);
		String fullpath = datafile.getAbsolutePath();
		return fullpath;
	}

	public static XSSFSheet ExcelWSheet;
	public static XSSFWorkbook ExcelWBook;
	public static XSSFCell Cell;
	public static XSSFRow Row;
	// Data provider
	@SuppressWarnings("unchecked")
	public static <UnicodeString> UnicodeString getCellData(int RowNum, int ColNum) throws Exception {
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			int dataType = Cell.getCellType();
			if (dataType == 3) {
				return (UnicodeString) "";
			} else {
				DataFormatter formatter = new DataFormatter();
				UnicodeString Data = (UnicodeString) formatter.formatCellValue(Cell);
				return Data;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}
	}
	public static Object[][] getDataForDataprovider(String FilePath, String SheetName,int startRow,int startCol)
			throws Exception {
		String[][] tabArray = null;
		try {
			FileInputStream ExcelFile = new FileInputStream(FilePath);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int ci, cj;
			int totalRows = ExcelWSheet.getLastRowNum();
			Row = ExcelWSheet.getRow(2);
			int totalCols = Row.getPhysicalNumberOfCells();
			tabArray = new String[totalRows - 1][totalCols];
			ci = 0;
			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = 0;
				for (int j = startCol; j < totalCols; j++, cj++) {
					tabArray[ci][cj] = getCellData(i, j);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return (tabArray);
	}

//  write data into excel file
	
	public static void writeExcel(String filename, String sheetname,  String cell[]) throws IOException {
		File datafile = new File(filename);
		String fullpath = datafile.getAbsolutePath();
		ExcelWBook = new XSSFWorkbook(fullpath);
		ExcelWSheet = ExcelWBook.getSheet(sheetname);
		int totalRows = ExcelWSheet.getLastRowNum();
		try {
			int rowno = totalRows + 1;
			FileInputStream inputStream = new FileInputStream(new File(fullpath));
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet firstSheet = workbook.getSheetAt(0);
			XSSFRow row = firstSheet.createRow(rowno);
			for(int i=0;i<cell.length;i++)
			{
				row.createCell(i).setCellValue(cell[i].toString());
			}
			inputStream.close();
			FileOutputStream fos = new FileOutputStream(new File(fullpath));
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// Create new excel file
	public static void createXLSFile(String filepath,String Sheetname, String cell[]) {
		try
		{
		File datafile = new File(filepath);
		String fullpath = datafile.getAbsolutePath();
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(Sheetname);
		XSSFRow row = sheet.createRow(0);
		for(int i=0;i<cell.length;i++)
		{
			row.createCell(i).setCellValue(cell[i].toString());
		}
		FileOutputStream fileOut = new FileOutputStream(fullpath);
		workbook.write(fileOut);
		fileOut.close();
		}catch (Exception ex) {
			System.out.println(ex);
		}
	}
// Get total number of row from excel sheet
	public static int getTotalRow(String Datafile,String sheet) {
		int totalRows = 0;
		try {
			File datafile = new File(Datafile);
			String fullpath = datafile.getAbsolutePath();
			ExcelWBook = new XSSFWorkbook(fullpath);
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			totalRows = ExcelWSheet.getLastRowNum();
		} catch (Exception e) {
		}
		return totalRows;
	}

	public static boolean verifyCellValue(String datafile,String sheet, String value,int i , int j)
	{
		boolean ServiceFlag = false;
		try {
			FileInputStream ExcelFile = new FileInputStream(datafile);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			String ser = getCellData(i, j);
			
					if (ser.equalsIgnoreCase(value.trim())||value.trim().contains(ser)||ser.contains(value.trim()))
						ServiceFlag = true;
				
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ServiceFlag;
	}
	public static String getCellValue(String datafile,String sheet,int i , int j)
	{
		String ser=null;
		try {
			FileInputStream ExcelFile = new FileInputStream(datafile);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			ser = getCellData(i, j);
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ser;
	}

	public static boolean verifyRowWiseColumnValue(String Datafile,String sheet,String value,int ColumnNumber) throws Exception {
		boolean ServiceFlag = false;
		String[][] tabArray = null;
		try {
			FileInputStream ExcelFile = new FileInputStream(Datafile);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			int startRow = 1;
			int ci, cj;
			int totalRows = ExcelWSheet.getLastRowNum();
			int totalCols = 5;
			tabArray = new String[totalRows][totalCols];
			ci = 0;
			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = ColumnNumber;
				int j = ColumnNumber;
				tabArray[ci][cj] = getCellData(i, j);

				if (((String) getCellData(i, j)).equalsIgnoreCase(value.trim())
						|| ((String) getCellData(i, j)).contains(value.trim())) {
					ServiceFlag = true;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return ServiceFlag;
	}
	public static void removeRowFromExcel(String Datafile,String sheetName, String value) throws IOException {
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(Datafile));
			Sheet sheet = wb.getSheet(sheetName);
			@SuppressWarnings("resource")
			Workbook wb2 = new HSSFWorkbook();
			wb2 = wb;
			Row row;
			row = sheet.getRow(0);
			if (row == null)
				row = sheet.getRow(1);
			int lastIndex = sheet.getLastRowNum();
			boolean flag = true;
			int rownum = 0;
			for (int n = 1; n <= sheet.getLastRowNum(); n++) {
				// sheet.getRow(0).getCell(0);
				row = sheet.getRow(n);
				for (int cn = 0; cn < row.getLastCellNum(); cn++) {
					Cell c = row.getCell(cn);
					String text = c.getStringCellValue();
					if (value.equals(text)) {
						flag = false;
						break;
					}
				}
				rownum = n;
				if (flag == false) {
					break;
				}
			}
			if (rownum != 0) {
				row = sheet.getRow(rownum);
				row.setZeroHeight(true);
				sheet.removeRow(row);
				if(rownum + 1<=lastIndex){
				sheet.shiftRows(rownum + 1, lastIndex, -1);
				}
				FileOutputStream fileOut = new FileOutputStream(Datafile);
				wb2.write(fileOut);
				fileOut.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getValueFromConfig(String Datafile, String value) throws IOException {
		String result="";
		File file = new File(Datafile);
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		try {
				prop.load(fileInput);
				result = prop.getProperty(value);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
		}
		return result;
	}
	public static void setCellData(String DataFile,String sheet, String Result,  int RowNum, int ColNum) throws Exception  
	 {
	  try
	  {
		  FileInputStream ExcelFile = new FileInputStream(DataFile);
		  ExcelWBook = new XSSFWorkbook(ExcelFile);
		  ExcelWSheet = ExcelWBook.getSheet(sheet);
	   Row  = ExcelWSheet.getRow(RowNum);
	   Cell = Row.getCell(ColNum);
	   if (Cell == null) 
	   {
	    Cell = Row.createCell(ColNum);
	    Cell.setCellValue(Result);
	   } 
	   else 
	   {
	    Cell.setCellValue(Result);
	   }
	   
	   /*Constant variables Test Data path and Test Data file name*/
	   FileOutputStream fileOut = new FileOutputStream(DataFile);
	   ExcelWBook.write(fileOut);
	   fileOut.flush();
	   fileOut.close();
	  }
	  catch(Exception e)
	  {
	   throw (e);
	  }
	 }
	public static void createZipfileForOutPut( String FolderName)
	{String home = System.getProperty("user.home");
	File directory = new File(home+"/Documents/" +"AutomationExecutionReports");
    if (! directory.exists()){
        directory.mkdir();
    }
	try {
		FileOutputStream fos = new FileOutputStream(home+"/Documents/AutomationExecutionReports/"+FolderName+".zip");
		ZipOutputStream zos = new ZipOutputStream(fos);
			File folder = new File("test-output/HHAeXchangeAutomation.html");
			ArrayList<File> filelist = new ArrayList<File>();
			listf(folder.getPath(), filelist);
	
		    for (int i = 0; i < filelist.size(); i++) {
		      if (filelist.get(i).isFile()) {
		    	  addCopyFile(filelist.get(i).getPath(), zos);
		      } else if (filelist.get(i).isDirectory()) {
		      }
		    }	
			zos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void listf(String directoryName, ArrayList<File> files) {
	    File directory = new File(directoryName);
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	            listf(file.getPath(), files);
	        }
	    }
	}
	
	public static void addCopyFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zos.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}
		zos.closeEntry();
		fis.close();
	}
	
	public static void deletePastScreenshots(String path)
	{
		File index = new File(path);
		
		if(index.exists() && index.isDirectory())
		{
			String[]entries = index.list();
			
			for(String s: entries)
			{
			    File currentFile = new File(index.getPath(),s);
			    currentFile.delete();
			}
			
			index.delete();
		}
	}
	
	public static void updatedBuildAnalysis(String DataFile,String sheet, String header,String TestName, String Result,int col) throws Exception  
	 {
		String[][] tabArray = null;
	  try
	  {
		  FileInputStream ExcelFile = new FileInputStream(DataFile);
		  ExcelWBook = new XSSFWorkbook(ExcelFile);
		  ExcelWSheet = ExcelWBook.getSheet(sheet);
	  // Row  = ExcelWSheet.getRow(RowNum);
	  // Cell = Row.getCell(ColNum);
	   int ColumnNumber=1;
	   int startRow = 1;
		int ci, cj;
		int totalRows = ExcelWSheet.getLastRowNum();
		int totalCols = 2;
		tabArray = new String[totalRows][totalCols];
		ci = 0;
		for (int i = startRow; i <= totalRows; i++, ci++) {
			cj = ColumnNumber;
			int j = ColumnNumber;
			tabArray[ci][cj] = getCellData(i, j);

			if (((String) getCellData(i, j)).equalsIgnoreCase(TestName.trim())) {
				
				break;
			}
		}
		Row= ExcelWSheet.getRow(Cell.getRowIndex());
		Cell=Row.createCell(col);
	    Cell.setCellValue(Result);
	  
	   FileOutputStream fileOut = new FileOutputStream(DataFile);
	   ExcelWBook.write(fileOut);
	   fileOut.flush();
	   fileOut.close();
	  }
	  catch(Exception e)
	  {
	   throw (e);
	  }
	
	 }
	public static int setHeaderDateTime(String DataFile,String sheet, String Header) throws Exception  
	 { int col=0;
	  try
	  {
		  FileInputStream ExcelFile = new FileInputStream(DataFile);
		  ExcelWBook = new XSSFWorkbook(ExcelFile);
		  ExcelWSheet = ExcelWBook.getSheet(sheet);
	   Row  = ExcelWSheet.getRow(0);
	   col=Row.getLastCellNum();
	   Cell = Row.getCell(col);
	   if (Cell == null) 
	   {
	    Cell = Row.createCell(col);
	    Cell.setCellValue(Header);
	   } 
	   else 
	   {
	    Cell.setCellValue(Header);
	   }
	   
	   FileOutputStream fileOut = new FileOutputStream(DataFile);
	   ExcelWBook.write(fileOut);
	   fileOut.flush();
	   fileOut.close();
	  }catch(Exception e)
	  {
	  }
	  return col;
	 }
}
