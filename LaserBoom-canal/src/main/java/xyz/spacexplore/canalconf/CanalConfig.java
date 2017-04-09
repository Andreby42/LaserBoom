package xyz.spacexplore.canalconf;

public class CanalConfig {
	private String mysqlpwd;
	private String mysqlusername;
	private String destination;

	public String getMysqlpwd() {
		return mysqlpwd;
	}

	public void setMysqlpwd(String mysqlpwd) {
		this.mysqlpwd = mysqlpwd;
	}

	public String getMysqlusername() {
		return mysqlusername;
	}

	public void setMysqlusername(String mysqlusername) {
		this.mysqlusername = mysqlusername;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "CanalConfig [mysqlpwd=" + mysqlpwd + ", mysqlusername=" + mysqlusername + ", destination=" + destination
				+ "]";
	}
}
