package com.company.inventory.util;

import com.company.inventory.model.Product;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class ProductExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Product> product;

    public ProductExcelExporter(List<Product> products){
        this.product = products;
        this.workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine(){
        this.sheet = workbook.createSheet("Result");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "Name", style);
        createCell(row, 2, "Price", style);
        createCell(row, 3, "Quantity", style);
        createCell(row, 4, "Category", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style){
        this.sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if(value instanceof Integer){
            cell.setCellValue((Integer) value);
        }else if(value instanceof  Boolean){
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);

    }

    private void writeDataLines(){
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for(Product result: product){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            this.createCell(row, columnCount++, String.valueOf(result.getId()),style);
            this.createCell(row, columnCount++, result.getName(),style);
            this.createCell(row, columnCount++, result.getPrice(),style);
            this.createCell(row, columnCount++, result.getQuantity(),style);
            this.createCell(row, columnCount++, result.getCategory().getName(),style);
        }
    }

    public void export(HttpServletResponse response)throws IOException {
        writeHeaderLine();  //write the header
        writeDataLines();   //write the data

        ServletOutputStream serverOutput = response.getOutputStream();
        workbook.write(serverOutput);
        workbook.close();

        serverOutput.close();
    }
}
