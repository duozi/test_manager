package com.xn.performance.util.jmeter;


import org.apache.jmeter.JMeter;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.engine.ClientJMeterEngine;
import org.apache.jmeter.engine.DistributedRunner;
import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.report.config.ConfigurationException;
import org.apache.jmeter.report.dashboard.GenerationException;
import org.apache.jmeter.report.dashboard.ReportGenerator;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.samplers.Remoteable;
import org.apache.jmeter.samplers.SampleSaveConfiguration;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.util.BeanShellInterpreter;
import org.apache.jmeter.util.BeanShellServer;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.backend.BackendListener;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.reflect.ClassTools;
import org.apache.jorphan.util.JMeterException;
import org.apache.jorphan.util.JOrphanUtils;
import org.apache.log.Logger;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.xn.performance.util.PropertyUtil.getProperty;
import static com.xn.performance.util.jmeter.StartJMeterAgent_SSH.exec_command;

public class XNJmeterStartRemot {
    private static final Logger logger = LoggingManager.getLoggerForClass();
    public ExecutorService threadPool = Executors.newFixedThreadPool(5);
    boolean is_running = false; // 本地 distributedRunner 是否在运行
    boolean interrupt = false;//是否被打断而结束

    public boolean is_running() {
        return is_running;
    }

    public boolean isInterrupt() {
        return interrupt;
    }

    ListenToTest agentlisten = null;  // 监听器
    DistributedRunner distributedRunner = null; // Jmeter 远程测试执行控制器
    List<JMeterEngine> engines = new LinkedList<JMeterEngine>(); // 远程执行机列表
    public Map<String, Boolean> setjmeterpros = new HashMap<String, Boolean>();

    String outputFileJtl = "";
    String outputFile = "";
    Integer id;
    String ip;
    String username;
    String pwd;
    String port;

    public XNJmeterStartRemot(Integer id,String ip,String username,String pwd,String port) {
        setjmeterpros.put("setAsXml", true);
        setjmeterpros.put("setCode", true);
        setjmeterpros.put("setLatency", true);
        setjmeterpros.put("setTime", true);
        setjmeterpros.put("setTimestamp", true);
        setjmeterpros.put("setBytes", true);
        setjmeterpros.put("setAssertionResultsFailureMessage", true);
        setjmeterpros.put("setSuccess", true);
        setjmeterpros.put("setThreadName", true);
        setjmeterpros.put("setThreadCounts", true);
        setjmeterpros.put("setLabel", true);

        // jmeter.properties
        JMeterUtils.loadJMeterProperties(getProperty("jmeter_root") + "bin/jmeter.properties");
        JMeterUtils.loadJMeterProperties(getProperty("jmeter_root") + "bin/user.properties");
        JMeterUtils.setLocale(Locale.ENGLISH);
        JMeterUtils.setJMeterHome(getProperty("jmeter_root"));


        this.id = id;
        this.ip=ip;
        this.username=username;
        this.pwd=pwd;
        this.port=port;
    }

    /**
     * 获取性能测试运行状态，
     *
     * @return True/False ---运行状态为 True
     */
    public boolean getRunState() {
        return is_running;
    }

    /**
     * 获取远程负载机信息，计划完成这些功能：获取远程执行机列表，状态，控制远程执行机退出
     *
     * @return
     */
    public Map<String, Boolean> getRemothostState() {
        Map<String, Boolean> rstate = new HashMap<>();
        for (JMeterEngine myengine : engines) {
            rstate.put(myengine.toString(), myengine.isActive());
        }
        return rstate;
    }

    /**
     * 停止分布式性能测试
     *
     * @param addresses 远程负载机ip地址 列表 List<String>
     */
    public void stop(List<String> addresses) {
        if (distributedRunner != null) {
            distributedRunner.stop(addresses);
        }
    }

    public void stop() {
        if (distributedRunner != null) {
            distributedRunner.stop();
            interrupt = true;
        }
    }

    /**
     * 退出部分远程负载机测试
     *
     * @param addresses 远程负载机ip地址列表  List<String>
     */
    public void exit(List<String> addresses) {
        distributedRunner.exit(addresses);
    }


    /**
     * Remote engines have been shut down
     *
     * @param addresses 远程负载机ip地址列表  List<String>
     */
    public void shutdown(List<String> addresses) {
        distributedRunner.shutdown(addresses);
    }


    /**
     * 获取不含后缀的文件名
     *
     * @param filename 文件名例如 filename.txt
     * @return 不含后缀名的文件名 如 filename
     */
    public String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * Transform raw JTL to friendly HTML using XSL
     */
    public void generateReportXMLToHtml() {
        generateReportXMLToHtml(outputFileJtl, outputFile);
    }

    public void generateReportXMLToHtml(String jtlfile, String htmlfile) {
        logger.info("*********JTL to HTML: html report generate start.");
        try {
            File xsl = new File(getProperty("reporttohtml_xsl"));
            jtl2html(xsl, new File(jtlfile), new File(htmlfile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("*********JTL to HTML: html report generate end.");
    }

    private void jtl2html(File stylesheet, File datafile, File fileOutput) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(datafile);

        TransformerFactory tFactory = TransformerFactory.newInstance();
        StreamSource stylesource = new StreamSource(stylesheet);
        Transformer transformer = tFactory.newTransformer(stylesource);

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(fileOutput);
        transformer.transform(source, result);
    }

    /**
     * Transform raw CSV to friendly HTML
     */
    public void generateReportCSVToHtml() {
        generateReportCSVToHtml(outputFileJtl, outputFile);
    }

    public void generateReportCSVToHtml(String csvfile, String htmlfile) {
        if (csvfile == "") {
            csvfile = outputFileJtl;
        }
        if (htmlfile == "") {
            htmlfile = outputFile;
        }

        File reportOutputFolderAsFile = new File(outputFile);
        JOrphanUtils.canSafelyWriteToFolder(reportOutputFolderAsFile);
        logger.info("Setting property 'jmeter.reportgenerator.outputdir' to:'" + reportOutputFolderAsFile.getAbsolutePath() + "'");
        //JMeterUtils.setProperty("jmeter.reportgenerator.outputdir", reportOutputFolderAsFile.getAbsolutePath());
        System.out.println("````````````````````````````````````");
        System.out.println(outputFile);
        System.out.println(reportOutputFolderAsFile.getAbsolutePath());
        System.out.println(JMeterUtils.getJMeterVersion());
        System.out.println(getProperty("jmeter_root") + "bin/jmeter.properties");
        System.out.println("````````````````````````````````````");
        JMeterUtils.setProperty("jmeter.reportgenerator.outputdir", reportOutputFolderAsFile.getAbsolutePath());

        ReportGenerator generator;
        try {
            JMeterUtils.setProperty("jmeter.reportgenerator.graph.syntheticResponseTimeDistribution.property.set_satisfied_threshold", String.valueOf(500));
            JMeterUtils.setProperty("jmeter.reportgenerator.graph.syntheticResponseTimeDistribution.property.set_tolerated_threshold", String.valueOf(1500));
            generator = new ReportGenerator(csvfile, null);

            generator.generate();
        } catch (ConfigurationException | GenerationException e) {
            e.printStackTrace();
        }

        System.out.println("*********CSV to HTML: html report generate end.");
    }

    /**
     * 读取jmeter测试脚本jmx文件，返回值为jmeter需要的hashtree格式
     *
     * @param reader
     * @return
     * @throws Exception
     */
    private static HashTree loadJMX(File reader) throws Exception {
        HashTree tree = SaveService.loadTree(reader);
        if (tree == null) {
            throw new RuntimeException("There was problems loading test plan. Please investigate error messages above.");
        }
        JMeter.convertSubTree(tree); // Remove the disabled items
        //JMeterEngine engine = new StandardJMeterEngine();
        //engine.configure(tree);
        return tree;
    }

    /**
     * set jmeter 配置文件属性。例如是否生成xml报告等，具体设置项目的作用需要调研：
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setTimestamp(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setAssertionResultsFailureMessage(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setAsXml(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setCode(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setLatency(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setTime(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setBytes(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setSuccess(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setThreadName(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setThreadCounts(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setLabel(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setMessage(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setAssertions(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setEncoding(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setSubresults(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setFieldNames(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setUrl(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setFileName(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setHostname(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setResponseData(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setDataType(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setSamplerData(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setIdleTime(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setConnectTime(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setSentBytes(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setSampleCount(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setResponseHeaders(boolean)
     * org.apache.jmeter.samplers.SampleSaveConfiguration.setRequestHeaders(boolean)
     *
     * @return SampleSaveConfiguration对象，要在jmeter启动方法中加入到hashtree中
     */
    public SampleSaveConfiguration setSampleSaveConfiguration() {
        SampleSaveConfiguration saveConfiguration = new SampleSaveConfiguration();
        try {
            for (String in : setjmeterpros.keySet()) {
                System.out.println("SampleSaveConfiguration method name: " + in + " | parameter is :" + setjmeterpros.get(in));
                Class<?> clazz = saveConfiguration.getClass();
                Method m2 = clazz.getMethod(in, boolean.class);
                m2.invoke(saveConfiguration, setjmeterpros.get(in));
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return saveConfiguration;
    }

    public void remoteStart(String remote_hosts_string, String rmi_server) throws Exception {
        remoteStart(remote_hosts_string, rmi_server, "");
    }

    /**
     * 启动远程负载机运行负载测试
     *
     * @param remote_hosts_string -- 负载机ip地址，多个ip用逗号分隔
     * @param rmi_server          -- 主控机ip，用于标示 rmi地址避免负载机连接不到主控机
     * @param jmxfile             -- 指定执行测试的jmx文件，如果设置为空 "" 则取 配置文件xnjmeter.properties中的配置 jmxfile = source/JavaProGO.jmx
     * @throws Exception
     */
    public void remoteStart(String remote_hosts_string, String rmi_server, String jmxfile) throws Exception {

        // jtl报告文件名称
        SimpleDateFormat df = new SimpleDateFormat("_yyyyMMdd_HHmmss");
        String jtltime = df.format(new Date());
        if (jmxfile == "") {
            jmxfile = getProperty("jmxfile");
        }
        String reportPath = getProperty("reports");

        if (setjmeterpros.get("setAsXml") == true) {
            outputFileJtl = reportPath + id + jtltime + ".jtl";
            // html报告文件名
            outputFile = reportPath + id + ".html";
        } else {
            // 使用Jmeter自带的 csv 转 html api 要求下面设个属性为 csv，光设置setAsXml=false还不够
            JMeterUtils.setProperty("jmeter.save.saveservice.output_format", "csv");
            JMeterUtils.setProperty("jmeter.save.saveservice.timestamp_format", "ms");
            JMeterUtils.setProperty("jmeter.save.saveservice.timestamp_format", "yyyy/MM/dd HH:mm:ss.SSS");
            outputFileJtl = reportPath + id + jtltime + ".csv";
            // jmeter3.x html报告需要路径，报告会包含js等多个文件
            outputFile = reportPath + id;
            JMeterUtils.getPropDefault("jmeter.save.saveservice.output_format", "csv");
        }

        String logFile = getProperty("reports");

        // you can comment this line out to see extra log messages of i.e. DEBUG level
        JMeterUtils.initLogging();
        JMeterUtils.initLocale();

        // Initialize JMeter SaveService
        SaveService.loadProperties();

        // 设置java rmi ip地址 （避免多个网卡时会找不到）
        System.getProperties().setProperty("java.rmi.server.hostname", rmi_server);

        File fileFeports = new File(getProperty("reports"));
        if (!fileFeports.exists()) {
            fileFeports.mkdir();
        }

        // Load existing .jmx Test Plan
        /**
         * // Load existing .jmx Test Plan
         FileInputStream in = new FileInputStream("/path/to/your/jmeter/extras/Test.jmx");
         HashTree testPlanTree = SaveService.loadTree(in);
         in.close();
         */
        File file = new File(jmxfile); //jmx文件
        HashTree testTree;
        try {
            testTree = loadJMX(file);

            System.out.println("get Tree over!");
            System.out.println(testTree.list().size());
            for (Object key : testTree.getArray()) {
                // 获取测试计划中的内容
                org.apache.jmeter.testelement.TestPlan plan1 = (TestPlan) key;
                // Result collector
                ResultCollector resultCollector = new ResultCollector();
                resultCollector.setFilename(outputFileJtl);

                SampleSaveConfiguration saveConfiguration = setSampleSaveConfiguration();
                /*// 设置配置文件内容，后面增加设置方法，这里先屏蔽使用jmeter配置文件内容
                SampleSaveConfiguration saveConfiguration = new SampleSaveConfiguration();
                saveConfiguration.setAsXml(true);
                ......*/
                resultCollector.setSaveConfig(saveConfiguration);
                testTree.get(key).add(resultCollector);
            }
            Arguments arguments = new Arguments();
            arguments.addArgument("graphiteMetricsSender", "org.apache.jmeter.visualizers.backend.graphite.TextGraphiteMetricsSender");
            arguments.addArgument("graphiteHost", "10.10.22.144");
            arguments.addArgument("graphitePort", "2003");
            arguments.addArgument("rootMetricsPrefix", "jmeter.");
            arguments.addArgument("summaryOnly", "true");
            arguments.addArgument("samplersList", ".*");
            arguments.addArgument("useRegexpForSamplersList", "false");
            arguments.addArgument("percentiles", "90;95;99");
            //arguments.addArgument("TestPlan.comments", "=");

            BackendListener backendListener = new BackendListener();
            backendListener.setArguments(arguments);
            backendListener.setClassname("org.apache.jmeter.visualizers.backend.graphite.GraphiteBackendListenerClient");
            backendListener.setQueueSize("5000");
            testTree.add(testTree.getArray()[0], backendListener);

            /**
             * jmeter.properties配置文件中，设置写日志的方式，避免大量写导致性能问题，下面设置每隔3分钟向jmeter.log中写入一行log
             * summariser.name=summary
             * # interval between summaries (in seconds) default 3 minutes
             * summariser.interval=180
             * # Write messages to log file
             * summariser.log=true
             * # Write messages to System.out
             * #summariser.out=true
             */

            Summariser summer = null;
            String summariserName = JMeterUtils.getPropDefault("summariser.name", "");//$NON-NLS-1$
            if (summariserName.length() > 0) {
                logger.info("Creating summariser <" + summariserName + ">");
                println("Creating summariser <" + summariserName + ">");
                summer = new Summariser(summariserName);
            }
            ReportGenerator reportGenerator = null;
            if (logFile != null) {
                ResultCollector logger = new ResultCollector(summer);
                logger.setFilename(logFile);
                testTree.add(testTree.getArray()[0], logger);
                if (!file.exists() || file.length() <= 0) {
                    //日志文件已存在，走到这里会报错
                    reportGenerator = new ReportGenerator(logFile, logger);
                }
            } else {
                // only add Summariser if it can not be shared with the ResultCollector
                if (summer != null) {
                    testTree.add(testTree.getArray()[0], summer);
                }
            }


            // 分布式测试的类 DistributedRunner
            //JMeterUtils.setProperty(DistributedRunner.RETRIES_NUMBER, "1");
            //JMeterUtils.setProperty(DistributedRunner.CONTINUE_ON_FAIL, "false");
            //obj.shutdown(hosts);
            //obj.stop(hosts);
            //obj.exit(hosts);

            //testTree.add(testTree.getArray()[0], new RemoteThreadsListenerTestElement());

//            agentlisten = new ListenToTest(this,engines, reportGenerator); // 第二个参数会导致测试结束，负载机被关闭
            agentlisten = new ListenToTest(this, null, reportGenerator);
            testTree.add(testTree.getArray()[0], agentlisten);

            java.util.StringTokenizer st = new java.util.StringTokenizer(remote_hosts_string, ",");
            List<String> hosts = new LinkedList<>();
            while (st.hasMoreElements()) {
                hosts.add((String) st.nextElement());
            }
            distributedRunner = new DistributedRunner();
            distributedRunner.setStdout(System.out);
            distributedRunner.setStdErr(System.err);


            retry(hosts, testTree);

            engines.addAll(distributedRunner.getEngines());
            /**
             * DistributedRunner.start(List<String> addresses) # 启动指定的负载机
             * DistributedRunner.start() # 启动所有初始化中的远程负载机，Start all engines that were previously initiated
             */
            distributedRunner.start(hosts);


            //agentlisten.testEnded();
            // Waiting for JMeter ending，读输出日志判断是否结束测试, 分布式读取不到输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));
            is_running = true;
            do {
                BufferedReader br = new BufferedReader(new StringReader(baos.toString()));
                String line;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                /**
                 * 以下测试结果，当以 传 hosts 启动测试，结束时，下面变量值一次 0 1 0 1
                 * 即  testStarted(String host)和testEnded(String host)各调了一次，在其中增加变量实现判断是否测试结束
                 int ha1 = agentlisten.startcount;
                 int ha2 = agentlisten.startcount_host;
                 int ha3 = agentlisten.endcount;
                 int ha4 = agentlisten.endcount_host;
                 */
                if (agentlisten.isTestEnd == true) {
                    is_running = false;
                    break;
                }
                while ((line = br.readLine()) != null) {
                    is_running = !line.equals("... end of run");
                }
            } while (is_running);

            startOptionalServers(); // 干什么的不知道？

            // Transform raw JTL to friendly HTML using XSL
            // File xsl = new File(xnjmeterpro.get("reporttohtml_xsl"));
            // Utils.jtl2html(xsl, new File(outputFileJtl), new File(outputFileHtml));
            // System.out.println("*********Jmeter runing End! html report show.");

        } catch (RuntimeException e) {
            is_running = false;
            throw e;
        } catch (Exception e) {
            is_running = false;
            e.printStackTrace();
            throw e;
        }
    }

    private void retry(List<String> hosts, HashTree testTree) throws InterruptedException {
        try {
            distributedRunner.init(hosts, testTree);
        } catch (Exception e) {

            logger.error("========jmeter server is lost ,retry agin");


            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    logger.info(new Date() + Thread.currentThread().getName() + "启动jmeter======");
                    exec_command(ip, username, pwd, Integer.parseInt(port), "bash  /data/apache-jmeter-3.1/bin/jmeter-server");
                }
            });

            Thread.sleep(5000);

            distributedRunner.init(hosts, testTree);
        }

    }


    private void startOptionalServers() {
        int bshport = JMeterUtils.getPropDefault("beanshell.server.port", 0);// $NON-NLS-1$
        String bshfile = JMeterUtils.getPropDefault("beanshell.server.file", "");// $NON-NLS-1$ $NON-NLS-2$
        if (bshport > 0) {
            logger.info("Starting Beanshell server (" + bshport + "," + bshfile + ")");
            Runnable t = new BeanShellServer(bshport, bshfile);
            t.run();
        }

        // Should we run a beanshell script on startup?
        String bshinit = JMeterUtils.getProperty("beanshell.init.file");// $NON-NLS-1$
        if (bshinit != null) {
            logger.info("Run Beanshell on file: " + bshinit);
            try {
                BeanShellInterpreter bsi = new BeanShellInterpreter();
                bsi.source(bshinit);
            } catch (ClassNotFoundException e) {
                logger.warn("Could not start Beanshell: " + e.getLocalizedMessage());
            } catch (JMeterException e) {
                logger.warn("Could not process Beanshell file: " + e.getLocalizedMessage());
            }
        }

        int mirrorPort = JMeterUtils.getPropDefault("mirror.server.port", 0);// $NON-NLS-1$
        if (mirrorPort > 0) {
            logger.info("Starting Mirror server (" + mirrorPort + ")");
            try {
                Object instance = ClassTools.construct(
                        "org.apache.jmeter.protocol.http.control.HttpMirrorControl",// $NON-NLS-1$
                        mirrorPort);
                ClassTools.invoke(instance, "startHttpMirror");
            } catch (JMeterException e) {
                logger.warn("Could not start Mirror server", e);
            }
        }
    }

    private static class ListenToTest implements TestStateListener, Runnable, Remoteable {
        private final AtomicInteger started = new AtomicInteger(0); // keep track of remote tests
        private final List<JMeterEngine> engines;
        private final ReportGenerator reportGenerator;
        public int endcount = 0;
        public int startcount = 0;
        public int endcount_host = 0;
        public int startcount_host = 0;
        public boolean isTestEnd = false;

        /**
         * //JMeter unused, List<JMeterEngine> engines, ReportGenerator reportGenerator
         *
         * @param unused          JMeter unused for now
         * @param engines         List<JMeterEngine>
         * @param reportGenerator {@link ReportGenerator}
         */
        public ListenToTest(XNJmeterStartRemot unused, List<JMeterEngine> engines, ReportGenerator reportGenerator) {
            this.engines = engines;
            this.reportGenerator = reportGenerator;
        }

        @Override
        /**
         * 测试结束后为所有线程调用一次。这将使用与测试开始时相同的元素实例
         * N.B. this is called by a daemon RMI thread from the remote host
         */
        public void testEnded(String host) {
            long now = System.currentTimeMillis();
            logger.info("Finished remote host: " + host + " (" + now + ")");
            if (started.decrementAndGet() <= 0) {
                Thread stopSoon = new Thread(this);
                // the calling thread is a daemon; this thread must not be
                // see Bug 59391
                stopSoon.setDaemon(false);
                stopSoon.start();
            }
            endcount_host++;
            isTestEnd = true;
        }

        @Override
        // Called once for all threads after the end of a test. This will use the same element instances as at the start of the test.
        public void testEnded() {
            long now = System.currentTimeMillis();
            println("Tidying up ...    @ " + new Date(now) + " (" + now + ")");
            try {
                generateReport();
            } catch (Exception e) {
                System.err.println("Error generating the report: " + e);
                logger.error("Error generating the report", e);
            }
            checkForRemainingThreads();
            println("... end of run");
            endcount++;
            isTestEnd = true;
        }

        @Override
        /**
         * 在开始测试之前，从主线程调用。这是在测试元素被克隆之前。请注意，并非所有的测试变量都将在这一点上设置。
         */
        public void testStarted(String host) {
            started.incrementAndGet();
            long now = System.currentTimeMillis();
            logger.info("Started remote host:  " + host + " (" + now + ")");
            startcount_host++;
        }

        @Override
        /**
         * 在开始测试之前，从主线程调用。这是在测试元素被克隆之前。请注意，并非所有的测试变量都将在这一点上设置。
         */
        public void testStarted() {
            long now = System.currentTimeMillis();
            logger.info(JMeterUtils.getResString("running_test") + " (" + now + ")");//$NON-NLS-1$
            startcount++;
        }

        /**
         * This is a hack to allow listeners a chance to close their files. Must
         * implement a queue for sample responses tied to the engine, and the
         * engine won't deliver testEnded signal till all sample responses have
         * been delivered. Should also improve performance of remote JMeter
         * testing.
         */
        @Override
        public void run() {
            long now = System.currentTimeMillis();
            println("Tidying up remote @ " + new Date(now) + " (" + now + ")");
            if (engines != null) { // it will be null unless remoteStop = true
                println("Exiting remote servers");
                for (JMeterEngine e : engines) {
                    e.exit();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(5); // Allow listeners to close files
            } catch (InterruptedException ignored) {
            }
            ClientJMeterEngine.tidyRMI(logger);
            try {
                generateReport();
            } catch (Exception e) {
                System.err.println("Error generating the report: " + e);
                logger.error("Error generating the report", e);
            }
            checkForRemainingThreads();
            println("... end of run");
        }

        /**
         * Generate report
         */
        private void generateReport() {
            if (reportGenerator != null) {
                try {
                    logger.info("Generating Dashboard");
                    reportGenerator.generate();
                    logger.info("Dashboard generated");
                } catch (GenerationException ex) {
                    logger.error("Error generating dashboard:" + ex.getMessage(), ex);
                }
            }
        }

        /**
         * Runs daemon thread which waits a short while;
         * if JVM does not exit, lists remaining non-daemon threads on stdout.
         */
        private void checkForRemainingThreads() {
            // This cannot be a JMeter class variable, because properties
            // are not initialised until later.
            final int REMAIN_THREAD_PAUSE =
                    JMeterUtils.getPropDefault("jmeter.exit.check.pause", 2000); // $NON-NLS-1$

            if (REMAIN_THREAD_PAUSE > 0) {
                Thread daemon = new Thread() {
                    @Override
                    public void run() {
                        try {
                            TimeUnit.MILLISECONDS.sleep(REMAIN_THREAD_PAUSE); // Allow enough time for JVM to exit
                        } catch (InterruptedException ignored) {
                        }
                        // This is a daemon thread, which should only reach here if there are other
                        // non-daemon threads still active
                        System.out.println("The JVM should have exitted but did not.");
                        System.out.println("The following non-daemon threads are still running (DestroyJavaVM is OK):");
                        JOrphanUtils.displayThreads(false);
                    }

                };
                daemon.setDaemon(true);
                daemon.start();
            } else if (REMAIN_THREAD_PAUSE <= 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("jmeter.exit.check.pause is <= 0, JMeter won't check for unterminated non-daemon threads");
                }
            }
        }

    }

    private static void println(String str) {
        System.out.println(str);
    }

    public void rstart_xml(String remote_hosts_string, String rmi_server) throws Exception {
        remoteStart(remote_hosts_string, rmi_server);
        generateReportXMLToHtml();
    }

    public void rstart_xml(String remote_hosts_string, String rmi_server, String jmxfile) throws Exception {
        remoteStart(remote_hosts_string, rmi_server, jmxfile);
        generateReportXMLToHtml();
    }

    /**
     * 测试用，使用配置文件配置的 jmx 测试文件，在配置的位置生成测试报告
     *
     * @param remote_hosts_string 远程负载机ip地址，多台用逗号分隔
     * @param rmi_server          本地主控机 rmi 使用的 ip地址，防止多网卡连接不到负载机
     * @throws Exception
     */
    public void rstart_csv(String remote_hosts_string, String rmi_server) throws Exception {
        setjmeterpros.put("setAsXml", false);
        remoteStart(remote_hosts_string, rmi_server);
        generateReportCSVToHtml();
    }

    public String rstart_csv(String remote_hosts_string, String rmi_server, String jmxfile) throws Exception {
        logger.info(Thread.currentThread().getName() + "===========rstart_csv remote_hosts_string:" + remote_hosts_string + " rmi_server:" + rmi_server + " jmxfile:" + jmxfile);
        setjmeterpros.put("setAsXml", false);
        remoteStart(remote_hosts_string, rmi_server, jmxfile);
        generateReportCSVToHtml();
        return outputFile;
    }


}
