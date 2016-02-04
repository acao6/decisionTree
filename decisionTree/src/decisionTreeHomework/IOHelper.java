package decisionTreeHomework;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class IOHelper<Type extends Serializable> {
	
	@SuppressWarnings("unchecked")
	public Type read(String filename){
		Type data = null;

		// Did I ever mention that writing exception-safe code is a pain?
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		try {
			fileIn = new FileInputStream(filename);
			in = new ObjectInputStream(fileIn);
			data = (Type)in.readObject();
		} catch (ClassNotFoundException	e) {
			throw new RuntimeException("Class not found!");	
		} catch (IOException e) {
			throw new RuntimeException("File not found!");	
		} finally {
			try {
				if (fileIn != null) {
					fileIn.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("File not found!");
			}
		}
		return data;
	}
	
	public void writeFile(Type object, String filename) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(object);
			out.close();
			fileOut.close();
		} catch(IOException e) {
		}
	}
}
