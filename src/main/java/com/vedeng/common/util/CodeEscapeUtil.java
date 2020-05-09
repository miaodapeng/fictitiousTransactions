/**
 * <b>Description:特殊字符编码转义工具</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName CodeEscapeUtil.java
 * <br><b>Date: 2018年4月28日 下午4:16:44 </b> 
 *
 */
package com.vedeng.common.util;

public class CodeEscapeUtil
{
	
	private final static String[] HEX =
		{ 	"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", 
			"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B", "1C", "1D", "1E", "1F", 
			"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", 
			"30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C", "3D", "3E", "3F", 
			"40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4A", "4B", "4C", "4D", "4E", "4F", 
			"50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D", "5E", "5F", 
			"60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6A", "6B", "6C", "6D", "6E", "6F", 
			"70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E", "7F", 
			"80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8A", "8B", "8C", "8D", "8E", "8F",
			"90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F", 
			"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA", "AB", "AC", "AD", "AE", "AF", 
			"B0", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", 
			"C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB", "CC", "CD", "CE", "CF", 
			"D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", 
			"E0", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC", "ED", "EE", "EF", 
			"F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF" 
		};
		private final static byte[] VAL =
		{ 	0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 
			0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 
			0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 
			0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F 
		};
		
		private final static char[] TSZF = {'`', '!', '@', '#', '$', '%', '^', '*', '.', '(', ')', '-', '=', '{', '}', '|', '?', '<', '>', '"', ':', '~', '_' };
		
		/**
		 * 
		 * <b>Description:将字符串转义</b><br> 
		 * @param oldStr 未转义字符串
		 * @return
		 * <b>Author: Franlin</b>  
		 * <br><b>Date: 2018年5月2日 上午8:51:56 </b>
		 */
		public static String escapeString(String oldStr)
		{
			return escapeString(oldStr, '-');
		}
		
		/**
		 * 
		 * <b>Description:将转义后的字符串还原</b><br> 
		 * @param escapeStr 转义字符串
		 * @return
		 * <b>Author: Franlin</b>  
		 * <br><b>Date: 2018年5月2日 上午8:57:30 </b>
		 */
		public static String restoreString(String escapeStr)
		{
			return restoreString(escapeStr, '-');
		}
		
		/**
		 * 
		 * <b>Description: 将字符串转义</b><br> 
		 * @param oldStr   未转义字符串
		 * @param escapeChar 转义字符'-'
		 * @return
		 * <b>Author: Franlin</b>  
		 * <br><b>Date: 2018年5月2日 上午8:58:16 </b>
		 */
		private static String escapeString(String oldStr, char escapeChar)
		{
			StringBuffer sbuf = new StringBuffer();
			int len = oldStr.length();
			for (int i = 0; i < len; i++)
			{
				int ch = oldStr.charAt(i);
				if (ch == ' ')
				{ // space : map to '+'
					sbuf.append('+');
				}
				else if ('A' <= ch && ch <= 'Z')
				{ // 'A'..'Z' : as it was
					sbuf.append((char) ch);
				}
				else if ('a' <= ch && ch <= 'z')
				{ // 'a'..'z' : as it was
					sbuf.append((char) ch);
				}
				else if ('0' <= ch && ch <= '9')
				{ // '0'..'9' : as it was
					sbuf.append((char) ch);
				}

				else if (ch == escapeChar)
				{
					// other ASCII : map to -XX
					if (ch <= 0x007F)
					{
						sbuf.append(escapeChar);
						sbuf.append(HEX[ch]);
					}
				}
				else
				{
					sbuf.append(escapeChar);
					sbuf.append('z');
					sbuf.append(HEX[(ch >>> 8)]);
					sbuf.append(HEX[(0x00FF & ch)]);
				}

			}
			return sbuf.toString();
		}

		/**
		 * 
		 * <b>Description: 还原转义字符串</b><br> 
		 * @param escapeStr 转义字符串
		 * @param escapeChar 转义字符, 默认'-'
		 * @return
		 * <b>Author: Franlin</b>  
		 * <br><b>Date: 2018年5月2日 上午8:58:53 </b>
		 */
		private static String restoreString(String escapeStr, char escapeChar)
		{
			StringBuffer sbuf = new StringBuffer();
			int i = 0;
			int len = escapeStr.length();
			while (i < len)
			{
				int ch = escapeStr.charAt(i);
				// + : map to ' '
				if (ch == '+')
				{
					sbuf.append(' ');
				}
				// 'A'..'Z' : as it was
				else if ('A' <= ch && ch <= 'Z')
				{
					sbuf.append((char) ch);
				}
				// 'a'..'z' : as it was
				else if ('a' <= ch && ch <= 'z')
				{
					sbuf.append((char) ch);
				}
				// '0'..'9' : as it was
				else if ('0' <= ch && ch <= '9')
				{
					sbuf.append((char) ch);
				}
				else if (ch != escapeChar)
				{
					for (char ts : TSZF)
					{
						if (ts == ch)
						{
							sbuf.append((char) ch);
							break;
						}
					}
				}
				else if (ch == escapeChar)
				{
					int cint = 0;
					// -XX : map to ascii(XX)
					if ('z' != escapeStr.charAt(i + 1))
					{
						cint = (cint << 4) | VAL[escapeStr.charAt(i + 1)];
						cint = (cint << 4) | VAL[escapeStr.charAt(i + 2)];
						i += 2;
					}
					// -+XXXX : map to unicode(XXXX)
					else
					{
						cint = (cint << 4) | VAL[escapeStr.charAt(i + 2)];
						cint = (cint << 4) | VAL[escapeStr.charAt(i + 3)];
						cint = (cint << 4) | VAL[escapeStr.charAt(i + 4)];
						cint = (cint << 4) | VAL[escapeStr.charAt(i + 5)];
						i += 5;
					}
					sbuf.append((char) cint);

				}

				i++;
			}
			return sbuf.toString();
		}	
}
