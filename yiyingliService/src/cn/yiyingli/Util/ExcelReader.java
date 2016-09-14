package cn.yiyingli.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 操作Excel表格的功能类
 */
public class ExcelReader {
	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFRow row;

	/**
	 * 读取Excel表格表头的内容
	 * 
	 * @param InputStream
	 * @return String 表头内容的数组
	 */
	public String[] readExcelTitle(InputStream is) {
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println("colNum:" + colNum);
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			// title[i] = getStringCellValue(row.getCell((short) i));
			title[i] = getCellFormatValue(row.getCell(i));
		}
		return title;
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public Map<Integer, String> readExcelContent(InputStream is) {
		Map<Integer, String> content = new HashMap<Integer, String>();
		String str = "";
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			while (j < colNum) {
				// 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
				// 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
				// str += getStringCellValue(row.getCell((short) j)).trim() +
				// "-";
				str += getCellFormatValue(row.getCell(j)).trim() + "    ";
				j++;
			}
			content.put(i, str);
			str = "";
		}
		return content;
	}

	public List<List<String>> excel2ListL(InputStream is) {
		List<List<String>> toReturn = new ArrayList<List<String>>();
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			List<String> content = new ArrayList<String>();
			row = sheet.getRow(i);
			int j = 0;
			while (j < colNum) {
				content.add(getCellFormatValue(row.getCell(j)).trim());
				j++;
			}
			toReturn.add(content);
		}
		return toReturn;
	}

	public List<Map<String, String>> excel2List(InputStream is) {
		List<Map<String, String>> toReturn = new ArrayList<Map<String, String>>();
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 获得标题栏
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			title[i] = getCellFormatValue(row.getCell(i));
		}
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			Map<String, String> content = new HashMap<String, String>();
			row = sheet.getRow(i);
			int j = 0;
			while (j < colNum) {
				content.put(title[j], getCellFormatValue(row.getCell(j)).trim());
				j++;
			}
			toReturn.add(content);
		}
		return toReturn;
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		return strCell;
	}

	/**
	 * 获取单元格数据内容为日期类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	private String getDateCellValue(HSSFCell cell) {
		String result = "";
		try {
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
				Date date = cell.getDateCellValue();
				result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
			} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
				result = "";
			}
		} catch (Exception e) {
			System.out.println("日期格式不正确!");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					DecimalFormat format = new DecimalFormat("#");
					cellvalue = format.format(cell.getNumericCellValue());
				}
				break;
			}
				// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}

	public List<String> getCellsByColunmName(InputStream is, String name) {
		List<String> toReturn = new ArrayList<String>();
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		int theCol = -1;
		for (int i = 1; i <= colNum; i++) {
			String title = getCellFormatValue(row.getCell(i)).trim();
			if (title.equals(name))
				theCol = i;
		}
		if (theCol == -1)
			return toReturn;
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			toReturn.add(getCellFormatValue(row.getCell(theCol)).trim());
		}
		return toReturn;
	}

	public List<List<String>> getCellsByColNames(InputStream is, String[] colNames) {
		List<List<String>> toReturn = new ArrayList<List<String>>();
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 获得标题栏
		List<String> titles = new ArrayList<String>();
		for (int i = 0; i < colNum; i++) {
			titles.add(getCellFormatValue(row.getCell(i)));
		}
		for (int i = 1; i <= colNum; i++) {
			List<String> data = new ArrayList<String>();
			row = sheet.getRow(i);
			for (String cn : colNames) {
				int j = titles.indexOf(cn);
				if (j != -1)
					data.add(getCellFormatValue(row.getCell(j)));
			}
			toReturn.add(data);
		}
		return toReturn;
	}

	public List<List<String>> getCellsByColNum(InputStream is, int[] colNums) {
		List<List<String>> toReturn = new ArrayList<List<String>>();
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		int rowNum = sheet.getLastRowNum();
		for (int i = 1; i <= rowNum; i++) {
			List<String> data = new ArrayList<String>();
			row = sheet.getRow(i);
			for (int j = 0; j < colNums.length; j++) {
				if (colNums[j] >= colNum)
					continue;
				data.add(getCellFormatValue(row.getCell(colNums[j])).trim());
			}
			toReturn.add(data);
		}
		return toReturn;
	}

	public static void main(String[] args) {
		try {
			// // 对读取Excel表格标题测试
			// InputStream is = new
			// FileInputStream("/Users/SDLL18/Desktop/Excel/1_1_2.xls");
			ExcelReader excelReader = new ExcelReader();
			// String[] title = excelReader.readExcelTitle(is);
			// System.out.println("获得Excel表格的标题:");
			// for (String s : title) {
			// System.out.print(s + " ");
			// }

			// 对读取Excel表格内容测试
			InputStream is2 = new FileInputStream("/Users/SDLL18/Desktop/Excel/1_1_2.xls");
			// Map<Integer, String> map = excelReader.readExcelContent(is2);
			// System.out.println("获得Excel表格的内容:");
			// for (int i = 1; i <= map.size(); i++) {
			// System.out.println(map.get(i));
			// }
			// List<Map<String, String>> datas = excelReader.excel2List(is2);
			// for (Map<String, String> data : datas) {
			// Iterator<String> it = data.keySet().iterator();
			// while (it.hasNext()) {
			// String key = it.next();
			// System.out.print(key + ":" + data.get(key) + "\t");
			// }
			// System.out.println("");
			// }
			List<List<String>> datas = excelReader.excel2ListL(is2);
			for (List<String> data : datas) {
				for (String cell : data) {
					System.out.print(cell + "\t");
				}
				System.out.println("");
			}

		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		}
	}
}