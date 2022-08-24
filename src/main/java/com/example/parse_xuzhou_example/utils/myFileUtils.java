package com.example.parse_xuzhou_example.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author GaoLiuKai
 * @date 2022/8/3 17:29
 */

@Slf4j
public class myFileUtils {
    // insertTime的时间格式
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    // 新增字段标识名字
    public static final String CAUSES_IS_MATCHED = "causesIsMatched";
    public static final String IS_INSERTED_DEV_ES = "isInsertedDevEs";
    public static final String CAUSES_SHANG = "侵害商标权纠纷";
    // 各个文件类型的后缀
    public static final String DOCX = ".docx";
    public static final String DOC = ".doc";
    public static final String ZIP = ".zip";
    public static final String TXT = ".txt";

    /**
     * 按行读取txt文件，并将结果解析成JSON字符串，加上 insertTime字段信息，对uniqueId去重，将causes改为采集的案由名称
     * @param fileName 读取文件的名字，为绝对路径
     */
    public static List<JSONObject> readTxtFiles(String fileName) {
        List<JSONObject> result = new ArrayList<>();

        Set<String> ids = new HashSet<>();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String[] split = fileName.split("\\\\");
        // 通过文件名字得到纠纷类型
        String fileType = split[split.length-1].substring(0,split[split.length-1].indexOf("."));

        try {
            JSONArray causeArray ;
            File myFile = new File(fileName);
            if (myFile.isFile() && myFile.exists()) {
                InputStreamReader Reader = new InputStreamReader(new FileInputStream(myFile), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(Reader);

                String lineTxt ;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    JSONObject txtJson = JSONObject.parseObject(lineTxt);
                    if(txtJson.get("causes") instanceof JSONArray){
                        causeArray = txtJson.getJSONArray("causes");
                        causeArray.removeAll(causeArray);
                        causeArray.add(fileType);
                    }
                    txtJson.put("insertTime",formatter.format(System.currentTimeMillis()));
                    if(ids.add(txtJson.getString("uniqueId"))){
                        result.add(txtJson);
                    }
                }
                Reader.close();
                log.info("读取成功");
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 对uniqueId去重，过滤江苏省，徐州市的案例
     * @param fileName  txt文件的绝对路径
     */
    public static List<JSONObject> readTxt(String fileName) throws IOException {

        List<JSONObject> result = new ArrayList<>();

        Set<String> ids = new HashSet<>();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String[] split = fileName.split("\\\\");
        String fileType = split[split.length-1].substring(0,split[split.length-1].indexOf("."));
        File myFile = new File(fileName);
        if (myFile.isFile() && myFile.exists()) {
            InputStreamReader Reader = new InputStreamReader(new FileInputStream(myFile), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(Reader);

            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                JSONObject txtJson = JSONObject.parseObject(lineTxt);
                JSONArray causes = txtJson.getJSONArray("causes");
                if(causes != null){
                    causes.removeAll(causes);
                    causes.add(fileType);
                    if(ids.add(txtJson.getString("uniqueId")) && "徐州市".equals(txtJson.getString("city"))
                            && "江苏省".equals(txtJson.getString("province"))){
                        txtJson.put("insertTime",formatter.format(System.currentTimeMillis()));
                        result.add(txtJson);
                    }
                }
            }
        }
        log.info("读取成功");
        return result;
    }

        // 将String类型的集合换行写入txt文档
    public static void writeToTxtFile(String filePath, List<String> list){
        if(!filePath.endsWith(myFileUtils.TXT)){
            return;
        }
        File file = new File(filePath);
        FileOutputStream stream = null;
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            stream = new FileOutputStream(filePath);

            for(String str :list){
                stream.write((str+"\n").getBytes(StandardCharsets.UTF_8));
            }
            log.info("写入成功");
        }  catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 将JSON类型集合写入到txt文件中
    public static void writeToFile(String filePath,List<JSONObject> documents){
        if(!filePath.endsWith(myFileUtils.TXT)){
            return;
        }
        File file = new File(filePath);
        FileOutputStream stream = null;
        try {
            if(!file.exists()){
                file.createNewFile();
            }
             stream = new FileOutputStream(filePath);

            for(JSONObject document : documents){
                String doc = document.toString()+"\n";
                stream.write(doc.getBytes(StandardCharsets.UTF_8));
            }
            log.info("写入成功");
        }  catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // 批量解压缩
    public static void UnZipFiles(List<File> ZipFiles) throws FileFormatException {
        for(File file : ZipFiles){
            if(file.getPath().endsWith(myFileUtils.ZIP)){
                myFileUtils.unzip(file.getPath());
            }else {
                throw new FileFormatException("待解压缩文件类型错误");
            }
        }
    }

    // 读取 docx/doc 文件的内容，集合中的每一项都是一个文件内容
    public static List<String> readAllDocxFiles(List<File> DocxFiles) throws FileFormatException {
        List<String> res = new ArrayList<>();
        String filePath;
        for(File file : DocxFiles){
            filePath = file.getPath();
            // 将解压过的压缩文件的删除，避免重复解压
            if(filePath.endsWith(myFileUtils.ZIP)){
                file.delete();
            }else if(filePath.endsWith(myFileUtils.DOCX) || filePath.endsWith(myFileUtils.DOC)){
                res.add(myFileUtils.readWord(file.getPath()));
            }else {
                throw new FileFormatException("待读取文件类型错误");
            }
        }
        return res;
    }

    /**
     * 获取指定类型后缀的文件列表
     * @param fileInput        文件名创建的File对象
     * @param fileTypeList     输出的文件列表
     * @param fileTypeSuffix   指定类型的文件名后缀 ，如.zip，.doc，.txt
     */
    public static void getFileOfType(File fileInput,List<File> fileTypeList,String fileTypeSuffix){
        // 获取文件列表
        File[] fileList = fileInput.listFiles();
        assert fileList != null;
        for (File file : fileList) {
            if (file.isDirectory()) {
                // 递归处理文件夹
                // 如果不想统计子文件夹则可以将下一行注释掉
                getFileOfType(file, fileTypeList,fileTypeSuffix);
            } else {
                // 如果是文件则将其加入到文件数组中
                if(file.getPath().endsWith(fileTypeSuffix)){
                    fileTypeList.add(file);
                }
            }
        }
    }

    // 获取文件夹下面所有的目录文件夹
    public static void getAllDirectory(File fileInput, List<File> allDirList){
        File[] fileList = fileInput.listFiles();
        assert fileList != null;
        for(File file : fileList){
            if(file.isDirectory()){
                allDirList.add(file);
            }
        }
    }

    // 读取单个docx文件
    public static String readWord(String filePath)  {

        String buffer;
        try {
            if (filePath.endsWith(DOC)) {
                InputStream is = new FileInputStream(filePath);
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                ex.close();

            } else if (filePath.endsWith(DOCX)) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                extractor.close();
            } else {
                return null;
            }
            return buffer;
        } catch (Exception e) {
            System.out.print("error---->" + filePath);
            e.printStackTrace();
            return null;
        }
    }

    // 对单个.zip文件进行解压
    private static void unzip(String zipPath) {

        File zipFile = new File(zipPath);
        // 1.创建解压缩目录
        // 获取zip文件的名称
        String zipFileName = zipFile.getName();

        // 根据zip文件名称，提取压缩文件目录
        String targetDirName = zipFileName.substring(0, zipFileName.indexOf("."));

        // 创建解压缩目录
        File targetDir = new File(zipFile.getParent() + "\\" + targetDirName);

        if (!targetDir.exists()) {
            targetDir.mkdir(); // 创建目录
        }

        // 2.解析读取zip文件
        try (ZipInputStream in = new ZipInputStream(new FileInputStream(zipFile), Charset.forName("gbk"))) {
            // 遍历zip文件中的每个子文件
            ZipEntry zipEntry ;
            while ((zipEntry = in.getNextEntry()) != null) {
                // 获取zip压缩包中的子文件名称
                String zipEntryFileName = zipEntry.getName();

                // 创建该文件的输出流
                String zipFilePath = targetDir.getPath() + "\\" + zipEntryFileName;

                // 输出流定义在try()块，结束自动清空缓冲区并关闭
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(zipFilePath))) {

                    // 读取该子文件的字节内容
                    byte[] buff = new byte[1024];
                    int len = -1;
                    while ((len = in.read(buff)) != -1) {
                        bos.write(buff, 0, len);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
