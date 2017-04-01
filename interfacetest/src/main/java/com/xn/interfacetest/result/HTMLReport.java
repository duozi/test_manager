package com.xn.interfacetest.result;/**
 * Created by xn056839 on 2016/9/12.
 */

import com.xn.interfacetest.mail.JavaMailWithAttachment;
import com.xn.interfacetest.response.Assert;
import com.xn.interfacetest.service.GetPara;
import com.xn.interfacetest.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class HTMLReport {
    private static final Logger logger = LoggerFactory.getLogger(HTMLReport.class);
    public static String STYLESHEET_TMPL = "<style type=\"text/css\" media=\"screen\">\n" +
            "body        { font-family: verdana, arial, helvetica, sans-serif; font-size: 80%; }\n" +
            "table       { font-size: 100%; }\n" +
            "pre         { }\n" +
            "\n" +
            "/* -- heading ---------------------------------------------------------------------- */\n" +
            "h1 {\n" +
            "\tfont-size: 16pt;\n" +
            "\tcolor: gray;\n" +
            "}\n" +
            ".heading {\n" +
            "    margin-top: 0ex;\n" +
            "    margin-bottom: 1ex;\n" +
            "}\n" +
            "\n" +
            ".heading .attribute {\n" +
            "    margin-top: 1ex;\n" +
            "    margin-bottom: 0;\n" +
            "}\n" +
            "\n" +
            ".heading .description {\n" +
            "    margin-top: 4ex;\n" +
            "    margin-bottom: 6ex;\n" +
            "}\n" +
            "\n" +
            "/* -- css div popup ------------------------------------------------------------------------ */\n" +
            "a.popup_link {\n" +
            "}\n" +
            "\n" +
            "a.popup_link:hover {\n" +
            "    color: red;\n" +
            "}\n" +
            "\n" +
            ".popup_link{\n" +
            "    margin-left: 50%;\n" +
            "}\n" +
            ".popup_window {\n" +
            "    display: none;\n" +
            "    position: relative;\n" +
            "    left: 0px;\n" +
            "    top: 0px;\n" +
            "    /*border: solid #627173 1px; */\n" +
            "    padding: 10px;\n" +
            "    background-color: #E6E6D6;\n" +
            "    font-family: \"Lucida Console\", \"Courier New\", Courier, monospace;\n" +
            "    text-align: left;\n" +
            "    font-size: 10pt;\n" +
            "    width: 98%;\n" +
            "}\n" +
            "\n" +
            "/* -- report ------------------------------------------------------------------------ */\n" +
            "#show_detail_line {\n" +
            "    margin-top: 3ex;\n" +
            "    margin-bottom: 1ex;\n" +
            "}\n" +
            "#result_table {\n" +
            "    width: 90%;\n" +
            "    border-collapse: collapse;\n" +
            "    border: 1px solid #777;\n" +
            "}\n" +
            "#header_row {\n" +
            "    font-weight: bold;\n" +
            "    color: white;\n" +
            "    background-color: #777;\n" +
            "}\n" +
            "#result_table td {\n" +
            "    border: 1px solid #777;\n" +
            "    padding: 2px;\n" +
            "}\n" +
            "#total_row  { font-weight: bold; }\n" +
            ".passClass  { background-color: #6c6; }\n" +
            ".failClass  { background-color: #c60; }\n" +
            ".errorClass { background-color: #c00; }\n" +
            ".passCase   { color: #6c6; }\n" +
            ".failCase   { color: #c60; font-weight: bold; }\n" +
            ".errorCase  { color: #c00; font-weight: bold; }\n" +
            ".hiddenRow  { display: none; }\n" +
            ".testcase   { margin-left: 2em; }\n" +
            ".testrange { margin-left: 0.4em; }\n" +
            "\n" +
            "/* -- ending ---------------------------------------------------------------------- */\n" +
            "#ending {\n" +
            "}\n" +
            "\n" +
            "</style>";
    public static String TEST_TMPL = "<div class='heading'>\n" +
            "<h1>%(title)s</h1>\n" +
            "</div></br>";
    public static String HEADING_TMPL = "<div class='heading'>\n" +
            "%(parameters)s\n" +
            "</div>";
    public static String HEADING_ATTRIBUTE_TMPL = "<p class='attribute'><strong>%(name)s:</strong> %(value)s</p>";
    public static String REPORT_TMPL = "<p id='show_detail_line'>Show\n" +
            "<a href='javascript:showCase(0)'>Summary</a>\n" +
            "<a href='javascript:showCase(1)'>Fail&amp;Error</a>\n" +
            "<a href='javascript:showCase(2)'>All</a>\n" +
            "</p>\n" +
            "<table id='result_table'>\n" +
            "<colgroup>\n" +
            "<col align='left' />\n" +
            "<col align='right' />\n" +
            "<col align='right' />\n" +
            "<col align='right' />\n" +
            "<col align='right' />\n" +
            "<col align='right' />\n" +
            "</colgroup>\n" +
            "<tr id='header_row'>\n" +
            "    <td  width=\"40%\">Test case</td>\n" +
            "    <td width=\"12%\">Count</td>\n" +
            "    <td width=\"12%\">Pass</td>\n" +
            "    <td width=\"12%\">Fail</td>\n" +
            "    <td width=\"12%\">Error</td>\n" +
            "    <td width=\"12%\">View</td>\n" +
            "</tr>\n" +
            "%(test_list)s\n" +
            "<tr id='total_row'>\n" +
            "    <td  >Total</td>\n" +
            "    <td>%(count)s</td>\n" +
            "    <td>%(pass)s</td>\n" +
            "    <td>%(fail)s</td>\n" +
            "    <td>%(error)s</td>\n" +
            "    <td>&nbsp;</td>\n" +
            "</tr>\n" +
            "</table>";
    public static String REPORT_CLASS_TMPL = "<tr class='%(style)s'>\n" +
            "    <td >%(desc)s</td>\n" +
            "    <td>%(count)s</td>\n" +
            "    <td>%(pass)s</td>\n" +
            "    <td>%(fail)s</td>\n" +
            "    <td>%(error)s</td>\n" +
            "    <td><a href=\"javascript:showClassDetail('%(cid)s',%(count)s)\">Detail</a></td>\n" +
            "</tr>\n";
    //    public static String METHOD_START = "<tr id='%(tid)s' class='%(Class)s'>\n" +
//            "    <td class='%(methodnamestyle)s' rowspan=\"%(methodcount)s\">" +
//            "       <div class='testcase'>%(methodname)s</div>" +
//            "    </td>" +
//            "    <td class='%(style)s' >" +
//            "       <div class='testcase'>%(desc)s</div>" +
//            "    </td>\n" +
//            "    <td colspan='5' >\n" +
//            "    <!--css div popup start-->\n" +
//            "    <a class=\"popup_link\" onfocus='this.blur();' href=\"javascript:showTestDetail('div_%(tid)s')\" >\n" +
//            "        %(status)s</a>\n" +
//            "\n" +
//            "    <div id='div_%(tid)s' class=\"popup_window\">\n" +
//            "        <div style='text-align: right; color:red;cursor:pointer'>\n" +
//            "        <a onfocus='this.blur();' onclick=\"document.getElementById('div_%(tid)s').style.display = 'none' \" >\n" +
//            "           [x]</a>\n" +
//            "        </div>\n" +
//            "        <pre style=\"white-space:pre-wrap;word-wrap:break-word\">\n" +
//            "%(script)s\n" +
//            "        </pre>\n" +
//            "    </div>\n" +
//            "    <!--css div popup end-->\n" +
//            "\n" +
//            "    </td>\n" +
//            "</tr>";
    public static String REPORT_TEST_WITH_OUTPUT_TMPL = "<tr id='%(tid)s' class='%(Class)s'>\n" +
            "    <td class='%(style)s'><div class='testcase'>%(desc)s</div></td>\n" +
            "    <td colspan='5' >\n" +
            "\n" +
            "    <!--css div popup start-->\n" +
            "    <a class=\"popup_link\" onfocus='this.blur();' href=\"javascript:showTestDetail('div_%(tid)s')\" >\n" +
            "        %(status)s</a>\n" +
            "\n" +
            "    <div id='div_%(tid)s' class=\"popup_window\">\n" +
            "        <div style='text-align: right; color:red;cursor:pointer'>\n" +
            "        <a onfocus='this.blur();' onclick=\"document.getElementById('div_%(tid)s').style.display = 'none' \" >\n" +
            "           [x]</a>\n" +
            "        </div>\n" +
            "        <pre style=\"white-space:pre-wrap;word-wrap:break-word\">\n" +
            "%(script)s\n" +
            "        </pre>\n" +
            "    </div>\n" +
            "    <!--css div popup end-->\n" +
            "\n" +
            "    </td>\n" +
            "</tr>";
    public static String REPORT_TEST_NO_OUTPUT_TMPL = "<tr id='%(tid)s' class='%(Class)s'>\n" +
            "    <td class='%(style)s'><div class='testcase'>%(desc)s</div></td>\n" +
            "    <td colspan='5' >%(status)s</td>\n" +
            "</tr>";
    public static String ERROR_STRING = "<pre style='white-space:pre-wrap;word-wrap:break-word;color: red'>%(content)s</pre>";
    public static String REPORT_TEST_OUTPUT_TMPL = "%(output)s";
    public static String ENDING_TMPL = "<div id='ending'>&nbsp;</div>";
    public static String Title = "dubbo接口测试报告";
    private static String HTML_TMPL = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "    <title>%(title)s</title>\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n" +
            "    %(stylesheet)s\n" +
            "</head>\n" +
            "<body>\n" +
            "<script language=\"javascript\" type=\"text/javascript\"><!--\n" +
            "output_list = Array();\n" +
            "\n" +
            "/* level - 0:Summary; 1:Failed; 2:All */\n" +
            "function showCase(level) {\n" +
            "    trs = document.getElementsByTagName(\"tr\");\n" +
            "    for (var i = 0; i < trs.length; i++) {\n" +
            "        tr = trs[i];\n" +
            "        id = tr.id;\n" +
            "        if (id.substr(0,2) == 'ft') {\n" +
            "            if (level < 1) {\n" +
            "                tr.className = 'hiddenRow';\n" +
            "            }\n" +
            "            else {\n" +
            "                tr.className = '';\n" +
            "            }\n" +
            "        }\n" +
            "        if (id.substr(0,2) == 'et') {\n" +
            "            if (level < 1) {\n" +
            "                tr.className = 'hiddenRow';\n" +
            "            }\n" +
            "            else {\n" +
            "                tr.className = '';\n" +
            "            }\n" +
            "        }" +
            "        if (id.substr(0,2) == 'pt') {\n" +
            "            if (level > 1) {\n" +
            "                tr.className = '';\n" +
            "            }\n" +
            "            else {\n" +
            "                tr.className = 'hiddenRow';\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "function showClassDetail(cid, count) {\n" +
            "    var id_list = Array(count);\n" +
            "    var toHide = 1;\n" +
            "    for (var i = 0; i < count; i++) {\n" +
            "        tid0 = 't' + cid.substr(1) + '.' + (i + 1);\n" +
            "        tid = 'f' + tid0;\n" +
            "        tr = document.getElementById(tid);\n" +
            "        if (!tr) {\n" +
            "            tid = 'e' + tid0;\n" +
            "            tr = document.getElementById(tid);\n" +
            "            if (!tr) {\n" +
            "                tid = 'p' + tid0;\n" +
            "                tr = document.getElementById(tid);\n" +
            "            }\n" +
            "        }" +
            "        id_list[i] = tid;\n" +
            "        if (tr.className) {\n" +
            "            toHide = 0;\n" +
            "        }\n" +
            "    }\n" +
            "    for (var i = 0; i < count; i++) {\n" +
            "        tid = id_list[i];\n" +
            "        if (toHide) {\n" +
            "            document.getElementById('div_'+tid).style.display = 'none'\n" +
            "            document.getElementById(tid).className = 'hiddenRow';\n" +
            "        }\n" +
            "        else {\n" +
            "            document.getElementById(tid).className = '';\n" +
            "        }\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "function showTestDetail(div_id){\n" +
            "    var details_div = document.getElementById(div_id)\n" +
            "    var displayState = details_div.style.display\n" +
            "    // alert(displayState)\n" +
            "    if (displayState != 'block' ) {\n" +
            "        displayState = 'block'\n" +
            "        details_div.style.display = 'block'\n" +
            "    }\n" +
            "    else {\n" +
            "        details_div.style.display = 'none'\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "function html_escape(s) {\n" +
            "    s = s.replace(/&/g,'&amp;');\n" +
            "    s = s.replace(/</g,'&lt;');\n" +
            "    s = s.replace(/>/g,'&gt;');\n" +
            "    return s;\n" +
            "}\n" +
            "\n" +
            "/* obsoleted by detail in <div>\n" +
            "function showOutput(id, name) {\n" +
            "    var w = window.open(\"\", //url\n" +
            "                    name,\n" +
            "                    \"resizable,scrollbars,status,width=800,height=450\");\n" +
            "    d = w.document;\n" +
            "    d.write(\"<pre>\");\n" +
            "    d.write(html_escape(output_list[id]));\n" +
            "    d.write(\"\\n\");\n" +
            "    d.write(\"<a href='javascript:window.close()'>close</a>\\n\");\n" +
            "    d.write(\"</pre>\\n\");\n" +
            "    d.close();\n" +
            "}\n" +
            "*/\n" +
            "--></script>\n" +
            "\n" +
            "%(heading)s\n" +
            "%(report)s\n" +
            "%(ending)s\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    public static void generateResultReport(String path, String sendMailTo) {
        Map reportAttrs = getReportAttributes();
        String styleSheet = generateStyleSheet();
        String heading = generateHeading(reportAttrs);
        String report = generateReport(reportAttrs);
        String ending = generateEnding();
        String result = HTML_TMPL.replace("%(title)s", Title).replace("%(stylesheet)s", styleSheet).replace("%(heading)s", heading).replace("%(report)s", report).replace("%(ending)s", ending);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        String timeStmp = format.format(new Date());
        String remark = new GetPara().getRemark();
        String reportName = path + "report/report_" + timeStmp +"_"+remark+".html";
        FileUtil.fileWrite(reportName, result);
        logger.info("-----------------"+reportName);
        JavaMailWithAttachment se = new JavaMailWithAttachment(true);


        se.doSendHtmlEmail("接口测试报告", "接口测试报告请下载附件", sendMailTo, reportName);
    }

    private static String generateEnding() {
        return ENDING_TMPL;
    }

    private static String generateReport(Map<String, String> att) {

        ReportResult reportResult = ReportResult.getReportResult();
        ArrayList<Assert> result = reportResult.getAssertList();

        LinkedHashMap<String, LinkedHashMap<String, ArrayList<Assert>>> interfaceMap = new LinkedHashMap();
        for (Assert ass : result) {
            String interfaceName = ass.getInterfaceName();
            String methodName = ass.getMethodName();
            if (interfaceMap.containsKey(interfaceName)) {
                LinkedHashMap<String, ArrayList<Assert>> methodMap = interfaceMap.get(interfaceName);
                if (methodMap.containsKey(methodName)) {
                    ArrayList<Assert> caseList = methodMap.get(methodName);
                    caseList.add(ass);
                } else {
                    ArrayList<Assert> caseList = new ArrayList();
                    caseList.add(ass);
                    methodMap.put(methodName, caseList);
                }
            } else {
                ArrayList<Assert> caseList = new ArrayList();
                caseList.add(ass);
                LinkedHashMap<String, ArrayList<Assert>> methodMap = new LinkedHashMap();
                methodMap.put(methodName, caseList);
                interfaceMap.put(interfaceName, methodMap);
            }
        }


        ArrayList<String> results = new ArrayList();
        int methodF = 0;
        int methodP = 0;
        int methodE = 0;
        int methodT = 0;
        int i = 1;
        for (String interfaceName : interfaceMap.keySet()) {
            methodF = 0;
            methodP = 0;
            methodE = 0;
            methodT = 0;
            LinkedHashMap<String, ArrayList<Assert>> methodMap = interfaceMap.get(interfaceName);
            for (String methodName : methodMap.keySet()) {
                methodT += methodMap.get(methodName).size();
                for (Assert ass : methodMap.get(methodName)) {
                    if (ass.getResult() != null && ass.getResult().equals("error")) {
                        methodE++;

                    } else if (ass.getResult() != null && ass.getResult().equals("failed")) {
                        methodF++;
                    } else {
                        methodP++;
                    }
                }
            }


            String line = REPORT_CLASS_TMPL.replace("%(desc)s", interfaceName).replace("%(count)s", String.valueOf(methodT)).replace("%(pass)s", String.valueOf(methodP)).replace("%(fail)s", String.valueOf(methodF)).replace("%(error)s", String.valueOf(methodE)).replace("%(cid)s", "c" + i);
            if (methodE > 0) {
                line = line.replace("%(style)s", "errorClass");
            } else if (methodF > 0) {
                line = line.replace("%(style)s", "failClass");
            } else {
                line = line.replace("%(style)s", "passClass");
            }
            results.add(line);
            generateReportTest(methodMap, results, i);
            i++;
        }


        String reportString = REPORT_TMPL.replace("%(test_list)s", StringUtils.join(results, "")).replace("%(count)s", String.valueOf(att.get("total"))).replace("%(pass)s", String.valueOf(att.get("pass"))).replace("%(fail)s", String.valueOf(att.get("fail"))).replace("%(error)s", String.valueOf(att.get("error")));
        return reportString;
    }

    private static void generateReportTest(LinkedHashMap<String, ArrayList<Assert>> methodMap, ArrayList<String> results, int i) {
        String tmpl = REPORT_TEST_WITH_OUTPUT_TMPL;
//        String tmpl_start = METHOD_START;
        String testString = "";
        int j = 0;
        for (String methodName : methodMap.keySet()) {
//            int k = 0;
            ArrayList<Assert> caseList = methodMap.get(methodName);
//            int f = 0;
//            int e = 0;
//            for (Assert ass : caseList) {
//                if (ass.getResult() != null && ass.getResult().equals("error")) {
//                    e++;
//                    break;
//
//                } else if (ass.getResult() != null && ass.getResult().equals("failed")) {
//                    f++;
//                    break;
//                }
//            }
            String tid = "";
            String desc = "";
            String script = "";
            for (Assert ass : caseList) {
//                k++;
                j++;
//                if (k == 1) {
//                    testString = tmpl_start;
//                    if (e > 0) {
//                        testString = testString.replace("%(methodcount)s", String.valueOf(caseList.size())).replace("%(methodname)s", methodName).replace("%(methodnamestyle)s", "errorCase");
//                    } else if (f > 0) {
//                        testString = testString.replace("%(methodcount)s", String.valueOf(caseList.size())).replace("%(methodname)s", methodName).replace("%(methodnamestyle)s", "failCase");
//                    } else {
//                        testString = testString.replace("%(methodcount)s", String.valueOf(caseList.size())).replace("%(methodname)s", methodName).replace("%(methodnamestyle)s", "none");
//                    }
//                } else {
                testString = tmpl;
//                }
                //分别处理Dubbo接口和http接口
                if (ass.getCaseName().startsWith("#")) {
                    desc = ass.getMethodName();
                } else {
                    desc = ass.getMethodName() + "/" + ass.getCaseName();
                }
                if (ass.getResult() != null && ass.getResult().equals("error")) {
                    tid = "et" + i + "." + j;
                    testString = testString.replace("%(style)s", "errorCase").replace("%(status)s", "error");

                } else if (ass.getResult() != null && ass.getResult().equals("failed")) {
                    tid = "ft" + i + "." + j;
                    testString = testString.replace("%(style)s", "failCase").replace("%(status)s", "fail");
                } else {
                    tid = "pt" + i + "." + j;
                    testString = testString.replace("%(style)s", "none").replace("%(status)s", "pass");
                }

                String request = "【请求参数】：\n" + dealString(ass.getRequest());
                String response = "【返回结果】：\n" + dealString(ass.getResponse().toString());
                String assertError = "";
                String exception = "";
                if (tid.startsWith("f")) {
                    assertError = "【AssertError】\nassertKey:" + ass.getDiff().getAssertKey() + "\nexpectValue:" + ass.getDiff().getExpect() + "\nexactValeu:" + ass.getDiff().getExact() + "\n";
                    assertError = ERROR_STRING.replace("%(content)s", assertError);
                    response = request + "\n" + response + "\n" + assertError;
                } else if (tid.startsWith("e")) {
//                    System.out.println("---------" + ass.getResponse().getException());
                    exception = "【Exception】\n" + ass.getResponse().getException();
                    exception = ERROR_STRING.replace("%(content)s", exception);
                    response = request + "\n" + response + "\n" + exception;
                } else {
                    response = request + "\n" + response;
                }


                script = REPORT_TEST_OUTPUT_TMPL.replace("%(output)s", response);
                testString = testString.replace("%(tid)s", tid).replace("%(Class)s", "none").replace("%(desc)s", desc).replace("%(script)s", script);

                results.add(testString);
            }
        }

    }

    private static String generateHeading(Map<String, String> att) {
        HEADING_TMPL = TEST_TMPL + HEADING_TMPL;
        ArrayList<String> lines = new ArrayList();
        for (String key : att.keySet()) {
            String line = HEADING_ATTRIBUTE_TMPL.replace("%(name)s", key).replace("%(value)s", att.get(key));
            lines.add(line);
        }
        String heading = HEADING_TMPL.replace("%(title)s", Title).replace("%(parameters)s", StringUtils.join(lines, ""));
        return heading;
    }

    private static String generateStyleSheet() {
        return STYLESHEET_TMPL;

    }

    private static String dealString(String input) {
        if (input == null) {
            return null;
        }

        if (input.contains("&")) {
            input = input.replace("&", "&amp;");
        } else {
            if (input.contains(">")) {
                input = input.replace(">", "&gt;");

            }
            if (input.contains("<")) {
                input = input.replace("<", "&lt;");
            }
        }
        return input;
    }

    private static LinkedHashMap<String, String> getReportAttributes() {
        ReportResult reportResult = ReportResult.getReportResult();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String startTime = format.format(reportResult.getStartTime());
        String duration = reportResult.getTimeLong();
        int total = reportResult.getTotal();
        int failed = reportResult.getFailed();
        int error = reportResult.getError();
        int pass = total - error - failed;
        LinkedHashMap<String, String> result = new LinkedHashMap();
        result.put("startTime", startTime);
        result.put("duration", duration + "ms");
        result.put("total", String.valueOf(total));
        result.put("pass", String.valueOf(pass));
        result.put("fail", String.valueOf(failed));
        result.put("error", String.valueOf(error));
        return result;

    }
}

//class MyComparator implements Comparator {
//    @Override
//    public int compare(Object o1, Object o2) {
//
//        Assert assertItem1 = (Assert) o1;
//
//        Assert assertItem2 = (Assert) o2;
//        int i = assertItem1.getInterfaceName().compareTo(assertItem2.getInterfaceName());
//        if (i == 0) {
//            int m = assertItem1.getMethodName().compareTo(assertItem2.getMethodName());
//            if (m == 0) {
//                int c = assertItem1.getCaseName().compareTo(assertItem2.getCaseName());
//                return c;
//
//            } else {
//                return m;
//            }
//        } else {
//            return i;
//        }
//
//    }
//}
