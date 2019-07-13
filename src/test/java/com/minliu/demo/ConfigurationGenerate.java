
import com.minliu.demo.Column;
import com.minliu.demo.ReadFromConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName: JDBCTest <br>
 * date: 3:54 PM 03/07/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */

public class ConfigurationGenerate {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationGenerate.class);
    private static final String URL = "jdbc:oracle:thin:@10.255.31.44:1521:hyperiondb";
    private static final String USERNAME = "rep1";
    private static final String PASSWORD = "oracle";

    private static final List<String> array = Arrays.asList("UUID", "GJAHR", "YEAR",
            "MONAT", "PERIOD", "CURRENCY", "BUKRS", "ENTITY", "ENTITYT", "BUTXT",
            "CREATOR", "DATASOURCE", "CREATEDATE", "BUSINESS", "FORM");

    private static List<String> suffixes = Arrays.asList("_TZ", "_PBC", "_TZH");

    private static List<String> prefixes = Arrays.asList("A", "B", "C");

    private static List<String> uselessColumns = new ArrayList<>(array);

    private static final StringBuilder sb = new StringBuilder();

    private static boolean isUpRow = false;

    private static String allColumnStr;


    private static String getSql(String tableName) {
        String sql = "SELECT\n" +
                "  COLUMN_NAME,\n" +
                "  DATA_TYPE\n" +
                "FROM USER_TAB_COLS\n" +
                "WHERE TABLE_NAME = ";
        return sql + "'" + tableName + "'";
    }


    private static Connection getConnection() throws Exception {
        Class.forName("oracle.jdbc.OracleDriver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    //获取表字段并过滤无用的属性
    private static List<Column> loadData(String tableName) {
        List<Column> columns = new ArrayList<>();
        String sql = getSql(tableName);
        logger.info(sql);
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet resultSet = pst.executeQuery()) {
            //填充属性
            while (resultSet.next()) {
                Column column = new Column();
                if (StringUtils.hasText(resultSet.getString(1))) {
                    column.setColumnName(resultSet.getString(1));
                    column.setDataType(resultSet.getString(2));
                }
                columns.add(column);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        //先缓存所有的字段拼接，后面Where条件用到
        allColumnStr = String.join(",",
                columns.stream().map(Column::getColumnName).collect(Collectors.toList()));
        columns.removeIf(column -> uselessColumns.contains(column.getColumnName()));
        return columns;
    }

    private static String TABLE_NAME = "CMBN_PBC1_ZCK_U1210";

    private static String compareProperty = "";

    public static void main(String[] args) {
        List<Column> columns = loadData(TABLE_NAME);

        begin();

        forType(columns);

        forRmk(TABLE_NAME, columns);

        forMap(columns);

        if (isUpRow) {
            forCompare();
        }

        forSql(TABLE_NAME);


        end();
        logger.info("\r\n{}", sb.toString());
        System.exit(0);
    }

    private static void forSql(String tableName) {
        String sql = "SELECT * FROM " + tableName + " WHERE ";

        if (allColumnStr.contains("ENTITY")) {
            sql = sql + "ENTITY='#{ENTITY}' ";
        } else if (allColumnStr.contains("BUKRS")) {
            sql = sql + "BUKRS='#{ENTITY}' ";
        }

        if (allColumnStr.contains("YEAR")) {
            sql = sql + "AND YEAR='#{END_YEAR}' ";
        } else if (allColumnStr.contains("GJAHR")) {
            sql = sql + "AND GJAHR='#{END_YEAR}' ";
        }

        if (allColumnStr.contains("PERIOD")) {
            sql = sql + "AND PERIOD='#{END_PERIOD}' ";
        } else if (allColumnStr.contains("MONAT")) {
            sql = sql + "AND MONAT='#{END_PERIOD}' ";
        }

        sb.append("SQL:").append(sql);

    }

    private static void forCompare() {
        logger.info("--COMPARE PROPERTY--\r\n");
        logger.info("FROM:\r\n");
        String from = ReadFromConsole.readAndReturn();
        logger.info("TO:\r\n");
        String to = ReadFromConsole.readAndReturn();
        sb.append("COMPARE:").append(compareProperty).append("=")
                .append(from).append("&").append(to).append(";");
    }

    private static void forType(List<Column> columns) {
        logger.info("\r\n1:ROW\r\n2:UPROW");
        sb.append("TYPE:");
        while (true) {
            String type = ReadFromConsole.readAndReturn();
            if ("1".equals(type)) {
                sb.append("ROW").append(";");
                break;
            } else if ("2".equals(type)) {
                sb.append("UPROW").append(";");
                isUpRow = true;
                break;
            }
        }
        if (isUpRow) {
            logger.info("Which is the compare property? Please input the number.\r\n");
            for (int i = 0; i < columns.size(); i++) {
                Column column = columns.get(i);
                int j = i;
                logger.info("{}:{}\r\n", j + 1, column.getColumnName());
            }
            while (true) {
                try {
                    int index = Integer.parseInt(ReadFromConsole.readAndReturn());
                    if (index > columns.size() || index <= 0) {
                        continue;
                    }
                    compareProperty = columns.get(index - 1).getColumnName();
                    columns.remove(index - 1);
                    break;
                } catch (Exception e) {
                    logger.error("输入的不是数字！");
                }
            }
        }
    }

    private static void forRmk(String tableName, List<Column> columns) {
        sb.append("RMK:[").append(tableName).append(",UUID,");
        List<String> numStrs = columns.stream().filter(column -> column.getDataType().contains("NUMBER"))
                .map(Column::getColumnName)
                .collect(Collectors.toList());
        sb.append(String.join(",", numStrs)).append("];");
    }

    private static List<String> getNumberColumns(List<Column> columns) {
        return columns.stream()
                .filter(column -> "NUMBER".equals(column.getDataType()))
                .map(Column::getColumnName)
                .collect(Collectors.toList());
    }

    private static void forMap(List<Column> columns) {
        sb.append("MAP:");
        //缓存 NUMBER类型无后缀的字段的映射
        Map<String, String> numMap = new LinkedHashMap<>();
        int size = columns.size();

        //获取NUMBER类型无后缀的字段
        List<String> list = getNumberColumns(columns);
        //获取所有字段名
        List<String> strList = columns.stream()
                .map(Column::getColumnName).collect(Collectors.toList());
        //获取没有后缀的字段
        List<String> columnsWithoutSuffix = getColumnsWithoutSuffix(strList);

        columnsWithoutSuffix.forEach(column -> {
            logger.info("\r\n" + column);
            String mapping = ReadFromConsole.readAndReturn();
            if (list.contains(column)) {
                numMap.put(mapping, column);
            }
            sb.append(mapping)
                    .append("=")
                    .append(column);
            if (strList.indexOf(column) < size - 1) {
                sb.append(",");
            }
        });
        handleColumnsWithSuffix(numMap);
        sb.append(";");
    }

    private static void handleColumnsWithSuffix(Map<String, String> map) {
        List<String> list = new ArrayList<>();
        map.forEach((key, value) ->
                suffixes.forEach(suffix -> {
                    int index = suffixes.indexOf(suffix);
                    String prefix = prefixes.get(index);
                    list.add(prefix + key + "=" + value + suffix);
                })
        );
        sb.append(String.join(",", list));
    }

    //获取带后缀字段
    private static List<String> getColumnsWithSuffix(List<String> allColumns) {
        return allColumns.stream().filter(ConfigurationGenerate::isEndWithSuffix)
                .collect(Collectors.toList());
    }

    //获取不带后缀字段
    private static List<String> getColumnsWithoutSuffix(List<String> allColumns) {
        allColumns.removeAll(getColumnsWithSuffix(allColumns));
        return allColumns;
    }

    /**
     * 是否是后缀结尾
     *
     * @param column 字段名
     * @return boolean
     */
    private static boolean isEndWithSuffix(String column) {
        for (String suffix : suffixes) {
            if (column.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }


    private static void begin() {
        sb.append("LOAD{");
        TABLE_NAME = TABLE_NAME.toUpperCase();
    }

    private static void end() {
        sb.append("}");
    }

}
