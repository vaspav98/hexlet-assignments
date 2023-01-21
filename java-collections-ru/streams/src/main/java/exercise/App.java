package exercise;

import java.util.List;

// BEGIN
class App {

    public static void main(String[] args) {
        List<String> emails = List.of(
                "info@gmail.com",
                "info@yandex.ru",
                "mk@host.com",
                "support@hexlet.io",
                "info@hotmail.com",
                "support.yandex.ru@host.com"
        );
        System.out.println(getCountOfFreeEmails(emails));
    }

    public static long getCountOfFreeEmails(List<String> emails) {
        List<String> freeDomains = List.of("gmail.com", "yandex.ru", "hotmail.com");
        return emails.stream()
                .map(email -> email.split("@")[1])
                .filter(email -> freeDomains.contains(email))
                .count();

// Другой вариант реализации
//        return emails.stream()
//                .filter(email -> email.endsWith("@gmail.com")
//                        || email.endsWith("@yandex.ru") || email.endsWith("@hotmail.com"))
//                .count();
    }
}
// END
