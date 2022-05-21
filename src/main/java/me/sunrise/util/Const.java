package me.sunrise.util;

public class Const {
//    public static final String TOKEN_CLAIM_USER_ID = "userId";

    // old code from vbi
    public final static String reportFromDate = "from_date";

    public final static String OTP_EXPIRED = "OTP_EXPIRED";

    public final static String ERROR = "ERROR";
    public final static String PASSWORD_DEFAULT = "123456Aa@";
    // driver name
    public final static String DRIVER_ORACLE = "oracle";
    public final static String DRIVER_MYSQL = "mysql";
    public final static String DRIVER_HIVE_DB = "hive";


    /*postgresql*/
//    public final static String SCHEMA = "\"AUTOML_PROD\"";
    /*oracle*/
    public final static String SCHEMA = "AUTOML_PROD";
//    public final static String SCHEMA = "AUTOML";
//    public final static String schema = Const.SCHEMA;

    public static final class ERROR_CODE {
        public static final String SUCCESS = "0";
        public static final String FAIL = "1";
    }

    // ------
    public static final String USERS_CONFIG_FILE = "users.json";

    public static final char DEFAULT_ESCAPE_CHAR = '&';

    public static final String AES_KEY = "v13ttEl_mObil3bI_k@rcut4";

    public interface TIME_TYPE {
        Long DATE = 1L;
        Long MONTH = 2L;
        Long QUARTER = 3L;
        Long YEAR = 4L;
    }

    public interface ROLE_CODE {
        String ADMIN = "ADMIN";
        String VIEW = "VIEW";
    }

    public interface SPECIAL_CHAR {
        String SPACE = " ";
        String UNDERSCORE = "_";
        String DOT = ".";
        String COMMA = ",";
        String COLON = ":";
        String SLASH = "/";
        String BACKSLASH = "\\";
    }
}
