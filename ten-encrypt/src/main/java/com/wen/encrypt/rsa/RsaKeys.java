package com.wen.encrypt.rsa;

/**
 * rsa加解密用的公钥和私钥
 * @author Administrator
 *
 */
public class RsaKeys {

	//生成秘钥对的方法可以参考这篇帖子
	//https://www.cnblogs.com/yucy/p/8962823.html

//	//服务器公钥
//	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6Dw9nwjBmDD/Ca1QnRGy"
//											 + "GjtLbF4CX2EGGS7iqwPToV2UUtTDDemq69P8E+WJ4n5W7Iln3pgK+32y19B4oT5q"
//											 + "iUwXbbEaAXPPZFmT5svPH6XxiQgsiaeZtwQjY61qDga6UH2mYGp0GbrP3i9TjPNt"
//											 + "IeSwUSaH2YZfwNgFWqj+y/0jjl8DUsN2tIFVSNpNTZNQ/VX4Dln0Z5DBPK1mSskd"
//											 + "N6uPUj9Ga/IKnwUIv+wL1VWjLNlUjcEHsUE+YE2FN03VnWDJ/VHs7UdHha4d/nUH"
//											 + "rZrJsKkauqnwJsYbijQU+a0HubwXB7BYMlKovikwNpdMS3+lBzjS5KIu6mRv1GoE"
//											 + "vQIDAQAB";
//
//	//服务器私钥(经过pkcs8格式处理)
//	private static final String serverPrvKeyPkcs8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDoPD2fCMGYMP8J"
//				 								 + "rVCdEbIaO0tsXgJfYQYZLuKrA9OhXZRS1MMN6arr0/wT5YniflbsiWfemAr7fbLX"
//				 								 + "0HihPmqJTBdtsRoBc89kWZPmy88fpfGJCCyJp5m3BCNjrWoOBrpQfaZganQZus/e"
//				 								 + "L1OM820h5LBRJofZhl/A2AVaqP7L/SOOXwNSw3a0gVVI2k1Nk1D9VfgOWfRnkME8"
//				 								 + "rWZKyR03q49SP0Zr8gqfBQi/7AvVVaMs2VSNwQexQT5gTYU3TdWdYMn9UeztR0eF"
//				 								 + "rh3+dQetmsmwqRq6qfAmxhuKNBT5rQe5vBcHsFgyUqi+KTA2l0xLf6UHONLkoi7q"
//				 								 + "ZG/UagS9AgMBAAECggEBANP72QvIBF8Vqld8+q7FLlu/cDN1BJlniReHsqQEFDOh"
//				 								 + "pfiN+ZZDix9FGz5WMiyqwlGbg1KuWqgBrzRMOTCGNt0oteIM3P4iZlblZZoww9nR"
//				 								 + "sc4xxeXJNQjYIC2mZ75x6bP7Xdl4ko3B9miLrqpksWNUypTopOysOc9f4FNHG326"
//				 								 + "0EMazVaXRCAIapTlcUpcwuRB1HT4N6iKL5Mzk3bzafLxfxbGCgTYiRQNeRyhXOnD"
//				 								 + "eJox64b5QkFjKn2G66B5RFZIQ+V+rOGsQElAMbW95jl0VoxUs6p5aNEe6jTgRzAT"
//				 								 + "kqM2v8As0GWi6yogQlsnR0WBn1ztggXTghQs2iDZ0YkCgYEA/LzC5Q8T15K2bM/N"
//				 								 + "K3ghIDBclB++Lw/xK1eONTXN+pBBqVQETtF3wxy6PiLV6PpJT/JIP27Q9VbtM9UF"
//				 								 + "3lepW6Z03VLqEVZo0fdVVyp8oHqv3I8Vo4JFPBDVxFiezygca/drtGMoce0wLWqu"
//				 								 + "bXlUmQlj+PTbXJMz4VTXuPl1cesCgYEA6zu5k1DsfPolcr3y7K9XpzkwBrT/L7LE"
//				 								 + "EiUGYIvgAkiIta2NDO/BIPdsq6OfkMdycAwkWFiGrJ7/VgU+hffIZwjZesr4HQuC"
//				 								 + "0APsqtUrk2yx+f33ZbrS39gcm/STDkVepeo1dsk2DMp7iCaxttYtMuqz3BNEwfRS"
//				 								 + "kIyKujP5kfcCgYEA1N2vUPm3/pNFLrR+26PcUp4o+2EY785/k7+0uMBOckFZ7GIl"
//				 								 + "FrV6J01k17zDaeyUHs+zZinRuTGzqzo6LSCsNdMnDtos5tleg6nLqRTRzuBGin/A"
//				 								 + "++xWn9aWFT+G0ne4KH9FqbLyd7IMJ9R4gR/1zseH+kFRGNGqmpi48MS61G0CgYBc"
//				 								 + "PBniwotH4cmHOSWkWohTAGBtcNDSghTRTIU4m//kxU4ddoRk+ylN5NZOYqTxXtLn"
//				 								 + "Tkt9/JAp5VoW/41peCOzCsxDkoxAzz+mkrNctKMWdjs+268Cy4Nd09475GU45khb"
//				 								 + "Y/88qV6xGz/evdVW7JniahbGByQhrMwm84R9yF1mNwKBgCIJZOFp9xV2997IY83S"
//				 								 + "habB/YSFbfZyojV+VFBRl4uc6OCpXdtSYzmsaRcMjN6Ikn7Mb9zgRHR8mPmtbVfj"
//				 								 + "B8W6V1H2KOPfn/LAM7Z0qw0MW4jimBhfhn4HY30AQ6GeImb2OqOuh3RBbeuuD+7m"
//				 								 + "LpFZC9zGggf9RK3PfqKeq30q";

	//服务器公钥
	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxWARt+Za/zJOJ2G5WxZJ\n" +
			"YZGfK1cWabCT7hqvOmz9G40egcBiltFWDpGFo5cy8e5UFfbHQ8hENmBBR+sivz6g\n" +
			"fGVeQe9k/3Vep2vbt/lNL7S8VkSpzIYaIZi6VRss9UBUilH1mNqiRMi5EqDHUcx5\n" +
			"uwsJBIxUFdkTpfDQODfCF8Ul9baeKnN7/ErEEcUR2NsUEMYYo1uXYVjWUllvbOLJ\n" +
			"vj9XNlSeFV2cbr1W17JvRi1ZfHeWSs13wlRCn81DBHFxxjP0aRYZ2h4A37fwAqiL\n" +
			"V3hAOAxfneAIT8EtnCCDsaIa6zgH6odB1tb2y7ogUFGrMQ2nfmzIiqM+suOtphKd\n" +
			"JQIDAQAB";

	//服务器私钥(经过pkcs8格式处理)
	private static final String serverPrvKeyPkcs8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDFYBG35lr/Mk4n\n" +
			"YblbFklhkZ8rVxZpsJPuGq86bP0bjR6BwGKW0VYOkYWjlzLx7lQV9sdDyEQ2YEFH\n" +
			"6yK/PqB8ZV5B72T/dV6na9u3+U0vtLxWRKnMhhohmLpVGyz1QFSKUfWY2qJEyLkS\n" +
			"oMdRzHm7CwkEjFQV2ROl8NA4N8IXxSX1tp4qc3v8SsQRxRHY2xQQxhijW5dhWNZS\n" +
			"WW9s4sm+P1c2VJ4VXZxuvVbXsm9GLVl8d5ZKzXfCVEKfzUMEcXHGM/RpFhnaHgDf\n" +
			"t/ACqItXeEA4DF+d4AhPwS2cIIOxohrrOAfqh0HW1vbLuiBQUasxDad+bMiKoz6y\n" +
			"462mEp0lAgMBAAECggEAHKD8sPIGzPFdCLVSD3ZCGpbmwYCWTapBKs5QEGdypyZn\n" +
			"AGcJZdrjYqWU/s+VoiXxqGXtLVXFHyYDEg13MspAuQpzC5lPywYZPquUaTEOExdi\n" +
			"Ihk/j9z7uVfP3zxb3w7/tDiDKTK9SsDap2sOvjysg4bsPe6k265q/Sza5uyfiJbc\n" +
			"vPJRZdCLutEjx7MCrHzN5JtzXNakm4At69XYxwx+Xb0yGJ570pEDx19gf4AZ1WhR\n" +
			"ennsMUPw3Hi7pGxLikSlfLlMhKDAG/LkGDLqg2reVnjpynCuQvVKbdLXBkxhJQCr\n" +
			"uH5S2B8u3d4RSvzaaIvL1Ty0x5F33mg1ieC2UY0JmQKBgQDvl7if20oZBYkStYo7\n" +
			"5s547j7rghCNas+fIq12xd65bz3h3RLRiaoY2MnZ4V1GtCwhy9Lo0vq071n+Flry\n" +
			"kG8V9rZ25C7gQ2uh25g3P5kHMSFgNG8aP4+0ipBCN47AkCiHURfPcy6yy0C3/tdu\n" +
			"qkVjM1nu9DAqHBh0SGjI05aWLwKBgQDS5D0OFjt+/Ra5Ci28zlqIPuBe1fEbP5qT\n" +
			"BLJ40RnJC2wbZ//Ox5JhX66oWbCGMYk+8JeSSk6w7mdVJhNAR596GOGVfTiI6Btz\n" +
			"xukyDBzFxRZJ+b7A4XoILHY3JOwMPISZOeAk5xsvTnTtmpNflMDP6FvR2N3knZ1E\n" +
			"MVO7V6VA6wKBgFrApXYnohGIPzuO+CVob5zu4XZjddGbO7mTcREkMhEbSd1b19z2\n" +
			"wC9ZLlwROTum03CZu/HkuIIAh53u6dQnUWyIXuT52aeo4gQVIbsLo/NNcM74DX1r\n" +
			"xgsk0S/Dy5a1BCT9Wn+BqZaf1ovbQ87vQAkrUk1owTWyHrEtHEO1obyRAoGBAKeL\n" +
			"4yHk8eHC6yGZf5roCq8B3TobGNHAJ2s0KSKpZaX/KCVh6675iUdJQDbKAt+qTVKu\n" +
			"zIzR8lDJJ1lTxamk/YOAbwBYGulSFjVWS4CSdO1+1mVxQGZ2/H+v6z+GWu6hyer+\n" +
			"OyC2ZzefSnBXBUzDnqfO/9vpQujVe9NoqRBG4qh/AoGBAOTMh6VQQ/avCBvV5iNz\n" +
			"fvgLYtc8kJkAZ4Szj9MW4tRQS7e/0gidDa2a9S3hrk3l48iAHIIsRp25JOK5n1Aq\n" +
			"r5PP1IExgXV1yTn1ySlQ16ngUBAldec0xLv6OhENdtN9qOaw91DzO50euqD/hFj8\n" +
			"re3P4rRIdI7ZF0qIpvRu5u5A";

	public static String getServerPubKey() {
		return serverPubKey;
	}

	public static String getServerPrvKeyPkcs8() {
		return serverPrvKeyPkcs8;
	}
	
}
