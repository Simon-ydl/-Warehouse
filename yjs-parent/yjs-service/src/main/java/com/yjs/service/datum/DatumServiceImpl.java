package com.yjs.service.datum;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itextpdf.text.pdf.PdfWriter;
import com.yjs.bean.datum.Datum;
import com.yjs.bean.dto.PageResult;
import com.yjs.dao.mapper.DatumMapper;
import com.yjs.dao.mapper.IDatumMapper;
import com.yjs.service.activiti.ActivitiService;
import com.yjs.service.datum.service.DatumService;
import com.yjs.service.item.ItemService;
import com.yjs.utils.JsonKit;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DatumServiceImpl implements DatumService {

    @Autowired
    IDatumMapper mapper;

    @Autowired
    DatumMapper datumMapper;

    @Autowired
    ItemService itemService;

    @Autowired
    ActivitiService activitiService;

    @Autowired
    DatumService datumService;

    /**
     * 待办任务页面点击办理时使用的方法
     * @param business_code 流水号
     * @param itemsId       事项id
     * @return
     */
    @Override
    public Map<String, Object> findDatumInformation(String business_code, Integer itemsId) {
        Map<String, Object> map = new HashMap<>();
        Map<String,Object> applicantDepartment = mapper.findApplicantDepartment(business_code, itemsId);//查询申请人经办人和部门
        List fileList = mapper.findFileNameAndUrl(business_code, itemsId);//查找文件名和url
        Map<String,Object> content = mapper.findContent(business_code,itemsId);//查找填写表单
//        PageResult pageResult = activitiService.activitiTaskNext(business_code);//调用蒲洋的工作流接口获取当前部门及下一个部门
        map.put("applicantDepartment", applicantDepartment);
        map.put("fileList", fileList);
        map.put("content", content);
//        map.put("activiti", pageResult);
        return map;
    }

    /**
     * 已办任务页面点击查看时使用的方法
     * @param business_code 流水号
     * @param itemsId       事项id
     * @return
     */
    @Override
    public Map<String, Object> findDatumInformation2(String business_code, Integer itemsId) {
        Map<String, Object> map = new HashMap<>();
        List applicantDepartment = mapper.findApplicantDepartment2(business_code, itemsId);//查询申请人经办人和部门
        List fileList = mapper.findFileNameAndUrl(business_code, itemsId);//查找文件名和url
        Map<String, Object> contentByItemsid = mapper.findContentByItemsid(business_code, itemsId);//根据流水号和事项id查找填写表单
        PageResult workFlow = itemService.getApproveHistoryByBusinessCode(business_code);//根据业务流水号查询受理信息和办理工作历史
        List<Map<String, Object>> byBusinessResultSatue = mapper.findByBusinessResultSatue(business_code);//港生已办任务页面审批流程节点图使用的方法

        map.put("applicantDepartment", applicantDepartment);
        map.put("byBusinessResultSatue", byBusinessResultSatue);
        map.put("fileList", fileList);
        map.put("workFlow", workFlow);
        map.put("contentByItemsid", contentByItemsid);
        return map;
    }

    /**
     * @param business_code
     * @return
     */
    @Override
    public String findBusiness(String business_code) {
        return mapper.findBusiness(business_code);
    }

    @Override
    public void save(Datum datum) {
        datumMapper.save(datum);
    }

    @javax.annotation.Resource
    private DataSource dataSource;

    /**
     * 查找datum表下的content申请表
     * @param business_code
     * @param itemsId
     * @param response
     */
    @Override
    public void findContent(String business_code, Integer itemsId, HttpServletResponse response) throws Exception{
        Datum datum = datumService.findByItemsIdAndBusinessCode(business_code, itemsId);
        String content = datum.getContent();
        Map<String, Object> map = JsonKit.toMap(content);
        System.out.println(map);
        if(itemsId == 11){
            //展示事项id为11的pdf表单
            //1.引入jasper文件
//            FileInputStream fis = new FileInputStream(resource.getFile());
            ClassPathResource resource = new ClassPathResource("templates/pdf/renshe.jasper");
            InputStream jasperStream = resource.getInputStream();
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
            String name = map.get("name").toString();
            String sex = map.get("sex").toString();
            String Idtype = map.get("Idtype").toString();
            String Idcard = map.get("Idcard").toString();
            String phone = map.get("phone").toString();
            String birthday = map.get("birthday").toString();
            String national = map.get("national").toString();
            String navtive = map.get("navtive").toString();
            String marital = map.get("marital").toString();
            String primarys = map.get("primarys").toString();
            String noPrimarys = map.get("noPrimarys").toString();
            String company = map.get("company").toString();
            String graduateSchool = map.get("graduateSchool").toString();
            String degree = map.get("degree").toString();
            String major = map.get("major").toString();
            String graduateTime = map.get("graduateTime").toString();
            //2.创建JasperPrint,向jasper文件中填充数据
            ServletOutputStream os = response.getOutputStream();
            try {
                /**
                 * fis: jasper 文件输入流
                 * new HashMap ：向模板中输入的参数
                 * JasperDataSource：数据源（和数据库数据源不同）
                 *              填充模板的数据来源（connection，javaBean，Map）
                 *              填充空数据来源：JREmptyDataSource
                 */
                Map renMap = new HashMap();
                renMap.put("name",name);
                renMap.put("Idtype",Idtype);
                renMap.put("Idcard",Idcard);
                renMap.put("sex",sex);
                renMap.put("phone",phone);
                renMap.put("national",national);
                renMap.put("birthday",birthday);
                renMap.put("navtive",navtive);
                renMap.put("marital",marital);
                renMap.put("primarys",primarys);
                renMap.put("noPrimarys",noPrimarys);
                renMap.put("company",company);
                renMap.put("graduateSchool",graduateSchool);
                renMap.put("degree",degree);
                renMap.put("major",major);
                renMap.put("graduateTime",graduateTime);
//                JasperPrint print = JasperFillManager.fillReport(fis, renMap,new JREmptyDataSource());
                JasperPrint print = JasperFillManager.fillReport(jasperReport, renMap, new JREmptyDataSource());
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(print));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                configuration.setPermissions(PdfWriter.AllowCopy | PdfWriter.AllowPrinting);

                configuration.setMetadataTitle("申请表");
                exporter.setConfiguration(configuration);
                exporter.exportReport();
                //3.将JasperPrint已PDF的形式输出
//                JasperExportManager.exportReportToPdfStream(print,os);
            } catch (JRException e) {
                e.printStackTrace();
            }finally {
                os.flush();
            }
        }else if(itemsId == 10){
            String marriage = map.get("marriage").toString();
            if (marriage.equals("未婚")){
                //未婚表单
                //1.引入jasper文件
//                FileInputStream fis = new FileInputStream(resource.getFile());
                ClassPathResource resource = new ClassPathResource("templates/pdf/weihun.jasper");
                InputStream jasperStream = resource.getInputStream();
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
                String name = map.get("name").toString();
                String sex = map.get("sex").toString();
                String Idtype = map.get("Idtype").toString();
                String Idcard = map.get("Idcard").toString();
                String phone = map.get("phone").toString();
                String applydate = map.get("applydate").toString();
                String accept = map.get("accept").toString();
                String security = map.get("security").toString();
                String Record = map.get("Record").toString();
                String bingyi = map.get("bingyi").toString();
                String inarea = map.get("inarea").toString();
                String height = map.get("height").toString();
                String statement = map.get("statement").toString();
                String wordarea = map.get("wordarea").toString();
                String bloodtype = map.get("bloodtype").toString();
                JSONObject jsonObjParentsData = JSONObject.fromObject(map.get("parentsData"));
                String haveson = jsonObjParentsData.get("haveson").toString();
                String f_name = jsonObjParentsData.get("f_name").toString();
                String f_cardtype = jsonObjParentsData.get("f_cardtype").toString();
                String f_Idcard = jsonObjParentsData.get("f_Idcard").toString();
                String m_name = jsonObjParentsData.get("m_name").toString();
                String m_cardtype = jsonObjParentsData.get("m_cardtype").toString();
                String m_Idcard = jsonObjParentsData.get("m_Idcard").toString();
                //2.创建JasperPrint,向jasper文件中填充数据
                ServletOutputStream os = response.getOutputStream();
                try {
                    /**
                     * fis: jasper 文件输入流
                     * new HashMap ：向模板中输入的参数
                     * JasperDataSource：数据源（和数据库数据源不同）
                     *              填充模板的数据来源（connection，javaBean，Map）
                     *              填充空数据来源：JREmptyDataSource
                     */
                    Map renMap = new HashMap();
                    renMap.put("name",name);
                    renMap.put("Idtype",Idtype);
                    renMap.put("idcard",Idcard);
                    renMap.put("sex",sex);
                    renMap.put("phone",phone);
                    renMap.put("applydate",applydate);
                    renMap.put("accept",accept);
                    renMap.put("security",security);
                    renMap.put("marriage",marriage);
                    renMap.put("Record",Record);
                    renMap.put("height",height);
                    renMap.put("bloodtype",bloodtype);
                    renMap.put("bingyi",bingyi);
                    renMap.put("wordarea",wordarea);
                    renMap.put("inarea",inarea);
                    renMap.put("major",height);
                    renMap.put("graduateTime",bloodtype);
                    renMap.put("statement",statement);
                    renMap.put("haveson",haveson);
                    renMap.put("fName",f_name);
                    renMap.put("fCardtype",f_cardtype);
                    renMap.put("fIdcard",f_Idcard);
                    renMap.put("mName",m_name);
                    renMap.put("mCardtype",m_cardtype);
                    renMap.put("mIdcard",m_Idcard);
                    JasperPrint print = JasperFillManager.fillReport(jasperReport, renMap,new JREmptyDataSource());
//                    //3.将JasperPrint已PDF的形式输出
//                    //生成我们的导出类JRPdfExporter 来自JRExporter
//                    JRPdfExporter jrpdfExporter = new JRPdfExporter();
//                    SimplePdfExporterConfiguration pdfExporter= new SimplePdfExporterConfiguration();
//                    //设JasperPrint参数
//                    jrpdfExporter.setParameter(JRExporterParameter.JASPER_PRINT,print);
//                    //设置输入的PDF文件放在什么地方
//                    jrpdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "WebRoot//JasperFile//申请人.pdf");
//                    jrpdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setExporterInput(new SimpleExporterInput(print));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                    SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                    configuration.setPermissions(PdfWriter.AllowCopy | PdfWriter.AllowPrinting);

                    configuration.setMetadataTitle("申请表");
                    exporter.setConfiguration(configuration);
                    exporter.exportReport();
                } catch (JRException e) {
                    e.printStackTrace();
                }finally {
                    os.flush();
                }
            }else if(marriage.equals("再婚")){
                //再婚表单
                //1.引入jasper文件
                ClassPathResource resource = new ClassPathResource("templates/pdf/zaihun.jasper");
                InputStream jasperStream = resource.getInputStream();
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
                String name = map.get("name").toString();
                String sex = map.get("sex").toString();
                String Idtype = map.get("Idtype").toString();
                String Idcard = map.get("Idcard").toString();
                String phone = map.get("phone").toString();
                String applydate = map.get("applydate").toString();
                String accept = map.get("accept").toString();
                String security = map.get("security").toString();
                String Record = map.get("Record").toString();
                String bingyi = map.get("bingyi").toString();
                String inarea = map.get("inarea").toString();
                String height = map.get("height").toString();
                String statement = map.get("statement").toString();
                String wordarea = map.get("wordarea").toString();
                String bloodtype = map.get("bloodtype").toString();

                JSONObject json = JSONObject.fromObject(map.get("SpouseData"));
                String name2 = json.get("name").toString();
//                String oldname = json.get("oldname").toString();
                String phone2 = json.get("phone").toString();
                String Idtype2 = json.get("Idtype").toString();
                String Idcard2 = json.get("Idcard").toString();
                String marriage2 = json.get("marriage").toString();
                String Record2 = json.get("Record").toString();
                String height2 = json.get("height").toString();
                String bloodtype2 = json.get("bloodtype").toString();
                String bingyi2 = json.get("bingyi").toString();
                String wordarea2 = json.get("wordarea").toString();
                String trailing = json.get("trailing").toString();
                String haveson = json.get("haveson").toString();
                //2.创建JasperPrint,向jasper文件中填充数据
                ServletOutputStream os = response.getOutputStream();
                try {
                    /**
                     * fis: jasper 文件输入流
                     * new HashMap ：向模板中输入的参数
                     * JasperDataSource：数据源（和数据库数据源不同）
                     *              填充模板的数据来源（connection，javaBean，Map）
                     *              填充空数据来源：JREmptyDataSource
                     */
                    Map renMap = new HashMap();
                    renMap.put("name",name);
                    renMap.put("Idtype",Idtype);
                    renMap.put("idcard",Idcard);
                    renMap.put("sex",sex);
                    renMap.put("phone",phone);
                    renMap.put("applydate",applydate);
                    renMap.put("accept",accept);
                    renMap.put("security",security);
                    renMap.put("marriage",marriage);
                    renMap.put("Record",Record);
                    renMap.put("height",height);
                    renMap.put("bloodtype",bloodtype);
                    renMap.put("bingyi",bingyi);
                    renMap.put("wordarea",wordarea);
                    renMap.put("inarea",inarea);
                    renMap.put("major",height);
                    renMap.put("graduateTime",bloodtype);
                    renMap.put("statement",statement);
                    renMap.put("Parameter2",haveson);
                    renMap.put("name2",name2);
//                    renMap.put("oldname",oldname);
                    renMap.put("phone2",phone2);
                    renMap.put("Idtype2",Idtype2);
                    renMap.put("Idcard2",Idcard2);
                    renMap.put("marriage2",marriage2);
                    renMap.put("Record2",Record2);
                    renMap.put("height2",height2);
                    renMap.put("bloodtype2",bloodtype2);
                    renMap.put("bingyi2",bingyi2);
                    renMap.put("wordarea2",wordarea2);
                    renMap.put("trailing",trailing);
                    JasperPrint print = JasperFillManager.fillReport(jasperReport, renMap,new JREmptyDataSource());
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setExporterInput(new SimpleExporterInput(print));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                    SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                    configuration.setPermissions(PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
                    configuration.setMetadataTitle("申请表");
                    exporter.setConfiguration(configuration);
                    exporter.exportReport();
                } catch (JRException e) {
                    e.printStackTrace();
                }finally {
                    os.flush();
                }
            }else if(marriage.equals("离婚")){
                //离婚表单
                //1.引入jasper文件
                Resource resource = new ClassPathResource("templates/pdf/lihun.jasper");
//                FileInputStream fis = new FileInputStream(resource.getFile());
                InputStream jasperStream = resource.getInputStream();
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
                String name = map.get("name").toString();
                String sex = map.get("sex").toString();
                String Idtype = map.get("Idtype").toString();
                String Idcard = map.get("Idcard").toString();
                String phone = map.get("phone").toString();
                String applydate = map.get("applydate").toString();
                String accept = map.get("accept").toString();
                String security = map.get("security").toString();
                String Record = map.get("Record").toString();
                String bingyi = map.get("bingyi").toString();
                String inarea = map.get("inarea").toString();
                String height = map.get("height").toString();
                String statement = map.get("statement").toString();
                String wordarea = map.get("wordarea").toString();
                String bloodtype = map.get("bloodtype").toString();
                String address = map.get("address").toString();

                //2.创建JasperPrint,向jasper文件中填充数据
                ServletOutputStream os = response.getOutputStream();
                try {
                    /**
                     * fis: jasper 文件输入流
                     * new HashMap ：向模板中输入的参数
                     * JasperDataSource：数据源（和数据库数据源不同）
                     *              填充模板的数据来源（connection，javaBean，Map）
                     *              填充空数据来源：JREmptyDataSource
                     */
                    Map renMap = new HashMap();
                    renMap.put("name",name);
                    renMap.put("Idtype",Idtype);
                    renMap.put("Idcard",Idcard);
                    renMap.put("sex",sex);
                    renMap.put("phone",phone);
                    renMap.put("applydate",applydate);
                    renMap.put("accept",accept);
                    renMap.put("security",security);
                    renMap.put("marriage",marriage);
                    renMap.put("Record",Record);
                    renMap.put("height",height);
                    renMap.put("bloodtype",bloodtype);
                    renMap.put("bingyi",bingyi);
                    renMap.put("wordarea",wordarea);
                    renMap.put("inarea",inarea);
                    renMap.put("major",height);
                    renMap.put("graduateTime",bloodtype);
                    renMap.put("statement",statement);
                    renMap.put("address",address);

                    JasperPrint print = JasperFillManager.fillReport(jasperReport, renMap,new JREmptyDataSource());
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setExporterInput(new SimpleExporterInput(print));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                    SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                    configuration.setPermissions(PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
                    configuration.setMetadataTitle("申请表.pdf");
                    exporter.setConfiguration(configuration);
                    exporter.exportReport();
                } catch (JRException e) {
                    e.printStackTrace();
                }finally {
                    os.flush();
                }
            }
        }else if(itemsId == 1){//食品经营许可证核发 东莞市监局

        }else if(itemsId == 2){//建设工程消防设计审查 东莞市公安局

        }else if(itemsId == 3){//公众聚集场所投入使用、营业前消防安全检查 东莞市消防局

        }else if(itemsId == 4){//设置大型户外广告审批 东莞市城管执法局

        }else if(itemsId == 5){//建设工程消防验收 东莞市公安局

        }else if(itemsId == 6){//污水排入排水管网许可证核发 东莞市环保局

        }
    }

    /**
     * 查找datum表下的content申请表
     */
    @Override
    public void findWebContent(String business_code, Integer itemsId, HttpServletResponse response) throws Exception{
        Datum datum = datumService.findByItemsIdAndBusinessCode(business_code, itemsId);
        String content = datum.getContent();
        Map<String, Object> map = JsonKit.toMap(content);
        System.out.println(map);
        if(itemsId == 11){
            //展示事项id为11的pdf表单
            //1.引入jasper文件
//            FileInputStream fis = new FileInputStream(resource.getFile());
            ClassPathResource resource = new ClassPathResource("templates/pdf/renshe.jasper");
            InputStream jasperStream = resource.getInputStream();
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
            String name = map.get("name").toString();
            String sex = map.get("sex").toString();
            String Idtype = map.get("Idtype").toString();
            String Idcard = map.get("Idcard").toString();
            String phone = map.get("phone").toString();
            String birthday = map.get("birthday").toString();
            String national = map.get("national").toString();
            String navtive = map.get("navtive").toString();
            String marital = map.get("marital").toString();
            String primarys = map.get("primarys").toString();
            String noPrimarys = map.get("noPrimarys").toString();
            String company = map.get("company").toString();
            String graduateSchool = map.get("graduateSchool").toString();
            String degree = map.get("degree").toString();
            String major = map.get("major").toString();
            String graduateTime = map.get("graduateTime").toString();
            //2.创建JasperPrint,向jasper文件中填充数据
            ServletOutputStream os = response.getOutputStream();
            try {
                /**
                 * fis: jasper 文件输入流
                 * new HashMap ：向模板中输入的参数
                 * JasperDataSource：数据源（和数据库数据源不同）
                 *              填充模板的数据来源（connection，javaBean，Map）
                 *              填充空数据来源：JREmptyDataSource
                 */
                Map renMap = new HashMap();
                renMap.put("name",name);
                renMap.put("Idtype",Idtype);
                renMap.put("Idcard",Idcard);
                renMap.put("sex",sex);
                renMap.put("phone",phone);
                renMap.put("national",national);
                renMap.put("birthday",birthday);
                renMap.put("navtive",navtive);
                renMap.put("marital",marital);
                renMap.put("primarys",primarys);
                renMap.put("noPrimarys",noPrimarys);
                renMap.put("company",company);
                renMap.put("graduateSchool",graduateSchool);
                renMap.put("degree",degree);
                renMap.put("major",major);
                renMap.put("graduateTime",graduateTime);
//                JasperPrint print = JasperFillManager.fillReport(fis, renMap,new JREmptyDataSource());
                JasperPrint print = JasperFillManager.fillReport(jasperReport, renMap, new JREmptyDataSource());
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(print));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                configuration.setPermissions(PdfWriter.AllowCopy | PdfWriter.AllowPrinting);

                configuration.setMetadataTitle("人才入户指导(条件准入类)申请表");
                exporter.setConfiguration(configuration);
                exporter.exportReport();
                //3.将JasperPrint已PDF的形式输出
//                JasperExportManager.exportReportToPdfStream(print,os);
            } catch (JRException e) {
                e.printStackTrace();
            }finally {
                os.flush();
            }
        }else if(itemsId == 10){
            String marriage = map.get("marriage").toString();
            if (marriage.equals("未婚")){
                //未婚表单
                //1.引入jasper文件
//                FileInputStream fis = new FileInputStream(resource.getFile());
                ClassPathResource resource = new ClassPathResource("templates/pdf/weihun.jasper");
                InputStream jasperStream = resource.getInputStream();
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
                String name = map.get("name").toString();
                String sex = map.get("sex").toString();
                String Idtype = map.get("Idtype").toString();
                String Idcard = map.get("Idcard").toString();
                String phone = map.get("phone").toString();
                String applydate = map.get("applydate").toString();
                String accept = map.get("accept").toString();
                String security = map.get("security").toString();
                String Record = map.get("Record").toString();
                String bingyi = map.get("bingyi").toString();
                String inarea = map.get("inarea").toString();
                String height = map.get("height").toString();
                String statement = map.get("statement").toString();
                String wordarea = map.get("wordarea").toString();
                String bloodtype = map.get("bloodtype").toString();
                JSONObject jsonObjParentsData = JSONObject.fromObject(map.get("parentsData"));
                String haveson = jsonObjParentsData.get("haveson").toString();
                String f_name = jsonObjParentsData.get("f_name").toString();
                String f_cardtype = jsonObjParentsData.get("f_cardtype").toString();
                String f_Idcard = jsonObjParentsData.get("f_Idcard").toString();
                String m_name = jsonObjParentsData.get("m_name").toString();
                String m_cardtype = jsonObjParentsData.get("m_cardtype").toString();
                String m_Idcard = jsonObjParentsData.get("m_Idcard").toString();
                //2.创建JasperPrint,向jasper文件中填充数据
                ServletOutputStream os = response.getOutputStream();
                try {
                    /**
                     * fis: jasper 文件输入流
                     * new HashMap ：向模板中输入的参数
                     * JasperDataSource：数据源（和数据库数据源不同）
                     *              填充模板的数据来源（connection，javaBean，Map）
                     *              填充空数据来源：JREmptyDataSource
                     */
                    Map renMap = new HashMap();
                    renMap.put("name",name);
                    renMap.put("Idtype",Idtype);
                    renMap.put("idcard",Idcard);
                    renMap.put("sex",sex);
                    renMap.put("phone",phone);
                    renMap.put("applydate",applydate);
                    renMap.put("accept",accept);
                    renMap.put("security",security);
                    renMap.put("marriage",marriage);
                    renMap.put("Record",Record);
                    renMap.put("height",height);
                    renMap.put("bloodtype",bloodtype);
                    renMap.put("bingyi",bingyi);
                    renMap.put("wordarea",wordarea);
                    renMap.put("inarea",inarea);
                    renMap.put("major",height);
                    renMap.put("graduateTime",bloodtype);
                    renMap.put("statement",statement);
                    renMap.put("haveson",haveson);
                    renMap.put("fName",f_name);
                    renMap.put("fCardtype",f_cardtype);
                    renMap.put("fIdcard",f_Idcard);
                    renMap.put("mName",m_name);
                    renMap.put("mCardtype",m_cardtype);
                    renMap.put("mIdcard",m_Idcard);
                    JasperPrint print = JasperFillManager.fillReport(jasperReport, renMap,new JREmptyDataSource());
//                    //3.将JasperPrint已PDF的形式输出
//                    //生成我们的导出类JRPdfExporter 来自JRExporter
//                    JRPdfExporter jrpdfExporter = new JRPdfExporter();
//                    SimplePdfExporterConfiguration pdfExporter= new SimplePdfExporterConfiguration();
//                    //设JasperPrint参数
//                    jrpdfExporter.setParameter(JRExporterParameter.JASPER_PRINT,print);
//                    //设置输入的PDF文件放在什么地方
//                    jrpdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "WebRoot//JasperFile//申请人.pdf");
//                    jrpdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setExporterInput(new SimpleExporterInput(print));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                    SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                    configuration.setPermissions(PdfWriter.AllowCopy | PdfWriter.AllowPrinting);

                    configuration.setMetadataTitle("条件准入类人才入户申请表");
                    exporter.setConfiguration(configuration);
                    exporter.exportReport();
                } catch (JRException e) {
                    e.printStackTrace();
                }finally {
                    os.flush();
                }
            }else if(marriage.equals("再婚")){
                //再婚表单
                //1.引入jasper文件
                ClassPathResource resource = new ClassPathResource("templates/pdf/zaihun.jasper");
                InputStream jasperStream = resource.getInputStream();
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
                String name = map.get("name").toString();
                String sex = map.get("sex").toString();
                String Idtype = map.get("Idtype").toString();
                String Idcard = map.get("Idcard").toString();
                String phone = map.get("phone").toString();
                String applydate = map.get("applydate").toString();
                String accept = map.get("accept").toString();
                String security = map.get("security").toString();
                String Record = map.get("Record").toString();
                String bingyi = map.get("bingyi").toString();
                String inarea = map.get("inarea").toString();
                String height = map.get("height").toString();
                String statement = map.get("statement").toString();
                String wordarea = map.get("wordarea").toString();
                String bloodtype = map.get("bloodtype").toString();

                JSONObject json = JSONObject.fromObject(map.get("SpouseData"));
                String name2 = json.get("name").toString();
//                String oldname = json.get("oldname").toString();
                String phone2 = json.get("phone").toString();
                String Idtype2 = json.get("Idtype").toString();
                String Idcard2 = json.get("Idcard").toString();
                String marriage2 = json.get("marriage").toString();
                String Record2 = json.get("Record").toString();
                String height2 = json.get("height").toString();
                String bloodtype2 = json.get("bloodtype").toString();
                String bingyi2 = json.get("bingyi").toString();
                String wordarea2 = json.get("wordarea").toString();
                String trailing = json.get("trailing").toString();
                String haveson = json.get("haveson").toString();
                //2.创建JasperPrint,向jasper文件中填充数据
                ServletOutputStream os = response.getOutputStream();
                try {
                    /**
                     * fis: jasper 文件输入流
                     * new HashMap ：向模板中输入的参数
                     * JasperDataSource：数据源（和数据库数据源不同）
                     *              填充模板的数据来源（connection，javaBean，Map）
                     *              填充空数据来源：JREmptyDataSource
                     */
                    Map renMap = new HashMap();
                    renMap.put("name",name);
                    renMap.put("Idtype",Idtype);
                    renMap.put("idcard",Idcard);
                    renMap.put("sex",sex);
                    renMap.put("phone",phone);
                    renMap.put("applydate",applydate);
                    renMap.put("accept",accept);
                    renMap.put("security",security);
                    renMap.put("marriage",marriage);
                    renMap.put("Record",Record);
                    renMap.put("height",height);
                    renMap.put("bloodtype",bloodtype);
                    renMap.put("bingyi",bingyi);
                    renMap.put("wordarea",wordarea);
                    renMap.put("inarea",inarea);
                    renMap.put("major",height);
                    renMap.put("graduateTime",bloodtype);
                    renMap.put("statement",statement);
                    renMap.put("Parameter2",haveson);
                    renMap.put("name2",name2);
//                    renMap.put("oldname",oldname);
                    renMap.put("phone2",phone2);
                    renMap.put("Idtype2",Idtype2);
                    renMap.put("Idcard2",Idcard2);
                    renMap.put("marriage2",marriage2);
                    renMap.put("Record2",Record2);
                    renMap.put("height2",height2);
                    renMap.put("bloodtype2",bloodtype2);
                    renMap.put("bingyi2",bingyi2);
                    renMap.put("wordarea2",wordarea2);
                    renMap.put("trailing",trailing);
                    JasperPrint print = JasperFillManager.fillReport(jasperReport, renMap,new JREmptyDataSource());
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setExporterInput(new SimpleExporterInput(print));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                    SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                    configuration.setPermissions(PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
                    configuration.setMetadataTitle("条件准入类人才入户申请表");
                    exporter.setConfiguration(configuration);
                    exporter.exportReport();
                } catch (JRException e) {
                    e.printStackTrace();
                }finally {
                    os.flush();
                }
            }else if(marriage.equals("离婚")){
                //离婚表单
                //1.引入jasper文件
                Resource resource = new ClassPathResource("templates/pdf/lihun.jasper");
//                FileInputStream fis = new FileInputStream(resource.getFile());
                InputStream jasperStream = resource.getInputStream();
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
                String name = map.get("name").toString();
                String sex = map.get("sex").toString();
                String Idtype = map.get("Idtype").toString();
                String Idcard = map.get("Idcard").toString();
                String phone = map.get("phone").toString();
                String applydate = map.get("applydate").toString();
                String accept = map.get("accept").toString();
                String security = map.get("security").toString();
                String Record = map.get("Record").toString();
                String bingyi = map.get("bingyi").toString();
                String inarea = map.get("inarea").toString();
                String height = map.get("height").toString();
                String statement = map.get("statement").toString();
                String wordarea = map.get("wordarea").toString();
                String bloodtype = map.get("bloodtype").toString();
                String address = map.get("address").toString();

                //2.创建JasperPrint,向jasper文件中填充数据
                ServletOutputStream os = response.getOutputStream();
                try {
                    /**
                     * fis: jasper 文件输入流
                     * new HashMap ：向模板中输入的参数
                     * JasperDataSource：数据源（和数据库数据源不同）
                     *              填充模板的数据来源（connection，javaBean，Map）
                     *              填充空数据来源：JREmptyDataSource
                     */
                    Map renMap = new HashMap();
                    renMap.put("name",name);
                    renMap.put("Idtype",Idtype);
                    renMap.put("Idcard",Idcard);
                    renMap.put("sex",sex);
                    renMap.put("phone",phone);
                    renMap.put("applydate",applydate);
                    renMap.put("accept",accept);
                    renMap.put("security",security);
                    renMap.put("marriage",marriage);
                    renMap.put("Record",Record);
                    renMap.put("height",height);
                    renMap.put("bloodtype",bloodtype);
                    renMap.put("bingyi",bingyi);
                    renMap.put("wordarea",wordarea);
                    renMap.put("inarea",inarea);
                    renMap.put("major",height);
                    renMap.put("graduateTime",bloodtype);
                    renMap.put("statement",statement);
                    renMap.put("address",address);

                    JasperPrint print = JasperFillManager.fillReport(jasperReport, renMap,new JREmptyDataSource());
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setExporterInput(new SimpleExporterInput(print));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                    SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                    configuration.setPermissions(PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
                    configuration.setMetadataTitle("条件准入类人才入户申请表.pdf");
                    exporter.setConfiguration(configuration);
                    exporter.exportReport();
                } catch (JRException e) {
                    e.printStackTrace();
                }finally {
                    os.flush();
                }
            }
        }else if(itemsId == 1){//食品经营许可证核发 东莞市监局

        }else if(itemsId == 2){//建设工程消防设计审查 东莞市公安局

        }else if(itemsId == 3){//公众聚集场所投入使用、营业前消防安全检查 东莞市消防局

        }else if(itemsId == 4){//设置大型户外广告审批 东莞市城管执法局

        }else if(itemsId == 5){//建设工程消防验收 东莞市公安局

        }else if(itemsId == 6){//污水排入排水管网许可证核发 东莞市环保局

        }
    }

    /**
     * 根据businessCode和itemsId查询所有的Datum
     *
     * @param businessCode
     * @param itemsId
     * @return
     */
    @Override
    public Datum findAllByBusinessCodeAndItemsId(String businessCode, int itemsId) {
        Datum datum = datumMapper.findAllByBusinessCodeAndItemsId(businessCode, itemsId);
        return datum;
    }

    @Override
    public Map<String, Object> findDeptId(String account) {
        return mapper.findDeptId(account);
    }

    /**
     * 根据流水号查找相关的文件和表单,未使用到这个方法
     *
     * @param business_code
     * @return
     */
    @Override
    public List findFileBybusinessCode(String business_code) {
        return mapper.findFileBybusinessCode(business_code);
    }

    @Override
    public List<Datum> findAllByBusinessCode(String businessCode) {
        List<Datum> datumList = datumMapper.findAllByBusinessCode(businessCode);
        return datumList;
    }

    /**
     * 根据流水号和事项id查找查找文件名和url地址
     *
     * @param business_code
     * @param item_id
     * @return
     */
    @Override
    public List findFileNameAndUrl(String business_code, Integer item_id) {
        return mapper.findFileNameAndUrl(business_code, item_id);
    }

    /**
     * 前端获得用户申请信息审批历史记录
     *
     * @param business_code
     * @return
     */
    @Override
    public List findUserinfo(String business_code) {
        List allByFront = mapper.findAllByFront(business_code);//获取申请人，申请表单，经办人，事项和部门
        List fileList = mapper.findFile(business_code);//获取申请表单
        PageResult workFlow = itemService.getWorkFlowOrderByBusinessCode(business_code);//获取审批历史
        allByFront.add(fileList);
        allByFront.add(workFlow);
        return allByFront;
    }

    @Override
    public Map<String, Object> findFileById(Integer id) {
        /**
         * 根据fileid查询file
         */
        return mapper.findFileById(id);
    }

    @Override
    public Datum findByItemsIdAndBusinessCode(String businessCode, Integer itemsId) {
        return mapper.findByItemsIdAndBusinessCode(businessCode, itemsId);
    }

    /**
     * 根据业务流水号修改表单状态
     * @param businessCode
     */
    @Override
    public void updateState(String businessCode, String state) {
        datumMapper.updateState(businessCode, state);
    }

    /**
     * 根据与公安局相关的事项id查询表单
     * @return
     */
    @Override
    public List<Datum> findAllByItemId() {
        List<Datum> datumList = datumMapper.findAllByItemId();
        return datumList;
    }


    @Override
    public void update(Datum datum) {
        datumMapper.update(datum);
    }

    /**
     * 待办任务页面数据表格及分页
     *
     * @param pageNum
     * @param pageSize
     * @param searchMap
     * @param orderBy
     * @param items_id  事项id
     * @return
     */
    @Override
    public PageInfo queryHelperPageInfo(int pageNum, int pageSize, Map<String, String> searchMap, String orderBy, String items_id, String assignee) {
        PageResult pageResult = activitiService.activitiTaskQuery(assignee);//调用工作流接口获取当前部门所有待审批事件
        List<String> data = (List<String>) pageResult.getData();
        PageHelper.startPage(pageNum, pageSize, orderBy);
        Example example = new Example(Datum.class);
        Example.Criteria criteria = example.createCriteria();
        if(data !=null){
            criteria.andIn("businessCode", data);
        }

        if (searchMap.containsKey("name")) {
            criteria.andLike("businessCode", "%" + searchMap.get("name") + "%");
        }
        if (assignee.equals("7")){//根据部门id查询所属的所有事项
            String[] split = items_id.split(",");
            List<Integer> itemsIds = new ArrayList<>();
            int itemsId = 0;
            for (String s : split) {
                itemsId = Integer.valueOf(s).intValue();
                itemsIds.add(itemsId);
            }
            criteria.andIn("itemsId", itemsIds);
        }else {
            int itemsId = Integer.parseInt(items_id);
            criteria.andEqualTo("itemsId", itemsId);
        }
        if (items_id!=null){
            criteria.andEqualTo("state", 1);
        }else {
            criteria.andEqualTo("state", -1);
        }

        List<Datum> datumList = mapper.selectByExample(example);
//        PageResult pageResult = activitiService.activitiTaskQuery(assignee);
//        List<String> list = (List<String>) pageResult.getData();
//        List<Datum> datumLists = new ArrayList<>();
//        for (String s : list) {
//            for (Datum datum : datumList) {
//                if (StringUtils.equals(datum.getBusinessCode(), s)) {
//                    datumLists.add(datum);
//                }
//            }
//        }
        return new PageInfo<Datum>(datumList);
    }

    /**
     * 已办任务页面数据表格及分页
     * @param pageNum
     * @param pageSize
     * @param searchMap
     * @param orderBy
     * @param items_id  事项id
     * @return
     */
    @Override
    public PageInfo queryHelperPageInfo2(int pageNum, int pageSize, Map<String, String> searchMap, String orderBy, String items_id, String assignee) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        Example example = new Example(Datum.class);
        List<String> state = new ArrayList<>();
        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("itemsId", items_id);
        if (assignee.equals("7")){//根据部门id查询所属的所有事项
            String[] split = items_id.split(",");
            List<Integer> itemsIds = new ArrayList<>();
            int itemsId = 0;
            for (String s : split) {
                itemsId = Integer.valueOf(s).intValue();
                itemsIds.add(itemsId);
            }
            criteria.andIn("itemsId", itemsIds);
        }else {
            int itemsId = Integer.parseInt(items_id);
            criteria.andEqualTo("itemsId", itemsId);
        }
        if (items_id!=null){
            state.add("2");
            state.add("3");
        }else {
            state.add("-1");
        }

        criteria.andIn("state", state);//判断状态为2和3
        if (searchMap.containsKey("name")) {
            criteria.andLike("businessCode", "%" + searchMap.get("name") + "%");
        }
        return new PageInfo<Datum>(mapper.selectByExample(example));
    }

    @Override
    public List<Datum> query(Datum datum) {
        return mapper.select(datum);
    }

    @Override
    public int insert(Datum datum) {
        return mapper.insert(datum);
    }
}
