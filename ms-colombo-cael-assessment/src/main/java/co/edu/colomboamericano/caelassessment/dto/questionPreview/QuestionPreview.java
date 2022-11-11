package co.edu.colomboamericano.caelassessment.dto.questionPreview;

public class QuestionPreview {
	
	private int code;
	private String status;
	private Data data;
	
	public QuestionPreview() {}
	
	public QuestionPreview(int code, String status, Data data) {
		super();
		this.code = code;
		this.status = status;
		this.data = data;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	
}
