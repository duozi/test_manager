package com.xn.manage.httputils;
/**
* @description 征信公司错误码,格式为模块id+0000
*/

public enum ReturnCodeEnum {

      SUCCESS(0, "成功"),
      PARAM_NULL(2390001, "缺少必填参数"),
      TOKEN_INVALID(2390002, "权限校验失败"),
      TRANS_ERROR(2390003, "传输层错误"),
      HUAZHENGCREDIT_ERROR(2390004, "业务错误"),
      HUAZHENGCREDIT_ERROR_SYS(2390005, "系统错误"),
      HTTP_ERROR(2390006, "HTTP层错误");

      private Integer returnCode;
      private String returnMsg;
      private ReturnCodeEnum(Integer returnCode, String returnMsg) {
            this.returnCode = returnCode;
            this.returnMsg = returnMsg;
      }
      public Integer getReturnCode() {
            return returnCode;
      }
      public String getReturnMsg() {
            return returnMsg;
      }
      public static ReturnCodeEnum get(Integer returnCode) {
           ReturnCodeEnum arr[] = ReturnCodeEnum.values();
           for(ReturnCodeEnum tmp: arr) {
               if(tmp.getReturnCode() == returnCode) {
                   return tmp;
               }
           }
           return null; 
      }
}

