package utils;

import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.util.*;

public class ExcelWriter {

    public static void writeCartResults(
            String filePath,
            List<Map<String, String>> productRows,
            double expectedTotal,
            double actualTotal,
            String finalStatus,
            String finalScreenshot
    ) throws IOException {

        File file = new File(filePath);

        if (file.exists()) {
            if (!file.delete()) {
                throw new IOException("Failed to delete existing file: " + file.getAbsolutePath());
            }
        }

        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Failed to create directory: " + parentDir.getAbsolutePath());
            }
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(file)) {

            XSSFSheet sheet = workbook.createSheet("CartResults");

            // =======================
            // כותרות מוצרים
            String[] productHeaders = {
                    "ProductName", "UnitPrice", "Quantity", "RowPrice", "Status", "Screenshot"
            };

            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < productHeaders.length; i++) {
                headerRow.createCell(i).setCellValue(productHeaders[i]);
            }

            // =======================
            // שורות מוצרים
            int rowIndex = 1;
            for (Map<String, String> map : productRows) {
                XSSFRow row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(map.getOrDefault("ProductName", ""));
                row.createCell(1).setCellValue(map.getOrDefault("UnitPrice", ""));
                row.createCell(2).setCellValue(map.getOrDefault("Quantity", ""));
                row.createCell(3).setCellValue(map.getOrDefault("RowPrice", ""));
                row.createCell(4).setCellValue(map.getOrDefault("Status", ""));
                row.createCell(5).setCellValue(map.getOrDefault("Screenshot", ""));
            }

            // =======================
            // שורת רווח
            rowIndex++;

            // =======================
            // כותרות סיכום
            XSSFRow summaryHeader = sheet.createRow(rowIndex++);
            summaryHeader.createCell(0).setCellValue("ExpectedTotal");
            summaryHeader.createCell(1).setCellValue("ActualTotal");
            summaryHeader.createCell(2).setCellValue("FinalStatus");
            summaryHeader.createCell(3).setCellValue("FinalScreenshot");

            // =======================
            // נתוני סיכום
            XSSFRow summaryRow = sheet.createRow(rowIndex);
            summaryRow.createCell(0).setCellValue(expectedTotal);
            summaryRow.createCell(1).setCellValue(actualTotal);
            summaryRow.createCell(2).setCellValue(finalStatus);
            summaryRow.createCell(3).setCellValue(finalScreenshot);

            // =======================
            // התאמת רוחב עמודות
            for (int i = 0; i < 10; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fos);
        }
    }
}
