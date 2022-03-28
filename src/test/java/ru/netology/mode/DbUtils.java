package ru.netology.mode;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DbUtils {
    @SneakyThrows
    public static String getVerificationCode() {
        var runner = new QueryRunner();
        var codeSQL = "SELECT code FROM auth_codes WHERE created = (SELECT max(created) FROM auth_codes);";
        String verificationCode = "";
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                PreparedStatement preparedStatement = conn.prepareStatement(codeSQL);
        ) {
            val code = runner.query(conn, codeSQL, new ScalarHandler<>());
            System.out.println(code);
            verificationCode = (String) code;
        }
        return verificationCode;
    }
}