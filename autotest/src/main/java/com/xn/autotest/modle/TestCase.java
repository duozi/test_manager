package com.xn.autotest.modle;/**
 * Created by xn056839 on 2016/12/30.
 */

import com.xn.autotest.command.StepCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCase extends MappedElement{
    private static final Logger logger = LoggerFactory.getLogger(TestCase.class);
    private String id;
    private List<String> tag;
    private String desc;
    private StepCommand beforeCommand;
    private StepCommand bodyCommand;
    private StepCommand tearDownCommand;
    private StepCommand afterCommand;

    public TestCase() {
    }

    public TestCase(String id, List<String> tag, String desc) {
        this.id = id;
        this.tag = tag;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public StepCommand pipeline() {
        return bodyCommand;
    }

    public void setBodyCommand(StepCommand bodyCommand) {
        this.bodyCommand = bodyCommand;
    }

    public StepCommand getBodyCommand() {
        return bodyCommand;
    }

    public StepCommand getTearDownCommand() {
        return tearDownCommand;
    }

    public void setTearDownCommand(StepCommand tearDownCommand) {
        this.tearDownCommand = tearDownCommand;
    }

    public StepCommand getBeforeCommand() {
        return beforeCommand;
    }

    public void setBeforeCommand(StepCommand beforeCommand) {
        this.beforeCommand = beforeCommand;
    }

    public StepCommand getAfterCommand() {
        return afterCommand;
    }

    public void setAfterCommand(StepCommand afterCommand) {
        this.afterCommand = afterCommand;
    }

    public TestCase clone() {
        TestCase testCase = new TestCase(this.id, this.tag, this.desc);
        testCase.setBodyCommand(cloneCommands(bodyCommand));
        testCase.setAfterCommand(cloneCommands(afterCommand));
        testCase.setBeforeCommand(cloneCommands(beforeCommand));
        testCase.setTearDownCommand(cloneCommands(tearDownCommand));
        return testCase;
    }

    protected StepCommand cloneCommands(StepCommand command) {
        List<StepCommand> commands = new ArrayList<StepCommand>();
        StepCommand current = command;
        while (current != null) {
            commands.add(current.cloneCommand());
            current = current.getNextCommand();
        }
        StepCommand head = null;
        for (int i = 0; i < commands.size(); ++i) {
            if (head == null) {
                head = commands.get(i);
            }
            if (i < commands.size() - 1) {
                commands.get(i).setNextCommand(commands.get(i + 1));
            }
        }
        return head;
    }

    @Override
    public Map asMap() {
        Map<Object, Object> caseMap = new HashMap<Object, Object>();
        caseMap.put("type", "scenario");
        caseMap.put("id", getId());
        caseMap.put("name", getDesc());
        caseMap.put("keyword", "Case ID");
        caseMap.put("tags", getTags(getTag()));
        return caseMap;
    }
}
