package com.xn.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class PageUtil {
    
    public static PageInfo getPageInfo(HttpServletRequest request) {
        String _iDisplayStart = request.getParameter("iDisplayStart");
        //当前页的第一条数据的偏移量
        int iDisplayStart = 0;
        if (StringUtils.isNotBlank(_iDisplayStart) && NumberUtils.isNumber(_iDisplayStart)) {
            iDisplayStart = Integer.valueOf(_iDisplayStart);
        }
        
        String _iDisplayLength = request.getParameter("iDisplayLength");
        //每页显示条数
        int iDisplayLength = 0;
        if (StringUtils.isNotBlank(_iDisplayLength) && NumberUtils.isNumber(_iDisplayLength)) {
            iDisplayLength = Integer.valueOf(_iDisplayLength);
        }
        
        int pageSize = iDisplayLength;
        int currentPage = 0;
        if(iDisplayStart < iDisplayLength)
            currentPage = 1;
        else 
            currentPage = iDisplayStart / iDisplayLength + 1;
        
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPagination(true);
        pageInfo.setPageSize(pageSize);
        pageInfo.setCurrentPage(currentPage);
        return pageInfo;
    }
    
    public static PageInfo getCustomPageInfo(HttpServletRequest request) {
        String page_size = request.getParameter("page_size");
        int pageSize = 10;
        if (StringUtils.isNotBlank(page_size) && NumberUtils.isNumber(page_size)) {
            pageSize = Integer.valueOf(pageSize);
        }
        
        String page_no = request.getParameter("page_no");
        int curPage = 1;
        if (StringUtils.isNotBlank(page_no) && NumberUtils.isNumber(page_no)) {
            curPage = Integer.valueOf(page_no);
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPagination(true);
        pageInfo.setPageSize(pageSize);
        pageInfo.setCurrentPage(curPage);
        return pageInfo;
    }
    
    public static Map<String, Object> getPageResult(Object aaData, PageInfo pageInfo) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("aaData", aaData);
        result.put("iTotalRecords", pageInfo.getTotalRows());
        result.put("iTotalDisplayRecords", pageInfo.getTotalRows());
        return result;
    }

}
