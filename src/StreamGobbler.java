import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
/** 
 * 用于处理Runtime.getRuntime().exec产生的错误流及输出流 
 */
public class StreamGobbler extends Thread {
    private InputStream in;//输入流对象
    private String type;//输入流类型
    private OutputStream out;//输出流对象
    
	public StreamGobbler() {
	}
   
	public StreamGobbler(InputStream in, String type) {
		this(in, type, null);
	}
	 // in Process的输入流对象  type 该输入流对象的作用 out Process的输出流对象
	public StreamGobbler(InputStream in, String type, OutputStream out) {
		this.in = in;
		this.type = type;
		this.out = out;
	}


	@Override
	public void run() {
		InputStreamReader isr = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			if(out!=null){
				pw = new PrintWriter(out);
			}
			in.available();
		    isr = new InputStreamReader(in,"UTF-8");
			br = new BufferedReader(isr);
			String line;
			while((line=br.readLine())!=null){
				System.out.println(type +" >> "+line);
				if(pw!=null){
					pw.write(line);
				}
			}
			if(pw!=null){
				pw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				isr.close();
				if(pw!=null){
					pw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
   
}
