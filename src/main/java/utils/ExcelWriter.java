package utils;
import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.util.*;

public class ExcelWriter {
    public static void writeCartResults(String filePath, List<Map<String,String>> rows) throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("CartResults");
        int r=0;
        XSSFRow header = sheet.createRow(r++);
        String[] heads = {"ProductName","UnitPrice","Quantity","RowPrice","Status","Screenshot"};
        for(int i=0;i<heads.length;i++) header.createCell(i).setCellValue(heads[i]);
        for(Map<String,String> map : rows){
            XSSFRow row = sheet.createRow(r++);
            row.createCell(0).setCellValue(map.get("ProductName"));
            row.createCell(1).setCellValue(map.get("UnitPrice"));
            row.createCell(2).setCellValue(map.get("Quantity"));
            row.createCell(3).setCellValue(map.get("RowPrice"));
            row.createCell(4).setCellValue(map.get("Status"));
            row.createCell(5).setCellValue(map.get("Screenshot"));
        }
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}
