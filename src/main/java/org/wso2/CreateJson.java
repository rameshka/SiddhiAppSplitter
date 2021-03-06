package org.wso2;

import net.minidev.json.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateJson {


    public void writeConfiguration(List<StrSiddhiApp> strSiddhiAppList, String deploymentJSONURI) throws IOException {


        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        HashMap<String,String>  hashMap;

        for (StrSiddhiApp strSiddhiApp : strSiddhiAppList) {
            jsonObject = new JSONObject();
            jsonObject.put("name", strSiddhiApp.getAppName());
            jsonObject.put("parallel", strSiddhiApp.getParallel());
            jsonObject.put("app", strSiddhiApp.toJsonString());

            JSONArray jsonArray1 = new JSONArray();


            for (Map.Entry<String,StrInputStream> entry:  strSiddhiApp.getInputStreamMap().entrySet()){

                hashMap = new HashMap<String, String>();
                hashMap.put("streamName",entry.getKey());
                hashMap.put("subscriptionStrategy",entry.getValue().getSubscriptionStrategy());
                hashMap.put("isUserGivenStream",String.valueOf(entry.getValue().getIsUserGiven()));
                jsonArray1.add((hashMap));
            }

            jsonObject.put("inputStream",jsonArray1);

            JSONArray jsonArray2 = new JSONArray();

            for (Map.Entry<String,StrOutputStream> entry:  strSiddhiApp.getOutputStreamMap().entrySet()){
                hashMap = new HashMap<String, String>();
                hashMap.put("streamName",entry.getKey());
                hashMap.put("publishingStrategy","");
                hashMap.put("isUserGivenStream",String.valueOf(entry.getValue().getIsUserGiven()));
                jsonArray2.add((hashMap));
            }
            jsonObject.put("outputStream",jsonArray2);

            jsonArray.add(jsonObject);

        }

        jsonObject = new JSONObject();
        jsonObject.put("name", "TestPlan");
        jsonObject.put("siddhiApps", jsonArray);

        String removeEscape = jsonObject.toJSONString().replaceAll("\\\\", "");

        FileWriter fileWriter = new FileWriter(deploymentJSONURI);
        fileWriter.write(removeEscape);

        fileWriter.flush();

    }
}
