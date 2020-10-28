/**
 * This class  contains Utility methods specific to the framework architechture and 
 * will be used to perform various actions across the framework
 * 
 * @author Subrato
 */


package com.api.utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.Desktop;
import java.awt.image.RenderedImage;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import com.api.base.DriverScript;
import com.api.reports.ExtentManager;

import freemarker.log.Logger;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.DecimalFormat;
import java.util.*;


public class Util{
		
	    public static String logging(String outputToBeLogged){
		    	Logger logger = Logger.getLogger("devpinoyLogger");
		    	logger.debug(outputToBeLogged);
				return outputToBeLogged;
	    }
	    
		public static String getProperty(String strVal) throws IOException{
				String val = null;
				try {
					String path=System.getProperty("user.dir")+"/EnvironmentDetails/config.properties";
					Properties prop = new Properties();
					FileInputStream fs = new FileInputStream(path);
					prop.load(fs);
					val = prop.getProperty(strVal).trim();
				}catch (Throwable t) {
					val =null;
				}
				return val;
			}
		
		public static String getGlobalProperty(String strVal) throws IOException{
				String val = null;
				try {
					String path=System.getProperty("user.dir")+"/EnvironmentDetails/globalvalue.properties";
					Properties prop = new Properties();
					FileInputStream fs = new FileInputStream(path);
					prop.load(fs);
					val = prop.getProperty(strVal).trim();
				}catch (Throwable t) {
					val =null;
				}
				return val;
		}

		/*		
		####################################################################################
		##############################
		# Function Name : appWait
		# No of Parameter : 1
		# Description   : Static wait depends as per user's inout
		# Parameters and its datatype : Integer
		# Developed on : 02/11/2020
		# Developed By : Subrato Sarkar
		####################################################################################
		##############################	
		*/
		public static void appWait(int timeout){							
			try{
				Thread.sleep(timeout);
			}catch(Throwable t){
				System.out.println(t.getMessage());
			}
			
		}
				
				
		/*		
		####################################################################################
		##############################
		# Function Name : appSync
		# No of Parameter : 1
		# Description   : Static wait depends as per user's input
		# Parameters and its datatype : Integer
		# Developed on : 03/06/2020
		# Developed By : Subrato Sarkar
		####################################################################################
		##############################	
		*/
		
		public static void appSync(int timeout){
			try{
				Thread.sleep(timeout*1000);
			}catch(Throwable t){
				System.out.println("The Exception for appWait  is : "+t.getMessage());
			}
		}
				
		/*		
		####################################################################################
		##############################
		# Function Name : htmlReportPathGenerated
		# No of Parameter : 1
		# Description   : To know the HTML report path generated dynamically
		# Parameters and its datatype : String
		# Developed on : 02/21/2020
		# Developed By : Subrato Sarkar
		####################################################################################
		##############################	
		*/
		public static String htmlReportPathGenerated(){
			try{
				return ExtentManager.dynamicHtmlReportPath;
			}catch(Throwable t){
				t.printStackTrace();
				return null;
			}
		}
				
		
		public static String openHTMLReport(String htmlReportPath){
			try {
				if (getProperty("htmlReportToBeOpen").toUpperCase().equals("YES")){
					File file = new File(htmlReportPathGenerated());
			        if(!Desktop.isDesktopSupported()){
			            System.out.println("Desktop is not supported");
			        }
			        Desktop desktop = Desktop.getDesktop();
			       if(file.exists()) desktop.open(file);
			        		return htmlReportPath;
					}else{
							return null;
					}	
			}catch (Throwable t) {
				return null;
			}
		}
		
		
		
		public static boolean openHTMLReport(){
			try {
				if(getProperty("htmlReportToBeOpen").toUpperCase().equals("YES")){
					File file = new File(htmlReportPathGenerated());
			        if(!Desktop.isDesktopSupported()){
			            System.out.println("Desktop is not supported");
			        }
			        Desktop desktop = Desktop.getDesktop();
			       if(file.exists()) desktop.open(file);
			        		return true;
					}else{
							return false;
					}
			}catch (Throwable t) {
				return false;
			}
		}
		
		
		/*		
		####################################################################################
		##############################
		# Function Name : getCurrDateWithTime
		# No of Parameter : 0
		# Description   : To get the current time
		# Parameters and its datatype : String
		# Developed on : 02/21/2020
		# Developed By : Subrato Sarkar
		####################################################################################
		##############################	
		*/
		public static String getCurrDateWithTime(){
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	        Calendar now = Calendar.getInstance();
			return formatter.format(now.getTime()).toString().replace("/", "_").replace(":", "_").replace(" ", "_");
		}
		
		public static String createNewDirectory(String pathDir){
			File file = new File(pathDir);
			if (!file.exists()) {
				if (file.mkdir()) {
					return pathDir;
				} else {
					return null;
				}
			}else{
				return pathDir;
			}
		}
		
		public static String printStringInDoubleQuotes(String elementToBePrintedInDoubleQuotes){
			try{
				return ("\"" + elementToBePrintedInDoubleQuotes + "\"");
			}catch(Throwable t){
				return null;
			}
					
		}
		
		/*		
		####################################################################################
		##############################
		# Function Name : assignNewAccountName
		# No of Parameter : 0
		# Description   : To print a New account for OPUS application, this function is designed exclusively for OPUS application
		# Parameters and its datatype : NA
		# Developed on : 03/01/2020
		# Developed By : Subrato Sarkar
		####################################################################################
		##############################	
		*/
			public static String assignNewAccountName(){
				   Date date = new Date();
			       SimpleDateFormat sdf = new SimpleDateFormat("MMdd_hhmmss"); 
			       String formattedDate = sdf.format(date);
			       return formattedDate + "_" + "NM_Auto_Reg_Phase1";
			}
				
			/*		
			####################################################################################
			##############################
			# Function Name : roundOffTo2DecPlaces
			# No of Parameter : 0
			# Description   : 
			# Parameters and its datatype : 
			# Developed on : 05/25/2020
			# Developed By : Subrato Sarkar
			####################################################################################
			##############################	
			*/	
			public static String roundOffTo2DecPlaces(String value){
				try{
					DecimalFormat f = new DecimalFormat("##.00");
					return f.format(Double.parseDouble(value));
				}catch(Throwable t){
					t.printStackTrace();
					return null;
				}
			}
			
			public static String ucase(String str){
					return str.toUpperCase();
			}
			
			public static String lcase(String str){
				return str.toLowerCase();
			}
			
			
			public static String currentMacIPAddress() {
				InetAddress ip;
				try {	
					ip = InetAddress.getLocalHost();
					System.out.println("your MAC IP address : " + ip.getHostAddress());
					return ip.getHostAddress().trim();				
				} catch (UnknownHostException e) {
					e.printStackTrace();
					return null;
				}
						
			}
			
			
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : roundOffTo2DecPlaces
			 # Description   :
			 # Developed on : 04/02/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String roundOf2Decimal(String val){
				try{
					double input = Double.parseDouble(val);
					return (String.format("%.2f", input).trim());
				}catch(Exception e){
					return ("0.00");
				}
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : printDoubleQuotes
			 # Description   :
			 # Developed on : 04/02/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String printDoubleQuotes(){
				return ("\"");
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : generateRandomInt
			 # Description   :
			 # Developed on : 04/02/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static int generateRandomInt(int upperRange){
				Random random = new Random();
				return random.nextInt(upperRange);
			}
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : generateRandomString
			 # Description   :
			 # Developed on : 04/02/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String generateRandomString(int length) {
				String rand = "abcdefghijklmnopqrstuvwxyz";
				Random random = new Random();
		        if (length < 1) throw new IllegalArgumentException();
		        StringBuilder sb = new StringBuilder(length);
		        for (int i = 0; i < length; i++) {
		            int rndCharAt = random.nextInt(rand.length());
		            char rndChar = rand.charAt(rndCharAt);
		            sb.append(rndChar);
		        }
		        return sb.toString();
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getCurrentMilliSeconds
			 # Description   : Return time in milliseconds
			 # Developed on : 04/02/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
		
			public static long getCurrentMilliSeconds(){
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				return timestamp.getTime();
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getRandomInteger
			 # Description   : Return random integer within a certain range
			 # Developed on : 04/02/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
		
			public static int getRandomInteger(int aStart, int aEnd){
				if (aStart > aEnd) {
					throw new IllegalArgumentException("Start cannot exceed End.");
				}
				Random random=new Random();
				int range = aEnd-aStart;
				int randomNumber =  aStart+random.nextInt(range);
				return randomNumber;
			}
		
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getYourMachineIP
			 # Description   : Return IP of your current machine
			 # Developed on : 04/04/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
		
			public static String getYourMachineIP() {
				InetAddress ip;
				try {
					ip = InetAddress.getLocalHost();
					return ip.getHostAddress().trim();
				} catch (Throwable t) {
					t.printStackTrace();
					return null;
				}
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : createXlFile
			 # Description   : Creating an excel file with sheet name
			 # Developed on : 04/04/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static void createXlFile(String directory, String excelFileName, String sheetName) throws IOException{
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet(sheetName);
				FileOutputStream outputStream = new FileOutputStream(directory+excelFileName+".xlsx");
				workbook.write(outputStream);
			}
		
			public static String getIntger(String input){
				double d = Double.parseDouble(input);
				int intValue = (int) d;
				return String.valueOf(intValue);
			}
		
			public static int randBetween(int start, int end) {
				return start + (int)Math.round(Math.random() * (end - start));
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getDate
			 # Description   : Generate random date(MM/DD/YYYY), user needs to provide range of year, between which he is asking for a date
			 # Developed on : 04/19/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getDate(int rangeStart, int rangeEnd){
				GregorianCalendar gc = new GregorianCalendar();
				String day,month;
				int yr;
				int year = randBetween(rangeStart,rangeEnd);
				gc.set(gc.YEAR, year);
				int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
				gc.set(gc.DAY_OF_YEAR, dayOfYear);
				if(String.valueOf(gc.get(gc.DAY_OF_MONTH)).length() < 2){
					day="0"+String.valueOf(gc.get(gc.DAY_OF_MONTH));
				}else{
					day=String.valueOf(gc.get(gc.DAY_OF_MONTH));
				}
				if(String.valueOf(gc.get(gc.MONTH) + 1).length() < 2){
					month="0"+String.valueOf(gc.get(gc.MONTH) + 1);
				}else{
					month=String.valueOf(gc.get(gc.MONTH) + 1);
				}
				yr=gc.get(gc.YEAR);
				return month+"/"+day+"/"+yr;
			}
		
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getRandomNumber
			 # Description   : Generate random number no based on length of the input
			 # Developed on : 04/19/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getRandomNumber(int targetNumberLength) {
				Random random=new Random();
				int randomNumber=0;
				boolean loop=true;
				while(loop) {
					randomNumber=random.nextInt();
					if(Integer.toString(randomNumber).length()==targetNumberLength && !Integer.toString(randomNumber).startsWith("-")) {
						loop=false;
					}
				}
				return String.valueOf(randomNumber);
			}
		
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getDateByTraversingYear
			 # Description   : Get date by adding or subtracting year(mm/dd/yyyy)
			 # Developed on : 04/29/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getDateByTraversingYear(String inputDate, int yearToTraverse){;
				String yr=String.valueOf(Integer.parseInt(inputDate.split("/")[2])+yearToTraverse);
				String outputDate=inputDate.split("/")[0]+"/"+inputDate.split("/")[1]+"/"+yr;
				return outputDate;
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getDateTraveringByMonth
			 # Description   : Get date by adding or subtracting month(mm/dd/yyyy), if bool=true, then it will return 1st day of month, otherwise traversing month from current date
			 # Developed on : 05/12/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getDateTraveringByMonth(boolean bool, int monthToBeTraversed){
				String getDate;
				try{
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
					cal.add(Calendar.MONTH, monthToBeTraversed);
					getDate=dateOnly.format(cal.getTime());
					if(bool){
						getDate=getDate.split("/")[0]+"/01/"+getDate.split("/")[2];
					}else{
						getDate=dateOnly.format(cal.getTime());
					}
				}catch(Throwable t){
					t.printStackTrace();
					getDate=null;
				}
				return getDate;
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getDateTraveringByMonth
			 # Description   : dayOfMonth as per user input, monthToBeTraversed need to be traversed
			 # Developed on : 02/13/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getDateTraveringByMonth(String dayOfMonth, int monthToBeTraversed){
				String getDate;
				try{
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
					cal.add(Calendar.MONTH, monthToBeTraversed);
					cal.add(Calendar.DAY_OF_MONTH,0);
					getDate=dateOnly.format(cal.getTime());
					if(dayOfMonth.length()==1){
						dayOfMonth="0"+String.valueOf(dayOfMonth);
					}
					getDate=getDate.split("/")[0]+"/"+dayOfMonth+"/"+getDate.split("/")[2];
				}catch(Throwable t){
					t.printStackTrace();
					getDate=null;
				}
				return getDate;
			}
		
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getDateDOB
			 # Description   : gate DOB based on year traversed, application exclusively for ongoing case
			 # Developed on : 05/24/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getDateDOB(int yearAddedOrSubtracted){
				String getDate;
				try{
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
					cal.add(Calendar.YEAR, yearAddedOrSubtracted);
					getDate=dateOnly.format(cal.getTime());
					int updatedMonth=Integer.parseInt(getDate.split("/")[0])-1;
					if (updatedMonth==0){
						updatedMonth=12;
					}
					getDate=String.valueOf(updatedMonth)+"/"+getDate.split("/")[1]+"/"+getDate.split("/")[2];
				}catch(Throwable t){
					t.printStackTrace();
					getDate=null;
				}
				return getDate;
			}
		
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getDateDOB1
			 # Description   : gate DOB based on year traversed, application exclusively for ongoing case
			 # Developed on : 05/24/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getDateDOB1(int yearAddedOrSubtracted,int daysDeviation){
				String getDate;
				try{
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
					cal.add(Calendar.YEAR, yearAddedOrSubtracted);
					cal.add(Calendar.DAY_OF_YEAR, daysDeviation);
					getDate=dateOnly.format(cal.getTime());
					getDate=getDate.split("/")[0]+"/"+getDate.split("/")[1]+"/"+getDate.split("/")[2];
				}catch(Throwable t){
					t.printStackTrace();
					getDate=null;
				}
				return getDate;
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getDateByYearTraversingFromToday1
			 # Description   : Return date based on user need to provide year to be added or subtracted from today's date and resultant date would be 3 days short, which is the final returned date
			 # Developed on : 05/14/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getDateByYearTraversingFromToday1(int yearAddedOrSubtracted, int daysDeviation){
				String getDate;
				try{
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
					cal.add(Calendar.YEAR, yearAddedOrSubtracted);
					cal.add(Calendar.DAY_OF_YEAR, daysDeviation);
					getDate=dateOnly.format(cal.getTime());
				}catch(Throwable t){
					t.printStackTrace();
					getDate=null;
				}
				return getDate;
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getDateTraveringByMonth
			 # Description   : Get date by adding or subtracting month(mm/dd/yyyy) from current date
			 # Developed on : 05/12/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getDateTraveringByMonth(int monthToBeTraversed){
				String getDate;
				try{
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
					cal.add(Calendar.MONTH, monthToBeTraversed);
					getDate=dateOnly.format(cal.getTime());
				}catch(Throwable t){
					t.printStackTrace();
					getDate=null;
				}
				return getDate;
			}
		
			public static boolean deleteFile(String pathOfFileToBeDeleted){
				boolean deleteFile=false;
				try{
					File file = new File(pathOfFileToBeDeleted);
					if(file.delete()){
						deleteFile=true;
					}
				}catch(Throwable t){
					deleteFile=false;
				}
				return deleteFile;
			}
		
		
			public static String getResponseTime(long startTime, long endTime ){
				long reponseTime = 0;
				try{
					if(Util.getProperty("ResponseTimeCheck").toUpperCase().equals("YES")){
						reponseTime=(endTime-startTime)/1000;
					}
					return String.valueOf(reponseTime);
				}catch(Throwable t){
					return null;
				}
			}
		
			public static long getTotalExecutionTime(long startTime, long endTime ){
				long reponseTime;
				try{
					reponseTime=(endTime-startTime)/1000;
					return reponseTime;
				}catch(Throwable t){
					return (Long) null;
				}
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getDateByYearTraversingFromToday
			 # Description   : Return date based on user need to provide year to be added or subtracted from today's date
			 # Developed on : 05/14/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getDateByYearTraversingFromToday(int yearAddedOrSubtracted){
				String getDate;
				try{
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
					cal.add(Calendar.YEAR, yearAddedOrSubtracted);
					getDate=dateOnly.format(cal.getTime());
				}catch(Throwable t){
					t.printStackTrace();
					getDate=null;
				}
				return getDate;
			}
		
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getDateBasedOnDayTraversing
			 # Description   : Return date based on user need to provide year to be added or subtracted from today's date
			 # Developed on : 05/14/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getDateBasedOnDayTraversing(int dayAddedOrSubtracted){
				String getDate;
				try{
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
					cal.add(Calendar.DAY_OF_MONTH, dayAddedOrSubtracted);
					getDate=dateOnly.format(cal.getTime());
				}catch(Throwable t){
					t.printStackTrace();
					getDate=null;
				}
				return getDate;
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getDateToday
			 # Description   : Return today's date
			 # Developed on : 05/15/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getDateToday(){
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
				return dateOnly.format(cal.getTime());
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getDollarSign
			 # Description   : Return $ sign
			 # Developed on : 05/15/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getDollarSign(){
				return "$";
			}
		
			/**
			 * ####################################################################################
			 * ##############################
			 * Keyword name: generateSSN
			 * Description: This keyword will generated SSN
			 * Developed on: 05/25/2020
			 * Author: Subrato Sarkar
			 * ####################################################################################
			 * ##############################
			 */
			public static String generateSSN(){
				String ssn=String.valueOf(891)+String.valueOf(getRandomNumber(6));
				return (ssn.substring(0, 3)+"-"+ssn.substring(3, 5)+"-"+ssn.substring(5, 9));
			}
		
			public static String getCurrentTimestamp(){
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				String strDate = sdf.format(cal.getTime());
				SimpleDateFormat sdf1 = new SimpleDateFormat();
				sdf1.applyPattern("MM/dd/yyyy HH:mm:ss");
				Date date = null;
				try {
					date = sdf1.parse(strDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String strDateNow=sdf1.format(date);
				return strDateNow;
			}
		
		
			public static void deleteDirectory(String directoryToDelete){
				try{
					File directory = new File(directoryToDelete);
					if(!directory.exists()){
						System.exit(0);
					}else{
						try{
							delete(directory);
						}catch(IOException e){
							e.printStackTrace();
							System.exit(0);
						}
					}
				}catch(Throwable t){
					t.printStackTrace();
				}
			}
		
			public static void delete(File file) throws IOException{
				if(file.isDirectory()){
					if(file.list().length==0){
						file.delete();
					}else{
						String[] files = file.list();
						for (String temp : files) {
							File fileDelete = new File(file, temp);
							delete(fileDelete);
						}
						if(file.list().length==0){
							file.delete();
						}
					}
				}else{
					file.delete();
				}
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : isPresent
			 #Parameter : Arraylist and value which is going to be tested for its presence
			 # Description   : 
			 # Developed on : 05/25/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static boolean isValidated(ArrayList list, String value){
				boolean isValidated = false;
				try{
					for (int i=0;i<list.size();i++){
						if(value!=""&&value!=" "&&value!="  "&&value!="   "&&value!="    "){
							if(list.get(i).toString().toUpperCase().equals(value.toUpperCase())){
								isValidated=true;
								break;
							}
						}else{
							isValidated=true;
						}
					}
				}catch(Throwable t){
					isValidated=false;
				}
				return isValidated;
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getMonthName
			 #Parameter : String date
			 # Description  :Get month by a given date
			 # Developed on : 05/30/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getMonthName(String date){
				try{
					int month=Integer.parseInt(date.split("/")[0]);
					String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
					Calendar cal = Calendar.getInstance();
					return monthName[month-1];
				}catch(Exception e){
					return ("Please enter a valid month in your input date!!");
				}
		
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getFirstDayOfCurrMonth
			 #Parameter : NA
			 # Description  :Get 1st day of current month
			 # Developed on : 05/04/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getFirstDayOfCurrMonth(){
				Calendar c=new GregorianCalendar();
				String month=String.valueOf(c.get(Calendar.MONTH)+1);
				String day="01";
				String year=String.valueOf(c.get(Calendar.YEAR));
				if(month.length()==1){
					month="0"+month;
				}
				return month+"/"+day+"/"+year;
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getMonthYear
			 #Parameter : date
			 # Description  :Get month and year, mm/yyyy
			 # Developed on : 05/19/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getMonthYear(String date){
				String month=date.split("/")[0];
				if(month.length()==1){
					month="0"+month;
				}
				String year=date.split("/")[2];
				return (month+"/"+year).trim();
			}
		
			public static String getCurrentMonth(){
				return getDateToday().split("/")[0];
			}
		
			public static String getCurrentYear(){
				return getDateToday().split("/")[2];
			}
		
			public static String getCurrYearMonth(){
				return getDateToday().split("/")[2]+"/"+getDateToday().split("/")[0];
			}
		
			public static String getCurrMonthYear(){
				return getDateToday().split("/")[0]+"/"+getDateToday().split("/")[2];
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getMonthYearTraversingByMonth
			 #Parameter : date
			 # Description  :Get month and year, mm/yyyy
			 # Developed on : 05/19/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getMonthYearTraversingByMonth(int monthToBeTraversed){
				String date=getDateTraveringByMonth(monthToBeTraversed);
				return date.split("/")[0]+"/"+date.split("/")[2];
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getYearMonthTraversingByMonth
			 #Parameter : date
			 # Description  :Get month and year, yyyy/mm
			 # Developed on : 05/19/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getYearMonthTraversingByMonth(int monthToBeTraversed){
				String date=getDateTraveringByMonth(monthToBeTraversed);
				return date.split("/")[2]+"/"+date.split("/")[0];
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getLastDayOfMonth
			 #Parameter : date
			 # Description  :Get last day of a month based on month traversing from current date
			 # Developed on : 05/14/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static String getLastDayOfMonth(int monthToBeTraversed) throws ParseException{
				try{
					Date today = new Date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(today);
					calendar.add(Calendar.MONTH, monthToBeTraversed+1);
					calendar.set(Calendar.DAY_OF_MONTH, 1);
					calendar.add(Calendar.DATE, -1);
					Date lastDayOfMonth = calendar.getTime();
					DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					return sdf.format(lastDayOfMonth);
				}catch(Exception e){
					return null;
				}
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : getDateDiffInDays
			 #Parameter : date
			 # Description  :Get date difference in days
			 # Developed on : 05/31/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static int getDateDiffInDays(String dateBeforeString, String dateAfterString){
				SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
				try {
					Date dateBefore = myFormat.parse(dateBeforeString);
					Date dateAfter = myFormat.parse(dateAfterString);
					long difference = dateAfter.getTime() - dateBefore.getTime();
					float daysBetween = (difference / (1000*60*60*24));
					return ((int) daysBetween);
				} catch (Exception e) {
					return 0;
				}
			}
		
			public static boolean isExistInArray(String[] arr, String toSearch){
				boolean isExist=false;
				for(int i=0;i<arr.length;i++){
					if(arr[i].toUpperCase().equals(toSearch.toUpperCase())){
						isExist=true;
					}
				}
				return isExist;
			}
		
		
			public static int getIndexOfArrElement(String[] arr, String toSearch){
				int count=0;
				for(int i=0;i<arr.length;i++){
					if(arr[i].contains(toSearch)){
						count=i;
					}
				}
				return count;
			}
		
			/*
			 ####################################################################################
			 ##############################
			 # Function Name : validateMonthYearGreaterThanCurr
			 #Parameter : date(MM/YYYY)
			 # Description  :To validate a input MM/YYYY is greater than current MM/YYYY, if Yes, then it return true, else it will return false
			 # Developed on : 05/31/2020
			 # Author : Subrato Sarkar
			 ####################################################################################
			 ##############################
			 */
			public static boolean validateMonthYearGreaterThanCurr(String dateToValidate){
				boolean greater=false;
				String today=getCurrMonthYear();
				if(Integer.parseInt(today.split("/")[1])<Integer.parseInt(dateToValidate.split("/")[1])){
					greater=true;
				}else if(Integer.parseInt(today.split("/")[1])==Integer.parseInt(dateToValidate.split("/")[1])){
					if(Integer.parseInt(today.split("/")[0])<Integer.parseInt(dateToValidate.split("/")[0])){
						greater=true;
					}
				}else{
					greater=false;
				}
				return greater;
			}
		
			/*####################################################################################
			 ##############################
			 # Function Name : getDateTraveringByMonth
			 # Description   : Get date by adding or subtracting month(mm/dd/yyyy) from first of date
			 # Developed on : 05/12/2020
			 # Author : USI
			 ####################################################################################
			 ##############################
			 */
			public static String getDateTraversingByMonth(int monthToBeTraversed){
				String getDate;
				try{
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
					cal.add(Calendar.MONTH, monthToBeTraversed);
					cal.set(Calendar.DAY_OF_MONTH, 1);
					getDate=dateOnly.format(cal.getTime());
				}catch(Throwable t){
					t.printStackTrace();
					getDate=null;
				}
				return getDate;
			}
		
		
			public static void terminateChromeDriverExe() throws IOException{
				if(System.getProperty("os.name").contains("Windows")) {
					Process process = Runtime. getRuntime(). exec("taskkill /F /IM chromedriver.exe");
					process.destroy();
				}
			}
		
		
			public static String decrypt(String strToDecrypt){
				try{
					byte[] decoded = Base64.getDecoder().decode(strToDecrypt.getBytes(StandardCharsets.UTF_8));
					String encryptedString = new String(decoded);
					return encryptedString;
				}catch(Throwable t){
					return null;
				}
			}
			
			public static String encrypt(String strToEncrypt){
		    	try{
		    		byte[] encoded = Base64.getEncoder().encode(strToEncrypt.getBytes(StandardCharsets.UTF_8));
		        	String base64Encoded = new String(encoded);
		    		return base64Encoded;
		    	}catch(Throwable t){
		    		return null;
		    	}
		    }
		
			public static void zipMe() throws IOException{
				if(Util.getProperty("RunOnJenkins").toUpperCase().equals("YES")){
					try{
						String path=System.getProperty("user.dir")+"/zipme.jar";
						Runtime.getRuntime().exec("cmd /c start "+path );
					}catch(Throwable t){
						t.printStackTrace();
					}
				}
			}
		
			public static int getResponseCode(String urlToTest) {
				int reponseCode = 0;
				try {
					URL url = new URL(urlToTest);
					HttpURLConnection http = (HttpURLConnection)url.openConnection();
					reponseCode = http.getResponseCode();
				}catch(Throwable t) {
					t.printStackTrace();
				}
				return reponseCode;
			}
			
			
		/*
		 ####################################################################################
		 ##############################
		 # FunctionName : pause
		 # No of Parameter : 1
		 # Description : Hard wait
		 # Developed on : 04/23/2019
		 # Developed By : Subrato
		 ####################################################################################
		 #############################
		 */
			public static void pause(double x){
				try{
					Thread.sleep((long) (x*500L));
				}catch(Throwable t){
					t.printStackTrace();
				}
			}
			
	}
