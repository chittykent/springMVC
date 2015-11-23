package com.app.simple.utils;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @desc
 * @author Hongbing.Zhang
 * @email  Hongbing.Zhang@163.com
 * @date 2011-2-11
 * @version 1.0
 */
public class StringHelper
{
    private static final String elide = "…";
    
    private StringHelper()
    {
    }

    public static boolean iswide(int ucs)
    {
        return ucs >= 0x1100 && ((ucs >= 0x1100 && ucs <= 0x115f) || /* Hangul Jamo */
                (ucs >= 0x2e80 && ucs <= 0xa4cf && (ucs & ~0x0011) != 0x300a && ucs != 0x303f) ||  /* CJK ... Yi */
                (ucs >= 0xac00 && ucs <= 0xd7a3) || /* Hangul Syllables */
                (ucs >= 0xf900 && ucs <= 0xfaff) || /* CJK Compatibility Ideographs */
                (ucs >= 0xfe30 && ucs <= 0xfe6f) || /* CJK Compatibility Forms */
                (ucs >= 0xff00 && ucs <= 0xff5f) || /* Fullwidth Forms */
                (ucs >= 0xffe0 && ucs <= 0xffe6));
    }

    public static boolean iswide(char ch)
    {
        return iswide((int) ch);
    }
    public static int getDisplayLength(String str)
    {
        if(StringUtils.isBlank(str)) return 0;
        char[] chArr = str.toCharArray();
        int l = 0;
        for (char ch : chArr)
        {
            if(iswide((int)ch)) l+=2;
            else l++;
        }
        return l;
    }

    public static String abbreviate(String input, int showLength)
    {
        if (showLength <= 1) throw new RuntimeException("Wrong length Argument!");
        if (showLength == 2)
        {
            char ch = input.charAt(0);
            if(StringHelper.iswide(ch))
            {
                return String.valueOf(ch);
            }
            else
            {
                return String.valueOf(ch) + elide;
            }
        }

        int elideLength = elide.length();//如果elide包含双字节字符,应改用getDisplayLength

        boolean tooShort = showLength <= elideLength;
        //if(!tooShort) showLength -= elideLength;


        char[] charArray = input.toCharArray();
        int charArrayLength = charArray.length;

        boolean append = false;
        int nextLength = 0;
        int i = 0;
        for (i = 0; i < charArrayLength; i++)
        {
            char next = charArray[i];
            boolean iswide = StringHelper.iswide(next);
            if (iswide)
            {
                nextLength += 2;
            }
            else
            {
                nextLength++;
            }

            //无法添加下一个字符
            if (nextLength > showLength)
            {
                append = true;
                break;
            }
            //刚好可以添加下一个字符,如果是字符串末位则不append,否则不添加这个字符并append
            else if (nextLength == showLength)
            {
                if (i+1 == charArrayLength)//末尾
                {
                    append = false;
                    i++;
                }
                else
                {
                    //不是末尾
//                    if (nextLength + elideLength > showLength)
//                    {
                        //添加elide也会超长
//                        i--;
//                    }
                    append = true;
                }
                break;
            }

        }
        String result = new String(charArray, 0, i);
        if (!tooShort && append) result += elide;
        return result;
    }
    
}
