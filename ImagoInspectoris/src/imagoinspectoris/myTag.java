package imagoinspectoris;

/**
 *
 * @author 0506344
 */
public class myTag implements java.io.Serializable{
    
	private static final long serialVersionUID = 69L;
        
	private String tagName, fileLocation, fileName;

	public myTag(String tagName, String fileLocation, String fileName ) {
		this.tagName = tagName;
		this.fileLocation = fileLocation;
                this.fileName = fileName;
	}
        @Override
	public String toString(){
            return fileName;
        }
        
        public String getTagName(){
		return tagName;
	}

	public String getFileLocation(){
		return fileLocation;
	}

        public String getFileName(){
                return fileName;    
        }

	public void setTagName(String t){
		this.tagName = t;
	}

        public void setFileLocation(String s){
		this.fileLocation = s;
	}
        
        public void setFileName(String n){
                this.fileName = n;
        }
        
}
