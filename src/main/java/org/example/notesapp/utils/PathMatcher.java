package org.example.notesapp.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathMatcher{
    private Map<String,String> parameters = new HashMap<>();

    public boolean match (String originalPath, String pathPattern){
        Pattern parameterPattern = Pattern.compile("\\{(\\w+)}");
        String[] patternParts = pathPattern.split("/");
        String[] pathParts = originalPath.split("/");

        if (patternParts.length !=pathParts.length) return false;

        for (int i = 0; i < patternParts.length; i++) {
            Matcher matcher = parameterPattern.matcher(patternParts[i]);
            if (!patternParts[i].equalsIgnoreCase(pathParts[i]) && !matcher.find()) return false;

            matcher.reset();
            if (matcher.find()) parameters.put(matcher.group(1),pathParts[i]);
        }
        return true;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
