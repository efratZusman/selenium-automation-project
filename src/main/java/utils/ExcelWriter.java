package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.util.*;

public class ExcelWriter {

    public static void writeCartResults(
            String filePath,
            List<Map<String, String>> productRows,
            double expectedTotal,
            double actualTotal,
            String finalStatus
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

            // ==============================
            // Styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle passStyle = createStatusStyle(workbook, IndexedColors.LIGHT_GREEN);
            CellStyle failStyle = createStatusStyle(workbook, IndexedColors.ROSE);

            // ==============================
            // Sheet 1: Product Results
            XSSFSheet sheet = workbook.createSheet("Cart Results");

            String[] headers = {
                    "Product Name", "Unit Price", "Quantity", "Row Price", "Status"
            };

            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIndex = 1;
            for (Map<String, String> map : productRows) {
                XSSFRow row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(map.getOrDefault("ProductName", ""));
                row.createCell(1).setCellValue(map.getOrDefault("UnitPrice", ""));
                row.createCell(2).setCellValue(map.getOrDefault("Quantity", ""));
                row.createCell(3).setCellValue(map.getOrDefault("RowPrice", ""));

                Cell statusCell = row.createCell(4);
                String status = map.getOrDefault("Status", "");
                statusCell.setCellValue(status);
                statusCell.setCellStyle(
                        status.equalsIgnoreCase("PASS") ? passStyle : failStyle
                );
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // ==============================
            // Sheet 2: Summary
            XSSFSheet summarySheet = workbook.createSheet("Summary");

            XSSFRow summaryHeader = summarySheet.createRow(0);
            summaryHeader.createCell(0).setCellValue("Expected Total");
            summaryHeader.createCell(1).setCellValue("Actual Total");
            summaryHeader.createCell(2).setCellValue("Final Status");

            for (int i = 0; i < 3; i++) {
                summaryHeader.getCell(i).setCellStyle(headerStyle);
            }

            XSSFRow summaryRow = summarySheet.createRow(1);
            summaryRow.createCell(0).setCellValue(expectedTotal);
            summaryRow.createCell(1).setCellValue(actualTotal);

            Cell finalStatusCell = summaryRow.createCell(2);
            finalStatusCell.setCellValue(finalStatus);
            finalStatusCell.setCellStyle(
                    finalStatus.equalsIgnoreCase("PASS") ? passStyle : failStyle
            );

            summarySheet.autoSizeColumn(0);
            summarySheet.autoSizeColumn(1);
            summarySheet.autoSizeColumn(2);

            workbook.write(fos);
        }
    }

    // ==============================
    // Styles Helpers

    private static CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        return style;
    }

    private static CellStyle createStatusStyle(XSSFWorkbook workbook, IndexedColors color) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        return style;
    }
}
