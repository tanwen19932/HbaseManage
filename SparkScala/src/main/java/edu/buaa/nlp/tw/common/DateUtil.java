package edu.buaa.nlp.tw.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtil {
    static final List<String> regexList = new ArrayList<>();

    // By PA Aug 24, 2015
    static {
        regexList.add( "yyyy-MM-dd HH:mm:ss" );
        regexList.add( "yyyy-MM-dd HH:mm:ss.0" );
        regexList.add( "MM\\.dd" );
        regexList.add( "yyyy-MM-dd HH:mm" );
        regexList.add( "yyyy-MM-dd" );
        regexList.add( "MMM dd, yyyy" );
        regexList.add( "于yyyy年MM月dd日" );
        regexList.add( "yyyy年MM月dd日" );
        regexList.add( "MMM dd, yyyy" );
        regexList.add( "MMM dd,HH:mm:ss" );
        // Tue, 04/26/2016 - 07:23
        regexList.add( "EEEE, MM/dd/yyyy - HH:mm" );
        // 2016-04-14T12:52:17Z
        regexList.add( "yyyy-MM-ddHH:mm:ss" );
    }

    public static String tryParse(String date) {

        SimpleDateFormat sdf2 = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

        for (int i = 0; i < regexList.size(); i++) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat( regexList.get( i ), Locale.ENGLISH );
                return sdf2.format( sdf.parse( date ) );
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static Date getDate(String date) {

        for (int i = 0; i < regexList.size(); i++) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat( regexList.get( i ), Locale.ENGLISH );
                return sdf.parse( date );
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static Date getFixDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-dd-MM HH:mm:ss", Locale.ENGLISH );
            return sdf.parse( date );
        } catch (Exception e) {
        }
        return null;
    }
    
    /**
     * @param date time  long 
     * @return yyyy-MM-dd  HH:mm:ss
     */
    public static String getFixDateStr(Long date) {
    	return getDateStr("yyyy-MM-dd HH:mm:ss", date);
    }
    
    public static String getFixDateStr(Date date) {
        return getDateStr("yyyy-MM-dd HH:mm:ss", date);
    }
    public static String getyyyy_MM_dd(Long date) {
        return getDateStr("yyyy-MM-dd", date);
    }
    public static String getyyyy_MM_dd(Date date) {
        return getDateStr("yyyy-MM-dd", date);
    }
    public static String getDateStr(String simpleDateFormatStr, Date date) {
    	 SimpleDateFormat sdf = new SimpleDateFormat( simpleDateFormatStr, Locale.ENGLISH );
         try {
             return sdf.format( date );
         } catch (Exception e) {
         }
         return null;
    }
    
    public static String getDateStr(String simpleDateFormatStr, Long date) {
   	 SimpleDateFormat sdf = new SimpleDateFormat( simpleDateFormatStr, Locale.ENGLISH );
        try {
            return sdf.format( new Date(date) );
        } catch (Exception e) {
        }
        return null;
   }
}
