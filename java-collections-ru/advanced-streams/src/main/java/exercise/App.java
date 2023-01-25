package exercise;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

// BEGIN
class App {

    public static void main(String[] args) {
        String file = "[bar:baz]\n" +
                "environment=\"key2=true,X_FORWARDED_var2=123\"\n" +
                "command=sudo -HEu tirion /bin/bash -c 'cd /usr/src/app && make prepare'";

        String file2 = "[program:options]\n" +
                "environment=\"X_FORWARDED_variable=value,  \"\n" +
                "\n" +
                "key=value";

        String file3 = "[foo:bar]\n" +
                "environment=\"X_FORWARDED_var1=111\"\n" +
                "\n" +
                "[bar:baz]\n" +
                "environment=\"key2=true,X_FORWARDED_var2=123\"\n" +
                "command=sudo -HEu tirion /bin/bash -c 'cd /usr/src/app && make prepare'\n" +
                "\n" +
                "[baz:foo]\n" +
                "key3=\"value3\"\n" +
                "command=sudo -HEu tirion /bin/bash -c 'cd /usr/src/app && make prepare'\n" +
                "\n" +
                "[program:prepare]\n" +
                "environment=\"key5=value5,X_FORWARDED_var3=value,key6=value6\"\n" +
                "\n" +
                "[program:start]\n" +
                "environment=\"pwd=/temp,user=tirion\"\n" +
                "\n" +
                "[program:options]\n" +
                "environment=\"X_FORWARDED_mail=tirion@google.com,X_FORWARDED_HOME=/home/tirion,language=en\"\n" +
                "command=sudo -HEu tirion /bin/bash -c 'cd /usr/src/app && make environment'\n" +
                "\n" +
                "[empty]\n" +
                "command=\"X_FORWARDED_HOME=/ cd ~\"";

        System.out.println(getForwardedVariables(file3));
    }

    public static String getForwardedVariables(String fileContents) {
        List<String> environmentVariables = Arrays.stream(fileContents.split("\n"))
                .filter(str -> str.startsWith("environment="))
                .map(str2 -> str2.replaceAll("environment=", ""))
                .flatMap(item -> Arrays.stream(item.split(",")))
                .map(str2 -> str2.replaceAll("\"", ""))
                .filter(element -> element.startsWith("X_FORWARDED_"))
                .map(str2 -> str2.replaceAll("X_FORWARDED_", ""))
                .collect(Collectors.toList());
        return String.join(",", environmentVariables);
    }
}
//END
