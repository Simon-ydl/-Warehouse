package com.yjs.admin.controller.file;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;

import com.yjs.bean.application.Applicat;
import com.yjs.bean.datum.Datum;
import com.yjs.bean.yjs.FileVo;
import com.yjs.dataSource.DataSourceSwitch;
import com.yjs.service.applicat.ApplicatService;
import com.yjs.service.datum.service.DatumService;
import com.yjs.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/file")
@EnableScheduling
public class FileController extends HttpServlet {
	
	@Autowired
	FileService fileService;
	@Autowired
    DatumService datumService;
	@Autowired
    ApplicatService applicatService;

    //项目启动5分钟开始执行定时任务,每隔10分钟执行一次
    @Scheduled(initialDelay = 1000 * 60 * 5, fixedDelay = 1000 * 60 * 10)//定时任务设置启动多久执行
	public void findAndUpdate (){
		List<FileVo> fileVoList = fileService.findByItemId();
		List<Datum> datumList = datumService.findAllByItemId();
        List<Applicat> applicatList = new ArrayList<>();
		List<Datum> datumList1 = new ArrayList<>();

        for(Datum datum : datumList){
            if(datum.getState() != "0"){
                datumList1.add(datum);
                Applicat applicat = applicatService.findAllByBusinessCode(datum.getBusinessCode());
                applicatList.add(applicat);
            }
        }
        DataSourceSwitch.set("ga_dba");
        for(FileVo fileVo : fileVoList){
            FileVo fileVo1 = fileService.findByFileId(fileVo.getId());
            if(fileVo1 == null){
                fileService.saveFileVo(fileVo);
            }
        }

        for(Applicat applicat : applicatList){
            Applicat applicat1 = applicatService.findAllByBusinessCode(applicat.getBusinessCode());
            if(applicat1 == null){
                applicatService.save(applicat);
            }
        }

        for (Datum datum : datumList1){
            Datum datum1 = datumService.findAllByBusinessCodeAndItemsId(datum.getBusinessCode(), datum.getItemsId());
            if(datum1 == null){
                datumService.save(datum);
            }
        }
        DataSourceSwitch.set(null);
	}
}
