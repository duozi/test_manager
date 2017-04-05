package com.xn.interfacetest.sign;/**
 * Created by xn056839 on 2016/11/22.
 */

import com.xn.interfacetest.Exception.CaseErrorEqualException;
import com.xn.interfacetest.service.GetPara;
import com.xn.interfacetest.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.TreeMap;

import static com.xn.interfacetest.util.SignUtil.mapToString;
import static com.xn.interfacetest.util.SignUtil.md5;

public class MessageAddSign {
    private static final Logger logger = LoggerFactory.getLogger(MessageAddSign.class);

//    public String MessageHttpAddSing(TreeMap<String, Object> treeMap, boolean useSign, String signType) throws CaseErrorEqualException {
//        String key = "";
//        //只有useSign为true&&传入sign为空才会计算sign
//        if (useSign) {
//            String oldSign = String.valueOf(treeMap.get("sign"));
//            if (StringUtils.isEmpty(oldSign)) {
//                String type = "";
//                if (treeMap.containsKey("partnerId")) {
//                    type = String.valueOf(treeMap.get("partnerId"));
//                }
//
//                GetPara getPara = new GetPara();
//                String path = getPara.getPath();
//                File file = new File(path + "suite/key.properties");
//                key = StringUtil.getConfig(file, signType + ".key." + type, "");
//                if (StringUtils.isEmpty(key)) {
//                    throw new CaseErrorEqualException("no key");
//                }
//
//                treeMap.remove("signature");
//                String sign_sb = mapToString(treeMap);
//                String signPara = sign_sb + "&key=" + key;
//                String sign = md5(signPara,null);
//                sign_sb += "&signature=" + sign;
//                return sign_sb;
//            }
//        }
//
//
//        String sign_sb = mapToString(treeMap);
//        return sign_sb;
//
//    }

}
