package Utiles;

import nl.flotsam.xeger.Xeger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    private final String sourceString;
    private final String sheetName;
    private final String rowDataSource;


    public ExcelReader(String sourceString, String sheetName, String rowDataSource) {
        this.sourceString = sourceString;
        this.sheetName = sheetName;
        this.rowDataSource = rowDataSource;
    }

    public static String regularExpressionGenerator(String regex) {
        if (regex.matches("GUID||UUID")) {
            regex = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";
        }
        Xeger xeger = new Xeger(regex);
        return xeger.generate();
    }

    public List<List<Object>> readMultipleRows() throws IOException {
        List<List<Object>> multiList = new ArrayList();

        String excelFilePath = new ReadDataMapper().getPath(this.sourceString);

       //try (FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath).getAbsoluteFile())) {
        try (FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\data\\AddPlaceAPIData.xlsx")) {
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = wb.getSheet(this.sheetName);
            String[] data = this.rowDataSource.split(";");
            for (String da : data) {
                int rowNumber = Integer.parseInt(da.replace("row", ""));
                Row row = sheet.getRow(rowNumber - 1);
                List<Object> valueList = new ArrayList();
                FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                 for (Cell cell : row) {
                    switch (evaluator.evaluateInCell(cell).getCellType()) {
                        case NUMERIC:
                            valueList.add((int) cell.getNumericCellValue());
                            break;

                        case STRING:
                            String tempStr = cell.toString();
                            if (tempStr.startsWith("Regex:")) {
                                String regex = tempStr.split(":")[1];
                                tempStr = regularExpressionGenerator(regex);
                                valueList.add(tempStr);
                                break;
                            } /*else if (tempStr.startsWith("Date:")) {
                                String regex = tempStr.split(":")[1];
                                tempStr = regularExpressionGenerator(regex);
                                valueList.add(tempStr);
                                break;
                            }*/
                            if (tempStr.charAt(0) == '"' && tempStr.charAt(tempStr.length() - 1) == '"') {
                                tempStr = tempStr.substring(1, tempStr.length() - 1);
                            }
                            if (tempStr.equalsIgnoreCase("BLANK")) {
                                valueList.add("");
                                break;
                            }
                            if (tempStr.equalsIgnoreCase("null") || tempStr.contains("null")) {
                                valueList.add(null);
                            } else {
                                valueList.add(tempStr);
                            }
                            break;

                        case _NONE:
                            valueList.add(null);
                            break;
                        case BLANK:
                            valueList.add("");
                            break;
                        case BOOLEAN:
                            valueList.add(cell.getBooleanCellValue());
                            break;
                    }
                }
                multiList.add(valueList);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return multiList;
    }

    public List<Object> readSingleRows() throws IOException {
        List<Object> valueList = new ArrayList<>();
        String excelFilePath = new ReadDataMapper().getPath(this.sourceString);

        try (FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath).getAbsoluteFile())) {
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = wb.getSheet(this.sheetName);
            FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
            int rowNumber = Integer.parseInt(this.rowDataSource.replace("row", ""));
            Row header = sheet.getRow(0);
            Row row = sheet.getRow(rowNumber - 1);
            DataFormatter dataFormatter = new DataFormatter();
            for (Cell cell : row) {
                switch (evaluator.evaluateInCell(cell).getCellType()) {
                    case NUMERIC:
                        valueList.add((int) cell.getNumericCellValue());
                        break;
                    case STRING:
                        String tempStr = cell.toString();
                        if (tempStr.startsWith("Regex:")) {
                            String regex = tempStr.split(":")[1];
                            tempStr = regularExpressionGenerator(regex);
                            valueList.add(tempStr);
                            break;
                        } /*else if (tempStr.startsWith("Date:")) {
                            String regex = tempStr.split(":")[1];
                            tempStr = regularExpressionGenerator(regex);
                            valueList.add(tempStr);
                            break;
                        }*/
                        if (tempStr.charAt(0) == '"' && tempStr.charAt(tempStr.length() - 1) == '"') {
                            tempStr = tempStr.substring(1, tempStr.length() - 1);
                        }
                        if (tempStr.equalsIgnoreCase("BLANK")) {
                            valueList.add("");
                            break;
                        }
                        if (tempStr.equalsIgnoreCase("null") || tempStr.contains("null")) {
                            valueList.add(null);
                        } else {
                            valueList.add(tempStr);
                        }
                        break;
                    case _NONE:
                        valueList.add(null);
                        break;
                    case FORMULA:
                        break;
                    case BLANK:
                        valueList.add("");
                        break;
                    case BOOLEAN:
                        valueList.add(cell.getBooleanCellValue());
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valueList;
    }
}
