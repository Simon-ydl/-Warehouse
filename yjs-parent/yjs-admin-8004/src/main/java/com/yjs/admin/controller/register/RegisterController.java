package com.yjs.admin.controller.register;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yjs.bean.file.Files;
import com.yjs.bean.item.Items;
import com.yjs.bean.materials.Materials;
import com.yjs.bean.matter.Matter;
import com.yjs.bean.vo.Message;
import com.yjs.bean.vo.TableData;
import com.yjs.service.file.FileService;
import com.yjs.service.item.ItemsService;
import com.yjs.service.materials.IMaterialsService;
import com.yjs.service.matter.service.IMatterApp;
import com.yjs.service.register.IRegisterService;
import com.yjs.utils.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    @Autowired
    IRegisterService registerService;

    @Autowired
    private IMatterApp matterApp;

    @Autowired
    FileService fileService;

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    ItemsService itemsService;

    @Autowired
    IMaterialsService materialsService;




    @RequestMapping("/toRegister.do")
    public ModelAndView toIndex(HttpServletRequest request){

        ModelAndView mv = new ModelAndView("register/premise");

        mv.addObject("itemId",request.getParameter("itemId"));

        if(StringUtils.isNotBlank(request.getParameter("itemId"))){

            Matter matter = matterApp.queryMatterByItemId(request.getParameter("itemId"));

            mv.addObject("matter",matter);

        }

        //根据一件事ID获取情形
        try{

            String getOnethingItem = ApiAccess.StringApipost("getOnethingItem", "oneThingId="+request.getParameter("itemId"));

            System.err.println("获取一件事情形列表---------"+getOnethingItem);

            mv.addObject("onethingItem", JSON.parse(getOnethingItem));

        }catch (Exception e){

        }finally {

            return mv;

        }




    }


    @RequestMapping(value = "/noLogin/readImageFile1", method = RequestMethod.GET)
    @ResponseBody
    public void getUrlFile(String url, HttpServletRequest request, HttpServletResponse response) {
        String serverUrl = "D:\\temp-appImg\\";
        String imgUrl = "D:\\测试文件上传1111.doc";
        File file = new File(imgUrl);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            int length = inputStream.read(data);
            inputStream.close();
//            setContentType("text/plain; charset=utf-8"); 文本
            response.setContentType("application/msword; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;fileName=123.doc");
            OutputStream stream = response.getOutputStream();
            stream.write(data);
            stream.flush();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转选择情形页（跳转材料页公用）
     * @param request
     * @return
     */
    @RequestMapping("/toRegisterIndex.do")
    public ModelAndView toRegisterIndex(HttpServletRequest request){

        IdWorker id = new IdWorker();

        String businessCode = id.nextId()+"";

        String toPage = request.getParameter("formPage");

        String url = "register/index";

        if(null != toPage && toPage.equals("material")){

            url = "register/materialList";
        }

        ModelAndView mv = new ModelAndView(url);

        mv.addObject("businessCode",businessCode);

        if(null != toPage && toPage.equals("material")){

            if(StringUtils.isNotBlank(request.getParameter("businessCode"))){
                List<Materials> list = materialsService.queryMaterialsbyCode(request.getParameter("businessCode"));
                if(null != list && list.size()>0){
                    mv.addObject("materialId",list.get(0).getMaterial());
                    mv.addObject("materialItemId",list.get(0).getMaterialInItem());
                }
            }




        }

        mv.addObject("itemId",request.getParameter("itemId"));

        if(StringUtils.isNotBlank(request.getParameter("itemId"))){

            Matter matter = matterApp.queryMatterByItemId(request.getParameter("itemId"));

            mv.addObject("matter",matter);

        }

        //根据前提选择得出来的办理事项
        String itemIdStr = request.getParameter("itemIdArr");
        System.out.print("等到事项ID————————"+itemIdStr);
        JSONArray itemList = new JSONArray();
        JSONArray situationList = new JSONArray();
        if(StringUtils.isNotBlank(itemIdStr)){

//            itemIdArr =  itemIdStr.split(",");
        }

        //根据一件事ID获取前置情形
        try{

            String getOnethingItem = ApiAccess.StringApipost("getOnethingItem", "oneThingId="+request.getParameter("itemId"));

            System.err.println("获取一件事情形列表---------"+getOnethingItem);

            //选出办理事项对象信息
            JSONObject onethingItem = (JSONObject) JSON.parse(getOnethingItem);

            if(null != onethingItem.get("data")){
                System.err.print("-------------------------------");
                JSONObject data = onethingItem.getJSONObject("data");
                JSONArray bitemList = data.getJSONArray("bitemList");

                if(null != bitemList && bitemList.size()>0){

                    for(int i=0;i<bitemList.size();i++){

                        JSONObject obj = bitemList.getJSONObject(i);
                        System.err.print("^^^^^^^^^^^^^^^"+itemIdStr.indexOf(obj.getString("itemId")));
                        if(itemIdStr.indexOf(obj.getString("itemId"))>=0){

                            itemList.add(obj);

                            String listLastSituationItemInfo = ApiAccess.StringApipost("listLastSituationItemInfo", "itemId="+obj.getString("itemId"));

                            System.out.println(listLastSituationItemInfo);

                            JSONObject itemInfo = (JSONObject) JSON.parse(listLastSituationItemInfo);

                            if(null != itemInfo.get("data")){

                                situationList.add(itemInfo.getJSONArray("data").get(0));
                            }



                        }

                    }

                }
            }


            //一体化获取到事项信息，判断当前系统有没有事项，没有就同步进库
            registerService.saveItem(itemList);

            mv.addObject("itemList", itemList);
            mv.addObject("onethingItem", situationList);
            mv.addObject("itemIdStr", itemIdStr);


        }catch (Exception e){

        }finally {

            return mv;

        }




    }


    @RequestMapping("/tohjApplyTable.do")
    public ModelAndView tohjApplyTable(HttpServletRequest request){

        ModelAndView mv = new ModelAndView("register/form_Birth");
        mv.addObject("itemId",request.getParameter("itemId"));
        String itemIdStr = request.getParameter("itemIdArr");
        mv.addObject("itemIdStr", itemIdStr);
        mv.addObject("materialId",request.getParameter("materialId"));
        mv.addObject("materialItemId",request.getParameter("materialItemId"));
        mv.addObject("businessCode",request.getParameter("businessCode"));

        return mv;

    }

    @RequestMapping("/toFormNonMarriage.do")
    public ModelAndView toFormNonMarriage(HttpServletRequest request){

        ModelAndView mv = new ModelAndView("register/form_NonMarriage");
        mv.addObject("itemId",request.getParameter("itemId"));
        String itemIdStr = request.getParameter("itemIdArr");
        mv.addObject("itemIdStr", itemIdStr);
        mv.addObject("businessCode",request.getParameter("businessCode"));

        return mv;

    }

    @RequestMapping("/toUploadMaterial.do")
    public String toUploadMaterial(){
        return "/register/uploadMaterial";
    }

    @RequestMapping("/getQxByItemCode.do")
    public Message getQxByItemCode(){

        try{

            String getQxByItemCode = ApiAccess.StringApipost("getQxByItemCode", "itemId=93e8a79b6dbf6410016dc2edaaaf0055");

            System.out.println(getQxByItemCode);

            return new Message("200",true,getQxByItemCode);

        }catch (Exception e){

            e.printStackTrace();
        }

        return new Message("1001",false,"");

    }


    @PostMapping("/saveRegisterFirst.do")
    @ResponseBody
    public Message registerFirst(HttpServletRequest request) {

        Map<String,String> map = new HashMap<>();

        //情形名称
        String siName = "哟哟哟";
        //情形内容
        String siContent = (String) request.getParameter("siContent");

//        RoUser roUser1 = (RoUser) request.getSession().getParameter("loginTime");

        /*现场登记，经办人和申请人是同一个人*/
        //申请人
        String apName = (String) request.getParameter("apName");
        String apNumberType = (String) request.getParameter("apNumberType");
        String apNumber = (String) request.getParameter("apNumber");
        //经办人
        String aName = (String) request.getParameter("apName");
        String aNumber = (String) request.getParameter("apNumber");
        String aNumberType = (String) request.getParameter("apNumberType");
        String aMobile = (String) request.getParameter("aMobile");
        String aEmail = (String) request.getParameter("aEmail");


        //收件人信息
        String rName = (String) request.getParameter("rName");
        String rWay = (String) request.getParameter("rWay");
        String rMobile = (String) request.getParameter("rMobile");
        String rArea = (String) request.getParameter("rArea");
        String rAddress = (String) request.getParameter("rAddress");
        String rHall = (String) request.getParameter("rHall");

        //用户标识
        String useridcode = apNumber;

        //前置条件内容
        String content = (String) request.getParameter("content");
        //事项code
        String matterCode = (String) request.getParameter("matterCode");

        //材料信息
        String inContent = (String) request.getParameter("inContent");

        //材料ID
        String materialId = (String) request.getParameter("materialId");

        //材料事项ID
        String materialItemId = (String) request.getParameter("materialItemId");

        //业务启动标识
        String businessTag = "2";


        map.put("siName",siName);
        map.put("siContent",siContent);
        map.put("aName",aName);
        map.put("aNumber",aNumber);
        map.put("aNumberType",aNumberType);
        map.put("aMobile",aMobile);
        map.put("aEmail",aEmail);
        map.put("apName",apName);
        map.put("apNumberType",apNumberType);
        map.put("apNumber",apNumber);
        map.put("rName",rName);
        map.put("rWay",rWay);
        map.put("rMobile",rMobile);
        map.put("rArea",rArea);
        map.put("rAddress",rAddress);
        map.put("rHall",rHall);
        map.put("useridcode",useridcode);
        map.put("content",content);
        map.put("matterCode",matterCode);
        map.put("businessTag",businessTag);

        map.put("inContent",inContent);
        map.put("materialId",materialId);
        map.put("materialItemId",materialItemId);

        String businesses = registerService.registerFirst(map,request);

        if(StringUtils.isNotBlank(businesses)){
            return new Message("200",true,businesses);
        }else{
            return new Message("10001",false,"");
        }
    }

    /**
     * 现场登记查询材料样本
     * @param id
     * @param name
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/downloadFile")
    @ResponseBody
    public byte[] downloadxzFile(String id, String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(null == name){
            //查询文件信息
            String cxapiName= "getFileInfo";
            String cxdata="id="+id;
            String cxrawData= ApiAccess.StringApipost(cxapiName,cxdata);
            net.sf.json.JSONObject json2 = net.sf.json.JSONObject.fromObject(cxrawData);
            if(json2.containsKey("data")){
                String data2=json2.getString("data");
                json2 = net.sf.json.JSONObject.fromObject(data2);
                name=json2.getString("name");
            }else{
                name="universal.txt";
            }
        }
        //文件下载
        String apiName= "downloadFile";
        String data="url="+id;
        String rawData= ApiAccess.StringApipost(apiName,data);
        net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(rawData);
        String xzdata=json.getString("data");
        System.out.print(rawData);
        byte[] bytes = Dase64Util.base64ToImgByteArray(xzdata);
//        response.setContentType("multipart/form-data;charset=UTF-8");
//        response.setHeader("Content-Disposition", "`attachment`;fileName="+java.net.URLEncoder.encode(name, "UTF-8"));
//        OutputStream stream = response.getOutputStream();
        return bytes;
    }

    /**
     * 跳转上传材料通用页面
     * @param request
     * @return
     */
    @RequestMapping("/toMaterialFile.do")
    public ModelAndView toMaterialFile(HttpServletRequest request){

        ModelAndView mv = new ModelAndView("register/materialUpload");

        mv.addObject("materialId", request.getParameter("materialId"));

        mv.addObject("materialName", request.getParameter("materialName"));

        mv.addObject("itemId", request.getParameter("itemId"));

        mv.addObject("materialCode", request.getParameter("materialCode"));

        mv.addObject("businessCode", request.getParameter("businessCode"));

        return mv;
    }

    /**
     * 跳转上传材料通用页面
     * @param request
     * @return
     */
    @RequestMapping("/getMaterialFile.do")
    @ResponseBody
    public TableData getMaterialFile(HttpServletRequest request){

        ModelAndView mv = new ModelAndView("register/materialUpload");

        String materialCode = request.getParameter("materialId");

        String businessCode = request.getParameter("businessCode");

        PageInfo<Files> list = registerService.queryMaterialFile(1,1000,materialCode,businessCode,"create_date desc");

        TableData td = new TableData("0","",list.getTotal(),list.getList());

        return td;
    }

    /**
     * 事项材料上传
     * fileCode //一体化材料id
     * fileName //材料名称
     * itemId //材料涉及的一体化事项ID
     * @return
     * @throws IOException
     */
    @PostMapping("/materialUpload.do")
    @ResponseBody
    public Message  materialUpload(HttpServletRequest request, HttpSession session) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        Files files = new Files();
        long  startTime=System.currentTimeMillis();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        String fileCode = request.getParameter("fileCode");
        String itemId = request.getParameter("itemId");
        String fileName =request.getParameter("fileName");
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request)){
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();


            List<Items> item = new ArrayList<>();

            if(StringUtils.isNotBlank(itemId)){

                String[] itemArr = itemId.split(",");

                List<String> itemList = new ArrayList<>();

                for(int i=0;i<itemArr.length;i++){

                    itemList.add(itemArr[i]);
                }

                item = registerService.queryItemsByCode(itemList);
            }

            IdWorker id = new IdWorker();

            while(iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                String originalFileName = System.currentTimeMillis()+"_"+fileName+"."+FilenameUtils.getExtension(file.getOriginalFilename());
                System.err.print("文件名-------"+originalFileName);

                if (null != file) {
                    StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
                    String businessCode = multiRequest.getParameter("businessCode");
                    session.setAttribute("businessCode", businessCode);
//                    fileCode = id.nextId()+"";

                    for(int i=0;i<item.size();i++){
                        Files files1 = new Files();
                        files.setFileName(fileName);
                        files.setOriginalFileName(originalFileName);
                        String fileUrl = storePath.getFullPath();
                        files.setFileUrl(fileUrl);
                        files.setBusinessCode(businessCode);
                        files.setItemId(item.get(i).getId());
                        files.setFileCode(fileCode);
                        fileService.save(files);
                    }


                }

            }
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return new Message("200",true,JSON.parse("{'fileCode':'"+fileCode+"'}"));
    }


    @ResponseBody
    @RequestMapping("/deleteMaterial")
    public Message deleteMaterial(HttpServletRequest request) {
//        Map<String, Object> maps = JsonKit.toMap(jsonStr);
//        Map<String, Object> map = new HashMap<>();
//        String businessCode = session.getAttribute("businessCode").toString();
//        Files files = fileService.findAllById(Integer.parseInt(maps.get("id").toString()));
//        fileService.delete(Integer.parseInt(maps.get("id").toString()),businessCode);
//        StorePath storePath = StorePath.parseFromUrl(files.getFileUrl());
//        storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
//        map.put("status", "success");
//        map.put("message", "删除成功");
//        map.put("code", 1);

        String materialCode = request.getParameter("materialCode");

        String businessCode = request.getParameter("businessCode");

        if(!StringUtils.isNotBlank(materialCode)){

            return new Message("参数错误","10001",false,"");
        }

        int size = registerService.deleteMaterial(materialCode,businessCode);

        if(size>0){
            return new Message("200",true,"");
        }else{
            return new Message("删除失败，请重试","10004",false,"");
        }



    }



}
