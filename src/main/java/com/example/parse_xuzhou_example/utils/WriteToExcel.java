package com.example.parse_xuzhou_example.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author GaoLiuKai
 * @date 2022/8/9 15:10
 */
// 整个流程：先初始化各项静态数据，再生成表头，再创建表格
public class WriteToExcel {

    // 常规excel行高
    private static final Short SMALL_CELL_HEIGHT = 400;
    // 常规excel列宽
    private static final Short NORMAL_COLUMN_WIDTH = 4000;
    // 填充大量文本内容的行高
    private static final Short LARGE_CELL_HEIGHT = 8000;
    // 单元格左对齐样式
    private static final HorizontalAlignment LEFT = HorizontalAlignment.LEFT;
    // 单元格垂直居中对齐样式
    private static final HorizontalAlignment CENTER = HorizontalAlignment.CENTER;

    // 案由表格里面的lawInfo数量
    private static final int LAW_INFO_SIZE = 4;
    // 案由表格里面的litigationParticipant下的非lawyer数量
    private static final int  LITIGATION_NOT_LAWYER_SIZE = 7;
    // 案由表格里面的party 数量
    private static final int PARTY_SIZE = 6;
    // 案由表格里面的paras数量
    private static final int PARA_SIZE = 7;

    // 动态获取一个徐州案由表格里面的lawyerInfo数量
    private static int LAWYER_INFO_SIZE ;
    // 动态获取一个徐州案由表格里面的litigationParticipant下的lawyer数量
    private static int  LITIGATION_LAWYER_SIZE ;

    // 处理表格的一些静态变量

    // 表头的字符串
    private static  List<String> headString ;
    // 表头字符串在单元格的起始列位置
    private static Map<String,Integer> headStringMap;
    // excel表对象
    private static XSSFWorkbook workbook;
    // 数据页对象
    private static XSSFSheet sheet;
    // 行对象
    private static XSSFRow row;
    // 单元格对象
    private static XSSFCell cell;
    // 输出 excel 表格内容的 file 对象
    private static File file;


    // 设置律师人数（lawyerInfo数最大值）
    public static void setLawyerInfoSize(int size){
        LAWYER_INFO_SIZE = size;
    }

    // 设置参与官司律师人数（读取所有的litigation，所取的最大值）
    public static void setLITIGATION_LAWYER_SIZE(int size){
        LITIGATION_LAWYER_SIZE = size;
    }

    // 为表格头部信息字符串 和 列的字段以及位置信息的map集合赋值
    public static void setHeadStringAndMap(String path) {
        String url = "https://shilv-apigateway.aegis-info.com/api/parsers/DocumentParser";
        // 随意一篇要解析的文档绝对地址

        String s = myFileUtils.readWord(path);

        // 得到要生成excel表格的JSON对象
        JSONObject text = PostOtherController.post(url, new HashMap<>() {
            {
                put("text", s);
            }
        });

        headString = new ArrayList<>(text.keySet());
        // 添加新增的表头字符串及其位置（严格比对，涉及到下面的 map集合 位置信息）
        headString.add(10,"previousTrialCaseNumber");
        headString.add(18,"evidences");
        headString.add(24,"docType");

        headStringMap = new HashMap<>(){
            {
                put("trialRound",0);
                put("city",1);
                put("lawInfo",2);
                put("paras",2+3*LAW_INFO_SIZE);
                put("court",2+3*LAW_INFO_SIZE+2*PARA_SIZE);
                put("title",2+3*LAW_INFO_SIZE+2*PARA_SIZE+1);
                put("topCause",2+3*LAW_INFO_SIZE+2*PARA_SIZE+2);
                put("content",2+3*LAW_INFO_SIZE+2*PARA_SIZE+3);
                put("caseType",2+3*LAW_INFO_SIZE+2*PARA_SIZE+4);
                put("caseNumberYear",2+3*LAW_INFO_SIZE+2*PARA_SIZE+5);
                put("previousTrialCaseNumber",2+3*LAW_INFO_SIZE+2*PARA_SIZE+6);
                put("litigationParticipant",2+3*LAW_INFO_SIZE+2*PARA_SIZE+7);
                put("province",2+3*LAW_INFO_SIZE+2*PARA_SIZE+7+6*LITIGATION_LAWYER_SIZE+2*LITIGATION_NOT_LAWYER_SIZE);
                put("caseNumber",2+3*LAW_INFO_SIZE+2*PARA_SIZE+7+6*LITIGATION_LAWYER_SIZE+2*LITIGATION_NOT_LAWYER_SIZE+1);
                put("causes",2+3*LAW_INFO_SIZE+2*PARA_SIZE+7+6*LITIGATION_LAWYER_SIZE+2*LITIGATION_NOT_LAWYER_SIZE+2);
                put("judgementResult",2+3*LAW_INFO_SIZE+2*PARA_SIZE+7+6*LITIGATION_LAWYER_SIZE+2*LITIGATION_NOT_LAWYER_SIZE+3);
                put("lawyerInfo",2+3*LAW_INFO_SIZE+2*PARA_SIZE+7+6*LITIGATION_LAWYER_SIZE+2*LITIGATION_NOT_LAWYER_SIZE+4);
                put("focusOfControversy",2+3*LAW_INFO_SIZE+2*PARA_SIZE+7+6*LITIGATION_LAWYER_SIZE+2*LITIGATION_NOT_LAWYER_SIZE+LAWYER_INFO_SIZE*3+4);
                put("evidences",2+3*LAW_INFO_SIZE+2*PARA_SIZE+7+6*LITIGATION_LAWYER_SIZE+2*LITIGATION_NOT_LAWYER_SIZE+LAWYER_INFO_SIZE*3+5);
                put("decideTime",2+3*LAW_INFO_SIZE+2*PARA_SIZE+7+6*LITIGATION_LAWYER_SIZE+2*LITIGATION_NOT_LAWYER_SIZE+LAWYER_INFO_SIZE*3+6);
                put("errors",2+3*LAW_INFO_SIZE+2*PARA_SIZE+7+6*LITIGATION_LAWYER_SIZE+2*LITIGATION_NOT_LAWYER_SIZE+LAWYER_INFO_SIZE*3+7);
                put("paraTags",2+3*LAW_INFO_SIZE+2*PARA_SIZE+7+6*LITIGATION_LAWYER_SIZE+2*LITIGATION_NOT_LAWYER_SIZE+LAWYER_INFO_SIZE*3+8);
                put("party",2+3*LAW_INFO_SIZE+2*PARA_SIZE+7+6*LITIGATION_LAWYER_SIZE+2*LITIGATION_NOT_LAWYER_SIZE+LAWYER_INFO_SIZE*3+9);
                put("uniqueId",2+3*LAW_INFO_SIZE+2*PARA_SIZE+7+6*LITIGATION_LAWYER_SIZE+2*LITIGATION_NOT_LAWYER_SIZE+LAWYER_INFO_SIZE*3+9+2*PARTY_SIZE);
                put("docType",2+3*LAW_INFO_SIZE+2*PARA_SIZE+7+6*LITIGATION_LAWYER_SIZE+2*LITIGATION_NOT_LAWYER_SIZE+LAWYER_INFO_SIZE*3+9+2*PARTY_SIZE+1);

            }
        };
    }

    // 垂直居中显示，自动换行 单元格格式
    private static XSSFCellStyle getCellStyle(HorizontalAlignment alignment,VerticalAlignment verticalAlignment){
        XSSFCellStyle cellStyle = workbook.createCellStyle();

        // 自动换行
        cellStyle.setWrapText(true);
        // 垂直样式  靠上/居中/靠下
        cellStyle.setVerticalAlignment(verticalAlignment);
        // 水平样式  靠左/居中/靠右
        cellStyle.setAlignment(alignment);

        return cellStyle;
    }

    // 合并单元格（开始行，结束行，开始列，结束列）
    private static void mergeCell(int beginRow,int endRow,int beginCol,int endCol){
        CellRangeAddress address = new CellRangeAddress(beginRow,endRow,beginCol,endCol);
        sheet.addMergedRegion(address);
    }
    //创建sheet页,设置相关列的列宽
    public static void setSheet(String sheetName) {
        workbook = new XSSFWorkbook();
        if(workbook.getSheet(sheetName) != null){
            sheet = workbook.getSheet(sheetName);

        }else{
            sheet = workbook.createSheet(sheetName);
        }

        // 冻结前两行窗格
        sheet.createFreezePane(0,2);

        // 设置lawInfo detail列宽
        for(int i = 2;i<=11;i+=3){
            sheet.setColumnWidth(headStringMap.get("lawInfo")+i,NORMAL_COLUMN_WIDTH);
        }

        for(int i = 1;i<=PARA_SIZE*2-1;i+=2){
            sheet.setColumnWidth(headStringMap.get("paras")+i,40000);
        }

        for(int i = 0;i<LITIGATION_LAWYER_SIZE;i++){
            sheet.setColumnWidth(headStringMap.get("litigationParticipant")+i*6,NORMAL_COLUMN_WIDTH);
            sheet.setColumnWidth(headStringMap.get("litigationParticipant")+i*6+1,NORMAL_COLUMN_WIDTH);
            sheet.setColumnWidth(headStringMap.get("litigationParticipant")+i*6+2,NORMAL_COLUMN_WIDTH);
        }

        sheet.setColumnWidth(headStringMap.get("judgementResult"),4500);
        sheet.setColumnWidth(headStringMap.get("focusOfControversy"),4500);

        for(int i=0;i<3*LAWYER_INFO_SIZE;i++){
            sheet.setColumnWidth(headStringMap.get("lawyerInfo")+i,NORMAL_COLUMN_WIDTH);
        }

        sheet.setColumnWidth(headStringMap.get("caseNumberYear"),NORMAL_COLUMN_WIDTH);
        sheet.setColumnWidth(headStringMap.get("previousTrialCaseNumber"),7000);
        sheet.setColumnWidth(headStringMap.get("content"),40000);
        sheet.setColumnWidth(headStringMap.get("evidences"),NORMAL_COLUMN_WIDTH);
        sheet.setColumnWidth(headStringMap.get("decideTime"),NORMAL_COLUMN_WIDTH);
        sheet.setColumnWidth(headStringMap.get("caseNumber"),2*NORMAL_COLUMN_WIDTH);
        sheet.setColumnWidth(headStringMap.get("uniqueId"),2*NORMAL_COLUMN_WIDTH+500);
    }

    //写入文件
    public static void writeToFile(String filePath){
        file = new File(filePath);
        //将文件保存到指定的位置
        try {
            workbook.write(new FileOutputStream(file));
            System.out.println("写入成功");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //创建表头
    public static void createHead() {
        int col = 0;
        // 按类型创建表格头
        XSSFRow row1 = sheet.createRow(col);
        row1.setHeight(SMALL_CELL_HEIGHT);

        XSSFRow row2 = sheet.createRow(1);
        row2.setHeight(SMALL_CELL_HEIGHT);
        for(String key:headString){
            if("lawInfo".equals(key)){
                for(int i = 0;i<3*LAW_INFO_SIZE;i++){
                    row1.createCell(col + i);
                    cell = row2.createCell(col + i);
                    if(i%3 == 0){
                        cell.setCellValue("lawName");
                    }else if(i%3 == 1){
                        cell.setCellValue("clauses");
                    }else {
                        cell.setCellValue("detail");
                        cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                    }
                }
                WriteToExcel.mergeCell(0,0,col,col+3*LAW_INFO_SIZE-1);
                sheet.getRow(0).getCell(col).setCellValue(key);
                sheet.getRow(0).getCell(col).setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                col = col+3*LAW_INFO_SIZE;
            }else if("paras".equals(key)){
                for(int i =0;i<2*PARA_SIZE;i++){
                    row1.createCell(col + i);
                    cell = row2.createCell(col + i);
                    if(i%2 == 0){
                        cell.setCellValue("tag");
                    }else{
                        cell.setCellValue("content");
                        cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                    }
                }
                WriteToExcel.mergeCell(0,0,col,col+2*PARA_SIZE-1);
                sheet.getRow(0).getCell(col).setCellValue(key);
                sheet.getRow(0).getCell(col).setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                col = col + 2*PARA_SIZE;
            }else if("litigationParticipant".equals(key)){
                for(int i = 0;i<6*LITIGATION_LAWYER_SIZE;i++){
                    row1.createCell(col + i);
                    cell = row2.createCell(col + i);
                    if(i%6 == 0){
                        cell.setCellValue("lawAgentType");
                    }else if(i%6 == 1){
                        cell.setCellValue("lawyerRepresent");
                    }else if(i%6 == 2){
                        cell.setCellValue("lawFirmName");
                    }
                    else if(i%6 == 3){
                        cell.setCellValue("name");
                    }
                    else if(i%6 == 4){
                        cell.setCellValue("isLawyer");
                    }else{
                        cell.setCellValue("type");
                    }
                    cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                }

                for(int i=0;i<2*LITIGATION_NOT_LAWYER_SIZE;i++){
                    row1.createCell(col+6*LITIGATION_LAWYER_SIZE + i);
                    cell = row2.createCell(col+6*LITIGATION_LAWYER_SIZE + i);
                    if(i%2 == 0){
                        cell.setCellValue("name");
                    }else{
                        cell.setCellValue("type");
                    }
                    cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                }
                WriteToExcel.mergeCell(0,0,col,col+2*LITIGATION_NOT_LAWYER_SIZE+6*LITIGATION_LAWYER_SIZE-1);
                sheet.getRow(0).getCell(col).setCellValue(key);
                sheet.getRow(0).getCell(col).setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                col = col + 2*LITIGATION_NOT_LAWYER_SIZE+6*LITIGATION_LAWYER_SIZE;
            }else if("lawyerInfo".equals(key)){
                for(int i = 0;i<3*LAWYER_INFO_SIZE;i++){
                    row1.createCell(col + i);
                    cell = row2.createCell(col + i);
                    if(i%3 == 0){
                        cell.setCellValue("lawAgentName");
                    }else if(i%3 == 1){
                        cell.setCellValue("lawyerRepresent");
                    }else {
                        cell.setCellValue("lawFirmName");
                        cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                    }
                }
                WriteToExcel.mergeCell(0,0,col,col+3*LAWYER_INFO_SIZE-1);
                sheet.getRow(0).getCell(col).setCellValue(key);
                sheet.getRow(0).getCell(col).setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                col = col+3*LAWYER_INFO_SIZE;
            }else if("party".equals(key)){
                for(int i=0;i<2*PARTY_SIZE;i++){
                    row1.createCell(col + i);
                    cell = row2.createCell(col + i);
                    if(i%2 == 0){
                        cell.setCellValue("name");
                    }else{
                        cell.setCellValue("type");
                    }
                }
                WriteToExcel.mergeCell(0,0,col,col+2*PARTY_SIZE-1);
                sheet.getRow(0).getCell(col).setCellValue(key);
                sheet.getRow(0).getCell(col).setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                col = col+2*PARTY_SIZE;
            }
            else{
                cell = row1.createCell(col);
                cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                cell.setCellValue(key);
                row2.createCell(col);
                WriteToExcel.mergeCell(0,1,col,col);
                col++;
            }
        }

    }

    // 创建excel表格
    public static void createExcelFile(List<JSONObject> jsonObjects){
        int rows = 2;
        for (JSONObject object : jsonObjects) {
            row = sheet.createRow(rows);

            row.setHeight(LARGE_CELL_HEIGHT);
            for (String key : object.keySet()) {
                Integer col;
                if ("lawInfo".equals(key)) {
                    col = headStringMap.get(key);

                    JSONArray lawInfo = object.getJSONArray(key);
                    int lawInfoSize = lawInfo.size();
                    JSONObject law;

                    int cellNum = 0;
                    for (int k = 0; k < lawInfoSize; k++) {
                        law = lawInfo.getJSONObject(k);
                        for (String lawKey : law.keySet()) {
                            cell = row.createCell(col + cellNum);
                            if ("lawName".equals(lawKey)) {
                                cell.setCellValue(law.getString(lawKey));
                            } else if (" ".equals(lawKey)) {
                                continue;
                            } else {
                                String s = law.getString(lawKey);
                                cell.setCellValue(s.substring(1, s.length() - 1));
                            }
                            cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                            cellNum++;
                        }
                    }

                } else if ("paras".equals(key)) {

                    col = headStringMap.get(key);
                    JSONArray paras = object.getJSONArray(key);
                    int paraSize = paras.size();
                    JSONObject para;
                    int cellIndex = 0;

                    for (int p = 0; p < paraSize; p++) {
                        para = paras.getJSONObject(p);
                        for (String paraKey : para.keySet()) {
                            cell = row.createCell(col + cellIndex);
                            cell.setCellValue(para.getString(paraKey));
                            CellStyle newCellStyle = null;
                            if("content".equals(paraKey)){
                                newCellStyle= workbook.createCellStyle();
                                newCellStyle.setAlignment(LEFT);
                                newCellStyle.setWrapText(true);
                                newCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                            }
                            cell.setCellStyle(newCellStyle==null?getCellStyle(CENTER,VerticalAlignment.CENTER):newCellStyle);
                            cellIndex++;
                        }
                    }
                } else if ("litigationParticipant".equals(key)) {
                    col = headStringMap.get(key);
                    JSONArray litigationParts = object.getJSONArray(key);
                    JSONObject part;
                    int partSize = litigationParts.size();
                    // 标识律师标签的单元格位置
                    int partIndex = 0;
                    // 标识原告被告标签的单元格位置
                    int nameAndTypeIndex = 0;

                    for (int a = 0; a < partSize; a++) {
                        part = litigationParts.getJSONObject(a);
                        if (part.containsKey("lawAgentType")) {
                            for (String partKey : part.keySet()) {
                                cell = row.createCell(col + partIndex);
                                cell.setCellValue(part.getString(partKey));
                                cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                                partIndex++;
                            }
                        } else {
                            cell = row.createCell(nameAndTypeIndex + col + 6*LITIGATION_LAWYER_SIZE);
                            cell.setCellValue(part.getString("name"));
                            cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));

                            cell = row.createCell(nameAndTypeIndex + 1 + col + 6*LITIGATION_LAWYER_SIZE);
                            cell.setCellValue(part.getString("type"));
                            cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));

                            nameAndTypeIndex += 2;
                        }
                    }
                } else if ("lawyerInfo".equals(key)) {
                    col = headStringMap.get(key);
                    JSONArray lawyerInfo = object.getJSONArray(key);
                    JSONObject lawyer;
                    int lawyerIndex = 0, lawyerSize = lawyerInfo.size();

                    for (int l = 0; l < lawyerSize; l++) {
                        lawyer = lawyerInfo.getJSONObject(l);
                        for (String lawyerKey : lawyer.keySet()) {
                            cell = row.createCell(col + lawyerIndex);
                            cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                            cell.setCellValue(lawyer.getString(lawyerKey));
                            lawyerIndex++;
                        }
                    }
                } else if ("party".equals(key)) {
                    col = headStringMap.get(key);
                    JSONArray parties = object.getJSONArray(key);
                    JSONObject party;
                    int partyIndex = 0, partySize = parties.size();

                    for (int pa = 0; pa < partySize; pa++) {
                        party = parties.getJSONObject(pa);

                        for (String partyKey : party.keySet()) {
                            cell = row.createCell(col + partyIndex);
                            cell.setCellValue(party.getString(partyKey));
                            cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                            partyIndex++;
                        }
                    }
                } else {
                    col = headStringMap.get(key);
                    cell = row.createCell(col);
                    cell.setCellValue(object.getString(key));
                    if ("content".equals(key)) {
                        CellStyle newCellStyle = workbook.createCellStyle();
                        newCellStyle.setAlignment(HorizontalAlignment.LEFT);
                        newCellStyle.setWrapText(true);
                        newCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                        cell.setCellStyle(newCellStyle);
                    }else if("court".equals(key)) {
                        cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                    }else if("title".equals(key)) {
                        cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                    }else if("causes".equals(key)) {
                        cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                    }else if("focusOfControversy".equals(key)) {
                        cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                    } else if("paraTags".equals(key)){
                        cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                    }else if("evidences".equals(key)){
                        cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                    }else if("previousTrialCaseNumber".equals(key)){
                        cell.setCellStyle(getCellStyle(CENTER,VerticalAlignment.CENTER));
                    }
                }
            }
            rows++;
        }
    }
}

