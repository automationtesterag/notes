```java
package other;

import org.apache.poi.ss.usermodel.*;

import java.io.*;

public class Excel {
    public static void readExcel() throws IOException{
        FileInputStream fis =new FileInputStream("test.xlsx");
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        for(Row row:sheet){
            for(Cell c :row){
                System.out.print(formatter.formatCellValue(c)+"\t");
            }
        }

        Row row = sheet.getRow(0);
        Cell cell = row.getCell(1);
        System.out.println("Cell value at row 0, column 1: " + formatter.formatCellValue(cell));
        workbook.close();
        fis.close();

    }

    public static void writeExcel() throws IOException {

            String filePath = "test.xlsx";

            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = WorkbookFactory.create(fis);

            fis.close();

            Sheet sheet = workbook.getSheetAt(0);

            // Update cell B2 (row index 1, column index 1)
            Row row = sheet.getRow(4);

            if (row == null) {
                row = sheet.createRow(4);
            }

            Cell cell = row.getCell(4);

            if (cell == null) {
                cell = row.createCell(4);
            }

            cell.setCellValue("newpass123");

            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);

            fos.close();
            workbook.close();

            System.out.println("Cell B2 updated successfully");
        }
    public static void main(String[] args) throws IOException {
        readExcel();
        writeExcel();
    }

}
```
