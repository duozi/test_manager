package com.xn.performance.util.jmeter;

import org.apache.jmeter.JMeter;
import org.apache.jmeter.engine.ClientJMeterEngine;
import org.apache.jmeter.engine.DistributedRunner;
import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.report.dashboard.GenerationException;
import org.apache.jmeter.report.dashboard.ReportGenerator;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.samplers.Remoteable;
import org.apache.jmeter.samplers.SampleSaveConfiguration;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JOrphanUtils;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.xn.performance.util.PropertyUtil.getProperty;

public class XNJmeterStartRemot {
    private static final org.apache.log.Logger logger = LoggingManager.getLoggerForClass();
    boolean running = false;
    ListenToTest agentlisten = null;

    DistributedRunner distributedRunner = null;

    public boolean stop() {
        if (distributedRunner != null) {
            distributedRunner.stop();
            return true;
        }
        return false;
    }

    public String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

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

    public void remoteStart(String remote_hosts_string, String rmi_server,String jmeterScriptPath) throws Exception {
        String logFile = getProperty("reports");
        // jmeter.properties
        JMeterUtils.loadJMeterProperties(getProperty("jmeter_root") + "bin/saveservice.properties");
        JMeterUtils.setLocale(Locale.ENGLISH);
        JMeterUtils.setJMeterHome(getProperty("jmeter_root"));
        System.getProperties().setProperty("java.rmi.server.hostname", rmi_server);

        File fileFeports = new File(getProperty("reports"));
        if (!fileFeports.exists()) {
            fileFeports.mkdir();
        }

        // jtl报告文件名称
        SimpleDateFormat df = new SimpleDateFormat("_yyyyMMdd_HHmmss");
        String jtltime = df.format(new Date());
        File tempFile = new File(jmeterScriptPath);
        String jmxfileName = tempFile.getName();
        System.out.println("jmxfileName = " + jmxfileName);
        String outputFileJtl = getProperty("reports") + jmxfileName + jtltime + ".jtl";
        // html报告文件名
        String outputFileHtml = getProperty("reports") + jmxfileName + jtltime + ".html";

        File file = new File( getProperty("jmxfile")); //jmx文件
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
                SampleSaveConfiguration saveConfiguration = new SampleSaveConfiguration();
                saveConfiguration.setAsXml(true);
                saveConfiguration.setCode(true);
                saveConfiguration.setLatency(true);
                saveConfiguration.setTime(true);
                saveConfiguration.setTimestamp(true);
                resultCollector.setSaveConfig(saveConfiguration);
                testTree.get(key).add(resultCollector);
            }

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

            List<JMeterEngine> engines = new LinkedList<JMeterEngine>();
//            agentlisten = new ListenToTest(this, engines, reportGenerator);
            agentlisten = new ListenToTest(this, null, reportGenerator);
            testTree.add(testTree.getArray()[0], agentlisten);

            StringTokenizer st = new StringTokenizer(remote_hosts_string, ",");
            List<String> hosts = new LinkedList<String>();
            while (st.hasMoreElements()) {
                hosts.add((String) st.nextElement());
            }
            DistributedRunner distributedRunner = new DistributedRunner();
            distributedRunner.setStdout(System.out);
            distributedRunner.setStdErr(System.err);
            distributedRunner.init(hosts, testTree);
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
            running = true;
            do {
                BufferedReader br = new BufferedReader(new StringReader(baos.toString()));
                String line;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
                int ha1 = agentlisten.startcount;
                int ha2 = agentlisten.startcount_host;
                int ha3 = agentlisten.endcount;
                int ha4 = agentlisten.endcount_host;
                if (agentlisten.isTestEnd == true) {
                    running = false;
                    break;
                }
                while ((line = br.readLine()) != null) {
                    running = !line.equals("... end of run");
                }
            } while (running);


            // Transform raw JTL to friendly HTML using XSL
            File xsl = new File(getProperty("reporttohtml_xsl"));
            jtl2html(xsl, new File(outputFileJtl), new File(outputFileHtml));
            System.out.println("*********Jmeter runing End! html report show.");
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void jtl2html(File stylesheet, File datafile, File fileOutput) throws Exception {
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
            ClientJMeterEngine.tidyRMI( logger);
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

}
