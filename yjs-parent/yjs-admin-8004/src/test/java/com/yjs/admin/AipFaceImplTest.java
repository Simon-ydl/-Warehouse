package com.yjs.admin;

import com.yjs.dataSource.DataSourceSwitch;
import com.yjs.bean.datum.Datums;
import com.yjs.bean.test.Model;
import com.yjs.dao.mapper.IDatumMapper;
import com.yjs.service.activiti.ActivitiService;
import com.yjs.service.test.service.ITestApp;
import com.yjs.utils.ApiAccess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class AipFaceImplTest {

	@Resource
	private ITestApp testApp;

	@Autowired
	ActivitiService activitiService;

	@Test
	public void contextLoads(){
		Model t = new Model();
		t.setId(1);
		t.setName("12313");
		t.setSex("1");
		//aipFaceApp.detect();
		//aipFaceApp.personVerify();
	}

	@Test
	public void test1(){
		Model t = new Model();
		t.setId(2121);
		t.setName("111");
		t.setSex("1");
		DataSourceSwitch.set("ga_dba");
//		testApp.query(t);
		testApp.insert(t);
		DataSourceSwitch.set(null);
		testApp.query(t);
		//aipFaceApp.detect();
		//aipFaceApp.personVerify();
	}

	@Test
	public void Test08() throws Exception {
		/**
		 * 传入接口名称和key value的数据结构
		 */
		String getQxByItemCode = ApiAccess.StringApipost("getAllOnethingList", "isActive=1");
		System.out.println(getQxByItemCode);

	}

	@Test
	public void Test09() throws Exception {
		/**
		 * 获取一件事事项情形
		 */
		String getQxByItemCode = ApiAccess.StringApipost("getQxByItemCode", "itemId=93e8a79b6dbf6410016dc2edaaaf0055");
		System.out.println(getQxByItemCode);

	}

	@Test
	public void Test06() throws Exception {
		/**
		 * 获取一件事事项材料
		 */
		String getQxByItemCode = ApiAccess.StringApipost("getClqdByItemCode", "itemId=ff8080816cb6dcad016cb72e02a104c6");
		System.out.println(getQxByItemCode);

	}

	@Test
	public void Test05() throws Exception {
		/**
		 * 获取一件事事项材料
		 */
		String getQxByItemCode = ApiAccess.StringApipost("getClqdByItemCode", "itemId=93e8a79b6dbf6410016dc2edaaaf0055");
		System.out.println(getQxByItemCode);

	}


	@Test
	public void Test03() throws Exception {
		/**
		 * 获取一件事申报办理前置条件情形
		 */
		String getQxByItemCode = ApiAccess.StringApipost("getOnethingItem", "oneThingId=93e8a79b6dbf6410016dc2edaaaf0055");
		System.out.println(getQxByItemCode);

	}

	@Test
	public void Test02() throws Exception {
		/**
		 * 获取一件事申报办理前置条件情形
		 */
		String getQxByItemCode = ApiAccess.StringApipost("listLastSituationItemInfo", "itemId=11441900007331881C3442106100010");
		System.out.println(getQxByItemCode);

	}
	@Autowired
	private IDatumMapper datumMapper;
	@Test
	public void getSearchResultByDepartmentIdAndState(){
		List<Datums> datumsList = (List<Datums>)datumMapper.getSearchResultByDepartmentIdAndState("2",6,"%人%",0,5);
		if(datumsList.size() > 0 && datumsList != null){
			for (Datums datums : datumsList) {
				System.out.println(datums);
			}
		}
	}


}