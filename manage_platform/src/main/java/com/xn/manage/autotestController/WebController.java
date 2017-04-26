package com.xn.manage.autotestController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xn.common.base.CommonResult;
import com.xn.manage.utils.FileUpload;

@Controller
@RequestMapping("/*")
public class WebController {
	
	@RequestMapping(value="/decorators/{path}", method = RequestMethod.GET)
	public String getDecoratorsPage(@PathVariable String  path ) {
		return "decorators/" + path;
	}

	/**
	 * 上传图片
	 *
	 * @param files
	 * @param request
	 * @return
	 */
	@RequestMapping("/autotest/{folderName}/uploadJar")
	@ResponseBody
	public CommonResult filesUpload(@RequestParam("file") MultipartFile[] files, @PathVariable String  folderName,
									HttpServletRequest request) {
		return FileUpload.upload(files,folderName,request);
	}
}
