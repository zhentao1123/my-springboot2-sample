package com.example.demo.web.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TestService;
import com.example.demo.web.BaseController;
import com.example.demo.web.request.FindTestByNameRequest;
import com.example.demo.web.request.RemoveTestByNameRequest;
import com.example.demo.web.request.SaveTestRequest;
import com.example.demo.web.request.to.TestVO;
import com.example.demo.web.response.CommResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/test/")
@Api(hidden=false, tags="Test接口 V1")
public class TestController extends BaseController{

	@Autowired
	TestService testService;
	
	@ApiOperation(value="新增", notes="新增")
	@ApiImplicitParam(name = "request", value = "请求对象", required = true, dataType = "SaveTestRequest")
	@RequestMapping(value="saveTest", method=RequestMethod.POST)
	@ResponseBody
	public CommResponse<TestVO> saveTest(@RequestBody SaveTestRequest request) throws Exception{
		TestVO result= testService.saveTest(request.getData());
		return CommResponse.getInstances4Succeed(result);
	}
	
	@ApiOperation(value="根据姓名查找", notes="根据姓名查找")
	@ApiImplicitParam(name = "request", value = "请求对象", required = true, dataType = "FindTestByNameRequest")
	@RequestMapping(value="findTestByName", method=RequestMethod.POST)
	@ResponseBody
	public CommResponse<TestVO> findTestByName(@RequestBody FindTestByNameRequest request) throws Exception{
		TestVO result= testService.findTestByName(request.getData());
		return CommResponse.getInstances4Succeed(result);
	}
	
	@ApiOperation(value="根据姓名删除", notes="根据姓名删除")
	@ApiImplicitParam(name = "request", value = "请求对象", required = true, dataType = "RemoveTestByNameRequest")
	@RequestMapping(value="removeTestByName", method=RequestMethod.POST)
	@ResponseBody
	public CommResponse<Boolean> removeTestByName(@RequestBody RemoveTestByNameRequest request) throws Exception{
		boolean result= testService.removeTestByName(request.getData());
		return CommResponse.getInstances4Succeed(result);
	}
}
