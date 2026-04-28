package com.company.inventory.util;

import com.company.inventory.model.Category;
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

public class CategoryExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Category> category;

    public CategoryExcelExporter(List<Category> categories){
        this.category = categories;
        this.workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine(){
        this.sheet = workbook.createSheet("Resultado");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "id", style);
        createCell(row, 1, "name", style);
        createCell(row, 2, "description", style);
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

        for(Category result: category){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            this.createCell(row, columnCount++, String.valueOf(result.getId()),style);
            this.createCell(row, columnCount++, result.getName(),style);
            this.createCell(row, columnCount++, result.getDescription(),style);
        }
    }

    public void export(HttpServletResponse response)throws IOException{
        writeHeaderLine();  //write the header
        writeDataLines();   //write the data

        ServletOutputStream serverOutput = response.getOutputStream();
        workbook.write(serverOutput);
        workbook.close();

        serverOutput.close();
    }

}
