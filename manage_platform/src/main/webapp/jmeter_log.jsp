<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.jcraft.jsch.ChannelExec" %>
<%@ page import="com.jcraft.jsch.JSch" %>
<%@ page import="com.jcraft.jsch.Session" %>
<%@ page import="javax.servlet.jsp.JspWriter" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.concurrent.ExecutorService" %>
<%@ page import="java.util.concurrent.Executors" %>


<%!
    Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();


    public ExecutorService threadPool = Executors.newFixedThreadPool(5);

    public void jmeterLog(final JspWriter out,String ip,String username,String password,int port) throws Exception {


        Session session = null;
        ChannelExec openChannel = null;


        try {

            JSch jsch = new JSch();
            session = jsch.getSession(username, ip, port);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(password);
            session.connect();

            openChannel = (ChannelExec) session.openChannel("exec");
            openChannel.setCommand("tailf jmeter-server.log");
            int exitStatus = openChannel.getExitStatus();

            openChannel.connect();


            InputStream in = openChannel.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            final String[] buf = {null};

            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        out.print("<div  style=\"height: 800px; overflow-y: scroll; background: #333; color: #aaa; padding: 10px;\">");
                        while ((buf[0] = reader.readLine()) != null) {
                            out.println("<br>"+new String(buf[0].getBytes("UTF-8"), "UTF-8")+"</br>");

                        }
                        out.print("</div>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            if (openChannel != null && !openChannel.isClosed()) {
                openChannel.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }


%>
<!DOCTYPE html >
<html lang="en">

<head>

    <script src="../../../js/common.js"></script>
    <script type="text/javascript">
        //        $(document).ready(function(){
        //            window.open("/performance/report/jmeter_log");
        //        });
    </script>
    <%--<script>--%>
        <%--function getQueryStr(sArgName)--%>
        <%--{--%>
            <%--var args = LocString.split("?");--%>
            <%--var retval = "";--%>
            <%--if(args[0] == LocString)--%>
            <%--{--%>
                <%--return retval;--%>
            <%--}--%>
            <%--var str = args[1];--%>
            <%--args = str.split("&");--%>
            <%--for(var i = 0; i < args.length; i ++)--%>
            <%--{--%>
                <%--str = args[i];--%>
                <%--var arg = str.split("=");--%>
                <%--if(arg.length <= 1) continue;--%>
                <%--if(arg[0] == sArgName) retval = arg[1];--%>
            <%--}--%>
            <%--return retval;--%>
        <%--}--%>
        <%--var ip=getQueryStr("ip");--%>
        <%--var username=getQueryStr("username");--%>
        <%--var password=getQueryStr("password");--%>
        <%--var port=getQueryStr("port");--%>
    <%--</script>--%>
    <meta http-equiv="refresh" content="1">
</head>
<body></body>


<!-- /.container-fluid -->

</html>
<%

    String ip = request.getParameter("ip");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    int port = Integer.valueOf(request.getParameter("port"));
    jmeterLog(out,ip,username,password,port);



%>

