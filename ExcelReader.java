
package TopBlocexcel.TopBlocexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader 
{
	public ExcelReader()
	{
		this.numbers1 = new double[4];
		this.numbers2 = new double[4];
		this.words = new String[4];
	}
	
	public ExcelReader(double[] column1, double[] column2, String wordColumn[] )
	{
		this.numbers1 = column1;
		this.numbers2 = column2;
		this.words = wordColumn;
	}

	/**
	 * 
	 * @param file: Excel file to be real
	 */
	public void readXcell(String file)
	{
		try 
		{
			File data = new File(file);
			FileInputStream fis = new FileInputStream(data);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheetAt(0);
			int j; //column iterator
			int k=-1; //row iterator set at -1 for simplicity
			Iterator<Row> itr = sheet.iterator();
			while (k < 4) 
			{
				Row row = itr.next();
				j = 0;
				Iterator<Cell> cellIterator = row.cellIterator();
				while (j <3) 
				{
					Cell cell = cellIterator.next();
					if (k != -1)
					{
						if (j == 0)
						{
							this.numbers1[k] = cell.getNumericCellValue();
						}
						if (j == 1)
						{
							this.numbers2[k] = cell.getNumericCellValue();
						}
						if (j == 2)
						{
							this.words[k] = cell.getStringCellValue();
						}
					}
					j++;
				}
				k++;
			}		
		} 
		catch (FileNotFoundException fe)
		{ 
			fe.printStackTrace();
		} 
		catch (IOException ie) 
		{ 
    		ie.printStackTrace();
    		}
	}
	
	/**
	 * 
	 * @param other the data to be merged
	 * @return the merged set of data
	 */
	
	public ExcelReader merge(ExcelReader other)
	{
		double products[] = new double[4];
		double quotients[] = new double[4];
		String concats[] = new String[4];
		int j = 0;
		while (j < 4)
		{
			products[j] = numbers1[j] * other.numbers1[j];
			quotients[j] = numbers2[j] / other.numbers2[j];
			concats[j] = new StringBuilder(14).append(words[j]).append(" ").append(other.words[j]).toString();
			j++;
		}
		return new ExcelReader(products, quotients, concats);
	}
	
	/**
	 * 
	 * @return the data in json format
	 */
	public String MakeJSON()
	{
		String id =  "\"id\":\"clstein1@outlook.com\",";
		String numberSetOne = "\"numberSetOne\": ["+numbers1[0]+", "+numbers1[1]+", "+numbers1[2]+", "+numbers1[3]+"],";
		String numberSetTwo = "\"numberSetTwo\": ["+numbers2[0]+", "+numbers2[1]+", "+numbers2[2]+", "+numbers2[3]+"],";
		String wordSetOne = "\"wordSetOne\":[ \""+words[0]+"\", \""+words[1]+"\", \""+words[2]+"\", \""+words[3]+"\"]";
		return "{ "+id+numberSetOne+numberSetTwo+wordSetOne+"}";
	}
	
	private double numbers1[];
	private double numbers2[];
	private String words[];		
}
