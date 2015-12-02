package util;

public class LogUtil {

	public static void error(String msg, Exception ex) {
		System.out.print("msg:"+msg+"ex:"+ex.getMessage());
	}

}
