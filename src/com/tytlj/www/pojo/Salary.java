package com.tytlj.www.pojo;

public class Salary {

	private String id;
	private String employeeId;// 员工编号
	private String employeeName;// 员工姓名
	private String employeeJob;// 员工职务
	private int baseSalary;// 绩效薪标准
	private String deptCode;// 部门编码
	private double coefficient;// 系数
	private double tran_price;// 转运办理列单价
	private double translocation = 0;// 转运办理列

	private double load_price = 0;// 装卸单价
	private double load = 0;// 装卸

	private double brig_price = 0;// 旅发人数单价
	private double brigades = 0;// 旅发人数

	private double pass_price = 0;// 客运收入单价
	private double passenger = 0;// 客运收入

	private double frei_price = 0;// 货运收入单价
	private double freight = 0;// 货运收入

	private double trans_price = 0;// 运输收入单价
	private double transport = 0;// 运输收入
	private String examineType;// 上线或者基本
	private int examine;// 上线或者基本扣除的数
	private int bgRed;// 大红
	private int red;// 红
	private int yellow;// 黄
	private double assessmentValue;// 日常考核扣除数
	private double assessment;// 日常考试，百分比
	private double payroll;// 实发工资
	private int isPost;// 是否已经提交数据
	private String month;// 年月
	private String remark;

	public Salary() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeJob() {
		return employeeJob;
	}

	public void setEmployeeJob(String employeeJob) {
		this.employeeJob = employeeJob;
	}

	public int getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(int baseSalary) {
		this.baseSalary = baseSalary;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}

	public double getTran_price() {
		return tran_price;
	}

	public void setTran_price(double tran_price) {
		this.tran_price = tran_price;
	}

	public double getLoad_price() {
		return load_price;
	}

	public void setLoad_price(double load_price) {
		this.load_price = load_price;
	}

	public double getBrig_price() {
		return brig_price;
	}

	public void setBrig_price(double brig_price) {
		this.brig_price = brig_price;
	}

	public double getPass_price() {
		return pass_price;
	}

	public void setPass_price(double pass_price) {
		this.pass_price = pass_price;
	}

	public double getFrei_price() {
		return frei_price;
	}

	public void setFrei_price(double frei_price) {
		this.frei_price = frei_price;
	}

	public double getTrans_price() {
		return trans_price;
	}

	public void setTrans_price(double trans_price) {
		this.trans_price = trans_price;
	}

	public double getTranslocation() {
		return translocation;
	}

	public void setTranslocation(double translocation) {
		this.translocation = translocation;
	}

	public double getLoad() {
		return load;
	}

	public void setLoad(double load) {
		this.load = load;
	}

	public double getBrigades() {
		return brigades;
	}

	public void setBrigades(double brigades) {
		this.brigades = brigades;
	}

	public double getPassenger() {
		return passenger;
	}

	public void setPassenger(double passenger) {
		this.passenger = passenger;
	}

	public double getFreight() {
		return freight;
	}

	public void setFreight(double freight) {
		this.freight = freight;
	}

	public double getTransport() {
		return transport;
	}

	public void setTransport(double transport) {
		this.transport = transport;
	}

	public int getExamine() {
		return examine;
	}

	public void setExamine(int examine) {
		this.examine = examine;
	}

	public double getPayroll() {
		return payroll;
	}

	public void setPayroll(double payroll) {
		this.payroll = payroll;
	}

	public int getIsPost() {
		return isPost;
	}

	public void setIsPost(int isPost) {
		this.isPost = isPost;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getAssessment() {
		return assessment;
	}

	public void setAssessment(double assessment) {
		this.assessment = assessment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExamineType() {
		return examineType;
	}

	public void setExamineType(String examineType) {
		this.examineType = examineType;
	}

	public int getBgRed() {
		return bgRed;
	}

	public void setBgRed(int bgRed) {
		this.bgRed = bgRed;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getYellow() {
		return yellow;
	}

	public void setYellow(int yellow) {
		this.yellow = yellow;
	}

	public double getAssessmentValue() {
		return assessmentValue;
	}

	public void setAssessmentValue(double assessmentValue) {
		this.assessmentValue = assessmentValue;
	}

	@Override
	public String toString() {
		return "Salary [id=" + id + ", employeeId=" + employeeId
				+ ", employeeName=" + employeeName + ", employeeJob="
				+ employeeJob + ", baseSalary=" + baseSalary + ", deptCode="
				+ deptCode + ", coefficient=" + coefficient + ", tran_price="
				+ tran_price + ", translocation=" + translocation
				+ ", load_price=" + load_price + ", load=" + load
				+ ", brig_price=" + brig_price + ", brigades=" + brigades
				+ ", pass_price=" + pass_price + ", passenger=" + passenger
				+ ", frei_price=" + frei_price + ", freight=" + freight
				+ ", trans_price=" + trans_price + ", transport=" + transport
				+ ", examineType=" + examineType + ", examine=" + examine
				+ ", bgRed=" + bgRed + ", red=" + red + ", yellow=" + yellow
				+ ", assessment=" + assessment + ", payroll=" + payroll
				+ ", isPost=" + isPost + ", month=" + month + ", remark="
				+ remark + "]";
	}

}
