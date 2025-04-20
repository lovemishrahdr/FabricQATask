package utilities;

import java.io.File;
import java.io.FileInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	XSSFWorkbook book;
	XSSFSheet sheet;
	XSSFCell cell;
	TestProperties testProp = TestProperties.getInstance();

	private static final Logger logger = LogManager.getLogger(ExcelUtil.class.getName());

	public String[][] getTestCase() {
		testProp.initProperties();
		String[][] excelArray;
		try {
			String excelPath = System.getProperty("user.dir") + File.separator + "TestData" + File.separator
					+ testProp.getWORKBOOKNAME();

			String sheetName = testProp.getSHEETNAME();
			FileInputStream excelFile = new FileInputStream(excelPath);
			book = new XSSFWorkbook(excelFile);
			sheet = book.getSheet(sheetName);
			excelArray = new String[sheet.getLastRowNum()][8];

			logger.info("Loading WorkBook: " + testProp.getWORKBOOKNAME());
			logger.info("Loading Sheet: " + sheetName);

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				for (int j = 0; j < 8; j++) {
					try {
						excelArray[i - 1][j] = sheet.getRow(i).getCell(j).getStringCellValue();
					} catch (Exception e) {
						excelArray[i - 1][j] = String.valueOf(sheet.getRow(i).getCell(j).getNumericCellValue());
					}
				}
			}
			return excelArray;

		} catch (Exception e) {
			logger.info("Unable to load excel please check");
			System.out.println(e.getMessage());
			return null;
		}

	}
}