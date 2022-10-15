package com.app.dataUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.app.entities.Product;
import com.app.entities.ProductCategory;
import com.app.entities.Rx;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

public class DataUtils {

	private final static String CSV_FILE_NAME = "DATA2";
	private final static String CSV_FILE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "resources" + File.separator + "data" + File.separator + CSV_FILE_NAME
			+ ".xlsx";

	// just for demo purpose

	public static void main(String[] args) {
		List<Product> list = getListsOfProducts();
		if (list != null) {
			for (Product product : list) {
				System.out.println(product.toString());
			}
		} else
			System.out.println("list is empty");

	}

	public static List<Product> readData() {
		List<Product> products = new ArrayList<Product>();
		try {
			FileReader fileReader = new FileReader(new File(CSV_FILE_PATH));
			CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
			Product product = new Product();
			List<String[]> allData = csvReader.readAll();
			for (String[] row : allData) {
				// product.setId(Integer.valueOf(row[0]));
				product.setProductName(row[1]);
				product.setGenericName(row[2]);
				product.setCompanyName(row[3]);
				product.setProductCategory(ProductCategory.valueOf(row[4]));
				product.setRx(Rx.valueOf(row[5]));
				product.setImageUrl(row[6]);
				product.setPackgDetails(row[7]);
				product.setStock(Integer.valueOf(row[8]));
				product.setUnitPrice(Integer.valueOf(row[9]));
				product.setProdDetails(row[10]);
				products.add(product);
			}

		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}
		return products;
	}

	public static List<Product> getListsOfProducts() {
		List<Product> listOfProducts = new ArrayList<Product>();
		try {
			FileInputStream file = new FileInputStream(new File(CSV_FILE_PATH));
			Workbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheet("product");
			int numberOfRows = sheet.getLastRowNum();
			if (numberOfRows > 0) {

				for (int i = 1; i <= numberOfRows; i++) {
					Product product = new Product();
					// product.setId((int) sheet.getRow(i).getCell(0).getNumericCellValue());
					product.setProductName((sheet.getRow(i).getCell(1).getStringCellValue()));
					product.setGenericName((sheet.getRow(i).getCell(2).getStringCellValue()));
					product.setCompanyName((sheet.getRow(i).getCell(3).getStringCellValue()));
					product.setProductCategory(
							ProductCategory.valueOf((sheet.getRow(i).getCell(4).getStringCellValue())));
					/*
					 * product.setProductCategory(
					 * handleStringConversion(sheet.getRow(i).getCell(4).getCellType() ==
					 * CellType.NUMERIC ?
					 * String.valueOf(sheet.getRow(i).getCell(4).getNumericCellValue()) :
					 * sheet.getRow(i).getCell(3).getStringCellValue()));
					 */

					product.setRx(Rx.valueOf((sheet.getRow(i).getCell(5).getStringCellValue())));

					product.setImageUrl(sheet.getRow(i).getCell(6).getStringCellValue());
					product.setPackgDetails(sheet.getRow(i).getCell(7).getStringCellValue());

					product.setStock((int) sheet.getRow(i).getCell(8).getNumericCellValue());
					product.setUnitPrice(sheet.getRow(i).getCell(9).getNumericCellValue());
					product.setProdDetails(sheet.getRow(i).getCell(10).getStringCellValue());

					listOfProducts.add(product);
				}
			} else {
				System.out.println(numberOfRows);
			}

			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfProducts;

	}

}