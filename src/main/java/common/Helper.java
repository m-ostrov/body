package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    private static final String HOME = System.getProperty("user.dir");
    private static final String SEP = File.separator;

    public static String getUrlSection(String url){
        String result = "";
        Pattern pattern = Pattern.compile("^http(.*?)//(.*?)/(.*)$");
        Matcher matcher = pattern.matcher(url);
        while(matcher.find()){
            result = matcher.group(3);
        }
        return result;
    }

    public static String getHomeDir(String[] tree){
        StringBuilder builder = new StringBuilder(HOME);
        Arrays.asList(tree).forEach(folder -> builder.append(SEP + folder));
        return builder.toString();
    }


    public static String getStringFromProperties(String file,String key){
        Properties properties = new Properties();
        String result = "";
        try {
            properties.load(new FileInputStream(file));
            result = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static boolean isThing(String str){
        boolean result = false;
        if(str != null &! str.isEmpty()){
            result = true;
        }
        return result;
    }

    public static String constructUrl( String universe, Map<String,String> parameters){
        String result = Helper.getStringFromProperties(Helper.getHomeDir(new String[]{"config","config.properties"}),"template.url");
        Set<String> keys = parameters.keySet();
        StringBuilder builder = new StringBuilder(result);
        keys.forEach(key -> builder.append(key + "=" + (String) parameters.get(key) + "&"));
        return builder.toString();
    }
}
