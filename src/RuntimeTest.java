


public class RuntimeTest {
   public static void main(String[] args) throws Exception {
	   //获取当前应用程序的 运行时环境
	   Runtime runtime = Runtime.getRuntime();
	   // 开启一个新的线程用于打开 Server.java文件
	   // start 打开文件   type 读取文件内容
	   Process process = runtime.exec("cmd /c type \\Server.java");
	  
	   /*为"错误输出流"单独开一个线程读取之,否则会造成标准输出流的阻塞*/  
	   StreamGobbler errorStream = new StreamGobbler(process.getErrorStream(), "error");
       errorStream.start();
       /*"标准输出流"就在当前方法中读取*/ 
	   StreamGobbler standardStream = new StreamGobbler(process.getInputStream(), "standard");
	   standardStream.start();
	   /**  java.lang.Process.waitFor()方法将导致当前的线程等待，如果必要的话，
	    *   直到由该Process对象表示的进程已经终止。如果子进程已经终止,此方法将立即返回。
	    *   如果子进程尚未终止，则调用线程将被阻塞，直到子进程退出。
	    */
       process.waitFor();
   }
 }
